package com.dj.caserecordchoose;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

// Add annotations on pages that support routing (required)
// The path here needs to pay attention to need at least two levels : /xx/xx
@Route(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jump2CaseRecordChoose(View view) {
        ARouter.getInstance().build("/case/Case_MainActivity").navigation();
    }
}