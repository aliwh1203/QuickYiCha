package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.MenuBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class MenuThreeAdapter extends BaseAdapter {

    private Context context;
    private List<MenuBean.ResultBean.ListBean> foodDatas;

    public MenuThreeAdapter(Context context, List<MenuBean.ResultBean.ListBean> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }


    @Override
    public int getCount() {
        return foodDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuBean.ResultBean.ListBean subcategory = foodDatas.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.menu_three_item, null);
            viewHold = new ViewHold();
            // 初始化View
            x.view().inject(viewHold, convertView);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(subcategory.getName());
        x.image().bind(viewHold.iv_icon, subcategory.getThumbnail(), MyApplication.builder.build());
        return convertView;


    }

    private static class ViewHold {
        @ViewInject(R.id.item_home_name)
        private TextView tv_name;
        @ViewInject(R.id.item_album)
        private ImageView iv_icon;
    }

}
