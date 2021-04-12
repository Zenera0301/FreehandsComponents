package com.dj.caserecordchoosemodule.activity;

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


import com.alibaba.android.arouter.facade.annotation.Route;
import com.dj.caserecordchoosemodule.R;
import com.dj.caserecordchoosemodule.adapter.CaseRecordChooseAdapter;
import com.dj.caserecordchoosemodule.entity.CaseRecordChooseBean;
import com.dj.caserecordchoosemodule.entity.RecordInfo;
import com.dj.caserecordchoosemodule.entity.CaseInfo;
import com.dj.caserecordchoosemodule.utils.UtilTool;
import com.dj.caserecordchoosemodule.utils.UtilsLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import in.srain.cube.views.ptr.PtrFrameLayout;

@Route(path = "/case/Case_MainActivity")
public class Case_MainActivity extends AppCompatActivity implements CaseRecordChooseAdapter.CheckInterface, CaseRecordChooseAdapter.ExpandInterface {
    ExpandableListView listView;
    CheckBox allCheckBox;
    TextView gotoAnaly;
    LinearLayout orderInfo;
    LinearLayout llCart;
    PtrFrameLayout mPtrFrame;
    TextView shoppingcatNum;
    Button actionBarEdit;
    LinearLayout empty_shopcart;
    ArrayList<CaseRecordChooseBean> beanLists = new ArrayList<>();

    private Context mcontext;
    private int mtotalCount = 0;
    private CaseRecordChooseAdapter adapter;
    private List<CaseInfo> caseList; // 案件的列表
    private Map<Integer, List<RecordInfo>> recordList; // 记录的列表 Integer是案件ID， List<RecordInfo>是该案件下的所有记录的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_case);
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
        // 隐藏标题栏
        if (getSupportActionBar() != null) {
            // 去掉阴影
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
     * 模拟数据
     * 遵循适配器的数据列表填充原则，组元素被放在一个list中，对应着组元素的下辖子元素被放在Map中
     * 其Key是组元素的Id
     */
    private void initData() {
        mcontext = this;
        caseList = new ArrayList<CaseInfo>();
        recordList = new HashMap<Integer, List<RecordInfo>>();

        // 拿到caseList和recordList
        caseList.add(new CaseInfo(0 + "", "案件1"));
        List<RecordInfo> records1 = new ArrayList<>();
        records1.add(new RecordInfo(0 + "-" + 0, caseList.get(0).getName() + "的第" + (0 + 1) + "个子项", caseList.get(0).getName() + "的第" + (0 + 1) + "个子项"));
        records1.get(0).setCases(caseList.get(0).getName()); // 将案件名与记录匹配起来
        records1.get(0).setType(getString(R.string.exclude));
        recordList.put(caseList.get(0).getId(), records1);
        for (int i = 1; i < 10; i++) {
            caseList.add(new CaseInfo(i + "", "案件" + (i + 1)));
            List<RecordInfo> records = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                //i-j 就是商品的id， 对应着第几个店铺的第几个商品，1-1 就是第一个店铺的第一个商品
                records.add(new RecordInfo(i + "-" + j, caseList.get(i).getName() + "的第" + (j + 1) + "个子项", caseList.get(i).getName() + "的第" + (j + 1) + "个子项"));
                records.get(j).setCases(caseList.get(i).getName()); // 将案件名与记录匹配起来
            }
            recordList.put(caseList.get(i).getId(), records);
        }
//        Log.i(TAG, "initData: caseList="+caseList.toString());
//        Log.i(TAG, "initData: recordList="+recordList.toString());
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initEvents() {
        actionBarEdit.setVisibility(View.GONE);//隐藏最头上的编辑按钮
        adapter = new CaseRecordChooseAdapter(caseList, recordList, mcontext);
        adapter.setCheckInterface(this); // 设置案件复选框的接口
        adapter.setExpandInterface(this); // 设置案件layout点击展开或收起的接口
        // 设置item被点击后的事件
        adapter.setOnItemClickLitener(new CaseRecordChooseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int casePosition, int recordPosition, boolean ischecked) {
                Log.i(TAG, "onItemClick: casePosition="+casePosition+" recordPosition="+recordPosition + " ischecked=" +ischecked);
                String targetType = ischecked ? getString(R.string.exclude) : getString(R.string.include);
                ModifyItemType(casePosition, recordPosition, targetType);
            }
        });
        listView.setGroupIndicator(null); // 设置属性 GroupIndicator 去掉向下箭头
        listView.setAdapter(adapter);

