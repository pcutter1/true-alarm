package edu.cnm.deepdive.truealarm;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.truealarm.service.AlarmDatabase;
import io.reactivex.schedulers.Schedulers;

public class TrueAlarmApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AlarmDatabase.setContext(this);
    AlarmDatabase database = AlarmDatabase.getInstance();
    database.getAlarmDao().delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
    Stetho.initializeWithDefaults(this);
  }

}
