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

package edu.cnm.deepdive.truealarm.controller.ui.alarmdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.controller.DateTimePickerFragment;
import edu.cnm.deepdive.truealarm.controller.DateTimePickerFragment.Mode;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import edu.cnm.deepdive.truealarm.model.entity.Location;
import edu.cnm.deepdive.truealarm.viewmodel.AlarmDetailViewModel;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * * AlarmDetailFragment is a detailed view at the elements of a single Alarm.
 *  * Here the User can edit the Name, Buffer Time, Arrival Time, Start/End Locations of an alarm.
 *  * This is also where the User is taken to create a new alarm.
 */
public class AlarmDetailFragment extends Fragment implements OnMapReadyCallback,
    DateTimePickerFragment.OnChangeListener {

  public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
  private static final String ID_KEY = "alarm_id";

  private DateFormat timeFormat;
  private Calendar calendar;
  private long alarmId;
  private AlarmDetailViewModel alarmDetailViewModel;
  private Location location;
  private Alarm alarm;
  private MapView mapView;
  private EditText arrivalTimeSelected;
  private EditText departAddress;
  private EditText arriveAddress;
  private EditText editName;
  private TextView alarmName;
  private EditText bufferTime;
  private TextView commuteTime;
  private AlertDialog dialog;

  /**
   *
   * @param savedInstanceState
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    alarmId = AlarmDetailFragmentArgs.fromBundle(getArguments()).getAlarmId();

  }

  /**
   * General setup for the connecting the AlarmDetail layout with the controller and viewmodel.
   * Inflates the fragment alarm detail layout and sets up the {@link DateTimePickerFragment} for selecting
   * an arrival time. It also enables the Save button functionality saving any edited fields to the database.
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_alarm_detail, container, false);
    mapView = view.findViewById(R.id.route_map);
    arrivalTimeSelected = view.findViewById(R.id.arrival_time_selected);
    arrivalTimeSelected.setOnClickListener((v) -> {
      DateTimePickerFragment fragment = DateTimePickerFragment.createInstance(Mode.TIME, calendar);
      fragment.show(getChildFragmentManager(), fragment.getClass().getName());
    });

    editName = view.findViewById(R.id.edit_name);
    bufferTime = view.findViewById(R.id.buffer_minutes_selected);
    departAddress = view.findViewById(R.id.depart_address);
    arriveAddress = view.findViewById(R.id.arrival_address);

    calendar = Calendar.getInstance();

    // commuteTime = //TODO Add commutetime logic

    view.findViewById(R.id.save).setOnClickListener((v) -> {
      alarm.setName(editName.getText().toString().trim());
      String bufferText = bufferTime.getText().toString().trim();
      alarm.setBuffer(bufferText.isEmpty() ? 0 : Integer.parseInt(bufferText));
      alarm.setArriveBy(calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE));
//      alarm.setStartLocationId();
//      alarm.setEndLocationId();
      alarmDetailViewModel.saveAlarm(alarm);
      NavDirections action = AlarmDetailFragmentDirections.returnHome();
      Navigation.findNavController(getView()).navigate(action);
    });



    initGoogleMap(savedInstanceState);
    return view;
  }


  private void initGoogleMap(Bundle savedInstanceState) {
    // *** IMPORTANT ***
    // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
    // objects or sub-Bundles.
    Bundle mapViewBundle = null;
    if (savedInstanceState != null) {
      mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
    }
    mapView.onCreate(mapViewBundle);

    mapView.getMapAsync(this);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
    if (mapViewBundle == null) {
      mapViewBundle = new Bundle();
      outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
    }

    mapView.onSaveInstanceState(mapViewBundle);
  }

  @Override
  public void onChange(Calendar calendar) {
    this.calendar = calendar;
    arrivalTimeSelected.setText(timeFormat.format(calendar.getTime()));
  }

  /**
   * The code that controls whether or not the AlarmDetail view is importing data from an existing alarm with an ID
   * or if it is creating a new alarm from scratch.
   */
  @Override
  public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
    super.onViewCreated(view, saveInstanceState);
    timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
    alarmDetailViewModel = new ViewModelProvider(getActivity()).get(AlarmDetailViewModel.class);
    if (alarmId != 0) {
      alarmDetailViewModel.getAlarm().observe(getViewLifecycleOwner(), (alarm) -> {
        this.alarm = alarm;
        editName.setText(alarm.getName());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getArriveBy() / 60);
        calendar.set(Calendar.MINUTE, alarm.getArriveBy() % 60);
        arrivalTimeSelected.setText(timeFormat.format(calendar.getTime()));
        bufferTime.setText(String.valueOf(alarm.getBuffer()));
        //TODO Figure out alarm location id vs string addresses
      });
      alarmDetailViewModel.setAlarmId(alarmId);
    } else {
      alarm = new Alarm();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  public void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onMapReady(GoogleMap map) {
    map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
  }

  @Override
  public void onPause() {
    mapView.onPause();
    super.onPause();
  }

  @Override
  public void onDestroy() {
    mapView.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }


}
