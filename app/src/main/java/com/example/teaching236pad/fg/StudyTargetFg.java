package com.example.teaching236pad.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.teaching236pad.R;
import com.example.teaching236pad.util.StringUtils;

/**
 * 学习目标
 *
 * @author chenhui 2021.01.25
 */
public class StudyTargetFg extends BaseNotPreLoadFg {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据

    private View allFgView;// 总布局
    private TextView tvContent;//目标内容

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_study_target,
                    null);

            tvContent = (TextView) allFgView.findViewById(R.id.tv_content_layout_fg_study_target);
            tvContent.setText("1.通过朗读、鉴赏，体会作者以天下为己任的革命使命感和远大抱负" +
                    StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                    "2.体会宏阔的深秋意境，提高形象思维能力；" +
                    StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                    "3.学习富有表现力的语言，提高朗读鉴赏能力");
        }

        return allFgView;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || hasLoadOnce) {
            return;
        }

    }
}
