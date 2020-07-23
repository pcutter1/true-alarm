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
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<Holder> {

  private final String unnamedAlarm;
  private final Context context;
  private final List<Alarm> alarms;

  public AlarmAdapter(Context context,
      List<Alarm> alarms) {
    this.context = context;
    this.alarms = alarms;
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

  class Holder extends RecyclerView.ViewHolder {

    private final View alarmView;
    private final TextView alarmName;
    private final TextView wakeTime;
    private final TextView leaveBy;

    public Holder (@NonNull View alarmView) {
      super(alarmView);
      this.alarmView = alarmView;
      alarmName = alarmView.findViewById(R.id.alarm_name);
      wakeTime = alarmView.findViewById(R.id.alarm_wake_time);
      leaveBy = alarmView.findViewById(R.id.alarm_leave_by);
    }

    private void bind(int position) {
      Alarm alarm = alarms.get(position);
      String name =
          (alarm.getName() != null) ? alarm.getName() : unnamedAlarm;
      alarmName.setText(alarm.getName());
      //wakeTime.setText();  TODO put logic to change wake time based on traffic data and arrive by time
      //leaveBy.setText();   TODO put logic to change leaveby time based on traffic data and arrive by time
      // TODO add onclick listener for edit and delete buttons

    }
  }


}
