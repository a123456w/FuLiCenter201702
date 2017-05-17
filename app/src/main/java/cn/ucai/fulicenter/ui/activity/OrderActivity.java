package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.utils.CommonUtils;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.ivTitle)
    ImageView ivTitle;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.RelativeLayout)
    android.widget.RelativeLayout RelativeLayout;
    @BindView(R.id.layout_order_title)
    android.widget.RelativeLayout layoutOrderTitle;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.ed_order_name)
    EditText edOrderName;
    @BindView(R.id.layout_order_name)
    android.widget.RelativeLayout layoutOrderName;
    @BindView(R.id.tv_order_phone)
    TextView tvOrderPhone;
    @BindView(R.id.ed_order_phone)
    EditText edOrderPhone;
    @BindView(R.id.layout_order_phone)
    android.widget.RelativeLayout layoutOrderPhone;
    @BindView(R.id.tv_order_province)
    TextView tvOrderProvince;
    @BindView(R.id.spin_order_province)
    Spinner spinOrderProvince;
    @BindView(R.id.layout_order_province)
    android.widget.RelativeLayout layoutOrderProvince;
    @BindView(R.id.tv_order_street)
    TextView tvOrderStreet;
    @BindView(R.id.ed_order_street)
    EditText edOrderStreet;
    @BindView(R.id.layout_order_street)
    android.widget.RelativeLayout layoutOrderStreet;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.layout_order)
    android.widget.RelativeLayout layoutOrder;
    int Extra=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
         Extra= getIntent().getIntExtra(I.Cart.PAY_PRICE, 0);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivTitle, R.id.tv_order_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTitle:
                finish();
                break;
            case R.id.tv_order_buy:
                if(input()){

                }
                break;
        }
    }

    private boolean input() {
        if(TextUtils.isEmpty(edOrderName.getText().toString())){
            CommonUtils.showLongToast(R.string.no_name);
            return false;
        }
        if(TextUtils.isEmpty(edOrderPhone.getText().toString())){
            CommonUtils.showLongToast(R.string.nophone);
            return false;
        }
        if(TextUtils.isEmpty(edOrderStreet.getText().toString())){
            CommonUtils.showLongToast(R.string.street);
            return false;
        }
        if(edOrderPhone.getText().toString().matches("[1][0-9]{10}")){
            CommonUtils.showLongToast(R.string.phone);
            return false;
        }
        return true;
    }
}