        listView.expandGroup(0);
        for (int i = 1; i < adapter.getGroupCount(); i++) {
            // listView.expandGroup(i); // 将ExpandableListView以展开的方式显示
            // 看现在是否是展开状态
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
                UtilsLog.i("childCount=" + view.getChildCount());//返回的是显示层面上的所包含的子view的个数
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
     * 修改Item的Type是包含还是排除
     * @param casePosition  该item所属的案件，位置
     * @param position  该item的位置
     * @param targetType  目标类别
     */
    private void ModifyItemType(int casePosition, int position, String targetType) {
        recordList.get(casePosition).get(position).setType(targetType);

        adapter.notifyDataSetChanged(recordList);
    }

    /**
     * 检查组复选框的选择情况
     * @param groupPosition 组元素的位置
     * @param isChecked     组元素的选中与否
     *                      思路:组元素被选中了，那么下辖全部的子元素也被选中
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        CaseInfo group = caseList.get(groupPosition);
        List<RecordInfo> child = recordList.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setChoosed(isChecked);
            if(isChecked) {

            } else {

            }
        }
        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     *
     * @param groupPosition 组元素的位置
     * @param childPosition 子元素的位置
     * @param isChecked     子元素的选中与否
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true; // 判断该组下面的所有子元素是否处于同一状态
        CaseInfo group = caseList.get(groupPosition);
        List<RecordInfo> child = recordList.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            //不选全中
            if (child.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }

        if (allChildSameState) {
            group.setChoosed(isChecked);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
        } else {
            group.setChoosed(false); // 否则一律视为未选中
        }

        if (isCheckAll()) {
            allCheckBox.setChecked(true); //全选
        } else {
            allCheckBox.setChecked(false); //反选
        }

        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * 当前案件被点击，需要展开或者收起
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
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    private void calulate() {
        mtotalCount = 0;
        for (int i = 0; i < caseList.size(); i++) {
            CaseInfo group = caseList.get(i);
            List<RecordInfo> child = recordList.get(group.getId());
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
     * 设置购物车的数量
     */
    private void setCartNum() {
        if (mtotalCount == 0) { // 购物车已经清空
            gotoAnaly.setText("请选择");
            shoppingcatNum.setText(getString(R.string.case_choose_init));
        } else {
            gotoAnaly.setText("去分析");
            shoppingcatNum.setText("案件选择(" + mtotalCount + ")");
        }
    }

    /**
     * 全选和全不选
     * @param view
     */
    public void selectAll(View view) {
        doCheckAll();
    }

    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {
        for (int i = 0; i < caseList.size(); i++) {
            CaseInfo group = caseList.get(i);
            group.setChoosed(allCheckBox.isChecked());
            List<RecordInfo> child = recordList.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChoosed(allCheckBox.isChecked());//这里出现过错误
            }
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (CaseInfo group : caseList) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    private static final String TAG = "Case_MainActivity";
    /**
     * 跳转到新的界面，分析案件
     */
    public void analyCaseRecord(View view) {
        AlertDialog dialog;
        if (mtotalCount == 0) {
            UtilTool.toast(mcontext, "请选择要分析的案件");
            return;
        }
        dialog = new AlertDialog.Builder(mcontext).create();
        dialog.setMessage("总计:" + mtotalCount + "个案件");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "分析", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                beanLists.clear();
                for (int i = 0; i < caseList.size(); i++) {
                    CaseInfo group = caseList.get(i);
                    List<RecordInfo> child = recordList.get(group.getId());
                    for (int j = 0; j < child.size(); j++) {
                        RecordInfo record = child.get(j);
                        if (record.isChoosed()) {
                            beanLists.add(new CaseRecordChooseBean(group.getName(), record.getName(), record.getType())); // 添加记录
                        }
                    }
                }
                Log.i(TAG, "onClick:beanLists="+beanLists.toString());
                return;
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
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
        recordList.clear();
        caseList.clear();
        mtotalCount = 0;
    }

}
