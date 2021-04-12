package com.dj.case_record_choose.entity;

/**
 * Created by DingJing on 2021/4/9
 * Contact me: 847467911@qq.com
 * Describe: 想要哪些信息，就在这边添加。根据这些信息去数据库中读取并操作相应数据。
 * 如：读取记录1和记录2用于并，读取记录3用于差
 */
public class CaseRecordChooseBean {
    String caseName;
    String recordName;
    String type; // 包含 include ，排除 exclude

    @Override
    public String toString() {
        return "\nCaseRecordChooseBean{" +
                "caseName='" + caseName + '\'' +
                ", recordName='" + recordName + '\'' +
                ", type='" + type + '\'' +
                "}";
    }

    public CaseRecordChooseBean(String caseName, String recordName, String type) {
        this.caseName = caseName;
        this.recordName = recordName;
        this.type = type;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
