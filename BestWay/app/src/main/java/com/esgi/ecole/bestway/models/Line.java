package com.esgi.ecole.bestway.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohsen raeisi on 29/11/2016.
 */
public class Line implements Parcelable {
   private String number ;
   private String vehicle ;

    public Line(){}

    public static final Creator<Line> CREATOR = new Creator<Line>() {
        @Override
        public Line createFromParcel(Parcel in) {
            return new Line(in);
        }

        @Override
        public Line[] newArray(int size) {
            return new Line[size];
        }
    };

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeString(this.vehicle);


    }


    public Line(Parcel in) {
        this.number = in.readString();
        this.vehicle = in.readString();


    }
}
