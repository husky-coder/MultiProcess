package com.husky.multiprocess.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableVideo implements Parcelable {

    private String name;
    private long duration;
    private int width;
    private int height;

    public ParcelableVideo() {
    }

    public ParcelableVideo(String name, long duration, int width, int height) {
        this.name = name;
        this.duration = duration;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ParcelableVideo{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    protected ParcelableVideo(Parcel in) {
        name = in.readString();
        duration = in.readLong();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<ParcelableVideo> CREATOR = new Creator<ParcelableVideo>() {
        @Override
        public ParcelableVideo createFromParcel(Parcel in) {
            return new ParcelableVideo(in);
        }

        @Override
        public ParcelableVideo[] newArray(int size) {
            return new ParcelableVideo[size];
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
        parcel.writeInt(width);
        parcel.writeInt(height);
    }
}
