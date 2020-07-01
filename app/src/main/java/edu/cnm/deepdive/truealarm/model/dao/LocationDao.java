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
