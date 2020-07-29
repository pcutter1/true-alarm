/*
 * <!--
 *   Copyright 2020 Paul Cutter
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0>
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * -->
 */

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

/**
 * HomeViewModel communicates with Alarm Repository and HomeFragment to retrieve and delete alarms. It also contains
 * methods which indicate whether or not persmissions have been checked.
 */
public class HomeViewModel extends AndroidViewModel implements LifecycleObserver {

  private MutableLiveData<Alarm> alarm_item;
  private MutableLiveData<List<Alarm>> alarms;
  private final AlarmRepository alarmRepository;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final MutableLiveData<Boolean> permissionsChecked;

  /**
   * A constructor which instantiates the private fields in this class.
   * Takes a @param application
   */
  public HomeViewModel(@NonNull Application application) {
   super(application);
   alarm_item = new MutableLiveData<Alarm>();
   alarms = new MutableLiveData<List<Alarm>>();
   alarmRepository = new AlarmRepository(application);
   throwable = new MutableLiveData<>();
   pending = new CompositeDisposable();
   permissionsChecked = new MutableLiveData<>(false);
  }

  /**
   *
   * @return true if permissions have been checked
   */
  public LiveData<Boolean> getPermissionsChecked() {
    return permissionsChecked;
  }

  /**
   * Sets boolean value indicating whether or not permissions have been checked
   * @param checked
   */
  public void setPermissionsChecked(boolean checked) {
    permissionsChecked.setValue(checked);}

  /**
   *
   * @return a livedata list of Alarm objects from the Alarm Repository
   */
  public LiveData<List<Alarm>> getAlarms() {return alarmRepository.getAll();}

  /**
   * Deletes an alarm object from the repository
   * @param alarm
   */
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