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
import edu.cnm.deepdive.truealarm.model.entity.Location;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
/**
 * Provides methods for saving and retrieving objects from the database in conjunction with the
 * Location Dao.
 */
public class LocationRepository {

  private final Context context;
  private final AlarmDatabase database;
  private final AlarmDao alarmDao;
  private final LocationDao locationDao;

  /**
   * Constructor for the private fields to be instantiated with the current context.
   * @param context
   */
  public LocationRepository(Context context) {
    this.context = context;
    database = AlarmDatabase.getInstance();
    alarmDao = database.getAlarmDao();
    locationDao = database.getLocationDao();
  }

  /**
   *
   * @return a livedata list of all locations in the Location Dao.
   */
  public LiveData<List<Location>> getAll() {return locationDao.selectAll();}

  /**
   * Takes a @param id of a location and returns that specific location from the database.
   * @return a single location
   */
  public Single<Location> get(long id) {
    return locationDao.selectById(id)
        .subscribeOn(Schedulers.io());
  }
  /**
   * When an Location object is passed through the save method, it checks to see if this is an existing location
   * or a new one. If it is an existing location, it updates that alarm based on its ID, otherwise it inserts a new location.
   * @param location
   */
  public Completable save(Location location) {
    if (location.getId() == 0) {
      return Completable.fromSingle(locationDao.insert(location))
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(locationDao.update(location))
          .subscribeOn(Schedulers.io());
    }
  }


  /**
   * When an Location is passed through the delete method, the method checks to see if this is an existing
   * location based on its ID. If the location is new with ID 0, then the method does nothing. Otherwise
   * it deletes the location from the database
   * @param location
   */
  public Completable delete (Location location) {
    if (location.getId() == 0) {
      return Completable.fromAction(() -> {})
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(locationDao.delete(location))
          .subscribeOn(Schedulers.io());

    }
  }

}
