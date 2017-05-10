package cn.ucai.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnLogin, R.id.btnRegis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                break;
            case R.id.btnRegis:
                startActivity(new Intent(LoginActivity.this,RegistrActivity.class));
                break;
        }
    }
}
