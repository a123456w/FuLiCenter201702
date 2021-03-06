package cn.ucai.fulicenter.ui.activity;

import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.Result;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.local.UserDao;
import cn.ucai.fulicenter.data.net.DownUserMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.MD5;
import cn.ucai.fulicenter.data.utils.ResultUtils;
import cn.ucai.fulicenter.data.utils.SharePrefrenceUtils;
import cn.ucai.fulicenter.ui.fiagment.CanterFragment;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    String username;
    String password;
    DownUserMode mode;
    UserDao userDao = new UserDao(this);
    ProgressDialog progressDialog;

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
                login();
                break;
            case R.id.btnRegis:
                startActivityForResult(new Intent(LoginActivity.this, RegistrActivity.class), 0);
                break;
        }
    }

    private void initDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.logining));
        progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    private void login() {
        initDialog();
        username = etUserName.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (checkinput()) {
            mode = new DownUserMode();
            mode.Login(LoginActivity.this, username, MD5.getMessageDigest(password)
                    , new OnCompleteListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            if (s != null) {
                                Result<User> result = ResultUtils.getResultFromJson(s, User.class);
                                if (result != null) {
                                    if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                        setUserNameMsg(etUserName, R.string.login_fail_unknow_user);
                                    } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                        setUserNameMsg(etPassword, R.string.login_fail_error_password);
                                    } else {
                                        User user = result.getRetData();
                                        LoginSuccess(user);
                                    }
                                }
                            }
                            dismissDialog();
                        }

                        @Override
                        public void onError(String error) {
                            dismissDialog();
                        }
                    });
        } else {
            dismissDialog();
        }
    }





    private void LoginSuccess(User user) {
        FuLiCenterApplication.getInstance().setUser(user);
        SharePrefrenceUtils.getInstance().setUserName(user.getMuserName());
        userDao.saveUser(user);
        setResult(RESULT_OK);
        finish();

    }



    private boolean checkinput() {
        if (TextUtils.isEmpty(username)) {
            setUserNameMsg(etUserName, R.string.user_name_connot_be_empty);
            return false;
        }

        if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            setUserNameMsg(etUserName, R.string.illegal_user_name);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            setUserNameMsg(etPassword, R.string.password_connot_be_empty);
            return false;
        }

        return true;
    }

    private void setUserNameMsg(EditText et, int msg) {
        et.requestFocus();
        et.setError(getString(msg));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String username = data.getStringExtra(I.User.USER_NAME);
            etUserName.setText(username);
        }
    }

    @OnClick(R.id.ivLogBank)
    public void onViewClicked() {
        finish();
    }
}
