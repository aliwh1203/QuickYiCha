package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
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
 * 右侧主界面ListView的适配器
 *
 * @author liwh
 */
public class MenuTwoAdapter extends BaseAdapter {

    private Context context;
    private List<MenuType.ResultBean.ChildsBeanX.ChildsBean> foodDatas;

    public MenuTwoAdapter(Context context, List<MenuType.ResultBean.ChildsBeanX.ChildsBean> foodDatas) {
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
        MenuType.ResultBean.ChildsBeanX.ChildsBean childsBean = foodDatas.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.menu_two_item, null);
            viewHold = new ViewHold();
            // 初始化View
            x.view().inject(viewHold, convertView);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.blank.setText(childsBean.getCategoryInfo().getName());
        return convertView;
    }

    private static class ViewHold {
        @ViewInject(R.id.blank)
        private TextView blank;
    }

}
