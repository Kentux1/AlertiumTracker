package com.kentux.alertiumtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class News implements Parcelable {

    @SerializedName("message")
    private String mNewsMessage;

    @SerializedName("link")
    private String mNewsLink;

    @SerializedName("imageLink")
    private String mNewsThumbnail;

    public String getmNewsMessage() {
        return mNewsMessage;
    }

    public void setmNewsMessage(String mNewsMessage) {
        this.mNewsMessage = mNewsMessage;
    }

    public String getmNewsLink() {
        return mNewsLink;
    }

    public void setmNewsLink(String mNewsLink) {
        this.mNewsLink = mNewsLink;
    }

    public String getmNewsThumbnail() {
        return mNewsThumbnail;
    }

    public void setmNewsThumbnail(String mNewsThumbnail) {
        this.mNewsThumbnail = mNewsThumbnail;
    }

    protected News(Parcel in) {
        mNewsMessage = in.readString();
        mNewsLink = in.readString();
        mNewsThumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNewsMessage);
        dest.writeString(mNewsLink);
        dest.writeString(mNewsThumbnail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
