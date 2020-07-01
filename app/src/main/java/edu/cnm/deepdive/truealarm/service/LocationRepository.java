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

public class LocationRepository {

  private final Context context;
  private final AlarmDatabase database;
  private final AlarmDao alarmDao;
  private final LocationDao locationDao;

  public LocationRepository(Context context) {
    this.context = context;
    database = AlarmDatabase.getInstance();
    alarmDao = database.getAlarmDao();
    locationDao = database.getLocationDao();
  }

  public LiveData<List<Location>> getAll() {return locationDao.selectAll();}

  public Single<Location> get(long id) {
    return locationDao.selectById(id)
        .subscribeOn(Schedulers.io());
  }

  public Completable save(Location location) {
    if (location.getId() == 0) {
      return Completable.fromSingle(locationDao.insert(location))
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(locationDao.update(location))
          .subscribeOn(Schedulers.io());
    }
  }

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
