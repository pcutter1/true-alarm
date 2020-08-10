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

package edu.cnm.deepdive.truealarm.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.truealarm.model.dao.AlarmDao;
import edu.cnm.deepdive.truealarm.model.dao.LocationDao;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Provides methods for saving and retrieving objects from the database in conjunction with the Alarm
 * Dao.
 */
public class AlarmRepository {

  private final Context context;
  private final AlarmDatabase database;
  private final AlarmDao alarmDao;
  private final LocationDao locationDao;

  /**
   * Constructor for the private fields to be instantiated with the current context.
   * @param context
   */
  public AlarmRepository(Context context) {
    this.context = context;
    database = AlarmDatabase.getInstance();
    alarmDao = database.getAlarmDao();
    locationDao = database.getLocationDao();
  }

  /**
   *
   * @return a livedata list of all alarms in the Alarm Dao
   */
  public LiveData<List<Alarm>> getAll() {return alarmDao.selectAll();}

  /**
   * Takes a @param id of an alarm and returns that specific alarm from the database
   * @return a single alarm
   */
  public Single<Alarm> get(long id) {
    return alarmDao.selectById(id)
        .subscribeOn(Schedulers.io());
  }

  /**
   * When an Alarm object is passed through the save method, it checks to see if this is an existing alarm or a new alarm
   * If it is an existing alarm, it updates that alarm based on its ID, otherwise it inserts a new alarm.
   * @param alarm
   */
  public Completable save(Alarm alarm) {
    if (alarm.getId() == 0) {
      return Completable.fromSingle(alarmDao.insert(alarm))
          .subscribeOn((Schedulers.io()));
    } else {
      return Completable.fromSingle(alarmDao.update(alarm))
          .subscribeOn(Schedulers.io());
    }
  }

  /**
   * When an alarm is passed through the delete method, the method checks to see if this is an existing
   * alarm based on its ID. If the alarm is a new alarm with ID 0, then the method does nothing. Otherwise
   * it deletes the alarm from the database
   * @param alarm
   */
  public Completable delete (Alarm alarm) {
    if (alarm.getId() == 0) {
      return Completable.fromAction(() -> {})
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(alarmDao.delete(alarm))
          .subscribeOn(Schedulers.io());
    }
  }

}
