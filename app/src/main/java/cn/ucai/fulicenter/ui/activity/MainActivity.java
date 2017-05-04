package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.IDownNewGood;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.ui.fiagment.GoodsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onCheckedChange(View v){
        getSupportFragmentManager().beginTransaction().add(R.id.FrameLayout,new GoodsFragment()).commit();
    }

}

