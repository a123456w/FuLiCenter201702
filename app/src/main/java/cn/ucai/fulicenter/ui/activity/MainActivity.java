package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.IDownNewGood;
import cn.ucai.fulicenter.data.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onCheckedChange(View v){
        textDownload();
    }
    void textDownload(){
        IDownNewGood newgood=new DownNewGoodMode();
        newgood.DownNewGoodData(MainActivity.this, 0, 1, 10,
                new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        Log.i("main" ,"result="+result);
                        if(result!=null){
                            for (NewGoodsBean bean : result) {
                                Log.i("main" ,bean.toString());
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
