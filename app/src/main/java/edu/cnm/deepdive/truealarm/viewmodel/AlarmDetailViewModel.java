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
import io.reactivex.disposables.CompositeDisposable;

public class AlarmDetailViewModel extends AndroidViewModel {

  private final LocationRepository locationRepository;
  private final AlarmRepository alarmRepository;
  private final MutableLiveData<Alarm> alarm;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public AlarmDetailViewModel(@NonNull Application application) {
    super(application);
    alarmRepository = new AlarmRepository(application);
    alarm = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    locationRepository = new LocationRepository(application);
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

  public void saveAlarm(Alarm alarm) {
    throwable.setValue(null);
    pending.add(
        alarmRepository.save(alarm)
            .subscribe());
  }



}