package edu.cnm.deepdive.truealarm.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.truealarm.model.dao.AlarmDao;
import edu.cnm.deepdive.truealarm.model.dao.LocationDao;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class AlarmRepository {

  private final Context context;
  private final AlarmDatabase database;
  private final AlarmDao alarmDao;
  private final LocationDao locationDao;


  public AlarmRepository(Context context) {
    this.context = context;
    database = AlarmDatabase.getInstance();
    alarmDao = database.getAlarmDao();
    locationDao = database.getLocationDao();
  }

  public LiveData<List<Alarm>> getAll() {return alarmDao.selectAll();}

  public Single<Alarm> get(long id) {
    return alarmDao.selectById(id)
        .subscribeOn(Schedulers.io());
  }

  public Completable save(Alarm alarm) {
    if (alarm.getId() == 0) {
      return Completable.fromSingle(alarmDao.insert(alarm))
          .subscribeOn((Schedulers.io()));
    } else {
      return Completable.fromSingle(alarmDao.update(alarm))
          .subscribeOn(Schedulers.io());
    }
  }

  public Completable delete (Alarm alarm) {
    if (alarm.getId() == 0) {
      return Completable.fromAction(() -> {})
          .subscribeOn(Schedulers.io());
    } else {
      return Completable.fromSingle(alarmDao.delete(alarm))
          .subscribeOn(Schedulers.io());

    }
  }

}
