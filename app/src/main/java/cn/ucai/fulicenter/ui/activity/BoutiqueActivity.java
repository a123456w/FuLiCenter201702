package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.ui.fiagment.GoodsFragment;

public class BoutiqueActivity extends AppCompatActivity {

    @BindView(R.id.ivTitle)
    ImageView ivTitle;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.BoutiqueFrameLayout)
    FrameLayout BoutiqueFrameLayout;
    Unbinder bind;
    @BindView(R.id.RelativeLayout)
    android.widget.RelativeLayout RelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        bind = ButterKnife.bind(this);
        int catid = getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, I.CAT_ID);
        String title = getIntent().getStringExtra(I.Boutique.TITLE);
        Log.i("main", "tvTitle.title=" + title);
        Log.i("main", "catid.catid=" + catid);
        tvTitle.setText(title);
        getSupportFragmentManager().beginTransaction().add(R.id.BoutiqueFrameLayout, new GoodsFragment(catid)).commit();
        RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
