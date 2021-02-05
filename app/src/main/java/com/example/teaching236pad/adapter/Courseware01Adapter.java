package com.example.teaching236pad.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.model.Courseware;

import java.util.List;

/**
 * 课件适配器
 *
 * @author chenhui 2021.02.03
 */
public class Courseware01Adapter extends BaseListAdapter<Courseware> {
    private int currentPos = 0;// 当前位置，默认是首项
    private Resources res;// 资源工具

    public Courseware01Adapter(Context context, List<Courseware> dataList) {
        super(context, dataList);
        res = context.getResources();
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
        return R.layout.layout_v_item_for_courseware;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void doAssignValueForView(int position, View resultView,
                                        Courseware dataObj) {
        TextView tv = (TextView) resultView;
        // 目录名称
        String catalogName = dataObj.getName();
        tv.setText(catalogName);

        if (this.currentPos == position) {// 选中项
            tv.setBackgroundColor(res.getColor(R.color.green01));
            tv.setTextColor(res.getColor(R.color.clog));
        } else {
            tv.setBackgroundColor(res.getColor(R.color.white));
            tv.setTextColor(res.getColor(R.color.color_text_content));
        }
    }
}
