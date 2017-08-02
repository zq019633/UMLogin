package com.umlogin;

import android.Manifest;
import android.app.Application;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by zhouqiang on 2017/8/1.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Config.DEBUG = true;
        UMShareAPI.get(this);
    }

    {
        PlatformConfig.setWeixin("wx214ad894fe5a2049", "d4624c36b6795d1d99dcf0547af5443d");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
}
