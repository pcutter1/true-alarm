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

package edu.cnm.deepdive.truealarm;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.truealarm.service.AlarmDatabase;
import io.reactivex.schedulers.Schedulers;

/**
 * The TrueAlarmApplication class. It extends the Applicaiton class and sets the current database context
 * to this, then gets an instance of it.
 */
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
