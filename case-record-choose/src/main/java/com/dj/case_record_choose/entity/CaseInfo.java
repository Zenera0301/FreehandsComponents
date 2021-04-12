package com.dj.case_record_choose.entity;

/**
 * Created by Administrator on 2017/3/26.
 * 店铺信息
 */

public class CaseInfo {
    private String id;
    private String name;
    private boolean isChoosed;
    private boolean isEditor; //自己对该组的编辑状态
    private boolean ActionBarEditor;// 全局对该组的编辑状态
    private int flag;

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

    public boolean isEditor() {
        return isEditor;
    }

    public void setEditor(boolean editor) {
        isEditor = editor;
    }
    public boolean isActionBarEditor() {
        return ActionBarEditor;
    }

    public void setActionBarEditor(boolean actionBarEditor) {
        ActionBarEditor = actionBarEditor;
    }

    @Override
    public String toString() {
        return "\nCaseInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isChoosed=" + isChoosed +
                ", isEditor=" + isEditor +
                ", ActionBarEditor=" + ActionBarEditor +
                ", flag=" + flag +
                '}';
    }
}
