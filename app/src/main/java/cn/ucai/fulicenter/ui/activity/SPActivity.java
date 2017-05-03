package cn.ucai.fulicenter.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.ucai.fulicenter.R;

public class SPActivity extends AppCompatActivity {
    TextView tvSplash;
    int time=5000;
    MyCountTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        tvSplash= (TextView) findViewById(R.id.tvSplash);
    }


    @Override
    protected void onResume() {
        super.onResume();
        timer=new MyCountTimer(time,1000);
        timer.start();
    }

    public void on(View view) {
        startActivity(new Intent(SPActivity.this,MainActivity.class));
    }

    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvSplash.setText("跳过"+millisUntilFinished/1000+"s");
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SPActivity.this,MainActivity.class));
        }
    }
}
