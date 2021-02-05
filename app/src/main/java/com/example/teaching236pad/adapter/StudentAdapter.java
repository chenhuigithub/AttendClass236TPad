package com.example.teaching236pad.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.model.Student;

import java.util.List;

/**
 * 学生适配器
 */
public class StudentAdapter extends BaseListAdapter<Student> {
    public StudentAdapter(Context context, List<Student> dataList) {
        super(context, dataList);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.layout_v_item_for_student;
    }

    @Override
    protected void doAssignValueForView(int position, View resultView, Student dataObj) {
        TextView tvName = (TextView) resultView.findViewById(R.id.tv_layout_v_item_for_student);
        if (!TextUtils.isEmpty(dataObj.getName())) {
            tvName.setText(dataObj.getName());
        }
    }
}
