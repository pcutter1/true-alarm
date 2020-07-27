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
import edu.cnm.deepdive.truealarm.view.AlarmAdapter;
import edu.cnm.deepdive.truealarm.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

  AlarmManager alarmManager;
  private RecyclerView alarmList;
  private HomeViewModel homeViewModel;
  private PendingIntent pendingIntent;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    alarmList = view.findViewById(R.id.alarm_recycler_view);
    view.findViewById(R.id.add_alarm).setOnClickListener((v) -> editAlarm(0));
    //   view.findViewById(R.id.delete).setOnClickListener((v) -> deleteAlarm);
    return view;
  }

  private void editAlarm(long id) {
    EditDetails action = HomeFragmentDirections.editDetails();
    action.setAlarmId(id);
    Navigation.findNavController(getView()).navigate(action);
  }

//  private void deleteAlarm(long id) {
//  }

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
