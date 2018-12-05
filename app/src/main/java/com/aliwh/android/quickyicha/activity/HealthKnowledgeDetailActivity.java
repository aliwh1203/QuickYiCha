package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.HealthBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 健康知识详情
 */
@ContentView(R.layout.activity_health_detail)
public class HealthKnowledgeDetailActivity extends BaseActivity {

    @ViewInject(R.id.health_knowledge_title_tv)
    private TextView healthKnowledgeTitle;
    @ViewInject(R.id.health_knowledge_content)
    private TextView healthKnowledgeContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HealthBean.ResultBean.ListBean listBean = (HealthBean.ResultBean.ListBean) getIntent().getSerializableExtra("bean");
        healthKnowledgeTitle.setText(listBean.getTitle());
        healthKnowledgeContent.setText(listBean.getContent());
    }


    // 返回
    @Event(R.id.back_btn)
    private void onBack(View v) {
        finish();
    }
}
