package edu.cnm.deepdive.truealarm.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
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
 Single<Long> update(Location... locations);

 @Delete
 Single<Long> delete (Location... locations);

 @Query("SELECT * FROM Location ORDER BY name")
 LiveData<Location> selectAll();

 @Query("SELECT * FROM Location ORDER BY address")
 LiveData<Location> selectByAddress();

}
