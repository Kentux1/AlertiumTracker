package com.kentux.alertiumtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Alert implements Parcelable {

    @SerializedName("node")
    private String mAlertMissionNode;

    @SerializedName("type")
    private String mAlertMissionType;

    @SerializedName("faction")
    private String mAlertMissionFaction;

    @SerializedName("asString")
    private String mAlertMissionReward;

    @SerializedName("thumbnail")
    private String mAlertThumbnail;

    @SerializedName("minEnemyLevel")
    private int mAlertMinEnemyLevel;

    @SerializedName("maxEnemyLevel")
    private int mAlertMaxEnemyLevel;

    @SerializedName("archwingRequired")
    private boolean mIsArchwingRequired;

    @SerializedName("eta")
    private String mTimeLeft;

    public String getmAlertMissionNode() {
        return mAlertMissionNode;
    }

    public void setmAlertMissionNode(String mAlertMissionNode) {
        this.mAlertMissionNode = mAlertMissionNode;
    }

    public String getmAlertMissionType() {
        return mAlertMissionType;
    }

    public void setmAlertMissionType(String mAlertMissionType) {
        this.mAlertMissionType = mAlertMissionType;
    }

    public String getmAlertMissionFaction() {
        return mAlertMissionFaction;
    }

    public void setmAlertMissionFaction(String mAlertMissionFaction) {
        this.mAlertMissionFaction = mAlertMissionFaction;
    }

    public String getmAlertMissionReward() {
        return mAlertMissionReward;
    }

    public void setmAlertMissionReward(String mAlertMissionReward) {
        this.mAlertMissionReward = mAlertMissionReward;
    }

    public String getmAlertThumbnail() {
        return mAlertThumbnail;
    }

    public void setmAlertThumbnail(String mAlertThumbnail) {
        this.mAlertThumbnail = mAlertThumbnail;
    }

    public int getmAlertMinEnemyLevel() {
        return mAlertMinEnemyLevel;
    }

    public void setmAlertMinEnemyLevel(int mAlertMinEnemyLevel) {
        this.mAlertMinEnemyLevel = mAlertMinEnemyLevel;
    }

    public int getmAlertMaxEnemyLevel() {
        return mAlertMaxEnemyLevel;
    }

    public void setmAlertMaxEnemyLevel(int mAlertMaxEnemyLevel) {
        this.mAlertMaxEnemyLevel = mAlertMaxEnemyLevel;
    }

    public boolean ismIsArchwingRequired() {
        return mIsArchwingRequired;
    }

    public void setmIsArchwingRequired(boolean mIsArchwingRequired) {
        this.mIsArchwingRequired = mIsArchwingRequired;
    }

    public String getmTimeLeft() {
        return mTimeLeft;
    }

    public void setmTimeLeft(String mTimeLeft) {
        this.mTimeLeft = mTimeLeft;
    }

    protected Alert(Parcel in) {
        mAlertMissionNode = in.readString();
        mAlertMissionType = in.readString();
        mAlertMissionFaction = in.readString();
        mAlertMissionReward = in.readString();
        mAlertThumbnail = in.readString();
        mAlertMinEnemyLevel = in.readInt();
        mAlertMaxEnemyLevel = in.readInt();
        mIsArchwingRequired = in.readByte() != 0x00;
        mTimeLeft = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAlertMissionNode);
        dest.writeString(mAlertMissionType);
        dest.writeString(mAlertMissionFaction);
        dest.writeString(mAlertMissionReward);
        dest.writeString(mAlertThumbnail);
        dest.writeInt(mAlertMinEnemyLevel);
        dest.writeInt(mAlertMaxEnemyLevel);
        dest.writeByte((byte) (mIsArchwingRequired ? 0x01 : 0x00));
        dest.writeString(mTimeLeft);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Alert> CREATOR = new Parcelable.Creator<Alert>() {
        @Override
        public Alert createFromParcel(Parcel in) {
            return new Alert(in);
        }

        @Override
        public Alert[] newArray(int size) {
            return new Alert[size];
        }
    };
}
