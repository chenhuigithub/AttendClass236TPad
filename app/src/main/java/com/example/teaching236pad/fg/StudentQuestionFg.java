package com.example.teaching236pad.fg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.adapter.StudentQuestionAdapter;
import com.example.teaching236pad.model.Question;
import com.example.teaching236pad.view.CustomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生质疑
 */
public class StudentQuestionFg extends BaseNotPreLoadFg {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据
    private List<Question> questionList;//学生所提问题列表

    private StudentQuestionAdapter questionAdapter;//问题列表适配器

    private View allFgView;// 总布局
    private CustomListView lstvQuestion;//学生所提问题

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_student_question,
                    null);

            lstvQuestion = (CustomListView) allFgView.findViewById(R.id.lstv_content_layout_fg_student_question);
            initDataList();
            setLstvContentAdapter(0);

            lstvQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }

        return allFgView;
    }

    private void initDataList() {
        questionList = new ArrayList<Question>();

        for (int i = 0; i < 5; i++) {
            Question q = new Question();
            q.setId("id" + String.valueOf(i));
            q.setName("");
            questionList.add(q);
        }
    }

    /**
     * 左侧listView知识点适配器
     */
    private void setLstvContentAdapter(int pos) {
        if (questionAdapter == null) {
            questionAdapter = new StudentQuestionAdapter(getActivity(),
                    questionList);
            lstvQuestion.setAdapter(questionAdapter);
        } else {
            questionAdapter.notifyDataSetChanged();
        }
        questionAdapter.setCurrentPos(pos);
    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || hasLoadOnce) {
            return;
        }
    }
}
