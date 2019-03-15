package com.saiyi.naideanlock.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.saiyi.naideanlock.enums.EnumDeviceAdmin;
import com.saiyi.naideanlock.enums.EnumDeviceLink;
import com.saiyi.naideanlock.enums.EnumSwitch;

import java.util.List;

public class MdlDevice implements Parcelable{

    public long bindingID;

    public String mac;

    /**
     * 手机号
     */
    public String number;

    @EnumDeviceAdmin._Type
    public int isAdmin;

    /**
     * wifi模块唯一标识
     * <p/>暂时用不到的
     */
    public String uid;

    /**
     * wifi模块密码
     * <p/>暂时用不到的
     */
    public String password;

    /**
     * 权限
     */
    public String jurisdiction;

    /**
     * 授权用户名称
     */
    public String remark;

    /**
     * 远程开锁密码
     */
    public String pwd;

    /**
     * 无人模式是否开启 0 ：关闭 1：开启
     */
    @EnumSwitch._Status
    public String no = EnumSwitch.OFF;

    /**
     * 防撬报警是否开启 0：关闭 1：开启
     */
    @EnumSwitch._Status
    public String prying = EnumSwitch.OFF;

    /**
     * 低电报警是否开启 0：关闭 1：开启
     */
    @EnumSwitch._Status
    public String low = EnumSwitch.OFF;

    public String appNumber;

    @EnumDeviceLink._Type
    public int linkType;

    public int type;

    /**
     * 设备名称
     */
    public String lockName;

    /**
     * 设备密码
     */
    public String lockPwd;

    public String userName;

    @SerializedName("headPicurl")
    public String headPicture;

    public List<MdlLockTimeListBean> lockTimeList;
    public List<MdlLockPeriodListBean> lockPeriodList;


    protected MdlDevice(Parcel in) {
        bindingID = in.readLong();
        mac = in.readString();
        number = in.readString();
        isAdmin = in.readInt();
        uid = in.readString();
        password = in.readString();
        jurisdiction = in.readString();
        remark = in.readString();
        pwd = in.readString();
        no = in.readString();
        prying = in.readString();
        low = in.readString();
        appNumber = in.readString();
        linkType = in.readInt();
        type = in.readInt();
        lockName = in.readString();
        lockPwd = in.readString();
        userName = in.readString();
        headPicture = in.readString();
        lockTimeList = in.createTypedArrayList(MdlLockTimeListBean.CREATOR);
        lockPeriodList = in.createTypedArrayList(MdlLockPeriodListBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(bindingID);
        dest.writeString(mac);
        dest.writeString(number);
        dest.writeInt(isAdmin);
        dest.writeString(uid);
        dest.writeString(password);
        dest.writeString(jurisdiction);
        dest.writeString(remark);
        dest.writeString(pwd);
        dest.writeString(no);
        dest.writeString(prying);
        dest.writeString(low);
        dest.writeString(appNumber);
        dest.writeInt(linkType);
        dest.writeInt(type);
        dest.writeString(lockName);
        dest.writeString(lockPwd);
        dest.writeString(userName);
        dest.writeString(headPicture);
        dest.writeTypedList(lockTimeList);
        dest.writeTypedList(lockPeriodList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MdlDevice> CREATOR = new Creator<MdlDevice>() {
        @Override
        public MdlDevice createFromParcel(Parcel in) {
            return new MdlDevice(in);
        }

        @Override
        public MdlDevice[] newArray(int size) {
            return new MdlDevice[size];
        }
    };
}
