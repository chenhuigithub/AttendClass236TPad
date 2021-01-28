package com.example.teaching236pad.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.model.Resource;
import com.example.teaching236pad.model.Test;
import com.example.teaching236pad.util.StringUtils;

import java.util.List;

/**
 * 题目适配器
 *
 * @author chenhui 2021.01.27
 */
public class TestAdapter extends BaseListAdapter<Test> {
    private int currentPos = 0;// 当前位置，默认是首项
    private List<Test> dataList;//资源列表

    private Resources res;// 资源工具

    public TestAdapter(Context context, List<Test> dataList) {
        super(context, dataList);

        this.dataList = dataList;
    }

    /**
     * 设置当前位置
     *
     * @param pos
     */
    public void setCurrentPos(int pos) {
        this.currentPos = pos;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.layout_v_item_for_test;
    }

    @Override
    protected void doAssignValueForView(int position, View resultView, Test dataObj) {
        TextView tvTest = (TextView) resultView.findViewById(R.id.tv_content_layout_v_item_for_test);
        tvTest.setText("1.下列各项中字形无误的一项是(　　)" + StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                "A．百侣　　磅礴　　橘子洲　　浪揭飞舟" + StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                "B．绚丽　　遒劲　　竟自由　　满江碧透" + StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                "C．粪土　　萧瑟　　岁月稠　　鱼翔浅底" + StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                "D．上阙　　碣石　　万户侯　　层林尽染");
    }
}
