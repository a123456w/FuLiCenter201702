package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.ui.fiagment.GoodsFragment;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class Categroy_Activity extends AppCompatActivity {
    @BindView(R.id.BoutiqueFrameLayout)
    FrameLayout BoutiqueFrameLayout;
    Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categroy_child);
        bind = ButterKnife.bind(this);
        int intExtra = getIntent().getIntExtra(I.CategoryChild.CAT_ID, I.CAT_ID);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.BoutiqueFrameLayout, new GoodsFragment(intExtra))
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bind!=null){
            bind.unbind();
        }
    }
}
