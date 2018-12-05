package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.HealthBean;
import com.aliwh.android.quickyicha.bean.WeiXinChoiceBean;
import com.aliwh.android.quickyicha.utils.ScreenUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 健康知识查询的适配器
 *
 * @author liwh
 */
public class HealthKnowledgeAdapter extends BaseAdapter {

    private Context context;
    private List<HealthBean.ResultBean.ListBean> list;

    public HealthKnowledgeAdapter(Context context, List<HealthBean.ResultBean.ListBean> list) {
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
            convertView = View.inflate(context, R.layout.health_knowledge_item, null);
            // 初始化View
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(list.get(arg0).getTitle());

        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.health_knowledge_title)
        private TextView tv_title;
    }
}
