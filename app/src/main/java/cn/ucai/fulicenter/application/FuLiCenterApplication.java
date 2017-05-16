package cn.ucai.fulicenter.application;

import android.app.Application;

import cn.ucai.fulicenter.data.bean.User;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;

    private static User user=null;
    private boolean isLogined=false;

    public static void setInstance(FuLiCenterApplication instance) {
        FuLiCenterApplication.instance = instance;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }

    public static User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        setLogined(user==null?false:true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    public static FuLiCenterApplication getInstance(){
        return instance;
    }
}
