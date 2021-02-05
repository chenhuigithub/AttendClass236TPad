package com.example.teaching236pad.listener;

import android.graphics.Picture;
import android.view.View;
import android.widget.AdapterView;

import com.example.teaching236pad.model.DataInfo;
import com.example.teaching236pad.model.KeyValue;
import com.example.teaching236pad.model.TestData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 回调接口的定义类
 *
 * @author Administrator
 */
public class InterfacesCallback {

    /**
     * 主界面与其分页面的交互，能够实现分页面之间相互跳转的功能。使用：分页面中需要调用此方法，将需要跳转到哪个页面的信息作为参数。主界面须实现此接口，
     * 从分页面中获知将要跳转到哪个分页面，进行跳转。
     *
     * @author zhaochenhui, 2017.01.12
     */

    public interface ICanKnowGoToWhichPager {
        /**
         * 获知需要跳转的分页面位置
         */
        public void getPagerPosition(int position);
    }

    /**
     * 交互接口：传递intent信息
     *
     * @author zhaochenhui_2017.05.24
     */
    public interface ICanKnowSth {
        /**
         * 获知信息
         */
        public void doSth(Object obj);
    }

    /**
     * 交互接口：传递string信息
     *
     * @author zhaochenhui_2017.05.24
     */
    public interface ICanKnowSth2 {
        /**
         * 获知信息
         */
        public void getInfo(String str);
    }

    /**
     * 交互接口：传递KeyValue信息
     *
     * @author zhaochenhui_2017.05.24
     */
    public interface ICanKnowSth3 {
        /**
         * 获知信息
         */
        public void doSth(KeyValue kv);
    }

    /**
     * 交互接口：传递题目信息
     *
     * @author zhaochenhui_2017.05.24
     */
    public interface ICanKnowSth4 {
        /**
         * 获知信息
         */
        public void doSth(TestData test);
    }


    /**
     * 交互接口：传递题目信息
     *
     * @author zhaochenhui_2017.05.24
     */
    public interface ICanKnowSth5 {
        /**
         * 获知信息
         */
        public void doSth(ArrayList<TestData> list);
    }

    /**
     * 交互接口：传递图片信息
     *
     * @author zhaochenhui_2019.10.30
     */
    public interface ICanKnowSth6 {
        /**
         * 获知信息
         */
        public void doSth(Picture pic);
    }

    /**
     * 交互接口：传递图片信息
     *
     * @author zhaochenhui_2019.10.30
     */
    public interface ICanKnowSth7 {
        /**
         * 获知信息
         */
        public void doSth(ArrayList<Picture> list);
    }

    /**
     * 交互接口：传递图片信息
     *
     * @author zhaochenhui_2019.10.30
     */
    public interface ICanKnowSth8 {
        /**
         * 获知信息
         */
        public void doSth(ArrayList<String> list);
    }

    /**
     * 交互接口：传递图片信息
     *
     * @author zhaochenhui_2019.10.30
     */
    public interface ICanKnowSth9 {
        /**
         * 获知信息
         */
        public void doSth(ArrayList<DataInfo> list);
    }

    /**
     * 交互接口：传递图片信息
     *
     * @author zhaochenhui_2019.10.30
     */
    public interface ICanKnowSth10 {
        /**
         * 获知信息
         */
        public void doSth(String key, HashMap<Object, Object> map);
    }


    /**
     * 课程详情Activity与Fragment之间进行信息交互的接口
     *
     * @author zhaochenhui, 2016.07.01
     */
    // public interface OnListenerForClassCircleCallback {
    // /**
    // * 传递班级圈或课程信息
    // */
    // public void ICanGetInfo(ICanGetKeyValue icanInfo);
    // }

    /**
     * 课程详情Activity与Fragment之间进行信息交互的接口
     *
     * @author zhaochenhui, 2016.07.01
     */
    // public interface OnListenerForClassCircleCallback2 extends
    // OnListenerForClassCircleCallback {
    // /**
    // * 传递某班级圈内的课程数
    // */
    // public void ICanGetCourseCount(String courseCount);
    // }

