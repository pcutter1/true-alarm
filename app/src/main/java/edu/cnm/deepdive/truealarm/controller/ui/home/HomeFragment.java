package edu.cnm.deepdive.truealarm.controller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.view.AlarmAdapter;
import edu.cnm.deepdive.truealarm.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

  private RecyclerView alarmList;
  private HomeViewModel homeViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    alarmList = view.findViewById(R.id.alarm_recycler_view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    homeViewModel = new ViewModelProvider(getActivity())
        .get(HomeViewModel.class);
    homeViewModel.getAlarms().observe(getViewLifecycleOwner(), (alarms) -> {
      if (alarms != null) {
        alarmList.setAdapter(new AlarmAdapter(getContext(), alarms));
      }
    });
  }
}
