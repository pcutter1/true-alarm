package edu.cnm.deepdive.truealarm.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
    foreignKeys = @ForeignKey(
        entity = Alarm.class,
        parentColumns = "alarm_id",
        childColumns = "alarm_id",
        onDelete = ForeignKey.SET_NULL
    )
)

public class AlarmSetting {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "alarm_setting_id")
  private long id;

  @ColumnInfo(name = "alarm_id")
  private Long alarmId;

  @ColumnInfo(name = "snooze")
  private boolean snoozeOn;

  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String alarmTone;

  @ColumnInfo(name = "commute_estimate")
  private int commuteTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getAlarmId() {
    return alarmId;
  }

  public void setAlarmId(Long alarmId) {
    this.alarmId = alarmId;
  }

  public boolean isSnoozeOn() {
    return snoozeOn;
  }

  public void setSnoozeOn(boolean snoozeOn) {
    this.snoozeOn = snoozeOn;
  }

  public String getAlarmTone() {
    return alarmTone;
  }

  public void setAlarmTone(String alarmTone) {
    this.alarmTone = alarmTone;
  }

  public int getCommuteTime() {
    return commuteTime;
  }

  public void setCommuteTime(int commuteTime) {
    this.commuteTime = commuteTime;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    return super.equals(obj);
  }

  @NonNull
  @Override
  public String toString() {
    return super.toString();
  }
}
