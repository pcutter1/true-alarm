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

package edu.cnm.deepdive.truealarm.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
    foreignKeys = {@ForeignKey(
        entity = Location.class,
        parentColumns = "location_id",
        childColumns = "start_location_id",
        onDelete = ForeignKey.SET_NULL
    ),
        @ForeignKey(
            entity = Location.class,
            parentColumns = "location_id",
            childColumns = "end_location_id",
            onDelete = ForeignKey.SET_NULL
        )
    }
)

public class Alarm {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "alarm_id")
  private long id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name = "";

  @ColumnInfo(name = "is_set")
  private boolean isSet;

  @ColumnInfo(name = "arrival_time")
  private int arriveBy;

  @ColumnInfo(name = "alert_buffer")
  private int buffer = 0;

  @ColumnInfo(name = "start_location_id", index = true)
  private Long startLocationId;

  @ColumnInfo(name = "end_location_id", index = true)
  private Long endLocationId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isSet() {
    return isSet;
  }

  public void setSet(boolean set) {
    isSet = set;
  }

  public int getArriveBy() {
    return arriveBy;
  }

  public void setArriveBy(int arriveBy) {
    this.arriveBy = arriveBy;
  }

  public int getBuffer() {
    return buffer;
  }

  public void setBuffer(int buffer) {
    this.buffer = buffer;
  }

  public Long getStartLocationId() {
    return startLocationId;
  }

  public void setStartLocationId(Long startLocation) {
    this.startLocationId = startLocation;
  }

  public Long getEndLocationId() {
    return endLocationId;
  }

  public void setEndLocationId(Long endLocation) {
    this.endLocationId = endLocation;
  }

  //TODO Figure out if I need these overrides.
  /*
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

   */
}
