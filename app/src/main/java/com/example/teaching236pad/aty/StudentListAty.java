package com.example.teaching236pad.aty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.teaching236pad.R;
import com.example.teaching236pad.adapter.StudentAdapter;
import com.example.teaching236pad.model.Student;
import com.example.teaching236pad.util.ConstantsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生名单
 *
 * @author chenhui 2021.02.04
 */
public class StudentListAty extends Activity {
    private static String[] names = ConstantsUtils.NAMES;

    private List<Student> studentList;//学生列表
    private StudentAdapter studentAdapter;

    private GridView gdvClassName;// 学生名单
    private LinearLayout tvClose;// 关闭
    private TextView tvCloseAll;// 关闭全部

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.layout_aty_student_list);

        studentList = new ArrayList<Student>();
        for (int i = 0; i < names.length; i++) {
            Student stu = new Student();
            stu.setId("id" + String.valueOf(i));
            stu.setName(names[i]);
            studentList.add(stu);
        }

        gdvClassName = (GridView) findViewById(R.id.gdv_name_layout_aty_student_list);
        if (studentAdapter == null) {
            studentAdapter = new StudentAdapter(this, studentList);
            gdvClassName.setAdapter(studentAdapter);
        } else {
            studentAdapter.notifyDataSetChanged();
        }

        tvClose = (LinearLayout) findViewById(R.id.ll_close_layout_aty_student_list);
        tvClose.setOnClickListener(new Listeners());
        tvCloseAll = (TextView) findViewById(R.id.tv_close_all_layout_aty_student_list);
        tvCloseAll.setOnClickListener(new Listeners());
    }


    /**
     * 控件监听
     */
    private class Listeners implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.ll_close_layout_aty_student_list://关闭
                    finish();

                    break;
                case R.id.tv_close_all_layout_aty_student_list://关闭全部
                    finish();

                    break;
            }
        }
    }
}
