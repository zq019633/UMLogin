package com.umlogin;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView wx_login;
    private TextView qq_login;
    private TextView weibo_login;
    private Button wx_share;
    private Button pyq_share;
    private Button qq_share;
    private Button weibo_share;
    private Button qzone_share;
    private ShareAction shareAction;
    private UMShareListener mShareListener;
    private Button wx_fav;
    private TextView sex;
    private ImageView headUrl;
    private TextView nickName;
    private Button out;
    private Button all;
    private Button share;
    private GridView gridView;
    private ArrayList<String> list;
    private ArrayList<Integer> imageViews;
    private Button diss;
    private Dialog d;
    private ArrayList<AppInfo> shareAppList;
    private ArrayList<AppInfo> dataList;
    private LinearLayout wx_ll;
    private LinearLayout qq_ll;
    private LinearLayout sina_ll;
    private LinearLayout all_ll;
    private RelativeLayout back_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //权限
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        initView();

        dataList = getShareAppList();
    }

    private void initView() {
        list = new ArrayList<>();
        list.add("微信分享");
        list.add("朋友圈分享");
        list.add("QQ分享");
        list.add("QQ空间分享");
        list.add("微博分享");
        list.add("更多");

        imageViews = new ArrayList<>();
        imageViews.add(R.drawable.umeng_socialize_wechat);
        imageViews.add(R.drawable.umeng_socialize_wxcircle);
        imageViews.add(R.drawable.umeng_socialize_qq);
        imageViews.add(R.drawable.umeng_socialize_qzone);
        imageViews.add(R.drawable.umeng_socialize_sina);
        imageViews.add(R.drawable.umeng_socialize_more);


        wx_login = (TextView) findViewById(R.id.wx_login);
        qq_login = (TextView) findViewById(R.id.qq_login);
        weibo_login = (TextView) findViewById(R.id.sign_login);


        wx_ll = (LinearLayout) findViewById(R.id.wx_ll);
        qq_ll = (LinearLayout) findViewById(R.id.qq_ll);
        sina_ll = (LinearLayout) findViewById(R.id.sina_ll);


        wx_share = (Button) findViewById(R.id.wx_share);
        pyq_share = (Button) findViewById(R.id.pyq_share);
        qq_share = (Button) findViewById(R.id.qq_share);
        weibo_share = (Button) findViewById(R.id.weibo_share);
        qzone_share = (Button) findViewById(R.id.qzone_share);
        wx_fav = (Button) findViewById(R.id.wx_fav);
        all = (Button) findViewById(R.id.all);

        sex = (TextView) findViewById(R.id.sex);
        headUrl = (ImageView) findViewById(R.id.headUrl);
        nickName = (TextView) findViewById(R.id.NickName);


        share = (Button) findViewById(R.id.share);


        all_ll = (LinearLayout) findViewById(R.id.all_ll);

        back_rl = (RelativeLayout) findViewById(R.id.back_rl);


        wx_ll.setOnClickListener(this);
        qq_ll.setOnClickListener(this);
        sina_ll.setOnClickListener(this);


        wx_share.setOnClickListener(this);
        pyq_share.setOnClickListener(this);
        qq_share.setOnClickListener(this);
        weibo_share.setOnClickListener(this);
        qzone_share.setOnClickListener(this);
        wx_fav.setOnClickListener(this);
        all.setOnClickListener(this);


        back_rl.setOnClickListener(this);


        share.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //-----------------------------登陆  先进行授权-----------------------------
            case R.id.wx_ll:
                login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.qq_ll:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.sina_ll:
                login(SHARE_MEDIA.SINA);
                break;
            //-----------------------------登陆  先进行授权-----------------------------


            //-------------退出登陆---------
            case R.id.back_rl:
                out();
                break;
            //-------------退出登陆---------

            //------------------------------分享-----------------------------------

            case R.id.wx_share:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.pyq_share:
                share(SHARE_MEDIA.WEIXIN_CIRCLE);

                break;
            case R.id.wx_fav:
                share(SHARE_MEDIA.WEIXIN_FAVORITE);
                break;

            case R.id.qq_share:
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.qzone_share:
                share(SHARE_MEDIA.QZONE);
                break;
            case R.id.weibo_share:
                share(SHARE_MEDIA.SINA);
                break;

            //------------------------------分享-----------------------------------
            case R.id.all:
                shareAppList = getShareAppList();
                for (int i = 0; i < shareAppList.size(); i++) {
                    android.util.Log.e("app", "appName" + shareAppList.get(i).getAppName());
                }

                break;

            case R.id.share:
                //弹出对话框
                showDialog();


                break;


        }

    }

    /**
     * 展示 分享的dialog
     */
    private void showDialog() {
        d = new Dialog(MainActivity.this, R.style.customDialog);
        d.requestWindowFeature(Window.FEATURE_PROGRESS);
        d.setCanceledOnTouchOutside(true);
        d.setCancelable(true);
        d.setContentView(R.layout.dialog_share_app);

        Window dialogWindow = d.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(lp);
        initDialog(d);
        d.show();

    }

    /**
     * 初始化分享的dialog
     * @param d
     */
    private void initDialog(final Dialog d) {
        gridView = (GridView) d.findViewById(R.id.gridView);
        diss = (Button) d.findViewById(R.id.btn_dismiss);
        diss.setOnClickListener(this);
        ZQAdapter zqAdapter = new ZQAdapter(imageViews, list, MainActivity.this);

        gridView.setAdapter(zqAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    share(SHARE_MEDIA.WEIXIN);
                } else if (position == 1) {
                    share(SHARE_MEDIA.WEIXIN_CIRCLE);
                } else if (position == 2) {
                    share(SHARE_MEDIA.QQ);
                } else if (position == 3) {
                    share(SHARE_MEDIA.QZONE);
                } else if (position == 4) {
                    share(SHARE_MEDIA.SINA);
                } else if (position == 5) {
                    showMoreDialog();
                }
            }
        });
        hideDialog(d);
    }

    /**
     * 隐藏分享dialog
     *
     * @param d
     */
    private void hideDialog(final Dialog d) {
        diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

    /**
     * 展示分享更多的 dialog
     */
    private void showMoreDialog() {
        d = new Dialog(MainActivity.this, R.style.customDialog);
        final Dialog d = new Dialog(MainActivity.this, R.style.customDialog);
        d.requestWindowFeature(Window.FEATURE_PROGRESS);
        d.setCanceledOnTouchOutside(true);
        d.setCancelable(true);

        d.setContentView(R.layout.dialog_share_app);

        Window dialogWindow = d.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(lp);
        initMoreDialog(d);
        d.show();

    }

    /**
     * 初始化 分享更多的dialog
     *
     * @param d
     */
    private void initMoreDialog(Dialog d) {
        gridView = (GridView) d.findViewById(R.id.gridView);
        diss = (Button) d.findViewById(R.id.btn_dismiss);
        diss.setOnClickListener(this);
        ZQMoreAdapter zqAdapter = new ZQMoreAdapter(imageViews, dataList, MainActivity.this);
        gridView.setAdapter(zqAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                AppInfo appInfo = (AppInfo) parent.getItemAtPosition(position);
                shareIntent.setComponent(new ComponentName(appInfo.getPkgName(), appInfo.getLaunchClassName()));
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "www.baidu.com");
                startActivity(shareIntent);
            }
        });
        hideDialog(d);


    }

    /**
     * 获取手机中所有的app 信息
     *
     * @return
     */
    private ArrayList<AppInfo> getShareAppList() {
        ArrayList<AppInfo> shareAppInfos = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfos = getShareApps(MainActivity.this);
        if (null == resolveInfos) {
            return null;
        } else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                AppInfo appInfo = new AppInfo();
                appInfo.setPkgName(resolveInfo.activityInfo.packageName);
                appInfo.setLaunchClassName(resolveInfo.activityInfo.name);
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                shareAppInfos.add(appInfo);
            }
        }

        return shareAppInfos;
    }

    //获取手机中所有能分享的app
    public List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps;
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return mApps;
    }

    /**
     * 退出登陆  取消授权
     */
    private void out() {
        //取消授权
        UMShareAPI.get(MainActivity.this).deleteOauth(MainActivity.this, SHARE_MEDIA.WEIXIN, outAuthListener);
    }

    //取消授权的回掉
    UMAuthListener outAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(MainActivity.this, "退出成功了", Toast.LENGTH_LONG).show();
            sex.setText("昵称");
            nickName.setText("性别");
            headUrl.setImageResource(R.mipmap.head);
            wx_ll.setVisibility(View.VISIBLE);
            qq_ll.setVisibility(View.VISIBLE);
            sina_ll.setVisibility(View.VISIBLE);

            back_rl.setVisibility(View.GONE);


        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };


    /**
     * 登陆  获取授权
     *
     * @param zq
     */
    private void login(SHARE_MEDIA zq) {
        UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, zq, authListener);
    }

    //获取授权的回掉
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
            android.util.Log.e("Map", "=" + data);

            if (platform == SHARE_MEDIA.WEIXIN) {
                String name = data.get("screen_name");
                String profile_image_url = data.get("profile_image_url");
                String gender = data.get("gender");
                sex.setText(gender);
                nickName.setText(name);
                Picasso.with(MainActivity.this).load(profile_image_url).into(headUrl);
                wx_ll.setVisibility(View.GONE);
                qq_ll.setVisibility(View.GONE);
                sina_ll.setVisibility(View.GONE);
                all_ll.setVisibility(View.VISIBLE);

                back_rl.setVisibility(View.VISIBLE);


            } else if (platform == SHARE_MEDIA.QQ) {
                String qqName = data.get("screen_name");
                String profile_image_url = data.get("profile_image_url");
                String gender = data.get("gender");
                sex.setText(gender);
                Picasso.with(MainActivity.this).load(profile_image_url).into(headUrl);
                nickName.setText(qqName);
                wx_ll.setVisibility(View.GONE);
                qq_ll.setVisibility(View.GONE);
                sina_ll.setVisibility(View.GONE);
                all_ll.setVisibility(View.VISIBLE);
                back_rl.setVisibility(View.VISIBLE);
            }


        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };


    /**
     * 分享的内容  常用链接的形式
     * @param zq
     */
    private void share(SHARE_MEDIA zq) {
        UMWeb web = new UMWeb("http://www.ciweek.com");
        web.setTitle("来自互联网周刊");
        web.setDescription("来自互联网周刊...");
        web.setThumb(new UMImage(MainActivity.this, R.mipmap.ic_launcher));

        new ShareAction(MainActivity.this)
                .setPlatform(zq)
                .withMedia(web)
                .setCallback(umShareListener)//回调监听器
                .share();
    }


    //分享的回掉
    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }


        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(MainActivity.this, " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(MainActivity.this, " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(MainActivity.this, " 分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        //拒绝权限  没同意

    }

    //集成qq 微博  需要重写这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
