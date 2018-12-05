package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.activity.CarSeriesActivity;
import com.aliwh.android.quickyicha.bean.CarBrand;
import com.aliwh.android.quickyicha.utils.PingYinUtil;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author liwh
 * @date 2018/4/27
 */

public class CarBrandAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<CarBrand.ResultBean> list;
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置

    public CarBrandAdapter(Context context, List<CarBrand.ResultBean> list) {
        this.mContext = context;
        this.list = list;
        alphaIndexer = new HashMap<String, Integer>();
        for (int i = 0; i < list.size(); i++) {
            // 获取当前汉语拼音首字母
            String currentStr = getAlpha(PingYinUtil.getPingYin(list.get(i).getName()));

            Log.e("CarBrandListAdapter", "CarBrandListAdapter: " + PingYinUtil.getPingYin(list.get(i).getName()));
            // 上一个汉语拼音首字母，如果不存在为" "
            String previewStr = (i - 1) >= 0 ? getAlpha(PingYinUtil.getPingYin(list.get(i - 1).getName())) : " ";
            if (!previewStr.equals(currentStr)) {
                String name = getAlpha(PingYinUtil.getPingYin(list.get(i).getName()));
                alphaIndexer.put(name, i);
            }
        }
    }

    public HashMap<String, Integer> getAlphaIndexer() {
        return alphaIndexer;
    }

    public void setAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
        this.alphaIndexer = alphaIndexer;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item, null);
            groupHolder = new GroupHolder();
            groupHolder.alpha = (TextView) convertView.findViewById(R.id.alpha);
            groupHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        String currentStr = getAlpha(PingYinUtil.getPingYin(list.get(groupPosition).getName()));
        if (groupPosition > 0) {
            String previewStr = (groupPosition - 1) >= 0 ? getAlpha(PingYinUtil.getPingYin(list.get(groupPosition - 1).getName())) : " ";
            if (!previewStr.equalsIgnoreCase(currentStr)) {
                groupHolder.alpha.setVisibility(View.VISIBLE);
                groupHolder.alpha.setText(currentStr);
            } else {
                groupHolder.alpha.setVisibility(View.GONE);
            }

        } else {
            groupHolder.alpha.setVisibility(View.VISIBLE);
            groupHolder.alpha.setText(currentStr);
        }
        groupHolder.name.setText(list.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.child_list_item, null);
            childHolder = new ChildHolder();
            childHolder.childCarName = (TextView) convertView.findViewById(R.id.child_car_name);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        //设置数据
        final CarBrand.ResultBean.SonBean sonBean = getGroup(groupPosition).getSon().get(childPosition);
        childHolder.childCarName.setText(sonBean.getCar() + sonBean.getType());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CarSeriesActivity.class);
                intent.putExtra("type", sonBean.getType());
                mContext.startActivity(intent);
            }
        });
        return convertView;

    }

    class GroupHolder {
        TextView alpha; // 首字母标题
        TextView name; // 汽车品牌名字

    }

    class ChildHolder {
        TextView childCarName; // 子汽车品牌名字
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSon().size();
    }

    @Override
    public CarBrand.ResultBean getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public CarBrand.ResultBean.SonBean getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getSon().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "定位";
        } else if (str.equals("1")) {
            return "全部";
        } else {
            return "#";
        }
    }

}
