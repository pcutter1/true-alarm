/*
 * <!--
 *   Copyright 2020 Paul Cutter
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0>
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * -->
 */

package edu.cnm.deepdive.truealarm.service;

import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.truealarm.R;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PermissionsService contains a set of methods to check for, request, and update permissions needed
 * to run this application.
 */
public class PermissionsService {

  private final MutableLiveData<Set<String>> permissions;

  private PermissionsService() {
    permissions = new MutableLiveData<>(Collections.emptySet());
  }

  /**
   *
   * @return an InstanceHolder
   */
  public static PermissionsService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   *
   * @return a LiveData set of permissions in String format
   */
  public LiveData<Set<String>> getPermissions() {
    return permissions;
  }

  /**
   * The checkPermissions method is aptly named. It uses an activity and a requestCode as parameters
   * and creates a LinkedList of permissions to request. If a required permission is not granted, it prompts
   * an explanation that the application needs that specific permission and asks again. If it is declined a second
   * time, it will no longer ask for that permission.
   * @param activity
   * @param requestCode
   */
  public void checkPermissions(AppCompatActivity activity, int requestCode) {
    try {
      PackageInfo info = activity.getPackageManager().getPackageInfo(
          activity.getPackageName(), PackageManager.GET_META_DATA | PackageManager.GET_PERMISSIONS);
      String[] permissions = info.requestedPermissions;
      Set<String> permissionsGranted = new HashSet<>();
      List<String> permissionsToRequest = new LinkedList<>();
      List<String> permissionsToExplain = new LinkedList<>();
      for (String permission : permissions) {
        if (ContextCompat.checkSelfPermission(activity, permission)
            != PackageManager.PERMISSION_GRANTED) {
          permissionsToRequest.add(permission);
          if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            permissionsToExplain.add(permission);
          }
        } else {
          permissionsGranted.add(permission);
        }
      }
      updatePermissions(permissionsGranted);
      if (!permissionsToExplain.isEmpty()) {
        PermissionsFragment fragment =
            PermissionsFragment.createInstance(permissionsToExplain.toArray(new String[0]),
                permissionsToRequest.toArray(new String[0]), requestCode);
        fragment.show(activity.getSupportFragmentManager(), fragment.getClass().getName());
      } else if (!permissionsToRequest.isEmpty()) {
        ActivityCompat.requestPermissions(
            activity, permissionsToRequest.toArray(new String[0]), requestCode);
      }
    } catch (NameNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * updatePermissions method takes parameters of permissions and an array of ints called grants.
   * It iterates through the positions of the granted permissions and, if they are granted, adds them
   * to the list of working permissions. Otherwise, it removes an ungranted permission from the list.
   * @param permissions
   * @param grants
   */
  public void updatePermissions(@NonNull String[] permissions, @NonNull int[] grants) {
    //noinspection ConstantConditions
    Set<String> workingPermissions = new HashSet<>(this.permissions.getValue());
    for (int i = 0; i < permissions.length; i++) {
      if (grants[i] == PackageManager.PERMISSION_GRANTED) {
        workingPermissions.add(permissions[i]);
      } else {
        workingPermissions.remove(permissions[i]);
      }
    }
    updatePermissions(workingPermissions);
  }

  private void updatePermissions(Set<String> permissions) {
    if (!Objects.equals(this.permissions.getValue(), permissions)) {
      this.permissions.setValue(Collections.unmodifiableSet(permissions));
    }
  }

  private void explainPermissions(AppCompatActivity activity,
      String[] permissionsToExplain, String[] permissionsToRequest, int requestCode) {
    PermissionsFragment fragment =
        PermissionsFragment.createInstance(permissionsToExplain, permissionsToRequest, requestCode);
    fragment.show(activity.getSupportFragmentManager(), fragment.getClass().getName());
  }

  private static class InstanceHolder {

    private static final PermissionsService INSTANCE = new PermissionsService();

  }

  /**
   * PermissionsFragment class contains the methods used to pop up a dialog that
   * requests permissions and explains why permissions are requested if they are not initially accepted.
   */
  public static class PermissionsFragment extends DialogFragment {

    private static final String PERMISSIONS_TO_EXPLAIN_KEY = "permissions_to_explain";
    private static final String PERMISSIONS_TO_REQUEST_KEY = "permissions_to_request";
    private static final String REQUEST_CODE_KEY = "request_code";
    private static final String EXPLANATION_KEY_SUFFIX = "_explanation";
    private static final String PERMISSION_DELIMITER = "\\.";

    @NonNull
    private static PermissionsFragment createInstance(@NonNull String[] permissionsToExplain,
        String[] permissionsToRequest, int requestCode) {
      if (permissionsToExplain.length == 0) {
        throw new IllegalArgumentException();
      }
      Bundle args = new Bundle();
      args.putStringArray(PERMISSIONS_TO_EXPLAIN_KEY, permissionsToExplain);
      args.putStringArray(PERMISSIONS_TO_REQUEST_KEY, permissionsToRequest);
      args.putInt(REQUEST_CODE_KEY, requestCode);
      PermissionsFragment fragment = new PermissionsFragment();
      fragment.setArguments(args);
      return fragment;
    }

    /**
     * This method contains the code that actually builds the dialog to prompt for permission requests
     * and permission explanations. It takes a savedInstanceState parameter and returns a dialog built
     * for a specific permission as it is being requested or explained.
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      Bundle args = (getArguments() != null) ? getArguments() : new Bundle();
      String[] permissionsToExplain = args.getStringArray(PERMISSIONS_TO_EXPLAIN_KEY);
      String[] permissionsToRequest = args.getStringArray(PERMISSIONS_TO_REQUEST_KEY);
      int requestCode = args.getInt(REQUEST_CODE_KEY);
      //noinspection ConstantConditions
      return new AlertDialog.Builder(getContext())
          .setIcon(android.R.drawable.ic_dialog_info)
          .setTitle(R.string.permissions_dialog_title)
          .setMessage(buildMessage(permissionsToExplain))
          .setNeutralButton(android.R.string.ok, (dlg, which) ->
              ActivityCompat.requestPermissions(getActivity(),
                  (permissionsToRequest != null) ? permissionsToRequest : new String[0],
                  requestCode))
          .create();
    }

    private String buildMessage(String[] permissionsToExplain) {
      //noinspection ConstantConditions
      String packageName = getContext().getPackageName();
      Resources res = getResources();
      return Arrays.stream((permissionsToExplain != null) ? permissionsToExplain : new String[0])
          .map((s) -> {
            String[] parts = s.split(PERMISSION_DELIMITER);
            String name = parts[parts.length - 1];
            String key = name.toLowerCase() + EXPLANATION_KEY_SUFFIX;
            int explanationId = res.getIdentifier(key, "string", packageName);
            return (explanationId != 0) ? getString(explanationId) : name;
          })
          .collect(Collectors.joining("\n"));
    }

  }


}
