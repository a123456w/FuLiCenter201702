package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;

public class BoutiqueActivity extends AppCompatActivity {

    @BindView(R.id.ivTitle)
    ImageView ivTitle;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.BoutiqueFrameLayout)
    FrameLayout BoutiqueFrameLayout;
    Unbinder bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        bind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bind!=null){
            bind.unbind();
        }
    }
}
