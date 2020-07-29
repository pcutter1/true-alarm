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
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import edu.cnm.deepdive.truealarm.service.AlarmRepository;
import edu.cnm.deepdive.truealarm.service.LocationRepository;
import io.reactivex.disposables.CompositeDisposable;

/**
 * AlarmDetailViewModel communicates with Alarm Repository and AlarmDetailFragment to retrieve, save, and delete alarms.
 */

public class AlarmDetailViewModel extends AndroidViewModel {

  private final LocationRepository locationRepository;
  private final AlarmRepository alarmRepository;
  private final MutableLiveData<Alarm> alarm;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  /**
   * A constructor which instantiates the private fields for this class
   * @param application
   */
  public AlarmDetailViewModel(@NonNull Application application) {
    super(application);
    alarmRepository = new AlarmRepository(application);
    alarm = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    locationRepository = new LocationRepository(application);
  }

  /**
   *
   * @return a livedata Alarm object to be ported to the AlarmDetailFragment
   */
  public LiveData<Alarm> getAlarm() {
    return alarm;
  }

  /**
   *
   * @return a livedata Throwable object
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * Takes a @param alarmId and retrieves the associated alarm from the Alarm Repository
   */
  public void fetch(long alarmId) {
    alarmRepository.get(alarmId)
        .subscribe(
            alarm::postValue,
            throwable::postValue
        );
  }

  /**
   * Sets an alarm's id
   * @param id
   */
  public void setAlarmId(long id) {
    throwable.setValue(null);
    pending.add(
        alarmRepository.get(id)
            .subscribe(
                (alarm) -> this.alarm.postValue(alarm),
                (throwable) -> this.throwable.postValue(throwable)
            )
    );
  }

  /**
   * Saves an alarm edited or created in the AlarmDetailFragment to the Alarm Repository
   * @param alarm
   */
  public void saveAlarm(Alarm alarm) {
    throwable.setValue(null);
    pending.add(
        alarmRepository.save(alarm)
            .subscribe(
                () -> {
                  Log.d(getClass().getName(), "Save Completed");
                },
                (throwable) -> this.throwable.postValue(throwable.getCause())
            ));
  }



}