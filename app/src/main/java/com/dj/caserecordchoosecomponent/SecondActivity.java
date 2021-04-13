package com.dj.caserecordchoosecomponent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dj.case_record_choose.entity.CaseRecordChooseBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/app/SecondActivity")
public class SecondActivity extends AppCompatActivity {


    @Autowired
    List<CaseRecordChooseBean> beanLists; // 这里不能写 = new ArrayList();

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ARouter.getInstance().inject(this);
        Log.i(TAG, "onCreate: beanLists=" + beanLists.toString());
    }
}