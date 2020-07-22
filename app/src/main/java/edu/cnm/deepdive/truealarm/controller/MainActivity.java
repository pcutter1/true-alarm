package edu.cnm.deepdive.truealarm.controller;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import edu.cnm.deepdive.truealarm.R;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

  Button arrivalTimePicker;
  EditText arrivalTime;
  private int mHour;
  private int mMinute;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //TODO Figure out ActivityMain layout issue
//    arrivalTimePicker = findViewById(R.id.select_arrival_time_button);
//    arrivalTime = findViewById(R.id.arrival_time_selected);
//
//    arrivalTimePicker.setOnClickListener(this);

    BottomNavigationView navView = findViewById(R.id.nav_view);
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_home, R.id.navigation_alarm_detail, R.id.navigation_settings)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(navView, navController);
  }

//    @Override
//    public void onClick(View v) {
//
//      if (v == arrivalTimePicker) {
//
//        final Calendar c = Calendar.getInstance();
//        mHour = c.get(Calendar.HOUR_OF_DAY);
//        mMinute = c.get(Calendar.MINUTE);
//
//        // Launch Time Picker Dialog
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//            (view, hourOfDay, minute) ->
//                arrivalTime.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
//        timePickerDialog.show();
//      }
//    }



  }

