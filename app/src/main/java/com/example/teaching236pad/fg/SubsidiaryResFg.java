package com.example.teaching236pad.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.adapter.SubsidiaryResAdapter;
import com.example.teaching236pad.model.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 配套资源
 *
 * @author chenhui 2021.01.27
 */
public class SubsidiaryResFg extends BaseNotPreLoadFg {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据
    private List<Resource> resList;//资源列表

    private SubsidiaryResAdapter resAdapter;//资源列表适配器

    private View allFgView;// 总布局
    private ListView lstvRes;//资源列表布局


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_subsidiary_res,
                    null);

            lstvRes = (ListView) allFgView.findViewById(R.id.lstv_content_layout_fg_subsidiary_res);
            initDataList();
            setLstvContentAdapter(0);

            lstvRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }

        return allFgView;
    }


    private void initDataList() {
        resList = new ArrayList<Resource>();

        for (int i = 0; i < 8; i++) {
            Resource q = new Resource();
            q.setId("id" + String.valueOf(i));
            q.setName("");
            resList.add(q);
        }
    }

    /**
     * 左侧listView知识点适配器
     */
    private void setLstvContentAdapter(int pos) {
        if (resAdapter == null) {
            resAdapter = new SubsidiaryResAdapter(getActivity(),
                    resList);
            lstvRes.setAdapter(resAdapter);
        } else {
            resAdapter.notifyDataSetChanged();
        }
        resAdapter.setCurrentPos(pos);
    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || hasLoadOnce) {
            return;
        }
    }
}
