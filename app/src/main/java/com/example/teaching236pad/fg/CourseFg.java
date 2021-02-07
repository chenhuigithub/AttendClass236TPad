package com.example.teaching236pad.fg;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.teaching236pad.R;
import com.example.teaching236pad.aty.PlayVideoActivity;
import com.example.teaching236pad.listener.OnListenerForPlayVideoCallback;
import com.example.teaching236pad.listener.OnListenerForPlayVideoSendOutInfo;
import com.example.teaching236pad.model.VideoAndAudioInfoModel;
import com.example.teaching236pad.model.VideoAudio;
import com.example.teaching236pad.util.ConstantsForServerUtils;
import com.example.teaching236pad.util.ConstantsUtils;
import com.example.teaching236pad.util.UrlUtils;
import com.example.teaching236pad.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 优课助学
 *
 * @author chenhui 2021.01.26
 */
public class CourseFg extends BaseNotPreLoadFg implements
        OnListenerForPlayVideoSendOutInfo {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据

    private List<VideoAudio> videoList;// 音视频列表
    private VideoAudio videoCurr;// 当前播放的视频

    private PlayVideoFragment videoFg;// 音视频
    private OnListenerForPlayVideoCallback callbackForVideo;// 音视频播放器

    private View allFgView;// 总布局
    private TextView tvContent;//目标内容

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_course,
                    null);

            videoList = new ArrayList<VideoAudio>();


            isPrepared = true;

        }

        // 因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        Utils.removeParent(allFgView);

        // 标志当前页面可见
        isPrepared = true;

        return allFgView;
    }

    /**
     * 设置视频布局
     */
    private void setVideoView() {
        // 初始化视频
        videoCurr = videoList.get(0);
        initVideo(videoCurr);
    }


    /**
     * 设置音视频播放器
     */
    private void initVideo(VideoAudio videoCurr) {
        // 将视频fragment填充到本activity中
        FragmentManager fgManager = getActivity().getSupportFragmentManager();// v4包内提供的方法
        FragmentTransaction fgTransaction = fgManager.beginTransaction();

        if (videoFg != null) {
            fgTransaction.remove(videoFg);
        }

        if (videoCurr != null) {
            videoFg = new PlayVideoFragment(videoCurr.getPath(),
                    videoCurr.getTitle(), videoList, false, "0");
            callbackForVideo = (OnListenerForPlayVideoCallback) videoFg;
        }

        fgTransaction.add(R.id.rl_video_layout_fg_course, videoFg);
        fgTransaction.commit();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || hasLoadOnce) {
            return;
        }

        //测试地址，2019.11.08，chenhui
        String url = "http://www.mibeike.com/upload/extend/video/201807/06/201807061457305988.mp4";
        VideoAudio va = new VideoAudio();
        va.setTitle("视频" + String.valueOf("1"));
        va.setPath(url.toString());

        if (videoList.size() > 0) {
            videoList.clear();
        }
        // 添加
        videoList.add(va);

        setVideoView();
    }

    @Override
    public void ICanGetVideoInfoCurrentPlay(VideoAndAudioInfoModel info) {
        videoCurr = new VideoAudio();
        videoCurr.setId(info.getId());
        videoCurr.setPath(info.getUri());
        videoCurr.setRemark("");
        videoCurr.setTitle(info.getName());

        initVideo(videoCurr);
    }

    @Override
    public void doAfterClickBack() {

    }

    @Override
    public void doSwitchFullScreen() {
        Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
        intent.putExtra(ConstantsUtils.VIDEO_LIST, (Serializable) videoList);
        intent.putExtra(ConstantsUtils.VIDEO, (Serializable) videoCurr);
        startActivity(intent);
    }

    @Override
    public void doSwitchHalfScreen() {

    }

    @Override
    public void onResume() {
        super.onResume();

//        initVideo(videoCurr);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        if (receiver != null) {
//            bm.unregisterReceiver(receiver);
//        }
    }
}
