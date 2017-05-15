package cn.ucai.fulicenter.data.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.CollectBean;
import cn.ucai.fulicenter.data.bean.MessageBean;
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

    @Override
    public void updateNick(Context context, String UserName, String NewNick, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,UserName)
                .addParam(I.User.NICK,NewNick)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void uploadAvatar(Context context, String username, String avatar_type, File file, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,username)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void loadCollectsCount(Context context, String username, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void addCollects(Context context, String goodsId, String username, OnCompleteListener<MessageBean> listener) {
        collectAction(I.ACTION_ADD_COLLECT,context,goodsId,username,listener);
    }

    private void collectAction(int action, Context context, String goodsId, String username, OnCompleteListener<MessageBean> listener) {
        String url=I.REQUEST_IS_COLLECT;
        if(action==I.ACTION_ADD_COLLECT){
            url=I.REQUEST_ADD_COLLECT;
        }else if(action==I.ACTION_DELETE_COLLECT){
            url=I.REQUEST_DELETE_COLLECT;
        }
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(url)
                .addParam(I.Collect.GOODS_ID,goodsId)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void removeCollects(Context context, String goodsId, String userneame, OnCompleteListener<MessageBean> listener) {
        collectAction(I.ACTION_DELETE_COLLECT,context,goodsId,userneame,listener);
    }

    @Override
    public void isCollects(Context context, String goodsId, String userneame, OnCompleteListener<MessageBean> listener) {
        collectAction(I.ACTION_ISCOLLECT,context,goodsId,userneame,listener);
    }

    @Override
    public void findCollects(Context context,String username, String pageid, String pagesize, OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.PAGE_ID,pageid)
                .addParam(I.PAGE_SIZE,pagesize)
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    @Override
    public void addCart(Context context, int goodsId, String username, int count, boolean isChecked, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID,String.valueOf(goodsId))
                .addParam(I.User.USER_NAME,username)
                .addParam(I.Cart.COUNT,String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(isChecked))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void removeCart(Context context, int cartId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,String.valueOf(cartId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void updateCart(Context context, int cartId, int count, boolean isChecked, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,String.valueOf(cartId))
                .addParam(I.Cart.COUNT,String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(isChecked))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void loadCart(Context context, String username, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.User.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
