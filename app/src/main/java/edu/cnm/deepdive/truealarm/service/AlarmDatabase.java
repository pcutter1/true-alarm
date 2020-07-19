package edu.cnm.deepdive.truealarm.service;


import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.truealarm.model.dao.AlarmDao;
import edu.cnm.deepdive.truealarm.model.dao.LocationDao;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import edu.cnm.deepdive.truealarm.model.entity.Location;
import edu.cnm.deepdive.truealarm.service.AlarmDatabase.Converters;
import java.util.Date;

@Database(
    entities = {Alarm.class, Location.class},
    version = 1,
    exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class AlarmDatabase extends RoomDatabase {

  private static final String DB_NAME = "alarm_db";

  private static Application context;

  public static void setContext(Application context) {
    AlarmDatabase.context = context;
  }

  public abstract AlarmDao getAlarmDao();

  public abstract LocationDao getLocationDao();

  public static AlarmDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final AlarmDatabase INSTANCE =
        Room.databaseBuilder(context, AlarmDatabase.class, DB_NAME).build();
  }

  public static class Converters {

    @TypeConverter
    public static Long dateToLong(Date value) {
      return (value != null) ? value.getTime() : null;
    }

    @TypeConverter
    public static Date longToDate(Long value) {
      return (value != null) ? new Date(value) : null;
    }

  }

}
