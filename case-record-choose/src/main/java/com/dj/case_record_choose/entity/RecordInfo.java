package com.dj.case_record_choose.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;


public class RecordInfo {
    public String cases; // 所属案件名
    public String type; // 包含 include ，排除 exclude
    public String id;
    public String name;
    public String desc;
    public int postion;
    public boolean isChoosed;

    @Override
    public String toString() {
        return "\nRecordInfo{" +
                "cases='" + cases + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", postion=" + postion +
                ", isChoosed=" + isChoosed +
                '}';
    }

    public RecordInfo() {
    }

    public RecordInfo(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.type = "include";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaseId(){
        if(id.length() != 3) {
            return null;
        }
        return id.split("-")[0];
    }

//    // parcelable
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(cases);
//        dest.writeString(type);
//        dest.writeString(id);
//        dest.writeString(name);
//        dest.writeString(desc);
//        dest.writeInt(postion);
//        dest.writeBoolean(isChoosed);
//    }
//
//
//    protected RecordInfo(Parcel in) {
//        cases = in.readString();
//        type = in.readString();
//        id = in.readString();
//        name = in.readString();
//        desc = in.readString();
//        postion = in.readInt();
//        isChoosed = in.readByte() != 0;
//    }
//
//    public static final Creator<RecordInfo> CREATOR = new Creator<RecordInfo>() {
//        @Override
//        public RecordInfo createFromParcel(Parcel in) {
//            return new RecordInfo(in);
//        }
//
//        @Override
//        public RecordInfo[] newArray(int size) {
//            return new RecordInfo[size];
//        }
//    };

}
