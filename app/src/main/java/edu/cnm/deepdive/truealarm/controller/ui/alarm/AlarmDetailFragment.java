package edu.cnm.deepdive.truealarm.controller.ui.alarm;

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
import edu.cnm.deepdive.truealarm.view.AlarmAdapter.OnEditListener;
import edu.cnm.deepdive.truealarm.viewmodel.AlarmDetailViewModel;
import java.util.Calendar;

public class AlarmDetailFragment extends Fragment implements OnMapReadyCallback,
    DateTimePickerFragment.OnChangeListener {

  public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
  private static final String ID_KEY = "alarm_id";

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


  public static AlarmDetailFragment newInstance(long alarmId) {
    AlarmDetailFragment fragment = new AlarmDetailFragment();
    Bundle args = new Bundle();
    args.putLong(ID_KEY, alarmId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      alarmId = getArguments().getLong(ID_KEY, 0);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_alarm_detail, container, false);
    mapView = view.findViewById(R.id.route_map);
    arrivalTimeSelected = view.findViewById(R.id.arrival_time_selected);
    arrivalTimeSelected.setOnClickListener((v) -> {
      Calendar calendar = Calendar.getInstance(); //TODO Needs to come from what we're editing
      DateTimePickerFragment fragment = DateTimePickerFragment.createInstance(Mode.TIME, calendar);
      fragment.show(getChildFragmentManager(), fragment.getClass().getName());
    });

    editName = view.findViewById(R.id.edit_name);
    bufferTime = view.findViewById(R.id.buffer_minutes_selected);
    departAddress = view.findViewById(R.id.depart_address);
    arriveAddress = view.findViewById(R.id.arrival_address);

    // commuteTime = //TODO Add commutetime logic

    view.findViewById(R.id.save).setOnClickListener((v) -> {
      Alarm alarm = new Alarm();
      alarm.setName(editName.getText().toString().trim());
      alarm.setBuffer(Integer.parseInt(bufferTime.getText().toString().trim()));
      alarm.setArriveBy(Integer.parseInt(arrivalTimeSelected.getText().toString().trim()));
//      alarm.setStartLocationId();
//      alarm.setEndLocationId();
      alarmDetailViewModel.saveAlarm(alarm);
      //TODO after save, navigate back to list of alarms
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
    //TODO update time in current alarm
  }


  @Override
  public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
    super.onViewCreated(view, saveInstanceState);
    alarmDetailViewModel = new ViewModelProvider(getActivity()).get(AlarmDetailViewModel.class);
    if (alarmId != 0) {
      alarmDetailViewModel.getAlarm().observe(getViewLifecycleOwner(), (alarm) -> {
        this.alarm = alarm;
        editName.setText(alarm.getName());
        arrivalTimeSelected.setText(alarm.getArriveBy());
        bufferTime.setText(alarm.getBuffer());
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
