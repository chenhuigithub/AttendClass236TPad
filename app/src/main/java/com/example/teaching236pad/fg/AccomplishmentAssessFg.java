package com.example.teaching236pad.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.util.StringUtils;

/**
 * 素养评价
 *
 * @author chenhui 2021.01.27
 */
public class AccomplishmentAssessFg extends BaseNotPreLoadFg {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据

    private View allFgView;// 总布局
    private TextView tvContent;//目标内容


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_accomplish_access,
                    null);

            tvContent = (TextView) allFgView.findViewById(R.id.tv_content_layout_fg_accomplish_access);
            tvContent.setText("综观全词，造像写意时，我们看到的是一个游人；借景抒情时，我们看到的是一个诗人；深沉发问时，我们看到的是一个哲人；怀人忆事时，我们看到的是一群战士。" +
                    StringUtils.getEnterStr() + StringUtils.getEnterStr() +
                    "鉴赏方法：诵读入境，抓关键词句理清思路，整体把握形象；了解抒情方式，体会情感特征。");
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
