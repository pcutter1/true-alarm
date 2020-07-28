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

package edu.cnm.deepdive.truealarm.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface AlarmDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Alarm alarm);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Alarm... alarms);

  @Update
  Single<Integer> update(Alarm... alarms);

  @Delete
  Single<Integer> delete(Alarm... alarms);

  @Query("SELECT * FROM Alarm ORDER BY name")
  LiveData<List<Alarm>> selectAll();

  @Query("SELECT * FROM Alarm WHERE alarm_id = :alarmId")
  Single<Alarm> selectById(long alarmId);

  /*
  @Query("SELECT * FROM Alarm WHERE end_location_id = :endLocationId")
  Single<Alarm> selectByEndLocation(long endLocationId);

  @Query("SELECT * FROM Alarm WHERE start_location_id = :startLocationId")
  Single<Alarm> selectByStartLocation(long startLocationId);
   */

}
