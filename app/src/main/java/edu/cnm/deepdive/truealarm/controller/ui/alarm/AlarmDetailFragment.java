package edu.cnm.deepdive.truealarm.controller.ui.alarm;

import static edu.cnm.deepdive.truealarm.util.Constants.MAPVIEW_BUNDLE_KEY;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.viewmodel.AlarmDetailViewModel;

public class AlarmDetailFragment extends Activity implements OnMapReadyCallback {

  private AlarmDetailViewModel alarmDetailViewModel;
  private MapView mapView;
  private FusedLocationProviderClient fusedLocationProviderClient;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_alarm_detail, container, false);
    mapView = view.findViewById(R.id.route_map);
    //final TextView textView = view.findViewById(R.id.select_arrival_time_button);
//    alarmDetailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//      @Override
//      public void onChanged(@Nullable String s) {
//        textView.setText(s);
//      }
//    });

    initGoogleMap(savedInstanceState);
    return view;
  }

//  private void getLastKnownLocation() {
//    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,
//        new OnSuccessListener<Location>() {
//          @Override
//          public void onSuccess(Location location) {
//            LatLng lastLocale = new LatLng(location.getLatitude(), location.getLongitude());
//          }
//        });
//  }


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
