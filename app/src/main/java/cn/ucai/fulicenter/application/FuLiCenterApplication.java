package cn.ucai.fulicenter.application;

import android.app.Application;

import cn.ucai.fulicenter.data.bean.User;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;

    private static User user=null;

    public static User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
