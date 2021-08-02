package net.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import net.utils.Utils;

import java.io.Serializable;

public class VideoEntity implements Serializable, Parcelable {
    String name;
    String size;
    String url;
    String duration;
    String time;

    public VideoEntity(){

    }

    public VideoEntity(String name,String size,String url,String duration,String time){
        this.name = name;
        this.size = size;
        this.url = url;
        this.duration = duration;
        this.time = time;
    }

    protected VideoEntity(Parcel in) {
        name = in.readString();
        size = in.readString();
        url = in.readString();
        duration = in.readString();
        time = in.readString();
    }

    public static final Creator<VideoEntity> CREATOR = new Creator<VideoEntity>() {
        @Override
        public VideoEntity createFromParcel(Parcel in) {
            return new VideoEntity(in);
        }

        @Override
        public VideoEntity[] newArray(int size) {
            return new VideoEntity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(url);
        dest.writeString(duration);
        dest.writeString(time);
    }
}
