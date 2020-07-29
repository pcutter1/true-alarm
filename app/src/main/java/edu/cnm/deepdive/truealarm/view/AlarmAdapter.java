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

package edu.cnm.deepdive.truealarm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import edu.cnm.deepdive.truealarm.view.AlarmAdapter.Holder;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<Holder> {

  private final String unnamedAlarm;
  private final Context context;
  private final List<Alarm> alarms;
  private final OnEditListener onEditListener;
  private final OnDeleteListener onDeleteListener;

  /**
   * A constructor which provides a context to and instantiates the private fields in this class
   * @param context
   * @param alarms
   * @param onEditListener
   * @param onDeleteListener
   */
  public AlarmAdapter(Context context,
      List<Alarm> alarms,
      OnEditListener onEditListener,
      OnDeleteListener onDeleteListener) {
    this.context = context;
    this.alarms = alarms;
    this.onEditListener = onEditListener;
    this.onDeleteListener = onDeleteListener;
    unnamedAlarm = "Alarm";
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return alarms.size();
  }

  /**
   * Holder class contains the specific methods of identifying the views in the home layout
   * and setting the appropriate values to those fields. It extends RecylcerView.ViewHolder
   */
  class Holder extends RecyclerView.ViewHolder {

    private final View alarmView;
    private final TextView alarmName;
    private final TextView arrivalTime;
    private final TextView wakeTime;
    private final TextView leaveBy;
    private final View edit;
    private final View delete;

    /**
     * Holder method identfies the views and buttons in the home layout. It requires an alarmView parameter
     * @param alarmView
     */
    public Holder(@NonNull View alarmView) {
      super(alarmView);
      this.alarmView = alarmView;
      alarmName = alarmView.findViewById(R.id.alarm_name);
      wakeTime = alarmView.findViewById(R.id.alarm_wake_time);
      leaveBy = alarmView.findViewById(R.id.alarm_leave_by);
      edit = alarmView.findViewById(R.id.edit);
      delete = alarmView.findViewById(R.id.delete);
      arrivalTime = alarmView.findViewById(R.id.arrival_time_selected);
    }
    /**
     * Bind method is used to set the views in the alarm detail layout to values that are retrieved
     * from the Alarm entity. Because it part of a RecyclerView, it requires a position as a parameter
     * to identify which alarm to display. Because arrivalTime is set in the database as number of minutes in the day,
     * it needs to be formatted from Hour/Minute format to fit the specified input in the database for arrivalTime.
     * @param position
     */
    private void bind(int position) {
      Alarm alarm = alarms.get(position);
      Calendar calendar = Calendar.getInstance();
      DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
      String name =
          (alarm.getName() != null) ? alarm.getName() : unnamedAlarm;
      alarmName.setText(alarm.getName());
      calendar.set(Calendar.HOUR_OF_DAY, alarm.getArriveBy() / 60);
      calendar.set(Calendar.MINUTE, alarm.getArriveBy() % 60);
      arrivalTime.setText(timeFormat.format(calendar.getTime()));
      //wakeTime.setText();  TODO put logic to change wake time based on traffic data and arrive by time
      //leaveBy.setText();   TODO put logic to change leaveby time based on traffic data and arrive by time
      edit.setOnClickListener((v) -> onEditListener.onEdit(alarm));
      delete.setOnClickListener((v) -> onDeleteListener.onDelete(alarm));

    }
  }

  public interface OnEditListener {

    void onEdit(Alarm alarm);
  }

  public interface OnDeleteListener {

    void onDelete(Alarm alarm);
  }
}
