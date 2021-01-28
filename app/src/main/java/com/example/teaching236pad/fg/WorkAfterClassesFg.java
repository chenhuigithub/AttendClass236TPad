package com.example.teaching236pad.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.adapter.TestAdapter;
import com.example.teaching236pad.model.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 课后作业
 *
 * @author chenhui 2021.01.27
 */
public class WorkAfterClassesFg extends BaseNotPreLoadFg {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据

    private List<Test> testList;//题目列表

    private TestAdapter testAdapter;//题目适配器

    private View allFgView;// 总布局
    private ListView lstvTest;//题目


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_work_after_classes,
                    null);

            lstvTest = (ListView) allFgView.findViewById(R.id.lstv_content_layout_v_item_for_work_after_classes);
            initDataList();
            setLstvContentAdapter(0);

            lstvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

        }

        return allFgView;
    }

    private void initDataList() {
        testList = new ArrayList<Test>();

        for (int i = 0; i < 8; i++) {
            Test t = new Test();
            t.setId("id" + String.valueOf(i));
            t.setName("");
            testList.add(t);
        }
    }

    /**
     * 左侧listView知识点适配器
     */
    private void setLstvContentAdapter(int pos) {
        if (testAdapter == null) {
            testAdapter = new TestAdapter(getActivity(),
                    testList);
            lstvTest.setAdapter(testAdapter);
        } else {
            testAdapter.notifyDataSetChanged();
        }
        testAdapter.setCurrentPos(pos);
    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || hasLoadOnce) {
            return;
        }
    }
}
