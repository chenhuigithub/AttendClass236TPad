package com.example.teaching236pad.aty;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.teaching236pad.R;
import com.example.teaching236pad.fg.PlayVideoFragment;
import com.example.teaching236pad.listener.InterfacesCallback;
import com.example.teaching236pad.listener.OnListenerForPlayVideoCallback;
import com.example.teaching236pad.listener.OnListenerForPlayVideoSendOutInfo;
import com.example.teaching236pad.model.VideoAndAudioInfoModel;
import com.example.teaching236pad.model.VideoAudio;
import com.example.teaching236pad.util.ConstantsUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 视频播放全屏
 *
 * @author chenhui 2021.02.01
 */
public class PlayVideoActivity extends FragmentActivity implements
        OnListenerForPlayVideoSendOutInfo, InterfacesCallback.ICanKnowSth2 {
    OnListenerForPlayVideoCallback callbackForVideo;
    List<VideoAudio> videoList;
    VideoAudio videoCurr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置全屏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_aty_play_video);
        // LinearLayout llAll = (LinearLayout)
        // findViewById(R.id.ll_all_layout_aty_play_video);

        dealWithExtras();
    }

    /**
     * 处理接收过来的数据
     */
    @SuppressWarnings("unchecked")
    private void dealWithExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Serializable videoListSer = bundle
                .getSerializable(ConstantsUtils.VIDEO_LIST);
        videoList = (List<VideoAudio>) videoListSer;

        Serializable videoSer = bundle.getSerializable(ConstantsUtils.VIDEO);
        videoCurr = (VideoAudio) videoSer;

        initVideo(videoList, videoCurr);
    }

    /**
     * 初始化视频播放器
     *
     * @param videoList 视频列表
     * @param video     当前播放的视频
     */
    private void initVideo(List<VideoAudio> videoList, VideoAudio video) {
        FragmentManager manager = getSupportFragmentManager();// FragmentManager调用v4包内的
        FragmentTransaction transaction = manager.beginTransaction();// FragmentTransaction调用v4包内的（FragmentTransaction
        // transaction声明成局部的）

        PlayVideoFragment videoFg = new PlayVideoFragment(video.getPath(),
                video.getTitle(), videoList, true, "0");
        callbackForVideo = (OnListenerForPlayVideoCallback) videoFg;

        transaction.replace(R.id.rl_all_layout_aty_play_video, videoFg)
                .commit();// 替换为名称为A的fragment并显示它
    }

    @Override
    public void ICanGetVideoInfoCurrentPlay(VideoAndAudioInfoModel info) {
        videoCurr = new VideoAudio();
        videoCurr.setId(info.getId());
        videoCurr.setPath(info.getUri());
        videoCurr.setRemark("");
        videoCurr.setTitle(info.getName());

        initVideo(videoList, videoCurr);
    }

    @Override
    public void doAfterClickBack() {
        finish();
    }

    @Override
    public void doSwitchFullScreen() {
    }

    @Override
    public void doSwitchHalfScreen() {
        finish();
    }

    @Override
    public void getInfo(String str) {
    }
}
