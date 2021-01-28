package com.example.teaching236pad.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.util.StringUtils;

/**
 * 学习任务
 *
 * @author chenhui 2021.01.25
 */
public class StudyTaskFg extends BaseNotPreLoadFg {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据

    private View allFgView;// 总布局
    private TextView tvContent;//目标内容


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_study_task,
                    null);

            tvContent = (TextView) allFgView.findViewById(R.id.tv_content_layout_fg_study_task);
            tvContent.setText("1.讨论本诗的节奏处理，用竖线将每句中需停顿的地方隔开。"
                    + StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                    "2.参看老师给出的节奏提示，比较异同： 独立/寒秋，湘江/北去，橘子洲头。看/万山/红遍，层林/尽染；漫江/碧透，百舸/争流。 鹰击/长空，鱼翔/浅底，万类霜天/竞/自由。怅/寥廓，问/苍茫大地，谁主/沉浮？ 携来/百侣曾游。忆/往昔/峥嵘岁月稠。恰/同学少年，风华/正茂；书生/意气，挥斥/方遒。 指点/江山，激扬/文字，粪土当年/万户侯。曾记否，到/中流击水，浪遏/飞舟？" +
                    StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                    "3.找出诗歌的韵脚，进行标注" + StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                    "4.参看老师给出的压韵提示，比较异同" +
                    StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                    "本篇守谱押用一部平韵，韵脚分别为“秋”、“头”、“流”、“由”、“浮”、“游”、“稠”、“遒”、“侯”、“舟”。按律，首句不必入韵，“秋”字盖添叶。");

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
