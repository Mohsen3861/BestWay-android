package com.esgi.ecole.bestway.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by mohsen raeisi on 07/09/2016.
 */


public class Trajet implements Parcelable{

    private int duration;
    private int type;
    private int praticalIndex;
    private int grade;
    private ArrayList<Line> lines;


    public Trajet() {
    }

    public Trajet(int duration, String type, int grade, int praticalIndex, ArrayList<Line> lines) {
        this.duration = duration;
        this.lines = lines;
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

            case "train":
                this.type = 6;
                break;
            case "tram":
                this.type = 7;
                break;
            default:
                break;

        }
        this.grade = grade;
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

    public int getPraticalIndex() {
        return praticalIndex;
    }

    public void setPraticalIndex(int praticalIndex) {
        this.praticalIndex = praticalIndex;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.duration);
        dest.writeInt(this.type);
        dest.writeInt(this.grade);
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
        this.grade = in.readInt();
        this.praticalIndex = in.readInt();

    }
}
