package cn.ucai.fulicenter.data.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.data.bean.CollectBean;
import cn.ucai.fulicenter.data.bean.MessageBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public interface IDownUser {
    void Login(Context context, String UserName, String PassWord, OnCompleteListener<String> listener);
    void registr(Context context, String UserName, String Nick, String PassWord, OnCompleteListener<String> listener);
    void updateNick(Context context, String UserName, String NewNick, OnCompleteListener<String> listener);
    void uploadAvatar(Context context, String username, String avatar_type, File file, OnCompleteListener<String> listener);
    void loadCollectsCount(Context context, String username, OnCompleteListener<MessageBean> listener);
    void addCollects(Context context, String goodsId, String userneame , OnCompleteListener<MessageBean> listener);
    void removeCollects(Context context, String goodsId, String userneame , OnCompleteListener<MessageBean> listener);
    void isCollects(Context context, String goodsId, String username , OnCompleteListener<MessageBean> listener);
    void findCollects(Context context, String username , String pageid, String pagesize,OnCompleteListener<CollectBean[]> listener);

}
