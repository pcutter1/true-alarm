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