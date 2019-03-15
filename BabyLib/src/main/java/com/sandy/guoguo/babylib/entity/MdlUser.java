package com.sandy.guoguo.babylib.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MdlUser implements Parcelable,Cloneable {
    /**
     * 手机号
     */
    @SerializedName("number")
    public String phone;

    public String token;


    /**
     * userID : 8
     * number : 17727881181
     * userName :
     * headPicurl :
     */

    public long userID;
    public String userName;
    @SerializedName("headPicurl")
    public String headPicture;

    public MdlUser() {
    }

    protected MdlUser(Parcel in) {
        phone = in.readString();
        token = in.readString();
        userID = in.readLong();
        userName = in.readString();
        headPicture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone);
        dest.writeString(token);
        dest.writeLong(userID);
        dest.writeString(userName);
        dest.writeString(headPicture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MdlUser> CREATOR = new Creator<MdlUser>() {
        @Override
        public MdlUser createFromParcel(Parcel in) {
            return new MdlUser(in);
        }

        @Override
        public MdlUser[] newArray(int size) {
            return new MdlUser[size];
        }
    };

    @Override
    public Object clone(){
        MdlUser mdlUser = null;
        try {
            mdlUser = (MdlUser) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return mdlUser;
    }

    @Override
    public String toString() {
        return "MdlUser{" +
                "phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", userID=" + userID +
                ", userName='" + userName + '\'' +
                ", headPicture='" + headPicture + '\'' +
                '}';
    }
}
