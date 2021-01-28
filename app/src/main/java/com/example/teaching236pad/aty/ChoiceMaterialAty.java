package com.example.teaching236pad.aty;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teaching236pad.R;
import com.example.teaching236pad.adapter.CourseCatalogLsvAdapter;
import com.example.teaching236pad.model.Catalog;
import com.example.teaching236pad.model.KeyValue;
import com.example.teaching236pad.util.ValidateFormatUtils;
import com.example.teaching236pad.util.ViewUtils;
import com.example.teaching236pad.view.CustomGridView;
import com.example.teaching236pad.view.CustomListView02;

import java.util.ArrayList;
import java.util.List;

public class ChoiceMaterialAty extends FragmentActivity {
    private long exitTime = 0;
    private Handler uiHandler;// ui主线程

//    private String jsonLastId = "";// 最后一步点击操作的数据id（后台这么设计，木办法。。。）
//    private String lastType;// 最后一步点击操作的类型，例如：xd

    private ArrayList<String> ids;// 存放id数据的字符串组合
    String unitIDCurr = "";

    private List<KeyValue> classesList;// 班级
    private List<KeyValue> editionList;// 版本
    private List<KeyValue> moduleList;// 模块
    private List<Catalog> catalogList;// 目录
    private KeyValue catelogSelected;// 已选目录

    private KeyValue periodSelected;// 已选学段
    private KeyValue subjectSelected;// 已选科目
    private KeyValue editionSelected;// 已选版本
    private KeyValue moduleSelected;// 已选模块

    private CourseCatalogLsvAdapter catalogLstvAdapter;// 目录

    private ViewUtils vUtils;// 布局工具
    private CustomGridView gdvClasses;//班级
    private CustomGridView gdvEdition1;//版本
    private CustomGridView gdvModule1;// 模块
    private LinearLayout llCatalog;// 目录
    private CustomListView02 lsvCourseCatalog;// 课程目录


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.layout_aty_choice_material);

        // 初始化数据
        uiHandler = new Handler(getMainLooper());
        ids = new ArrayList<String>();
        vUtils = new ViewUtils(this);

        classesList = new ArrayList<KeyValue>();
        editionList = new ArrayList<KeyValue>();
        moduleList = new ArrayList<KeyValue>();
        catalogList = new ArrayList<Catalog>();

        periodSelected = new KeyValue();
        subjectSelected = new KeyValue();
        editionSelected = new KeyValue();
        moduleSelected = new KeyValue();
        catelogSelected = new KeyValue();

        gdvClasses = (CustomGridView) findViewById(R.id.gdv_calsses_layout_aty_choice_material);
        gdvModule1 = (CustomGridView) findViewById(R.id.gdv_edition1_layout_aty_choice_material);
        gdvEdition1 = (CustomGridView) findViewById(R.id.gdv_module1_layout_aty_choice_material);

        //目录
        llCatalog = (LinearLayout) findViewById(R.id.ll_wrapper_catalog_layout_aty_choice_material);
        llCatalog.setVisibility(View.VISIBLE);

        dealWithExtras();

        KeyValue kv = new KeyValue();
        kv.setId("c1");
        kv.setName("2019级1班");
        kv.setChoiced(true);

        KeyValue kv2 = new KeyValue();
        kv2.setId("c1");
        kv2.setName("2019级1班");
        kv2.setChoiced(false);

        classesList.add(kv);
        classesList.add(kv2);

        showGdvClasses(classesList, gdvClasses);

        KeyValue kv3 = new KeyValue();
        kv3.setId("e1");
        kv3.setName("统编版");
        kv3.setChoiced(true);
        editionList.add(kv3);

        showGdvEdition(editionList, gdvEdition1);

        KeyValue kv4 = new KeyValue();
        kv4.setId("m1");
        kv4.setName("必修 上册");
        kv4.setChoiced(true);

        KeyValue kv5 = new KeyValue();
        kv5.setId("m2");
        kv5.setName("必修 下册");
        kv5.setChoiced(false);
        moduleList.add(kv4);
        moduleList.add(kv5);

        showGdvModule(moduleList, gdvModule1);

        initLstvForCatalog();

        // 目录
