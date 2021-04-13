package com.dj.case_record_choose.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class CaseInfo {
    public String id;
    public String name;
    public boolean isChoosed;

    public CaseInfo() {
    }

    public CaseInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return Integer.parseInt(id);
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


    @Override
    public String toString() {
        return "\nCaseInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }
}
