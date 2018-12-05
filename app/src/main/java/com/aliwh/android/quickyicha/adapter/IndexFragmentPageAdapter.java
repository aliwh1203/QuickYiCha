package com.aliwh.android.quickyicha.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aliwh.android.quickyicha.bean.WeiXinChoiceType;
import com.aliwh.android.quickyicha.fragment.PageFragment;

import java.util.List;

/**
 * @author liwh
 * @date 2018/4/25
 */

public class IndexFragmentPageAdapter extends FragmentPagerAdapter {

    private List<WeiXinChoiceType.ResultBean> list;

    public IndexFragmentPageAdapter(FragmentManager fm, List<WeiXinChoiceType.ResultBean> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        PageFragment pageFragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cid", list.get(position).getCid());
        //fragment保存参数，传入一个Bundle对象
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
