package com.example.teaching236pad.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.teaching236pad.R;
import com.example.teaching236pad.model.Courseware;
import com.example.teaching236pad.util.UrlUtils;

import java.util.List;

/**
 * 画廊效果适配器:文件内容
 *
 * @author chenhui, 2018.08.09
 */
public class GalleryAdapter extends BaseListAdapter<Courseware> {
    // public static boolean isJoinPreview = false;// 是否加入 预览状态

    private Resources res;
    private int currentPos;// 当前选中的位置(全都未选中：-1，全部选中：-2)

    public GalleryAdapter(Context context, List<Courseware> dataList) {
        super(context, dataList);
        res = context.getResources();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.layout_adapter_item_for_file_content;
    }

    /**
     * 设置当前位置
     */
    public void setCurrentPos(int pos) {
        this.currentPos = pos;
    }

    /**
     * 获取当前位置
     */
    public int getCurrentPos() {
        return this.currentPos;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void doAssignValueForView(int position, View resultView,
                                        Courseware dataObj) {
        RelativeLayout rlAll = (RelativeLayout) resultView
                .findViewById(R.id.rl_all_layout_adapter_item_for_course);

        LinearLayout ll03 = (LinearLayout) resultView
                .findViewById(R.id.ll03_layout_adapter_item_for_course);

        TextView tv = (TextView) resultView
                .findViewById(R.id.tv_layout_adapter_item_for_file_content);
        ImageView iv = (ImageView) resultView
                .findViewById(R.id.iv_layout_adapter_item_for_file_content);

        // 是否已添加到预览的标识
        ImageView ivPreviewSign = (ImageView) resultView
                .findViewById(R.id.iv_sign01_layout_adapter_item_for_file_content);

        if ((position == this.currentPos) || (-2 == this.currentPos)) {// 加载的位置正好为当前选中位置或者全部已选中
            ll03.setBackgroundResource(R.drawable.selector_for_green_stroke_rectangle);
        } else {// 包括全部未选中（currentPos=-1）的情况
            ll03.setBackgroundResource(R.color.grey04);
        }
        // 是否选中
        boolean hasJoinPreview = dataObj.isHasJoinPreview();
        if (hasJoinPreview) {
            ivPreviewSign.setVisibility(View.VISIBLE);
            ivPreviewSign.setBackgroundResource(R.drawable.checkselect);
        } else {
            // 未加入预览的普通状态
            ivPreviewSign.setVisibility(View.GONE);
        }

        // 当前页页码
        String num = String.valueOf(position + 1);
        tv.setText(num);
        // 图片
//        String picHtml = UrlUtils.PREFIX + "/" + dataObj.getThumbPath();
//        Glide.with(context).load(picHtml)
//                .error(res.getDrawable(R.drawable.ic_launcher_background)).into(iv);


        iv.setBackground(context.getResources().getDrawable(dataObj.getPicResID()));

    }
}
