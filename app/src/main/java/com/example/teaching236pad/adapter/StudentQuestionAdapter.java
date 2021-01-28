package com.example.teaching236pad.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.example.teaching236pad.R;
import com.example.teaching236pad.model.Question;

import java.util.List;

/**
 * 学生所提问题列表适配器
 *
 * @author chenhui 2021.01.26
 */
public class StudentQuestionAdapter extends BaseListAdapter<Question> {
    private int currentPos = 0;// 当前位置，默认是首项
    private List<Question> dataList;//问题列表

    private Resources res;// 资源工具

    public StudentQuestionAdapter(Context context, List<Question> dataList) {
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
        return R.layout.layout_v_item_for_student_question;
    }

    @Override
    protected void doAssignValueForView(int position, View resultView, Question dataObj) {

    }
}
