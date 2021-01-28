package com.example.teaching236pad.aty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.teaching236pad.R;
import com.example.teaching236pad.model.AdditionalInfo;
import com.example.teaching236pad.model.ServiceVersionInfo;
import com.example.teaching236pad.model.User;
import com.example.teaching236pad.util.APKVersionCodeUtils;
import com.example.teaching236pad.util.ConstantsForPreferencesUtils;
import com.example.teaching236pad.util.ConstantsForServerUtils;
import com.example.teaching236pad.util.ConstantsUtils;
import com.example.teaching236pad.util.DeviceUtils;
import com.example.teaching236pad.util.NetworkUtils;
import com.example.teaching236pad.util.PermissionUtils;
import com.example.teaching236pad.util.PreferencesUtils;
import com.example.teaching236pad.util.ServerDataAnalyzeUtils;
import com.example.teaching236pad.util.ServerRequestUtils;
import com.example.teaching236pad.util.SoftKeyboardStateHelper;
import com.example.teaching236pad.util.UpdateManager;
import com.example.teaching236pad.util.UrlUtils;
import com.example.teaching236pad.util.ValidateFormatUtils;
import com.example.teaching236pad.util.ViewUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录
 *
 * @author chenhui 2018.06.26
 */
public class LoginActivity extends Activity {
    private long exitTime = 0;
    /**
     * 修改登录信息后的展示状态
     */
    public static String MODIFY_LOGIN_INFO = "MODIFY_LOGIN_INFO";
    private String showType = "";// 展示状态

    private String loginName;// 登录名
    private String password;// 登录密码

    private ServerRequestUtils requestUtils;// 网络请求
    private ViewUtils vUtils;// 布局工具
    private Handler uiHandler;// 主线程handler
    private DeviceUtils deviceUtils;// 设备信息工具

    private EditText edtName;// 登录名
    private EditText edtPassword;// 登录密码
    private CheckBox cboxRememberPsd;// 记住密码
    // private NotificationUtils nUtils; // 通知栏工具

    private Button tvLogin;// 登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //动态获取权限
        PermissionUtils permissionAty = new PermissionUtils();
        permissionAty.checkPermission(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_aty_login);

        uiHandler = new Handler(getMainLooper());
        // 初始化服务器请求操作
        requestUtils = new ServerRequestUtils(this);
        vUtils = new ViewUtils(this);

        deviceUtils = new DeviceUtils(this);

        // 初始化通知栏的进度条
        // nUtils = new NotificationUtils(this);

        // 记住密码
        RelativeLayout rlRememberPsd = (RelativeLayout) findViewById(R.id.rl_remember_layout_aty_login);
        cboxRememberPsd = (CheckBox) findViewById(R.id.cbox_remember_layout_aty_login);
        cboxRememberPsd.setClickable(false);

        // 登录
        tvLogin = (Button) findViewById(R.id.tv_login_layout_aty_login);
        tvLogin.setFocusable(true);
        tvLogin.setFocusableInTouchMode(true);
        // 然后获取焦 点
        tvLogin.requestFocus();
        tvLogin.requestFocusFromTouch();

        // 用户登录名
        edtName = (EditText) findViewById(R.id.edt_user_name_layout_aty_login);
        String loginName = PreferencesUtils.acquireInfoFromPreferences(this,
                ConstantsForPreferencesUtils.LOGIN_NAME);
        edtName.setText(loginName);
        // 将光标设置到内容末尾
        edtName.setSelection(loginName.length());

        // 登录密码
        edtPassword = (EditText) findViewById(R.id.edt_password_layout_aty_login);
        String password = PreferencesUtils.acquireInfoFromPreferences(this,
                ConstantsForPreferencesUtils.LOGIN_PASSWORD);
        edtPassword.setText(password);
        // 将光标移至内容末尾
        edtPassword.setSelection(password.length());

        // 用户名/手机号/邮箱
        LinearLayout llName = (LinearLayout) findViewById(R.id.ll_user_name_layout_aty_login);

        // 软件盘相关
        SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(
                findViewById(R.id.fl_all_layout_aty_login));

        dealWithExtras();

        rlRememberPsd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = cboxRememberPsd.isChecked();
                if (isChecked) {// 当前是已经选中的状态，需要取消选中
                    cboxRememberPsd.setChecked(false);
                } else {// 当前是没有选中的状态，需要选中
                    cboxRememberPsd.setChecked(true);
                }

