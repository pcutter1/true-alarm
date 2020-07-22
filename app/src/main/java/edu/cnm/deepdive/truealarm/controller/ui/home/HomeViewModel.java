package edu.cnm.deepdive.truealarm.controller.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import edu.cnm.deepdive.truealarm.service.AlarmRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class HomeViewModel extends ViewModel implements LifecycleObserver {

  private MutableLiveData<Alarm> alarm_item;
  private MutableLiveData<List<Alarm>> alarms;
  private final AlarmRepository alarmRepository;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public HomeViewModel(@NonNull Application application) {
   super();
   alarm_item = new MutableLiveData<Alarm>();
   alarms = new MutableLiveData<List<Alarm>>();
   alarmRepository = new AlarmRepository(application);
   throwable = new MutableLiveData<>();
   pending = new CompositeDisposable();
  }

  public LiveData<List<Alarm>> getAlarms() {return alarmRepository.getAll();}

  public void saveAlarm(Alarm alarm) {
    throwable.setValue(null);
    pending.add(
        alarmRepository.save(alarm)
        .subscribe(
            () -> {
            },
            (throwable) -> this.throwable.postValue(throwable)
        )
    );
  }


  public void deleteAlarm(Alarm alarm) {
    throwable.setValue(null);
    pending.add(
        alarmRepository.delete(alarm)
            .subscribe(
                () -> {
                },
                (throwable) -> this.throwable.postValue(throwable)
            )
    );
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {pending.clear();}

}