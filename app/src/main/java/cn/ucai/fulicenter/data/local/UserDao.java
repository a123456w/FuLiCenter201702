package cn.ucai.fulicenter.data.local;

import android.content.Context;

import cn.ucai.fulicenter.data.bean.User;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class UserDao {

    public UserDao(Context context){
        DBManager.getInstensce().initDB(context);
    }
    public User getUser(String username){
        return DBManager.getInstensce().getUser(username);
    }
    public boolean saveUser(User user){
        return DBManager.getInstensce().saveUser(user);
    }
}
