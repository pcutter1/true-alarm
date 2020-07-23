package edu.cnm.deepdive.truealarm.controller.ui.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.viewmodel.AlarmDetailViewModel;

public class AlarmDetailFragment extends Fragment {

  private AlarmDetailViewModel alarmDetailViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    alarmDetailViewModel =
        ViewModelProviders.of(this).get(AlarmDetailViewModel.class);
    View root = inflater.inflate(R.layout.fragment_alarm_detail, container, false);
    final TextView textView = root.findViewById(R.id.select_arrival_time_button);
    alarmDetailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        textView.setText(s);
      }
    });
    return root;
  }
}
