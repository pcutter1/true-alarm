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

  @Transaction
  @Query("SELECT * FROM Alarm ORDER BY name")
  LiveData<List<Alarm>> selectAll();

  @Query("SELECT * FROM Alarm WHERE alarm_id = :alarmId")
  Single<Alarm> selectById(long alarmId);

  @Query("SELECT * FROM Alarm WHERE end_location_id = :endLocationId")
  LiveData<Alarm> selectByEndLocation(long endLocationId);

  @Query("SELECT * FROM Alarm WHERE start_location_id = :startLocationId")
  LiveData<Alarm> selectByStartLocation(long startLocationId);

}
