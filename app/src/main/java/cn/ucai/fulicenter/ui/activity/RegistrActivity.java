package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class RegistrActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegistr)
    public void onViewClicked() {

    }
}
