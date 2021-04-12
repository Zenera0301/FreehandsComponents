package com.dj.case_record_choose.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dj.case_record_choose.R;
import com.dj.case_record_choose.entity.CaseInfo;
import com.dj.case_record_choose.entity.RecordInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2017/3/26.
 * 购物车适配器
 */

public class CaseRecordChooseAdapter extends BaseExpandableListAdapter /**implements BaseAdapter<CaseRecordChooseAdapter.ViewHolder> **/{
    private List<CaseInfo> caseList; // 店铺列表
    private Map<Integer, List<RecordInfo>> recordList;//这个Integer对应着StoreInfo的Id，也就是店铺的Id。Map结构是：商品所属店铺，商品列表
    private Context mContext;
    private CheckInterface checkInterface;
    private ExpandInterface expandInterface;

    //设置item的点击回调接口
    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int casePosition, int position, boolean ischecked);
    }


    public void notifyDataSetChanged(Map<Integer, List<RecordInfo>> recordList) {
        this.recordList = recordList;
        super.notifyDataSetChanged();
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * ShopcatAdapter 构造函数
     *
     * @param caseList   商铺列表
     * @param recordList 商铺Id和商品列表
     * @param mContext   上下文
     */
    public CaseRecordChooseAdapter(List<CaseInfo> caseList, Map<Integer, List<RecordInfo>> recordList, Context mContext) {
        this.caseList = caseList;
        this.recordList = recordList;
        this.mContext = mContext;
    }

    /**
     * getGroupCount 获得案件数量
     *
     * @return int型数字
     */
    public int getGroupCount() {
        return caseList.size();
    }

    /**
     * getChildrenCount 获得案件中的记录数量
     *
     * @param casePosition 商铺Id
     * @return
     */
    public int getChildrenCount(int casePosition) {
        Integer groupId = caseList.get(casePosition).getId();
        return recordList.get(groupId).size();
    }


    /**
     * getGroup 获得案件对象
     *
     * @param groupPosition 商铺的Id
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return caseList.get(groupPosition);
    }

    /**
     * getChild 获得案件中记录对象
     *
     * @param groupPosition 商品的Id
     * @param childPosition 商品的Id
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<RecordInfo> childs = recordList.get(caseList.get(groupPosition).getId());
        return childs.get(childPosition);
    }

    /**
     * getGroupId 获得案件的Id
     *
     * @param groupPosition 商铺的Id
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * getChildId 获得记录的Id
     *
     * @param groupPosition 商铺的Id
     * @param childPosition 商品的Id
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 不知道这是干嘛的
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }


    /**
     * getGroupView 获得案件的View
     * 同时会初始化商铺的groupViewHolder对象 （一次性使用？）
     *
     * @param groupPosition 案件Id
     * @param isExpanded    是否是展开状态
     * @param convertView   View对象
     * @param parent        ViewGroup对象
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_shopcat_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final CaseInfo group = (CaseInfo) getGroup(groupPosition);
        groupViewHolder.storeName.setText(group.getName());

        // 看当前案件中有没有被选中的记录，如果有，变成另一种颜色，标识
        int colorAllChoosed = mContext.getColor(R.color.lightyellow);
        int colorPartChoosed = mContext.getColor(R.color.ivory);
        int colorNoneChoosed = mContext.getColor(R.color.white);
        int colorAtLast = colorNoneChoosed;
        if(group.isChoosed()){
            colorAtLast = colorAllChoosed;
        }else{
            for (RecordInfo recordInfo : Objects.requireNonNull(recordList.get(groupPosition))) {
                if(recordInfo.isChoosed()){
                    colorAtLast = colorPartChoosed;
                    break;
                }
            }
        }
        groupViewHolder.RL_item_shopcat_group.setBackgroundColor(colorAtLast); // 如果被选中就变彩色


        groupViewHolder.storeCheckBox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
                if (((CheckBox) v).isChecked()) {
                    groupViewHolder.RL_item_shopcat_group.setBackgroundColor(mContext.getColor(R.color.lightyellow)); // 如果被选中就变彩色
                } else {
                    groupViewHolder.RL_item_shopcat_group.setBackgroundColor(mContext.getColor(R.color.white)); // 如果未被选中就变回白色
                }
            }
        });

        groupViewHolder.storeCheckBox.setChecked(group.isChoosed());

        // 点击案件名那行，展开或收起它
        groupViewHolder.RL_item_shopcat_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    expandInterface.expandGroup(groupPosition, false);
                } else {
                    expandInterface.expandGroup(groupPosition, true);
                }
            }
        });

        /**【文字指的是组的按钮的文字】
         * 当我们按下ActionBar的 "编辑"按钮， 应该把所有组的文字显示"编辑",并且设置按钮为不可见
         * 当我们完成编辑后，再把组的编辑按钮设置为可见
         */
        if (group.isActionBarEditor()) {
            group.setEditor(false);
            groupViewHolder.storeEdit.setVisibility(View.GONE);
        } else {
            groupViewHolder.storeEdit.setVisibility(View.GONE);//无论如何这里要隐藏“编辑”按钮
        }

        /**
         * 思路:当我们按下组的"编辑"按钮后，组处于编辑状态，文字显示"完成"
         * 当我们点击“完成”按钮后，文字显示"编辑“,组处于未编辑状态
         */
        if (group.isEditor()) {
            groupViewHolder.storeEdit.setText("完成");
        } else {
            groupViewHolder.storeEdit.setText("编辑");
        }

        groupViewHolder.storeEdit.setOnClickListener(new GroupViewClick(group, groupPosition, groupViewHolder.storeEdit));
        return convertView;
    }

    /**
     * getChildView 获得案件中记录的view
     *
     * @param groupPosition 案件Id
     * @param childPosition 记录Id
     * @param isLastChild   是最后一个产品吗
     * @param convertView   View类型对象
     * @param parent        ViewGroup对象
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_shopcat_product, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        final RecordInfo child = (RecordInfo) getChild(groupPosition, childPosition);
        if (child != null) {
            childViewHolder.item_toggleButton.setChecked("exclude".equals(child.getType())); // 每次加载item，都去读取当前最新的设置，刷新item。这样就可以避免item复用导致的错乱
            childViewHolder.CL_item_shopcat_product.setBackgroundColor(child.isChoosed()?mContext.getColor(R.color.lightyellow):mContext.getColor(R.color.light_white)); // 如果被选中就变彩色

            childViewHolder.goodsName.setText(child.getDesc());
            childViewHolder.singleCheckBox.setChecked(child.isChoosed());
            childViewHolder.singleCheckBox.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    child.setChoosed(((CheckBox) v).isChecked());
                    childViewHolder.singleCheckBox.setChecked(((CheckBox) v).isChecked());

                    if (child.isChoosed()) {
                        childViewHolder.CL_item_shopcat_product.setBackgroundColor(mContext.getColor(R.color.lightyellow)); // 如果被选中就变彩色
                    } else {
                        childViewHolder.CL_item_shopcat_product.setBackgroundColor(mContext.getColor(R.color.light_white)); // 如果未被选中就变回灰色
                    }

                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
                }
            });


            // 通过为条目设置点击toggleButton事件触发回调
            if (mOnItemClickLitener != null) {
                // 设置整条item上子控件的监听事件：点了某个item上的某个控件，tg_module_df控件是开关按钮
                childViewHolder.item_toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemClick(view, groupPosition, childPosition, ((ToggleButton) view).isChecked());
                    }
                });
            }
        }

        return convertView;
    }


    /**
     * 记录是否可选
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 设置回调接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 回调接口中用到的接口
     */
    public interface CheckInterface {

        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param isChecked     组元素的选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param isChecked     子元素的选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 设置回调接口
     *
     * @param expandInterface
     */
    public void setExpandInterface(ExpandInterface expandInterface) {
        this.expandInterface = expandInterface;
    }

    /**
     * 回调接口中用到的接口
     * 点击案件，收起或展开
     */
    public interface ExpandInterface {
        void expandGroup(int groupPosition, boolean toExpand);
    }


    /**
     * 使某个小组处于编辑状态
     */
    private class GroupViewClick implements View.OnClickListener {
        private CaseInfo group;
        private int groupPosition;
        private TextView editor;

        public GroupViewClick(CaseInfo group, int groupPosition, TextView editor) {
            this.group = group;
            this.groupPosition = groupPosition;
            this.editor = editor;
        }

        @Override
        public void onClick(View v) {
            if (editor.getId() == v.getId()) {
                if (group.isEditor()) {
                    group.setEditor(false);
                } else {
                    group.setEditor(true);
                }
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 内部静态类 GroupViewHolder
     */
    static class GroupViewHolder {
        CheckBox storeCheckBox;
        TextView storeName;
        TextView storeEdit;
        RelativeLayout RL_item_shopcat_group;

        public GroupViewHolder(View view) {
            storeCheckBox = view.findViewById(R.id.store_checkBox);
            storeName = view.findViewById(R.id.store_name);
            storeEdit = view.findViewById(R.id.store_edit);
            RL_item_shopcat_group = view.findViewById(R.id.RL_item_shopcat_group);
        }
    }

    /**
     * 内部静态类 ChildViewHolder
     */
    static class ChildViewHolder {
        CheckBox singleCheckBox;
        TextView goodsName;
        TextView delGoods;
        ConstraintLayout CL_item_shopcat_product;
        ToggleButton item_toggleButton;

        public ChildViewHolder(View view) {
            singleCheckBox = view.findViewById(R.id.single_checkBox);
            goodsName = view.findViewById(R.id.goods_name);
            delGoods = view.findViewById(R.id.del_goods);
            CL_item_shopcat_product = view.findViewById(R.id.CL_item_shopcat_product);
            item_toggleButton = view.findViewById(R.id.item_toggleButton);
        }
    }


}
