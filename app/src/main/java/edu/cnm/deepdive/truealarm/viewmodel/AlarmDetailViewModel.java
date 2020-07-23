package edu.cnm.deepdive.truealarm.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import edu.cnm.deepdive.truealarm.service.AlarmRepository;

public class AlarmDetailViewModel extends AndroidViewModel {

  private final AlarmRepository alarmRepository;
  private final MutableLiveData<Alarm> alarm;
  private final MutableLiveData<Throwable> throwable;

  public AlarmDetailViewModel(@NonNull Application application) {
    super(application);
    alarmRepository = new AlarmRepository(application);
    alarm = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  public LiveData<Alarm> getAlarm() {
    return alarm;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void fetch(long alarmId) {
    alarmRepository.get(alarmId)
        .subscribe(
            alarm::postValue,
            throwable::postValue
        );
  }
}