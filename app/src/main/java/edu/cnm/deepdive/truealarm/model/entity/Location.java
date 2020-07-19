package edu.cnm.deepdive.truealarm.model.entity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


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
