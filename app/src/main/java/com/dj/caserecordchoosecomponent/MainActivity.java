package com.dj.caserecordchoosecomponent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dj.case_record_choose.entity.CaseInfo;
import com.dj.case_record_choose.entity.CaseRecordChooseBean;
import com.dj.case_record_choose.entity.RecordInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(path="/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private List<CaseInfo> caseList; // 案件的列表
    private Map<Integer, List<RecordInfo>> recordListMap; // 记录的列表 Integer是案件ID， List<RecordInfo>是该案件下的所有记录的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *  遵循适配器的数据列表填充原则，组元素被放在一个list中，对应着组元素的下辖子元素被放在Map中
     *  其Key是组元素的Id
     */
    void initShowData(){
        caseList = new ArrayList<CaseInfo>();
        recordListMap = new HashMap<Integer, List<RecordInfo>>();

        // 拿到caseList和recordList
        caseList.add(new CaseInfo(0 + "", "案件1"));
        List<RecordInfo> records1 = new ArrayList<>();
        records1.add(new RecordInfo(0 + "-" + 0, caseList.get(0).getName() + "的第" + (0 + 1) + "个子项", caseList.get(0).getName() + "的第" + (0 + 1) + "个子项"));
        records1.get(0).setCases(caseList.get(0).getName()); // 将案件名与记录匹配起来
        records1.get(0).setType(getString(com.dj.case_record_choose.R.string.exclude));
        recordListMap.put(caseList.get(0).getId(), records1);
        for (int i = 1; i < 10; i++) {
            caseList.add(new CaseInfo(i + "", "案件" + (i + 1)));
            List<RecordInfo> records = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                //i-j 就是商品的id， 对应着第几个店铺的第几个商品，1-1 就是第一个店铺的第一个商品
                records.add(new RecordInfo(i + "-" + j, caseList.get(i).getName() + "的第" + (j + 1) + "个子项", caseList.get(i).getName() + "的第" + (j + 1) + "个子项"));
                records.get(j).setCases(caseList.get(i).getName()); // 将案件名与记录匹配起来
            }
            recordListMap.put(caseList.get(i).getId(), records);
        }
        Log.i(TAG, "initData: caseList="+caseList.toString());
        Log.i(TAG, "initData: recordList="+ recordListMap.toString());
    }

    public void jump2CaseRecordChoose(View view) {
        initShowData();
        ARouter.getInstance()
                .build("/case/Case_MainActivity")
                .withObject("caseList", caseList)
                .withObject("recordListMap", recordListMap)
                .navigation();
    }
}