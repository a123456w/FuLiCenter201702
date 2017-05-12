package cn.ucai.fulicenter.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.ucai.fulicenter.data.bean.User;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class DBManager {
    private static DBOpenHelper sHelper;
    private static DBManager manager = new DBManager();

    public static DBManager getInstensce(){
        return manager;
    }
    public static void initDB(Context context){
        sHelper=DBOpenHelper.getInstance(context);
    }
    private void DBManager(){

    }
    public synchronized boolean saveUser(User user){
        SQLiteDatabase database = sHelper.getWritableDatabase();
        if(database.isOpen()){
            ContentValues values = new ContentValues();
            values.put(DBOpenHelper.USER_COLUMN_NAME,user.getMuserName());
            values.put(DBOpenHelper.USER_COLUMN_NICK,user.getMuserNick());
            values.put(DBOpenHelper.USER_COLUMN_AVATAR_PATH,user.getMavatarPath());
            values.put(DBOpenHelper.USER_COLUMN_AVATAR_SUFFIX,user.getMavatarSuffix());
            values.put(DBOpenHelper.USER_COLUMN_AVATAR_UPDATE_TIME,user.getMavatarLastUpdateTime());

            long insert = database.replace(DBOpenHelper.USER_TABALE_NAME, null, values);
            return insert>0?true:false;
        }
        return false;
    }
    public synchronized User getUser(String username){
        User user=new User();
        SQLiteDatabase database = sHelper.getReadableDatabase();
        if(database.isOpen()) {
            Cursor cursor = database.rawQuery("select * from " + DBOpenHelper.USER_TABALE_NAME + " where " +
                    DBOpenHelper.USER_COLUMN_NAME + "=?", new String[]{username});
            if(cursor.moveToNext()){
                String nick = cursor.getString(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_NICK));
                String mavatarLastUpdateTime = cursor.getString(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_AVATAR_UPDATE_TIME));
                String path = cursor.getString(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_AVATAR_PATH));
                String suffix = cursor.getString(cursor.getColumnIndex(DBOpenHelper.USER_COLUMN_AVATAR_SUFFIX));

                user.setMuserName(username);
                user.setMuserNick(nick);
                user.setMavatarSuffix(suffix);
                user.setMavatarPath(path);
                user.setMavatarLastUpdateTime(mavatarLastUpdateTime);
            }
        }
        return user;
    }


}
