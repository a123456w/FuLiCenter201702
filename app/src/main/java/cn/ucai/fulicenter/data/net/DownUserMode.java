package cn.ucai.fulicenter.data.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class DownUserMode implements IDownUser {
    @Override
    public void Login(Context context, String UserName, String PassWord, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,UserName)
                .addParam(I.User.PASSWORD,PassWord)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void registr(Context context, String UserName, String Nick, String PassWord, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,UserName)
                .addParam(I.User.NICK,Nick)
                .addParam(I.User.PASSWORD,PassWord)
                .post()
                .targetClass(String.class)
                .execute(listener);
    }
}