                // 获取焦 点
                tvLogin.requestFocus();
                tvLogin.requestFocusFromTouch();
            }
        });

        tvLogin.setOnClickListener(new Listeners());
        llName.setOnClickListener(new Listeners());

        softKeyboardStateHelper
                .addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
                    @Override
                    public void onSoftKeyboardOpened(int keyboardHeightInPx) { // 键盘打开
                        Log.i("软键盘监听addSoftKeyboard", "键盘打开");
                    }

                    @Override
                    public void onSoftKeyboardClosed() { // 键盘关闭
                        // 登录按钮重新获取焦点
                        tvLogin.requestFocus();
                        tvLogin.requestFocusFromTouch();
                    }
                });

        // 更新版本
        updateToServer();
    }

    /**
     * 处理接收过来的数据
     */
    private void dealWithExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        showType = bundle.getString(ConstantsUtils.TYPE);
        if (MODIFY_LOGIN_INFO.equals(showType)) {
            edtName.setEnabled(false);
        }
    }

    /**
     * 更新版本
     */
    private void updateToServer() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        // 测试用
        RequestBody formBody = new FormBody.Builder().add("apptype", "3")
                .build();
        Request request = new Request.Builder()
                .url(UrlUtils.PREFIX_MOBILE + "getUpdate").post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException ex) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (ValidateFormatUtils.isEmpty(ex.toString())) {
                            Toast.makeText(LoginActivity.this, "获取版本升级信息失败",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, ex.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                final String str = response.body().string();
                Log.i("user", str.toString());
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ServiceVersionInfo versionInfo = new Gson().fromJson(
                                str.toString(), ServiceVersionInfo.class);
                        if (versionInfo != null && versionInfo.data != null
                                && versionInfo.data.serverVersion != null
                                && versionInfo.data.updateurl != null) {

                            UpdateManager update = new UpdateManager(
                                    LoginActivity.this,
                                    versionInfo.data.serverVersion,
                                    versionInfo.data.lowVersion,
                                    versionInfo.data.updateurl,
                                    versionInfo.data.lastForce);
                            update.checkUpdate();

                        }
                    }
                });
            }
        });
    }

    /**
     * 登录
     */
    private void loginToServer() {
        User user = new User();

        // 登录名
        user.setLoginName(loginName);
        // 登录密码
        user.setLoginPassword(password);

        // 附加信息
        AdditionalInfo aInfo = new AdditionalInfo();
        aInfo.setDeviceType(ConstantsForServerUtils.DEVICE_TYPE_VALUE);

        // 设备名称
        aInfo.setDeviceName(deviceUtils.getName());

        // 设备系统版本
        aInfo.setDeviceVersion(deviceUtils.getAndroidVersion());

        // 设备IMEI,唯一标识
        aInfo.setDeviceSerialNumber(deviceUtils.getIMEI());

        // 设备型号
        aInfo.setDeviceModel(deviceUtils.getModel());

        int versionCode = APKVersionCodeUtils
                .getVersionCode(LoginActivity.this);

        // 应用当前版本号
        aInfo.setAppVersion(String.valueOf(versionCode));

        user.setAdditionalInfo(aInfo);

        String jsonStr = com.alibaba.fastjson.JSON.toJSONString(user);

        requestUtils.request("appLogin", jsonStr, "正在登录...",
                new ServerRequestUtils.OnServerRequestListener() {
                    @Override
                    public void onResponse(final String msg,
                                           final JSONObject data, String count) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功",
                                        Toast.LENGTH_SHORT).show();
                                if (data != null) {
                                    dealWithData(data);
                                }
                                vUtils.dismissDialog();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final String msg) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!ValidateFormatUtils.isEmpty(msg)) {
                                    Toast.makeText(LoginActivity.this, msg,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "登录失败",
                                            Toast.LENGTH_SHORT).show();
                                }
                                vUtils.dismissDialog();
                            }
                        });
                    }
                });
    }

    /**
     * 判断是否具备登录的条件
     *
     * @return
     */
    private boolean isCanLogin() {
        loginName = edtName.getText().toString().trim();
        if (ValidateFormatUtils.isEmpty(loginName)) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        password = edtPassword.getText().toString().trim();
        if (ValidateFormatUtils.isEmpty(loginName)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        // 检测网络是否连接
        if (!NetworkUtils.checkNetworkState(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this,
                    R.string.check_network_connections, Toast.LENGTH_SHORT)
                    .show();

            return false;
        }
        return true;
    }

    /**
     * 处理结果
     */
    private void dealWithData(JSONObject data) {
        // 发送广播，告知主界面需要刷新用户信息
        Intent intent2 = new Intent();
        intent2.setAction(ConstantsUtils.REFRESH_USER_INFO);// 刷新用户信息

        // 用户昵称
        String nickName = ServerDataAnalyzeUtils.getValue(data,
                ConstantsForServerUtils.DATANAME);
        if (!ValidateFormatUtils.isEmpty(nickName)) {
            PreferencesUtils.saveInfoToPreferences(this,
                    ConstantsForPreferencesUtils.NICK_NAME, nickName);

            // 广播
            intent2.putExtra(ConstantsForPreferencesUtils.NICK_NAME, nickName);// 昵称
        } else {
            Log.i("LoginActivity", "用户名username获取失败");
        }

        // 头像
        String headPicUrl = ServerDataAnalyzeUtils.getValue(data,
                ConstantsForServerUtils.AVATAR);
        if (!ValidateFormatUtils.isEmpty(headPicUrl)) {
            // 拼接地址，重新赋值，存入完整地址
            headPicUrl = UrlUtils.PREFIX + headPicUrl;
            PreferencesUtils.saveInfoToPreferences(this,
                    ConstantsForPreferencesUtils.USER_HEAD_PIC_URL, headPicUrl);

            // 广播
            intent2.putExtra(ConstantsForPreferencesUtils.USER_HEAD_PIC_URL,
                    headPicUrl);// 头像
        } else {
            Log.i("LoginActivity", "用户名头像获取失败");
        }

        // 用户身份唯一值Token
        String token = ServerDataAnalyzeUtils.getValue(data,
                ConstantsForServerUtils.TOKEN);
        if (!ValidateFormatUtils.isEmpty(token)) {
            PreferencesUtils.saveInfoToPreferences(this,
                    ConstantsForPreferencesUtils.TOKEN, token);
        }

        // 记下登录名，登录名与昵称不一样，是从EditText中取的值
        PreferencesUtils.saveInfoToPreferences(LoginActivity.this,
                ConstantsForPreferencesUtils.LOGIN_NAME, loginName);

        // 若用户选择了记住密码
        if (cboxRememberPsd.isChecked()) {
            PreferencesUtils.saveInfoToPreferences(LoginActivity.this,
                    ConstantsForPreferencesUtils.LOGIN_PASSWORD, password);
        } else {
            PreferencesUtils.saveInfoToPreferences(LoginActivity.this,
                    ConstantsForPreferencesUtils.LOGIN_PASSWORD, "");
        }

        if (MODIFY_LOGIN_INFO.equals(showType)) {// 修改登录信息后
            // 作用：通知修改密码界面，方便处理跳转等功能
            Intent intent = new Intent();
            setResult(ConstantsUtils.INTENT_01, intent);

            // 发送广播
//            LocalBroadcastManager.getInstance(LoginActivity.this)
//                    .sendBroadcast(intent2);
        } else {
            // 跳转至选择教材界面
//            Intent intent = new Intent(LoginActivity.this,
//                    ChoiceTextbookEditionActivity.class);
//            startActivity(intent);
        }

        //获取班级列表
        acquireClassList();

        // 关闭本页面
        finish();
    }

    /**
     * 获取班级列表
     */
    private void acquireClassList() {
        requestUtils.request("getClassList", "", "加载班级数据中", ServerRequestUtils.REQUEST_SHORT_TIME, new ServerRequestUtils.OnServerRequestListener2() {
            @Override
            public void onFailure(String msg) {
                if (!ValidateFormatUtils.isEmpty(msg)) {
                    Toast.makeText(LoginActivity.this, msg,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "获取班级列表失败",
                            Toast.LENGTH_SHORT).show();
                }
                vUtils.dismissDialog();
            }

            @Override
            public void onResponse(String msg, JSONArray data, String count) {
                //暂无数据，用假数据代替，2019.11.20
//                data = new JSONArray();
//                try {
//                    JSONObject obj1 = new JSONObject();
//                    obj1.put("DataID", 1);
//                    obj1.put("DataName", "高一(1)班");
//
//                    JSONObject obj2 = new JSONObject();
//                    obj2.put("DataID", 2);
//                    obj2.put("DataName", "高一(2)班");
//
//                    data.put(obj1);
//                    data.put(obj2);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                if (data != null) {
                    //把班级jsonArray存入首选项文件
                    PreferencesUtils.saveInfoToPreferences(LoginActivity.this, ConstantsForPreferencesUtils.CLASS_LIST_JSONARR, data.toString());
                }
            }
        });
    }

    /**
     * 监听
     *
     * @author chenhui 2018.06.27
     */
    public class Listeners implements OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.tv_login_layout_aty_login://登录
                    // loginName = "demo";
                    // password = "123456";

//                    if (isCanLogin()) {
                    vUtils.showLoadingDialog("");

//                        loginToServer();
                    Intent intent = new Intent(LoginActivity.this,
                            ChoiceMaterialAty.class);
                    startActivity(intent);

                    if (vUtils != null)
                        vUtils.dismissDialog();
//                    }

                    break;
                case R.id.ll_user_name_layout_aty_login:// 用户名
                    // 然后获取焦 点
                    tvLogin.requestFocus();
                    tvLogin.requestFocusFromTouch();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.PERMISSION_REQUEST:

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (vUtils != null) {
            vUtils.setCanShowDialog(true);
        }
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
