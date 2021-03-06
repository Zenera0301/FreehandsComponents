package com.dj.case_record_choose.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dj.case_record_choose.R;
import com.dj.case_record_choose.adapter.CaseRecordChooseAdapter;
import com.dj.case_record_choose.entity.CaseInfo;
import com.dj.case_record_choose.entity.CaseRecordChooseBean;
import com.dj.case_record_choose.entity.RecordInfo;
import com.dj.case_record_choose.utils.UtilTool;
import com.dj.case_record_choose.utils.UtilsLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import in.srain.cube.views.ptr.PtrFrameLayout;

@Route(path = "/case/Case_MainActivity")
public class Case_MainActivity extends AppCompatActivity implements CaseRecordChooseAdapter.CheckInterface, CaseRecordChooseAdapter.ExpandInterface {
    private static final String TAG = "Case_MainActivity";
    ExpandableListView listView;
    CheckBox allCheckBox;
    TextView gotoAnaly;
    LinearLayout orderInfo;
    LinearLayout llCart;
    PtrFrameLayout mPtrFrame;
    TextView shoppingcatNum;
    Button actionBarEdit;
    LinearLayout empty_shopcart;
    List<CaseRecordChooseBean> beanLists = new ArrayList<>();

    private Context mcontext;
    private int mtotalCount = 0;
    private CaseRecordChooseAdapter adapter;

    @Autowired
    List<CaseInfo> caseList; // ???????????????

    @Autowired
    Map<Integer, List<RecordInfo>> recordListMap; // ??????????????? Integer?????????ID??? List<RecordInfo>???????????????????????????????????????


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_case);

        // ????????????
        ARouter.getInstance().inject(this);
