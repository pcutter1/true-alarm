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
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;

/**
 * The Location entity class. It logs attributes about locations used for starting and ending addresses
 * and stores them locally to the database. Location can be indexed by Name and Address, which are each unique
 * attributes. Its primary key is an auto-generated ID of type long.
 */


@Entity(
    indices = {@Index(value = {"name", "address"}, unique = true)}
)
public class Location {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "location_id")
  private long id;

  @ColumnInfo(name = "latitude")
  private double latitude;

  @ColumnInfo(name = "longitude")
  private double longitude;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String address = "";

  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  /**
   * @returns a location ID
   */

  public long getId() {
    return id;
  }

  /**
   * Sets the location ID
   * @param id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   *
   * @returns the latitude of a location in double format
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Sets the latitude of a location when given a
   * @param latitude
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   *
   * @returns the longitude of a location in double format
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Sets the longitude of a location when given a
   * @param longitude
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   *
   * @returns the String address of a location
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the String address of a location when provided a
   * @param address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   *
   * @returns the String name of a location e.g. School or Work if existent.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of a location in String format e.g. Home or Work when provided with a
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

//  public static class GeoCode {
//
//    @Expose
//    private LatLng latLng;
//
//    public LatLng getLatLng() {return latLng;}
//    public void setLatLng(LatLng latLng) {
//      this.latLng = latLng;
//    }
//
//  }

  //TODO Figure out if I need these overrides.
  /*
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    return (obj == this) || (obj instanceof Location && name.equalsIgnoreCase(((Location)obj).name));

  }

  @NonNull
  @Override
  public String toString() {
    return super.toString();
  }

   */
}
