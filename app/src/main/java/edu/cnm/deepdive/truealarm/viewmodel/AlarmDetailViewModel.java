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