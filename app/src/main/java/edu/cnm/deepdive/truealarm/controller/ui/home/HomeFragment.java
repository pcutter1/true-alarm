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

package edu.cnm.deepdive.truealarm.controller.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.controller.ui.home.HomeFragmentDirections.EditDetails;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import edu.cnm.deepdive.truealarm.view.AlarmAdapter;
import edu.cnm.deepdive.truealarm.viewmodel.HomeViewModel;

/**
 * This is the home screen for the application. From here you view a list of all currently existing alarms
 * which are displayed with a recyclerview. There is a floating action button which sends the User to
 * the AlarmDetails fragment where they can create a new alarm.
 */

public class HomeFragment extends Fragment {

  AlarmManager alarmManager;
  private RecyclerView alarmList;
  private HomeViewModel homeViewModel;
  private PendingIntent pendingIntent;

  /**
   * Current setup code which shows an alarm in its set layout enables an edit button on each alarm
   */
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    alarmList = view.findViewById(R.id.alarm_recycler_view);
    view.findViewById(R.id.add_alarm).setOnClickListener((v) -> editAlarm(0));
//    view.findViewById(R.id.delete).setOnClickListener((v) -> deleteAlarm);
    return view;
  }

  /**
   * Sends the user to the {@link edu.cnm.deepdive.truealarm.controller.ui.alarmdetail.AlarmDetailFragment}
   * and uses the @param id of the current alarm so that the proper data is pulled to be edited.
   */

  private void editAlarm(long id) {
    EditDetails action = HomeFragmentDirections.editDetails();
    action.setAlarmId(id);
    Navigation.findNavController(getView()).navigate(action);
  }

 // private void delete(long id) {
 // }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    homeViewModel = new ViewModelProvider(getActivity())
        .get(HomeViewModel.class);
    homeViewModel.getAlarms().observe(getViewLifecycleOwner(), (alarms) -> {
      if (alarms != null) {
        alarmList.setAdapter(new AlarmAdapter(getContext(), alarms, (alarm) -> editAlarm(alarm.getId()),
            (alarm) -> homeViewModel.deleteAlarm(alarm)));
      }
    });
  }

}
