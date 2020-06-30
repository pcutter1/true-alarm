package edu.cnm.deepdive.truealarm.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    indices = @Index(value = "name", unique = true)
)
public class Alarm {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "alarm_id")
  private long id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name = "";

  @ColumnInfo(name = "active")
  private boolean set;

  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String startLocation;

  @ColumnInfo (collate = ColumnInfo.NOCASE)
  private String endLocation;

  @NonNull
  @ColumnInfo(name = "arrive_by")
  private Long arriveBy = 0L;

  @ColumnInfo(name = "buffer_time")
  private int buffer = 0;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public boolean isSet() {
    return set;
  }

  public void setSet(boolean set) {
    this.set = set;
  }

  public String getStartLocation() {
    return startLocation;
  }

  public void setStartLocation(String startLocation) {
    this.startLocation = startLocation;
  }

  public String getEndLocation() {
    return endLocation;
  }

  public void setEndLocation(String endLocation) {
    this.endLocation = endLocation;
  }

  @NonNull
  public Long getArriveBy() {
    return arriveBy;
  }

  public void setArriveBy(@NonNull Long arriveBy) {
    this.arriveBy = arriveBy;
  }

  public int getBuffer() {
    return buffer;
  }

  public void setBuffer(int buffer) {
    this.buffer = buffer;
  }

  @Override
  public int hashCode() {
    return name.toLowerCase().hashCode();
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
