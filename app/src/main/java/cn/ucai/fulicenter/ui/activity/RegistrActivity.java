package cn.ucai.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.Result;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.DownUserMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class RegistrActivity extends AppCompatActivity {
    String username;
    String usernick;
    String password;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPassword1)
    EditText etPassword1;
    DownUserMode Mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnRegistr)
    public void onViewClicked() {
        initDialog();
     register();
    }
    ProgressDialog progressDialog;
    private void initDialog() {
        progressDialog= new ProgressDialog(RegistrActivity.this);
        progressDialog.setMessage(getString(R.string.registering));
        progressDialog.show();
    }
    public void dismissDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }

    }

    private void register() {

        if (checkinput()) {
            Mode = new DownUserMode();
            Mode.registr(RegistrActivity.this, username, usernick, password,
                    new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    if(s!=null){
                        Result result = ResultUtils.getResultFromJson(s, User.class);
                        if(result!=null){
                            if(result.getRetCode()== I.MSG_REGISTER_USERNAME_EXISTS){
                                etUserName.requestFocus();
                                etUserName.setError(getString(R.string.register_fail_exists));
                            }else if (result.getRetCode()==I.MSG_REGISTER_FAIL){
                                etUserName.requestFocus();
                                etUserName.setError(getString(R.string.register_fail));
                            }else {
                                registerSuccess();
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
        }else{
            dismissDialog();
        }
    }

    private void registerSuccess() {
        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
        finish();
    }


    private boolean checkinput() {
        username=etUserName.getText().toString().trim();
        usernick=etNick.getText().toString().trim();
        password=etPassword.getText().toString().trim();
        String cpwd = etPassword1.getText().toString().trim();
        if(TextUtils.isEmpty(username)){//username是否为空
            etUserName.requestFocus();
            etUserName.setError(getString(R.string.user_name_connot_be_empty));
            return false;
        }
        if(!username.matches("[a-zA-Z]\\w{5,15}")){
            etUserName.requestFocus();
            etUserName.setError(getString(R.string.illegal_user_name));
        }
        if(TextUtils.isEmpty(usernick)){//username是否为空
            etNick.requestFocus();
            etNick.setError(getString(R.string.nick_name_connot_be_empty));
            return false;
        }
        if(TextUtils.isEmpty(password)){//cpwd是否为空
            etPassword1.requestFocus();
            etPassword1.setError(getString(R.string.confirm_password_connot_be_empty));
            return false;
        }
        if(TextUtils.isEmpty(cpwd)){//cpwd是否为空
            etPassword1.requestFocus();
            etPassword1.setError(getString(R.string.confirm_password_connot_be_empty));
            return false;
        }
        if(!password.equals(cpwd)){//密码是否一至
            etPassword1.requestFocus();
            etPassword1.setError(getString(R.string.two_input_password));
            return false;
        }
        return true;
    }
}
