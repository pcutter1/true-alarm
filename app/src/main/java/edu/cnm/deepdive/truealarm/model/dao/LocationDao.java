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
import edu.cnm.deepdive.truealarm.model.entity.Location;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface LocationDao {

 @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Location location);

 @Insert(onConflict = OnConflictStrategy.IGNORE)
 Single<List<Long>> insert(Location... locations);

 @Update
 Single<Integer> update(Location... locations);

 @Delete
 Single<Integer> delete (Location... locations);

 @Transaction
 @Query("SELECT * FROM Location ORDER BY name")
 LiveData<List<Location>> selectAll();

 @Query("SELECT * FROM Location WHERE location_id = :locationId")
 Single<Location> selectById(long locationId);

 @Transaction
 @Query("SELECT * FROM Location ORDER BY address")
 LiveData<List<Location>> selectByAddress();

}
