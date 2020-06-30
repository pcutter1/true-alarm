package edu.cnm.deepdive.truealarm.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import io.reactivex.Single;

@Dao
public interface AlarmDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> instert(Alarm alarm);

  @Update
  Single<Integer> update(Alarm... alarms);

  @Delete
  Single<Integer> delete(Alarm... alarms);

  @Query("SELECT * FROM Alarm ORDER BY name")
  Single<Alarm> selectAll();

  @Query("SELECT * FROM Alarm ORDER BY arrival_time")
  Single<Alarm> selectById(long alarmId);

  // TODO figure out these queries
  /*
  @Query("SELECT * FROM Alarm WHERE end_location = :endLocation")
  Single<Alarm> selectby

  @Query("SELECT * FROM Alarm WHERE end_location = :endLocation")
  Single<Alarm> selectby

   */
}
