package cn.ucai.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.ui.fiagment.BoutiqueFragment;
import cn.ucai.fulicenter.ui.fiagment.CanterFragment;
import cn.ucai.fulicenter.ui.fiagment.CategoryFragment;
import cn.ucai.fulicenter.ui.fiagment.GoodsFragment;

public class MainActivity extends AppCompatActivity {
    Fragment[] mFragments;
    GoodsFragment mGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    CanterFragment mCanterFragment;
    int cIndex, index;
    @BindView(R.id.tv_New_Good)
    RadioButton tvNewGood;
    @BindView(R.id.tv_Boutique)
    RadioButton tvBoutique;
    @BindView(R.id.tvCategory)
    RadioButton tvCategory;
    @BindView(R.id.tv_Cart)
    RadioButton tvCart;
    @BindView(R.id.tv_Canter)
    RadioButton tvCanter;

    RadioButton[] mRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initRadioButton();
        showFragment();
    }

    private void initRadioButton() {
        mRadioButton=new RadioButton[5];
        mRadioButton[0]=tvNewGood;
        mRadioButton[1]=tvBoutique;
        mRadioButton[2]=tvCategory;
        mRadioButton[3]=tvCart;
        mRadioButton[4]=tvCanter;
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.FrameLayout, mFragments[0])
                .add(R.id.FrameLayout, mFragments[1])
                .add(R.id.FrameLayout, mFragments[2])
                .show(mFragments[0])
                .hide(mFragments[1])
                .hide(mFragments[2])
                .commit();
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mGoodsFragment = new GoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mCanterFragment = new CanterFragment();
        mFragments[0] = mGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        mFragments[4] = mCanterFragment;
    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.tv_New_Good:
                index = 0;
                break;
            case R.id.tv_Boutique:
                index = 1;
                break;
            case R.id.tvCategory:
                index = 2;
                break;
            case R.id.tv_Canter:
                L.e("main","index="+index);
                if (FuLiCenterApplication.getUser() == null) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), I.REQUEST_CODE_LOGIN);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("main","onActivityResult"+index);
        if(resultCode==RESULT_OK&&requestCode==I.REQUEST_CODE_LOGIN){
            index=4;
        }
        setFragment();
    }

    private void setFragment() {
        if (index != cIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[cIndex]);
            if (!mFragments[index].isAdded()) {
                ft.add(R.id.FrameLayout, mFragments[index]);
            }
            ft.show(mFragments[index]);
            ft.commit();
            cIndex = index;
        }
        setRedioButton();

    }

    private void setRedioButton() {
        for (int i = 0; i < mRadioButton.length; i++) {
            mRadioButton[i].setChecked(i==index?true:false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(index==4&&FuLiCenterApplication.getInstance().getUser()==null){
            index=0;
        }
        setFragment();
    }
}

