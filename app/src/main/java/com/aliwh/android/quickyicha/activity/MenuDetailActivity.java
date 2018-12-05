package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.MenuBean;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 菜谱详情信息
 */
@ContentView(R.layout.activity_menu_detail)
public class MenuDetailActivity extends BaseActivity {

    private MenuBean.ResultBean.ListBean listBean;

    @ViewInject(R.id.menu_detail_name)
    private TextView menuDetailName;

    @ViewInject(R.id.menu_detail_type)
    private TextView menuDetailType;

    @ViewInject(R.id.menu_detail_make_title)
    private TextView menuDetailMakeType;

    @ViewInject(R.id.menu_detail_make_sumary)
    private TextView menuDetailMakeSumary;

    @ViewInject(R.id.recipe_img)
    private ImageView recipeImg;

    @ViewInject(R.id.menu_detail_make_ingredients)
    private TextView menuDetailMakeIngredients;

    @ViewInject(R.id.box)
    private LinearLayout boxLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBean = (MenuBean.ResultBean.ListBean) getIntent().getSerializableExtra("bean");
        menuDetailName.setText(listBean.getName());
        menuDetailType.setText("所属类别：" + listBean.getCtgTitles());
        menuDetailMakeType.setText(listBean.getRecipe().getTitle());
        menuDetailMakeSumary.setText("简介：" + listBean.getRecipe().getSumary());
        menuDetailMakeIngredients.setText("原料：" + listBean.getRecipe().getIngredients());
        if (TextUtils.isEmpty(listBean.getRecipe().getImg())) {
            recipeImg.setVisibility(View.GONE);
        } else {
            x.image().bind(recipeImg, listBean.getRecipe().getImg(), MyApplication.builder.build());
        }

        String medthod = listBean.getRecipe().getMethod();
        Log.e("allwhere", "result: " + medthod);
        Gson gson = new Gson();
        MenuBean.ResultBean.ListBean.RecipeBean.MethodBean[] methodBeenArray = gson.fromJson(medthod, MenuBean.ResultBean.ListBean.RecipeBean.MethodBean[].class);
        for (MenuBean.ResultBean.ListBean.RecipeBean.MethodBean methodBean : methodBeenArray) {
            View mView = View.inflate(this, R.layout.item_menu_detail, null);
            TextView textView = (TextView) mView.findViewById(R.id.method_text);
            ImageView imgView = (ImageView) mView.findViewById(R.id.method_img);
            textView.setText(methodBean.getStep());
            if (TextUtils.isEmpty(methodBean.getImg())) {
                imgView.setVisibility(View.GONE);
            } else {
                x.image().bind(imgView, methodBean.getImg(), MyApplication.builder.build());
            }
            boxLayout.addView(mView);
        }
        Log.e("allwhere", "onResponse: " + listBean.toString());
    }


    // 返回
    @Event(R.id.back_btn)
    private void onBack(View v) {
        finish();
    }
}
