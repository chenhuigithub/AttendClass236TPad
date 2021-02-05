package com.example.teaching236pad.aty;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.teaching236pad.R;
import com.example.teaching236pad.adapter.StudentAdapter;
import com.example.teaching236pad.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 随机点名
 *
 * @author chenhui 2021.02.04
 */

public class RandomRollCallAty extends Activity {
    private String[] names = {"李小明", "宋公明", "孙悟空", "曹孟德", "卢俊义", "王朗", "史湘云", "鲁子敬", "贾探春", "司马仲达", "沙悟净",
            "燕小乙", "张翼德", "刘唐", "周公瑾", "林黛玉", "荀文若", "吴用", "张子房", "郭奉孝", "韩信", "公孙胜", "孙仲谋", "石秀", "许攸"};

    /**
     * 是否开始，0-否，1-是
     */
    private int isStart;
    private Handler mHandler;
    private Timer mTimer;


    private List<Student> studentList;//学生列表
    private StudentAdapter studentAdapter;

    private GridView gdvClassName;// 学生名单
    private LinearLayout tvClose;// 关闭
    private TextView tvCloseAll;// 关闭全部
    private TextView tvSwitch;// 开始
    private TextView tvName;// 学生姓名

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.layout_aty_random_roll_call);

        mHandler = new Handler(new InnerCallback());

        studentList = new ArrayList<Student>();
        for (int i = 0; i < 30; i++) {
            Student stu = new Student();
            stu.setId("id" + String.valueOf(i));
            stu.setName("李小明");
            studentList.add(stu);
        }


        tvSwitch = (TextView) findViewById(R.id.tv_switch_layout_aty_random_roll_call);
        tvSwitch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (isStart == 0) {
                    start();
                } else {
                    stop();
                }
            }
        });

        tvName = (TextView) findViewById(R.id.tv_name_layout_aty_random_roll_call);
        tvName.setText(names[0]);

        tvClose = (LinearLayout) findViewById(R.id.ll_close_layout_aty_random_roll_call);
        tvClose.setOnClickListener(new Listeners());

        tvCloseAll = (TextView) findViewById(R.id.tv_close_all_layout_aty_random_roll_call);
        tvCloseAll.setOnClickListener(new Listeners());

    }

    private void start() {
        isStart = 1;
        tvSwitch.setText("停止");
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            int max = names.length;
            int min = 0;

            @Override
            public void run() {
                if (isStart == 1) {
                    int num = (int) Math.round(Math.random() * (max - min) + min);
                    if (num >= max) {
                        num--;
                    }
                    //通过Message.obtain构造一个message，并通过Handler发送
                    mHandler.sendMessage(Message.obtain(mHandler, num));
                }
            }
        }, 0, 80);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void stop() {
        isStart = 0;
        tvSwitch.setText("开始");
        mTimer.cancel();

        tvName.setTextColor(getResources().getColor(R.color.red03, null));
        tvSwitch.setTextColor(getResources().getColor(R.color.white, null));
    }

    private class InnerCallback implements Handler.Callback {


        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            //这里接收到sendMessage发送过来的消息，当前线程为UI线程
            int index = msg.what;
            tvName.setText(names[index]);
            tvName.setTextColor(getResources().getColor(R.color.color_text_content, null));
            tvSwitch.setTextColor(getResources().getColor(R.color.red03, null));

            return true;

        }
    }


    /**
     * 控件监听
     */
    private class Listeners implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.ll_close_layout_aty_random_roll_call://关闭
                    finish();

                    break;
                case R.id.tv_close_all_layout_aty_random_roll_call://关闭全部
                    finish();

                    break;
            }
        }
    }
}
