package com.example.teaching236pad.aty;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.example.teaching236pad.R;
import com.example.teaching236pad.adapter.FragmentVPagerAdapter;
import com.example.teaching236pad.fg.AWordWorkFg;
import com.example.teaching236pad.fg.AccomplishmentAssessFg;
import com.example.teaching236pad.fg.CourseFg;
import com.example.teaching236pad.fg.InteractiveClassesFg;
import com.example.teaching236pad.fg.StudentQuestionFg;
import com.example.teaching236pad.fg.StudyTargetFg;
import com.example.teaching236pad.fg.StudyTaskFg;
import com.example.teaching236pad.fg.SubsidiaryResFg;
import com.example.teaching236pad.fg.WorkAfterClassesFg;
import com.example.teaching236pad.fg.TestBeforeClassesFg;
import com.example.teaching236pad.fg.TestInClassesFg;
import com.example.teaching236pad.listener.InterfacesCallback;
import com.example.teaching236pad.listener.OnListenerForPlayVideoSendOutInfo;
import com.example.teaching236pad.model.VideoAndAudioInfoModel;
import com.example.teaching236pad.util.ConstantsUtils;
import com.example.teaching236pad.util.NotificationUtils;
import com.example.teaching236pad.util.PicFormatUtils;
import com.example.teaching236pad.util.ServerRequestUtils;
import com.example.teaching236pad.util.ValidateFormatUtils;
import com.example.teaching236pad.util.ViewUtils;
import com.example.teaching236pad.view.CustomViewpager;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements InterfacesCallback.ICanKnowSth2, OnListenerForPlayVideoSendOutInfo, InterfacesCallback.ICanKnowNotication {
    private ArrayList<Fragment> fragmentsList;
    private String currentPage = ConstantsUtils.MR;// 当前正在传递消息的Fragment名称，默认是配套资源页面

    private String unitName = "";//单元名称
    private String catalogID = "";//目录ID
    private String catalogName = "";//目录名称

    private NotificationUtils nUtils; // 通知栏工具
    private BroadcastReceiver receiver;// 广播
    private LocalBroadcastManager broadcastManager;// 广播接收
    private ViewUtils vUtils;// 布局工具
    private PicFormatUtils pUtils;// 图片工具
    private ServerRequestUtils requestUtils;// 请求服务器的工具
    private OnListenerForPlayVideoSendOutInfo callbackForCourse;// 优课助学回调

    private Fragment studyTargetFg;//学习目标
    private Fragment studyTaskFg;//学习任务
    private Fragment courseFg;//优课助学
    private Fragment testBeforeClassesFg;//课前练习
    private Fragment studentQuestionFg;//学生质疑
    private Fragment subsidiaryResFg;//配套资源

    private Fragment interactiveClasses;//互动课堂
    private Fragment testInClassesFg;//课中练习
    private Fragment accomplishmentAssess;//素养评价

    private Fragment aWordWork;//一句话作业
    private Fragment workAfterClasses;//课后作业

    private TextView tvUnitName;//单元名称
    private TextView tvCatalogName;//目录名称

    private GridView gdvTitle02;//二级标题
    private CustomViewpager vper;//分页布局
    private TextView tvStudyTarget;//学习目标
    private TextView tvStudyTask;//学习任务
    private TextView tvCourseFg;//优课助学
    private TextView tvTestBeforeClassesFg;//课前练习
    private TextView tvStudentQuestiong;//学生质疑
    private TextView tvSubsidiaryResources;//配套资源

    private TextView tvInteractiveClasses;//互动课堂
    private TextView tvTestInClassesFg;//课中练习
    private TextView tvAccomplishmentAssess;//素养评价

    private TextView tvAWordWork;//一句话作业
    private TextView tvWorkAfterClasses;//课后作业

    private TextView tvLastTab;//前次点击的标签

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.layout_aty_main);

        dealWithExtras();

        // 初始化通知栏的进度条
        nUtils = new NotificationUtils(this);
        pUtils = new PicFormatUtils();
        requestUtils = new ServerRequestUtils(this);
        vUtils = new ViewUtils(this);

        tvUnitName = (TextView) findViewById(R.id.tv_classes_layout_aty_main);
        tvCatalogName = (TextView) findViewById(R.id.tv_catalog_layout_aty_main);

        vper = (CustomViewpager) findViewById(R.id.vpager_content_layout_aty_main);

        tvStudyTarget = (TextView) findViewById(R.id.tv_title101_layout_aty_main);

        tvStudyTask = (TextView) findViewById(R.id.tv_title102_layout_aty_main);
        tvCourseFg = (TextView) findViewById(R.id.tv_title103_layout_aty_main);
        tvTestBeforeClassesFg = (TextView) findViewById(R.id.tv_title104_layout_aty_main);
        tvStudentQuestiong = (TextView) findViewById(R.id.tv_title105_layout_aty_main);
        tvSubsidiaryResources = (TextView) findViewById(R.id.tv_title106_layout_aty_main);

        tvInteractiveClasses = (TextView) findViewById(R.id.tv_title201_layout_aty_main);
        tvTestInClassesFg = (TextView) findViewById(R.id.tv_title202_layout_aty_main);
        tvAccomplishmentAssess = (TextView) findViewById(R.id.tv_title203_layout_aty_main);

        tvAWordWork = (TextView) findViewById(R.id.tv_title301_layout_aty_main);
        tvWorkAfterClasses = (TextView) findViewById(R.id.tv_title302_layout_aty_main);

        initVPager();

        tvStudyTarget.setOnClickListener(new MyOnClickListener(0));
        tvStudyTask.setOnClickListener(new MyOnClickListener(1));
        tvCourseFg.setOnClickListener(new MyOnClickListener(2));
        tvTestBeforeClassesFg.setOnClickListener(new MyOnClickListener(3));
        tvStudentQuestiong.setOnClickListener(new MyOnClickListener(4));
        tvSubsidiaryResources.setOnClickListener(new MyOnClickListener(5));

        tvInteractiveClasses.setOnClickListener(new MyOnClickListener(6));
        tvTestInClassesFg.setOnClickListener(new MyOnClickListener(7));
        tvAccomplishmentAssess.setOnClickListener(new MyOnClickListener(8));

        tvAWordWork.setOnClickListener(new MyOnClickListener(9));
        tvWorkAfterClasses.setOnClickListener(new MyOnClickListener(10));

        tvUnitName.setText(unitName);
        tvCatalogName.setText(catalogName);

        tvStudyTarget.performClick();
    }

    private void dealWithExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        String unitName = bundle.getString("UNIT_NAME");
        if (!TextUtils.isEmpty(unitName)) {
            this.unitName = unitName;
        }

        String catalogID = bundle.getString(ConstantsUtils.CATALOG_ID);
        if (!TextUtils.isEmpty(catalogID)) {
            this.catalogID = catalogID;
        }

        String catalogName = bundle.getString(ConstantsUtils.CATALOG_NAME);
        if (!TextUtils.isEmpty(catalogName)) {
            this.catalogName = catalogName;
        }
    }

    /**
     * 初始化viewPager主体内容
     */
    @SuppressWarnings("deprecation")
    private void initVPager() {
        fragmentsList = new ArrayList<Fragment>();
        vper.setOffscreenPageLimit(1);

        studyTargetFg = new StudyTargetFg();
        studyTaskFg = new StudyTaskFg();

        courseFg = new CourseFg();
        callbackForCourse = (OnListenerForPlayVideoSendOutInfo) courseFg;

        testBeforeClassesFg = new TestBeforeClassesFg();
        studentQuestionFg = new StudentQuestionFg();
        subsidiaryResFg = new SubsidiaryResFg();

        interactiveClasses = new InteractiveClassesFg();
        testInClassesFg = new TestInClassesFg();
        accomplishmentAssess = new AccomplishmentAssessFg();

        aWordWork = new AWordWorkFg();
        workAfterClasses = new WorkAfterClassesFg();

        fragmentsList.add(studyTargetFg);
        fragmentsList.add(studyTaskFg);
        fragmentsList.add(courseFg);
        fragmentsList.add(testBeforeClassesFg);
        fragmentsList.add(studentQuestionFg);
        fragmentsList.add(subsidiaryResFg);

        fragmentsList.add(interactiveClasses);
        fragmentsList.add(testInClassesFg);
        fragmentsList.add(accomplishmentAssess);

        fragmentsList.add(aWordWork);
        fragmentsList.add(workAfterClasses);

        vper.setAdapter(new FragmentVPagerAdapter(getSupportFragmentManager(),
                fragmentsList));
        vper.setCurrentItem(0);
        vper.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 显示提示栏
     */
    private void showNotification(int notificationID, int count) {
        nUtils.showNotification(notificationID, count);
        nUtils.updateProgress(notificationID, count);

        if (count == 100) {
            nUtils.cancel(notificationID);
        }
    }

    @Override
    public void getInfo(String str) {
        if (!ValidateFormatUtils.isEmpty(str)) {
            currentPage = str;
        }
    }


    @Override
    public void ICanGetVideoInfoCurrentPlay(VideoAndAudioInfoModel info) {
        if (ConstantsUtils.ER.equals(currentPage)) {
            callbackForCourse.ICanGetVideoInfoCurrentPlay(info);
        }
    }

    @Override
    public void doAfterClickBack() {
        if (ConstantsUtils.ER.equals(currentPage)) {
            callbackForCourse.doAfterClickBack();
        }
    }

    @Override
    public void doSwitchFullScreen() {
        if (ConstantsUtils.ER.equals(currentPage)) {
            callbackForCourse.doSwitchFullScreen();
        }
    }

    @Override
    public void doSwitchHalfScreen() {
        if (ConstantsUtils.ER.equals(currentPage)) {
            callbackForCourse.doSwitchHalfScreen();
        }
    }

    @Override
    public void getNotication(String type, int count, int notificationID) {
        if ("progressbar_notification".equals(type)) {
            showNotification(notificationID, count);
        }
    }


    /**
     * 切换页卡的监听
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            if (index != 2 && callbackForCourse != null) {
                callbackForCourse.doAfterClickBack();
            }


            TextView tv = (TextView) v;
            tv.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red03));

            if (tvLastTab != null) {
                tvLastTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_text_title01));
            }

            tvLastTab = tv;

            vper.setCurrentItem(index);
        }
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
