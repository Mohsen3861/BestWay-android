package com.esgi.ecole.bestway.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohsen raeisi on 07/09/2016.
 */
public class Trajet implements Parcelable{
   private int duration;
   private int type;
    private int durationIndex;
    private int praticalIndex;

    public Trajet() {
    }

    public Trajet(int duration, String type, int durationIndex, int praticalIndex) {
        this.duration = duration;
        switch (type){
            case "walking":
                this.type = 1;
                break;
            case "bicycling":
                this.type = 2;
                break;
            case "subway":
                this.type = 3;
                break;
            case "bus":
                this.type = 4;
                break;
            case "driving":
                this.type = 5;
                break;
        }
        this.durationIndex = durationIndex;
        this.praticalIndex = praticalIndex;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDurationIndex() {
        return durationIndex;
    }

    public void setDurationIndex(int durationIndex) {
        this.durationIndex = durationIndex;
    }

    public int getPraticalIndex() {
        return praticalIndex;
    }

    public void setPraticalIndex(int praticalIndex) {
        this.praticalIndex = praticalIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.duration);
        dest.writeInt(this.type);
        dest.writeInt(this.durationIndex);
        dest.writeInt(this.praticalIndex);

    }

    public static final Parcelable.Creator<Trajet> CREATOR = new Parcelable.Creator<Trajet>()
    {
        @Override
        public Trajet createFromParcel(Parcel source)
        {
            return new Trajet(source);
        }

        @Override
        public Trajet[] newArray(int size)
        {
            return new Trajet[size];
        }
    };

    public Trajet(Parcel in) {
        this.duration = in.readInt();
        this.type = in.readInt();
        this.durationIndex = in.readInt();
        this.praticalIndex = in.readInt();

    }
}
