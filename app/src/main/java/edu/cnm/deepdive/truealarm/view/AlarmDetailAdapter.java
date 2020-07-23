package edu.cnm.deepdive.truealarm.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.truealarm.R;
import edu.cnm.deepdive.truealarm.model.entity.Alarm;
import java.util.List;

public class AlarmDetailAdapter extends RecyclerView.Adapter<AlarmDetailAdapter.Holder> {

  private final String unnamedAlarm;
  private final Context context;
  private final List<Alarm> alarms;
  private final OnCLickListener clickListener;


  public AlarmDetailAdapter(Context context,
      List<Alarm> alarms,
      OnCLickListener clickListener) {
    super();
    this.context = context;
    this.alarms = alarms;
    this.clickListener = clickListener;
    unnamedAlarm = "Alarm";
  }


  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.fragment_alarm_detail, parent, false);
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

    private final View alarmDetailView;
    private final EditText arrivalTime;
    private final TextView commuteTime;
    private final EditText alarmName;
    private final EditText bufferMinutes;

    Holder(@NonNull View alarmDetailView) {
      super(alarmDetailView);
      this.alarmDetailView = alarmDetailView;
      arrivalTime = alarmDetailView.findViewById(R.id.arrival_time_selected);
      commuteTime = alarmDetailView.findViewById(R.id.est_commute_time);
      alarmName = alarmDetailView.findViewById(R.id.alarm_name);
      bufferMinutes = alarmDetailView.findViewById(R.id.buffer_minutes_selected);

    }

    private void bind(int position) {
      Alarm alarm = alarms.get(position);
      String name =
          (alarm.getName() != null) ? alarm.getName() : unnamedAlarm;
      alarmName.setText(alarm.getName());
      arrivalTime.setText(alarm.getArriveBy());
      //commuteTime.setText(); TODO implement with google routes
      bufferMinutes.setText(alarm.getBuffer());
      alarmDetailView.setOnClickListener((v) -> clickListener.OnClick(v, getAdapterPosition(), alarm));
    }


  }

  public interface OnCLickListener {

    void OnClick(View v, int position, Alarm alarm);

  }



}
