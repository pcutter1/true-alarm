package edu.cnm.deepdive.truealarm.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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

public class HomeViewModel extends AndroidViewModel implements LifecycleObserver {

  private MutableLiveData<Alarm> alarm_item;
  private MutableLiveData<List<Alarm>> alarms;
  private final AlarmRepository alarmRepository;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final MutableLiveData<Boolean> permissionsChecked;

  public HomeViewModel(@NonNull Application application) {
   super(application);
   alarm_item = new MutableLiveData<Alarm>();
   alarms = new MutableLiveData<List<Alarm>>();
   alarmRepository = new AlarmRepository(application);
   throwable = new MutableLiveData<>();
   pending = new CompositeDisposable();
   permissionsChecked = new MutableLiveData<>(false);
  }

  public LiveData<Boolean> getPermissionsChecked() {
    return permissionsChecked;
  }

  public void setPermissionsChecked(boolean checked) {
    permissionsChecked.setValue(checked);}

  public LiveData<List<Alarm>> getAlarms() {return alarmRepository.getAll();}

  public void saveAlarm(Alarm alarm) {
    throwable.setValue(null);
    pending.add(
        alarmRepository.save(alarm)
        .subscribe(
            () -> {},
            this.throwable::postValue
        )
    );
  }


  public void deleteAlarm(Alarm alarm) {
    throwable.setValue(null);
    pending.add(
        alarmRepository.delete(alarm)
            .subscribe(
                () -> {},
                this.throwable::postValue
            )
    );
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {pending.clear();}

}