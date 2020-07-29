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

/**
 * AlarmDatabase extends RoomDatabase. It contains the Alarm and Location entities.
 * It also contains methods for converting Date objects to type Long and Long values to Date types.
 */

@Database(
    entities = {Alarm.class, Location.class},
    version = 1,
    exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class AlarmDatabase extends RoomDatabase {

  private static final String DB_NAME = "alarm_db";

  private static Application context;

  /**
   * Takes the application @param context and sets it as the current context for the database
   */
  public static void setContext(Application context) {
    AlarmDatabase.context = context;
  }

  /**
   *
   * @return the Alarm Dao
   */
  public abstract AlarmDao getAlarmDao();

  /**
   *
   * @return the Location Dao
   */
  public abstract LocationDao getLocationDao();

  /**
   *
   * @return an INSTANCE of the current database
   */
  public static AlarmDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final AlarmDatabase INSTANCE =
        Room.databaseBuilder(context, AlarmDatabase.class, DB_NAME).build();
  }

  /**
   * Converters that change Date objects to Long values and Long values to Date types.
   */
  public static class Converters {

    /**
     * Takes a @param Date value and as long as that value is not null, it will convert it and @return a Long
     * value, otherwise it will @return null.
     */
    @TypeConverter
    public static Long dateToLong(Date value) {
      return (value != null) ? value.getTime() : null;
    }

    /**
     * Takes a @param Long value and if that value is not null, it will convert and @return a Date value
     * otherwise it will @return null.
     */
    @TypeConverter
    public static Date longToDate(Long value) {
      return (value != null) ? new Date(value) : null;
    }

  }

}