    /**
     * 播放视频的Fragment向其所属Activity类发送信息的接口（引用此视频Fragment的类实现此接口）
     *
     * @author zhaochenhui, 2016.08.02
     *
     */
    // public interface OnListenerForPlayVideoSendOutInfo {
    // /**
    // * 当前播放的视频的课时信息
    // *
    // * @param info
    // */
    // public void ICanGetVideoInfoCurrentPlay(VideoAndAudioInfoModel info);
    //
    // /**
    // * 点击返回按钮后的处理
    // */
    // public void doAfterClickBack();
    //
    // /**
    // * 切换至全屏后的处理
    // */
    // public void doSwitchFullScreen();
    //
    // /**
    // * 切换至半屏后的处理
    // */
    // public void doSwitchHalfScreen();
    // }

    /**
     * 播放视频的Fragment接收其所属Activity类的信息的接口（视频Fragment实现此接口）
     *
     * @author zhaochenhui, 2016.08.02
     *
     */
    // public interface OnListenerForPlayVideoCallback {
    // /**
    // * 当前播放的视频的课时信息
    // *
    // * @param info
    // */
    // public void ICanGetInfoSelected(VideoAndAudioInfoModel info);
    //
    // /**
    // * 剧集信息列表
    // *
    // * @param infoList
    // */
    // public void ICanGetInfoList(List<VideoAndAudioInfoModel> infoList);
    //
    // }

    /**
     * fragment界面实现
     *
     * @param <T>
     * @author zhaochenhui, 2016.11.02
     * @deprecated
     */
    public interface ICanShowCustomList<T> {

        /**
         * 处理获取到的列表数据
         *
         * @param infoList
         */
        public void doSthAfterAcquireList(int currentPageNum, List<T> infoList);
    }

    ;

    /**
     * activity实现，实现与所用的fragment交互的操作
     *
     * @author zhaochenhui, 2016.11.02
     * @deprecated
     */
    public interface ICanDoSthWithListSurfaceOperationCallback {
        /**
         * 处理下拉刷新上拉加载更多的事件
         *
         * @param params
         */
        public void doOnPullDownToRefreshOnTask(Void... params);

        /**
         * 处理上拉加载更多的事件
         *
         * @param params
         */
        public void doOnPullUpToLoadMoreOnTask(Void... params);

        /**
         * 处理班级圈类表listView布局单项选择事件
         */
        public void doSthForLstvItemClickListener(AdapterView<?> adapterview,
                                                  View view, int i, long l);
    }

    ;

    /**
     * 答案解析（做题）界面Activity与Fragment之间的信息交互
     *
     * @author zhaochenhui_2017.05.16
     */
    public interface doOnQuestionCallback {
        /**
         * Fragment中确定展示方式（做题/解析）后，调用此方法设置Activity中的布局
         *
         * @param isExercise 当前界面是否展示为做题方式
         */
        public void doAfterKonwShowType(boolean isExercise);

    }

    /**
     * Notification交互的接口
     *
     * @author zhaochenhui_2017.05.24
     */
    public interface ICanKnowNotication {
        /**
         * 获知信息
         */
        public void getNotication(String type, int count, int notificationID);
    }

    /**
     * 个人中心接口
     *
     * @author chenhui
     */
    public interface doSthForPersonCenter {
        /**
         * 刷新
         */
        public void doRefresh();
    }

    /**
     * 选择教材的交互接口
     */
    public interface DoSthAboutChoiceMaterial {
        /**
         * 筛选条件后的操作
         *
         * @param period
         * @param subject
         * @param edition
         * @param module
         */
        public void doAfterChoicData(KeyValue period, KeyValue subject, KeyValue edition, KeyValue module);

        /**
         * 最后一步选择的条件ID
         */
        public void doAfterChoiceLastID(String jsonLastID);

//        /**
//         * 选择学科后
//         */
//        public void doAfterChoiceSubject(KeyValue subject);
//
//        /**
//         * 选择版本后
//         */
//        public void doAfterChoiceEdition(KeyValue edition);
//
//        /**
//         * 选择模块后
//         */
//        public void doAfterChoiceModule(KeyValue module);
    }
}
