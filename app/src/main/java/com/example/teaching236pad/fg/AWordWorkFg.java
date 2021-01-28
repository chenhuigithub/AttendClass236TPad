package com.example.teaching236pad.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.util.StringUtils;

/**
 * 一句话作业
 *
 * @author chenhui 2021.01.27
 */
public class AWordWorkFg extends BaseNotPreLoadFg {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据

    private View allFgView;// 总布局
    private TextView tvContent;//目标内容

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_a_word_work,
                    null);

            tvContent = (TextView) allFgView.findViewById(R.id.tv_content_layout_a_word_work);
            tvContent.setText("秋天，万物凋零的季节。纤弱的文人往往表现出“伤秋”“悲秋”的意绪。“多情自古伤离别，更那堪冷落清秋节”等等。说说你对毛泽东笔下的秋天的感受，或写写你自己对秋天的感觉。（任选一项，不少于50字）");
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
