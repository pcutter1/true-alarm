package edu.cnm.deepdive.truealarm.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import edu.cnm.deepdive.truealarm.service.AlarmRepository;
import edu.cnm.deepdive.truealarm.service.LocationRepository;
import java.util.List;

public class SettingsViewModel extends AndroidViewModel {

  private MutableLiveData<Alarm> alarm;
  private MutableLiveData<List<Alarm>> alarms;
  private final AlarmRepository alarmRepository;
  private final LocationRepository locationRepository;



  public SettingsViewModel(@NonNull Application application) {
    super(application);
    alarmRepository = new AlarmRepository(application);
    locationRepository = new LocationRepository(application);
    alarms = new MutableLiveData<List<Alarm>>();
  }

 // public LiveData<List<Alarm>> getAlarms() {return alarmRepository.getAll();}

}