//        cAdapter = new CatalogAdapter(this, catalogList, 0);
//        gdvCatalog.setAdapter(cAdapter);
//        cAdapter.setCurrentPosition(-1);


        Catalog ca = new Catalog();
        ca.setId("ca01");
        ca.setName("第一单元");
        ca.setPid("");
        ca.setPname("");
        ca.setSelected(true);

        List<Catalog> list001 = new ArrayList<Catalog>();
        Catalog ca001 = new Catalog();
        ca001.setId("ca001");
        ca001.setPid("ca01");
        ca001.setId("ca001");
        ca001.setSelected(true);
        ca001.setPname("第一单元");
        ca001.setName("1.沁园春·长沙");

        Catalog ca002 = new Catalog();
        ca002.setId("ca002");
        ca002.setPid("ca01");
        ca002.setId("ca002");
        ca002.setSelected(true);
        ca002.setPname("第一单元");
        ca002.setName("2.立在地球边上放哨");

        list001.add(ca001);
        list001.add(ca002);

        ca.setCatalog(list001);

        catalogList.add(ca);
        catalogList.add(ca);

        setCatalogAdapter(unitIDCurr, catelogSelected.getId());

    }

    /**
     * 处理接收到的数据
     */
    private void dealWithExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
    }

    /**
     * 展示版本Gdv布局
     *
     * @param list 数据
     */
    public void showGdvClasses(final List<KeyValue> list, CustomGridView gdv) {
        resetGdvLayout(gdv);

        for (int i = 0; i < list.size(); i++) {
            final KeyValue kv = list.get(i);
//            kv.setName("测试模块名");
//            kv.setId("ceshi_edition_id");
//            kv.setChoiced(false);

            View vItem = LayoutInflater.from(this).inflate(R.layout.layout_v_single_line, null);
            TextView tvName = vItem.findViewById(R.id.tv_layout_v_single_line);
            tvName.setText(list.get(i).getName());
//            tvName.setText("测试模块名");
            gdv.addChild(vItem);

            if (kv != null) {
                if (editionSelected != null) {
                    if (kv.getId().equals(editionSelected.getId())) {
                        tvName.setBackgroundResource(R.color.clog);
                        tvName.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        tvName.setBackgroundResource(R.color.white);
                        tvName.setTextColor(getResources().getColor(
                                R.color.color_text_title));
                    }
                }
            }

            vItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editionSelected = kv;
                    refreshWidgetForSelectEdition();

                    // 显示加载框
                    vUtils.showLoadingDialog("");
                    //请求数据
//                    requestDataFromServer(false);

                    showGdvClasses(classesList, gdvEdition1);
                    showGdvEdition(editionList, gdvEdition1);
                    showGdvModule(moduleList, gdvModule1);
                }
            });
        }
    }


    /**
     * 展示版本Gdv布局
     *
     * @param list 数据
     */
    public void showGdvEdition(final List<KeyValue> list, CustomGridView gdv) {
        resetGdvLayout(gdv);

        for (int i = 0; i < list.size(); i++) {
            final KeyValue kv = list.get(i);
//            kv.setName("测试模块名");
//            kv.setId("ceshi_edition_id");
//            kv.setChoiced(false);

            View vItem = LayoutInflater.from(this).inflate(R.layout.layout_v_single_line, null);
            TextView tvName = vItem.findViewById(R.id.tv_layout_v_single_line);
            tvName.setText(list.get(i).getName());
//            tvName.setText("测试模块名");
            gdv.addChild(vItem);

            if (kv != null) {
                if (editionSelected != null) {
                    if (kv.getId().equals(editionSelected.getId())) {
                        tvName.setBackgroundResource(R.color.clog);
                        tvName.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        tvName.setBackgroundResource(R.color.white);
                        tvName.setTextColor(getResources().getColor(
                                R.color.color_text_title));
                    }
                }
            }

            vItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editionSelected = kv;
                    refreshWidgetForSelectEdition();

                    // 显示加载框
                    vUtils.showLoadingDialog("");
                    //请求数据
//                    requestDataFromServer(false);

                    showGdvEdition(editionList, gdvEdition1);
                    showGdvModule(moduleList, gdvModule1);
                }
            });
        }
    }

    /**
     * 展示模块Gdv布局
     *
     * @param list 数据
     */
    public void showGdvModule(final List<KeyValue> list, CustomGridView gdv) {
        resetGdvLayout(gdv);

        for (int i = 0; i < list.size(); i++) {
            final KeyValue kv = list.get(i);
//            kv.setName("测试模块名");
//            kv.setId("ceshi_module_id");
//            kv.setChoiced(false);

            View vItem = LayoutInflater.from(this).inflate(R.layout.layout_v_single_line, null);
            TextView tvName = vItem.findViewById(R.id.tv_layout_v_single_line);
            tvName.setText(list.get(i).getName());
//            tvName.setText("测试模块名");
            gdv.addChild(vItem);

            if (kv != null) {
                if (moduleSelected != null) {
                    if (kv.getId().equals(moduleSelected.getId())) {
                        tvName.setBackgroundResource(R.color.clog);
                        tvName.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        tvName.setBackgroundResource(R.color.white);
                        tvName.setTextColor(getResources().getColor(
                                R.color.color_text_title));
                    }
                }
            }

            vItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moduleSelected = kv;
                    refreshWidgetForSelectModule();

                    // 显示加载框
                    vUtils.showLoadingDialog("");
                    //请求数据
                    //requestdatafromserver(false);

                    showGdvModule(moduleList, gdvModule1);
                }
            });
        }
    }


    private void initLstvForCatalog() {
        lsvCourseCatalog = (CustomListView02) findViewById(R.id.lsv_catalog_layout_aty_choice_material);
        lsvCourseCatalog.setEnabled(true);
        lsvCourseCatalog.setClickable(false);
        lsvCourseCatalog.setFocusable(true);
        lsvCourseCatalog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }


    /**
     * 课程目录适配器
     *
     * @param unitIDCurr    当前单元ID
     * @param catalogIDCurr 当前目录ID
     */
    private void setCatalogAdapter(String unitIDCurr, String catalogIDCurr) {
//        // if (catalogLstvAdapter == null) {
        catalogLstvAdapter = new CourseCatalogLsvAdapter(this, catalogList,
                unitIDCurr, catalogIDCurr);
        // } else {
        // catalogLstvAdapter.notifyDataSetChanged();
        // }
//
//        catalogLstvAdapter.setCurrentUnit(unitIDCurr);
//        catalogLstvAdapter.setCurrentCatalogID(catalogIDCurr);
        lsvCourseCatalog.setAdapter(catalogLstvAdapter);
    }

    private void resetGdvLayout(CustomGridView gdv) {
        if (gdv != null) {
            gdv.removeAllViews();
        }
    }

    /**
     * 版本
     */
    private void refreshWidgetForSelectEdition() {
        // 版本
        if (editionSelected != null) {
            // 赋新值
//            jsonLastId = editionSelected.getId();
//            lastType = "bb";
        }
    }

    /**
     * 模块
     */
    private void refreshWidgetForSelectModule() {

        // 版本
        if (editionSelected != null) {
            if (!ValidateFormatUtils.isEmpty(editionSelected.getId())) {
                // ids.put(ConstantsForServerUtils.BB, editionId);
                ids.add(editionSelected.getId());
            }
        }
        // 模块
        if (moduleSelected != null) {
//            jsonLastId = moduleSelected.getId();
//            lastType = "mk";
        }
    }

    public void onClick(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (vUtils != null) {
            vUtils.setCanShowDialog(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//                System.exit(0);
//            }
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }
}
