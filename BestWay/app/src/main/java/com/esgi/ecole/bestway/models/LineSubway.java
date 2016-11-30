package com.esgi.ecole.bestway.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohsen raeisi on 29/11/2016.
 */
public class LineSubway extends Line implements Parcelable {

    private String slug;
    private String title;
    private String message;

    public LineSubway(){
        super();

    }


    protected LineSubway(Parcel in) {
        super(in);
        slug = in.readString();
        title = in.readString();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(slug);
        dest.writeString(title);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LineSubway> CREATOR = new Creator<LineSubway>() {
        @Override
        public LineSubway createFromParcel(Parcel in) {
            return new LineSubway(in);
        }

        @Override
        public LineSubway[] newArray(int size) {
            return new LineSubway[size];
        }
    };

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
