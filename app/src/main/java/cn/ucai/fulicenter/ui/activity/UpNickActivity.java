package cn.ucai.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

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
import cn.ucai.fulicenter.data.utils.CommonUtils;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class UpNickActivity extends AppCompatActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etSetNick)
    EditText etSetNick;
    User user;
    DownUserMode mode;
    ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upnick);
        ButterKnife.bind(this);
        initView();
    }
    public void initDialog(){
        pd=new ProgressDialog(UpNickActivity.this);
        pd.setMessage(getString(R.string.update_user_nick));
        pd.show();
    }
    public void dismissDialog(){
        if(pd!=null){
            pd.dismiss();
        }
    }

    private void initView() {

        user = FuLiCenterApplication.getInstance().getUser();
        tvTitle.setText(getString(R.string.update_user_nick));
        etSetNick.setText(user.getMuserNick());
        etSetNick.selectAll();
        Log.i("main", "UpNickActivity.user:" + user);
    }


    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        initDialog();
        mode=  new DownUserMode();
        String newnick = etSetNick.getText().toString().trim();
        if(checkinput()){
            mode.updateNick(UpNickActivity.this, user.getMuserName(), newnick, new OnCompleteListener<String>() {
                         @Override
                         public void onSuccess(String s) {
                                if(s!=null){
                                    Result<User> result = ResultUtils.getResultFromJson(s, User.class);
                                    Log.i("main", "UpNickActivity.result:" + result.getRetCode());
                                    if(result!=null){
                                        if(result.getRetCode()==I.MSG_USER_UPDATE_NICK_FAIL){
                                            CommonUtils.showLongToast(getString(R.string.update_fail));
                                        }else{
                                            updateSuccess(result.getRetData());
                                            dismissDialog();
                                        }
                                    }
                                }
                          }

                           @Override
                          public void onError(String error) {
                            dismissDialog();
                          }
            });
        }else{
        dismissDialog();
        }
        finish();
    }

    private void updateSuccess(User retData) {
        CommonUtils.showLongToast(getString(R.string.update_user_nick_success));
        UserDao userDao = new UserDao(UpNickActivity.this);
        userDao.saveUser(retData);
        FuLiCenterApplication.getInstance().setUser(retData);
        setResult(RESULT_OK);
        finish();
    }

    private boolean checkinput() {
        String newnick = etSetNick.getText().toString().trim();
        if(newnick.equals(user.getMuserNick())){
            CommonUtils.showLongToast(getString(R.string.update_nick_fail_unmodify));
            return false;
        }
        return true;
    }
}
