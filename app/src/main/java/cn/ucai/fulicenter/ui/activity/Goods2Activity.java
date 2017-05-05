package cn.ucai.fulicenter.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;

public class Goods2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int intExtra = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID,I.CAT_ID);

        setContentView(R.layout.activity_goods2);
    }
}
