package com.example.teaching236pad.fg;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.teaching236pad.R;
import com.example.teaching236pad.adapter.Courseware01Adapter;
import com.example.teaching236pad.adapter.CustomPager03Adapter;
import com.example.teaching236pad.adapter.GalleryAdapter;
import com.example.teaching236pad.aty.RandomRollCallAty;
import com.example.teaching236pad.aty.StudentListAty;
import com.example.teaching236pad.aty.DrawBoardAty;
import com.example.teaching236pad.model.Courseware;
import com.example.teaching236pad.util.ConstantsUtils;
import com.example.teaching236pad.view.CustomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 互动课堂
 *
 * @author 2021.02.02
 */
public class InteractiveClassesFg extends BaseNotPreLoadFg {
    private boolean isPrepared;// 标志位，标志已经初始化完成
    public static boolean hasLoadOnce;// 是否已被加载过一次，第二次就不再去请求数据

    private List<Courseware> coursewareList;//课件内容
    private List<Courseware> fileList;//文件（课件）名称列表
    private int prePosition;// 幻灯片展示的上一个位置

    private Resources res;

    private CustomPager03Adapter vpagerAdapter;// 课件大图滑动布局适配器
    private GalleryAdapter glAdapter;// 课件画廊适配器
    private Courseware01Adapter courseware01Adapter;//文件（课件）列表适配器

