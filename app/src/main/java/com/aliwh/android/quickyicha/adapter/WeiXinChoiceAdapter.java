package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.WeiXinChoiceBean;
import com.aliwh.android.quickyicha.utils.ScreenUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 左侧菜单ListView的适配器
 *
 * @author liwh
 */
public class WeiXinChoiceAdapter extends BaseAdapter {

    private Context context;
    private List<WeiXinChoiceBean.ResultBean.ListBean> list;

    public WeiXinChoiceAdapter(Context context, List<WeiXinChoiceBean.ResultBean.ListBean> list) {
        this.list = list;
        this.context = context;
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
            convertView = View.inflate(context, R.layout.weixin_choice_item, null);
            // 初始化View
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(list.get(arg0).getTitle());
        holder.tv_pubTime.setText(list.get(arg0).getPubTime());
        int sideLength = ScreenUtils.dip2px(context, 50);
        x.image().bind(holder.iv_image, list.get(arg0).getThumbnails(), MyApplication.builder.build());
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.weixin_choice_image)
        private ImageView iv_image;
        @ViewInject(R.id.weixin_choice_title)
        private TextView tv_title;
        @ViewInject(R.id.weixin_choice_pubTime)
        private TextView tv_pubTime;
    }
}
