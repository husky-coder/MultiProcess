package com.husky.multiprocess.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableMusic implements Parcelable {

    private String name;
    private long duration;

    public ParcelableMusic() {
    }

    public ParcelableMusic(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ParcelableMusic{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }

    protected ParcelableMusic(Parcel in) {
        name = in.readString();
        duration = in.readLong();
    }

    public static final Creator<ParcelableMusic> CREATOR = new Creator<ParcelableMusic>() {
        @Override
        public ParcelableMusic createFromParcel(Parcel in) {
            return new ParcelableMusic(in);
        }

        @Override
        public ParcelableMusic[] newArray(int size) {
            return new ParcelableMusic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeLong(duration);
    }
}
