package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.IDownNewGood;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.ui.fiagment.BoutiqueFragment;
import cn.ucai.fulicenter.ui.fiagment.GoodsFragment;

public class MainActivity extends AppCompatActivity {
    Fragment[] mFragments;
    GoodsFragment mGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    int cIndex,index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        showFragment();
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.FrameLayout,mFragments[0])
                .add(R.id.FrameLayout,mFragments[1])
                .show(mGoodsFragment)
                .hide(mBoutiqueFragment)
                .commit();
    }

    private void initFragment() {
        mFragments=new Fragment[5];
        mGoodsFragment=new GoodsFragment();
        mBoutiqueFragment=new BoutiqueFragment();
        mFragments[0]=mGoodsFragment;
        mFragments[1]=mBoutiqueFragment;
    }

    public void onCheckedChange(View v){
        switch (v.getId()){
            case R.id.tv_New_Good:
                index=0;
                break;
            case R.id.tv_Boutique:
                index=1;
                break;
        }
        setFragment();
    }

    private void setFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!mFragments[cIndex].isAdded()) {
            fragmentTransaction.add(R.id.FrameLayout,mFragments[cIndex]);
        }
        if (index != cIndex) {
            fragmentTransaction.hide(mFragments[cIndex]);
            fragmentTransaction.show(mFragments[index]).commit();
        }
        cIndex=index;
    }

}

