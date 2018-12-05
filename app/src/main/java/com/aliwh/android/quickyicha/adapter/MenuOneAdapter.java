package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.MenuType;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 左侧菜单ListView的适配器
 *
 * @author liwh
 */
public class MenuOneAdapter extends BaseAdapter {

    private Context context;
    private int selectItem = 0;
    private List<MenuType.ResultBean.ChildsBeanX.CategoryInfoBeanX> list;

    public MenuOneAdapter(Context context, List<MenuType.ResultBean.ChildsBeanX.CategoryInfoBeanX> list) {
        this.list = list;
        this.context = context;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.menu_one_item, null);
            // 初始化View
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (arg0 == selectItem) {
            holder.tv_name.setBackgroundColor(Color.WHITE);
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.tv_name.setBackgroundColor(context.getResources().getColor(R.color.background));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.tv_name.setText(list.get(arg0).getName());
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.item_name)
        private TextView tv_name;
    }
}
