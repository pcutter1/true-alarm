package edu.cnm.deepdive.truealarm.controller.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;


public class SettingsFragment extends Fragment implements OnItemSelectedListener {

  private SettingsViewModel settingsViewModel;
  private Spinner spinner;


  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    settingsViewModel =
        ViewModelProviders.of(this).get(SettingsViewModel.class);
    View root = inflater.inflate(R.layout.fragment_settings, container, false);
    final TextView textView = root.findViewById(R.id.settings_title);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        SettingsFragment.this.getContext(), R.array.alarms_array, android.R.layout.simple_spinner_item
    );
//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//       spinner.setAdapter(adapter);
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

  @Override
  public void onNothingSelected(AdapterView<?> parent) {}

}
