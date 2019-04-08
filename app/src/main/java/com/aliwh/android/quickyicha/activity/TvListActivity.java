package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.adapter.TvItemProgramAdapter;

public class TvListActivity extends BaseActivity {
    private ListView mListView;
    private Button mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_list);
        mListView = (ListView) findViewById(R.id.lv_program);
        mBackBtn = (Button) findViewById(R.id.back_btn);
        mListView.setAdapter(new TvItemProgramAdapter(TvListActivity.this));
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TvListActivity.this.finish();
            }
        });
    }
}
