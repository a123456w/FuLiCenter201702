package cn.ucai.fulicenter.data.net;

import android.content.Context;

import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public interface IDownUser {
    void Login(Context context, String UserName, String PassWord, OnCompleteListener<String> listener);
    void registr(Context context, String UserName, String Nick, String PassWord, OnCompleteListener<String> listener);
    void updateNick(Context context, String UserName, String NewNick, OnCompleteListener<String> listener);
}
