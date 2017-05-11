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
            long insert = database.insert(DBOpenHelper.USER_TABALE_NAME, null, values);
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
                user.setMuserNick(nick);
            }
        }
return user;
    }


}
