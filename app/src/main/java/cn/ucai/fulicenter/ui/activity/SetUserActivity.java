package cn.ucai.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.data.utils.SharePrefrenceUtils;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class SetUserActivity extends AppCompatActivity {
    @BindView(R.id.ivUserAvatars)
    ImageView ivAvatars;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserNick)
    TextView tvUserNick;
    Unbinder bind;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setuser);
        bind = ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        user = FuLiCenterApplication.getInstance().getUser();
        if (user != null) {
            tvUserNick.setText(user.getMuserNick());
            tvUserName.setText(user.getMuserName());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), SetUserActivity.this, ivAvatars);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    @OnClick(R.id.btnBank)
    public void onViewClicked() {
        FuLiCenterApplication.getInstance().setUser(null);
        SharePrefrenceUtils.getInstance().removeUser();
        startActivity(new Intent(SetUserActivity.this,LoginActivity.class));
        finish();
    }
}