    private View allFgView;// 总布局
    private CustomListView lstvLeftTitle03;// 课件名字列表
    private ViewPager vpager;// 课件内容，右侧滑动布局
    private RelativeLayout rlPre;// 前一次点击过的二级标题栏单项布局
    private Gallery glFile;// 画廊效果：文件（PPT）缩略图展示布局
    private LinearLayout llPoint;// 滑动布局圆点状索引
    // 上一个（小图）
    private TextView tvPrevious;
    // 下一个（小图）
    private TextView tvNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == allFgView) {
            allFgView = View.inflate(getActivity(), R.layout.layout_fg_interactive_classes,
                    null);

            // 初始化数据
            res = getResources();
            initData();

            // 学生名单
            tvNext = (TextView) allFgView
                    .findViewById(R.id.tv_student_list_layout_fg_ic);
            tvNext.setOnClickListener(new Listeners());
            // 随机点名
            tvNext = (TextView) allFgView
                    .findViewById(R.id.tv_random_roll_call_layout_fg_ic);
            tvNext.setOnClickListener(new Listeners());
            // 电子白板
            tvNext = (TextView) allFgView
                    .findViewById(R.id.tv_electronic_whiteboard_layout_fg_ic);
            tvNext.setOnClickListener(new Listeners());

            // 上一个
            tvPrevious = (TextView) allFgView
                    .findViewById(R.id.ll_previous_layout_fg_class);
            tvPrevious.setOnClickListener(new Listeners());
            // 下一个
            tvNext = (TextView) allFgView
                    .findViewById(R.id.ll_next_layout_fg_class);
            tvNext.setOnClickListener(new Listeners());


            //课件大图滑动布局
            initPagerViews();
            setPagerAdapter(0);

            //课件画廊效果
            initGallery(allFgView);
            setGalleryAdapter(0);

            //文件(课件)列表布局
            initCoursewareFile();

            setCoursewareNameAdapter();

        }

        return allFgView;
    }


    /**
     * 初始化viewPager相关布局
     */
    private void initPagerViews() {
        // 教案内容，滑动布局
        vpager = (ViewPager) allFgView
                .findViewById(R.id.vpager_courseware_layout_fg_class);
        // 限制加载数量
        vpager.setOffscreenPageLimit(2);
        // 监听
        vpager.setOnPageChangeListener(new VPagerChangeListener());

        llPoint = (LinearLayout) allFgView
                .findViewById(R.id.ll_point_container);

    }

    /**
     * 设置viewPager适配器
     *
     * @param position 某个位置
     */
    private void setPagerAdapter(int position) {
        // 设置适配器
        if (vpagerAdapter == null) {
            vpagerAdapter = new CustomPager03Adapter(getActivity(),
                    coursewareList);
            vpager.setAdapter(vpagerAdapter);

            // 默认设置到中间的某个位置
            if (coursewareList.size() > 0) {
                int pos = Integer.MAX_VALUE / 2
                        - (Integer.MAX_VALUE / 2 % coursewareList.size());
                // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
            }
        } else {
            // 刷新布局
            vpagerAdapter.notifyDataSetChanged();
        }

        // 设置到某个位置
        vpager.setCurrentItem(position);
    }

    /**
     * 以画廊效果展示文件内容（PPT）
     *
     * @param v
     */
    private void initGallery(View v) {
        // 实例化控件
        glFile = (Gallery) v.findViewById(R.id.gl_file_layout_fg_class);

        // 给Gallery添加监听
        glFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long arg3) {
                if (prePosition != position) {
                    // 页码
                    int numInt = position + 1;
//                    tvNum.setText("幻灯片:" + numInt + "/" + coursewareList.size());

                    // 课件大图
                    vpager.setCurrentItem(position);

                    // 重新赋值
                    prePosition = position;
                }

                setCurrentMenu(prePosition);

                setGalleryAdapter(prePosition);
            }
        });
    }


    /**
     * 设置课件画廊的适配器
     *
     * @param pos 当前选中位置
     */
    private void setGalleryAdapter(int pos) {
        if (glAdapter == null) {
            // 实例化ImageAdapter适配器
            glAdapter = new GalleryAdapter(getActivity(), coursewareList);
            // 将适配器的数据存储到Gallery组件中（设置Gallery组件的Adapter对象）
            glFile.setAdapter(glAdapter);
            // glFile.setGravity(Gravity.LEFT);
            // glFile.setSelection(coursewareList.size()*50);
            glFile.setUnselectedAlpha(0.7f);
            if (pos < 5) {
                // 设置第三个数据在中央位置
                glFile.setSelection(2);
            } else {
                glFile.setSelection(prePosition);
            }

            // MarginLayoutParams lp = (MarginLayoutParams) glFile
            // .getLayoutParams();
            // WindowManager wm = (WindowManager)
            // getActivity().getSystemService(
            // Context.WINDOW_SERVICE);
            // int width = wm.getDefaultDisplay().getWidth();
            // lp.setMargins(-width * 1 / 9, 0, 0, 0);

            if (pos >= 0) {
                glAdapter.setCurrentPos(pos);
            }
        } else {
            glFile.setUnselectedAlpha(0.7f);

            if (coursewareList.size() > 3 && pos < 5) {
                // 设置第三个数据在中央位置
                glFile.setSelection(2);
            } else {
                glFile.setSelection(prePosition);
            }

            if (pos >= 0) {
                glAdapter.setCurrentPos(pos);
            }

            glAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化课件名字列表ListView
     */
    private void initCoursewareFile() {
        lstvLeftTitle03 = (CustomListView) allFgView
                .findViewById(R.id.lstv_catalog_layout_fg_class);
        lstvLeftTitle03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos,
                                    long id) {
                courseware01Adapter.setCurrentPos(pos);
                courseware01Adapter.notifyDataSetChanged();

//                title03Id = title03List.get(pos).getId();

//                vUtils.showLoadingDialog("");

                // 获取右侧课件
//                requestForCourseware();
            }
        });
    }

    private void setCurrentMenu(int prePosition) {
        Courseware courseware = coursewareList.get(prePosition);
        boolean hasJoin = courseware.isHasJoinPreview();
        // 初始化加入、移除当前页按钮
//        if (hasJoin) {
//            // 显示移除当前页
//            setMenu1ForRemove();
//        } else {
//            // 显示加入当前页
//            setMenu1ForAdd();
//        }
    }


    private void setCoursewareNameAdapter() {
        courseware01Adapter = new Courseware01Adapter(getActivity(), fileList);
        lstvLeftTitle03.setAdapter(courseware01Adapter);
    }

    private void initData() {
        coursewareList = new ArrayList<Courseware>();

        for (int i = 0; i < 5; i++) {
            Courseware info = new Courseware();
            info.setId("id" + String.valueOf(i));
//            info.setBigPath("");
//            info.setThumbPath("");
            info.setPicResID(R.drawable.ppt01_big);
            coursewareList.add(info);
        }


        fileList = new ArrayList<Courseware>();

        for (int i = 0; i < 5; i++) {
            Courseware file = new Courseware();
            file.setId("id_file" + String.valueOf(i));
            file.setName("沁园春·长沙课件ppt");
            fileList.add(file);
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || hasLoadOnce) {
            return;
        }
    }

    /**
     * 主页面底部页卡偏移事件监听
     */
    private class VPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // 滚动时调用
        }

        @Override
        public void onPageSelected(int arriveIndex) { // 新的条目被选中时调用
            // System.out.println("onPageSelected: " + arriveIndex);
            int newPosition = arriveIndex % coursewareList.size();

            // 显示当前页页码与总页数
            int posInt = newPosition + 1;
//            tvNum.setText("幻灯片：" + posInt + " / " + coursewareList.size());

            // 把之前的禁用, 把最新的启用, 更新指示器
            // llPoint.getChildAt(prePosition).setEnabled(false);
            // llPoint.getChildAt(newPosition).setEnabled(true);

            // 记录之前的位置
            prePosition = newPosition;

            // 更新画廊
            setGalleryAdapter(prePosition);

//            setCurrentMenu(prePosition);
        }
    }

    /**
     * 控件监听
     *
     * @author chenhui
     */
    private class Listeners implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.tv_student_list_layout_fg_ic://学生名单
                    Intent in = new Intent(getActivity(), StudentListAty.class);
                    startActivity(in);

                    break;
                case R.id.tv_random_roll_call_layout_fg_ic://随机点名
                    Intent in1 = new Intent(getActivity(), RandomRollCallAty.class);
                    startActivity(in1);

                    break;
                case R.id.tv_electronic_whiteboard_layout_fg_ic://电子白板
                    Intent intent2 = new Intent(getActivity(),
                            DrawBoardAty.class);
                    //白板状态
                    intent2.putExtra(DrawBoardAty.SHOW_STYLE,
                            ConstantsUtils.WHITE_BOARD);
                    startActivity(intent2);

                    break;
                case R.id.ll_previous_layout_fg_class:// 上一个（小图）
                    if (prePosition > 0) {
                        prePosition = prePosition - 1;

                        setGalleryAdapter(prePosition);

                        // 页码
                        int numInt = prePosition;
//                        tvNum.setText("幻灯片:" + numInt + "/" + coursewareList.size());

                        // 课件大图
                        vpager.setCurrentItem(prePosition);

                    } else if (prePosition == 0) {// 当前选中的是第一项
                    }

                    setCurrentMenu(prePosition);

                    break;
                case R.id.ll_next_layout_fg_class:// 下一个（小图）
                    if (prePosition < coursewareList.size() - 1) {
                        prePosition = prePosition + 1;

                        setGalleryAdapter(prePosition);

                        // 页码
                        int numInt = prePosition;
//                        tvNum.setText("幻灯片:" + numInt + "/" + coursewareList.size());

                        // 课件大图
                        vpager.setCurrentItem(prePosition);
                    } else if (prePosition == coursewareList.size() - 1) {// 当前选中的是最后一项
                    }

                    setCurrentMenu(prePosition);

                    break;
            }

        }
    }

}
