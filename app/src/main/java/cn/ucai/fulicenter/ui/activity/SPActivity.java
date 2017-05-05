package cn.ucai.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;

public class SPActivity extends AppCompatActivity {
    int time = 5000;
    MyCountTimer timer;
    @BindView(R.id.tvSplash) TextView tvSplash;
    Unbinder bind;
    @OnClick(R.id.tvSplash) void onClick(View v){
        timer.cancel();
        timer.onFinish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        bind = ButterKnife.bind(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        timer = new MyCountTimer(time, 1000);
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvSplash.setText("跳过" + millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SPActivity.this, MainActivity.class));
            finish();
        }

    }
}
