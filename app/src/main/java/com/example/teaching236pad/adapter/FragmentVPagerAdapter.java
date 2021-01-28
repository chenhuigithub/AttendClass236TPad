package com.example.teaching236pad.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 为fragment布局量身定做的ViewPager适配器
 *
 * @author zhaochenhui
 */
public class FragmentVPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public FragmentVPagerAdapter(FragmentManager manager, List<Fragment> fragmentList) {
        super(manager);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 如果注释这行，那么不管怎么切换，page都不会被销毁
        // super.destroyItem(container, position, object);
    }
}