//        Log.i(TAG, "onCreate: caseList="+caseList);
//        Log.i(TAG, "onCreate: recordList="+ recordListMap);

        initView();
        initActionBar();
        initData();
        initEvents();
    }
    private void initView() {
        mPtrFrame = findViewById(R.id.mPtrframe);
        llCart = findViewById(R.id.ll_cart);
        orderInfo = findViewById(R.id.order_info);
        gotoAnaly = findViewById(R.id.go_pay);
        allCheckBox = findViewById(R.id.all_checkBox);
        listView = findViewById(R.id.listView);
    }

    private void initActionBar() {
        // ???????????????
        if (getSupportActionBar() != null) {
            // ????????????
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            View view = getLayoutInflater().inflate(R.layout.acitonbar, null);
            findView(view);
            getSupportActionBar().setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ActionBar.LayoutParams lp = (ActionBar.LayoutParams) view.getLayoutParams();
            lp.gravity = Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;
            getSupportActionBar().setCustomView(view, lp);
        }
    }
    private void findView(View view) {
        shoppingcatNum = (TextView) view.findViewById(R.id.shoppingcat_num);
        actionBarEdit = (Button) view.findViewById(R.id.actionBar_edit);
        empty_shopcart = (LinearLayout) findViewById(R.id.layout_empty_shopcart);
    }

    /**
     * ???????????????
     */
    private void initData() {
        mcontext = this;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initEvents() {
        actionBarEdit.setVisibility(View.GONE);//??????????????????????????????
        adapter = new CaseRecordChooseAdapter(caseList, recordListMap, mcontext);
        adapter.setCheckInterface(this); // ??????????????????????????????
        adapter.setExpandInterface(this); // ????????????layout??????????????????????????????
        // ??????item?????????????????????
        adapter.setOnItemClickLitener(new CaseRecordChooseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int casePosition, int recordPosition, boolean ischecked) {
                Log.i(TAG, "onItemClick: casePosition="+casePosition+" recordPosition="+recordPosition + " ischecked=" +ischecked);
                String targetType = ischecked ? getString(R.string.exclude) : getString(R.string.include);
                ModifyItemType(casePosition, recordPosition, targetType);
            }
        });
        listView.setGroupIndicator(null); // ???????????? GroupIndicator ??????????????????
        listView.setAdapter(adapter);

        listView.expandGroup(0);
        for (int i = 1; i < adapter.getGroupCount(); i++) {
            // listView.expandGroup(i); // ???ExpandableListView????????????????????????
            // ??????????????????????????????
            if(listView.isGroupExpanded(i)){
                listView.collapseGroup(i);
            } else {
                listView.expandGroup(i);
            }
        }

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int firstVisiablePostion = view.getFirstVisiblePosition();
                int top = -1;
                View firstView = view.getChildAt(firstVisibleItem);
                UtilsLog.i("childCount=" + view.getChildCount());//?????????????????????????????????????????????view?????????
                if (firstView != null) {
                    top = firstView.getTop();
                }
                UtilsLog.i("firstVisiableItem=" + firstVisibleItem + ",fistVisiablePosition=" + firstVisiablePostion + ",firstView=" + firstView + ",top=" + top);
                if (firstVisibleItem == 0 && top == 0) {
                    mPtrFrame.setEnabled(true);
                } else {
                    mPtrFrame.setEnabled(false);
                }
            }
        });
    }

    /**
     * ??????Item???Type?????????????????????
     * @param casePosition  ???item????????????????????????
     * @param position  ???item?????????
     * @param targetType  ????????????
     */
    private void ModifyItemType(int casePosition, int position, String targetType) {
        recordListMap.get(casePosition).get(position).setType(targetType);

        adapter.notifyDataSetChanged(recordListMap);
    }

    /**
     * ?????????????????????????????????
     * @param groupPosition ??????????????????
     * @param isChecked     ????????????????????????
     *                      ??????:??????????????????????????????????????????????????????????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        CaseInfo group = caseList.get(groupPosition);
        List<RecordInfo> child = recordListMap.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setChoosed(isChecked);
            if(isChecked) {

            } else {

            }
        }
        if (isCheckAll()) {
            allCheckBox.setChecked(true);//??????
        } else {
            allCheckBox.setChecked(false);//??????
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     *
     * @param groupPosition ??????????????????
     * @param childPosition ??????????????????
     * @param isChecked     ????????????????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true; // ????????????????????????????????????????????????????????????
        CaseInfo group = caseList.get(groupPosition);
        List<RecordInfo> child = recordListMap.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            //????????????
            if (child.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }

        if (allChildSameState) {
            group.setChoosed(isChecked);//??????????????????????????????????????????????????????????????????????????????????????????
        } else {
            group.setChoosed(false); // ???????????????????????????
        }

        if (isCheckAll()) {
            allCheckBox.setChecked(true); //??????
        } else {
            allCheckBox.setChecked(false); //??????
        }

        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * ????????????????????????????????????????????????
     * @param groupPosition
     * @param toExpand
     */
    @Override
    public void expandGroup(int groupPosition, boolean toExpand) {
        if(toExpand){
            listView.expandGroup(groupPosition);
        }else{
            listView.collapseGroup(groupPosition);
        }
    }

    /**
     * ????????????????????????????????????
     * 1.?????????????????????,??????
     * 2.?????????????????????????????????????????????????????????????????????????????????
     * 3.???textView????????????
     */
    private void calulate() {
        mtotalCount = 0;
        for (int i = 0; i < caseList.size(); i++) {
            CaseInfo group = caseList.get(i);
            List<RecordInfo> child = recordListMap.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                RecordInfo record = child.get(j);
                if (record.isChoosed()) {
                    mtotalCount++;
                }
            }
        }
        setCartNum();
    }

    /**
     * ????????????????????????
     */
    private void setCartNum() {
        if (mtotalCount == 0) { // ?????????????????????
            gotoAnaly.setText("?????????");
            shoppingcatNum.setText(getString(R.string.case_choose_init));
        } else {
            gotoAnaly.setText("?????????");
            shoppingcatNum.setText("????????????(" + mtotalCount + ")");
        }
    }

    /**
     * ??????????????????
     * @param view
     */
    public void selectAll(View view) {
        doCheckAll();
    }

    /**
     * ???????????????
     * ???????????????????????????????????????
     */
    private void doCheckAll() {
        for (int i = 0; i < caseList.size(); i++) {
            CaseInfo group = caseList.get(i);
            group.setChoosed(allCheckBox.isChecked());
            List<RecordInfo> child = recordListMap.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChoosed(allCheckBox.isChecked());//?????????????????????
            }
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * @return ???????????????????????????
     */
    private boolean isCheckAll() {
        for (CaseInfo group : caseList) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * ????????????????????????????????????
     */
    public void analyCaseRecord(View view) {
        AlertDialog dialog;
        if (mtotalCount == 0) {
            UtilTool.toast(mcontext, "???????????????????????????");
            return;
        }
        dialog = new AlertDialog.Builder(mcontext).create();
        dialog.setMessage("??????:" + mtotalCount + "?????????");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                beanLists.clear();
                for (int i = 0; i < caseList.size(); i++) {
                    CaseInfo group = caseList.get(i);
                    List<RecordInfo> child = recordListMap.get(group.getId());
                    for (int j = 0; j < child.size(); j++) {
                        RecordInfo record = child.get(j);
                        if (record.isChoosed()) {
                            beanLists.add(new CaseRecordChooseBean(group.getName(), record.getName(), record.getType())); // ????????????
                        }
                    }
                }
                Log.i(TAG, "onClick:beanLists="+beanLists.toString());

                ARouter.getInstance().build("/app/SecondActivity")
                        .withObject("beanLists", beanLists)
                        .navigation();
                return;
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter = null;
        recordListMap.clear();
        caseList.clear();
        mtotalCount = 0;
    }

}
