package edu.cnm.deepdive.truealarm.controller;

import android.app.TimePickerDialog;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.maps.android.data.geojson.GeoJsonPoint;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.service.PermissionsService;
import edu.cnm.deepdive.truealarm.viewmodel.HomeViewModel;
import java.util.Calendar;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

  Button arrivalTimePicker;
  EditText arrivalTime;
  private int mHour;
  private int mMinute;

  private static final int PERMISSIONS_REQUEST_CODE = 999;
  private final PermissionsService permissionsService = PermissionsService.getInstance();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    checkPermissionsOnce();


    //TODO setup observe throwable in viewmodel and display toast

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_home, R.id.navigation_alarm_detail, R.id.navigation_settings)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == PERMISSIONS_REQUEST_CODE) {
      permissionsService.updatePermissions(permissions, grantResults);
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }


  private void checkPermissionsOnce() {
    HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    // Observe a flag indicating whether permissions have been checked previously.
    viewModel.getPermissionsChecked().observe(this, (checked) -> {
      // If permissions have not yet been checked, do so; then set the flag accordingly.
      if (!checked) {
        viewModel.setPermissionsChecked(true);
        permissionsService.checkPermissions(this, PERMISSIONS_REQUEST_CODE);
      }
    });
  }


  }

