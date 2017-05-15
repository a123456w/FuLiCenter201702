package cn.ucai.fulicenter.ui.fiagment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.data.bean.CartBean;
import cn.ucai.fulicenter.data.bean.MessageBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.DownUserMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class CartFragment extends Fragment {
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvSave)
    TextView tvSave;
    @BindView(R.id.rlClearing)
    RelativeLayout rlClearing;
    Unbinder unbinder;
    DownUserMode mode;
    User user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_cart, null);
        mode=new DownUserMode();
        unbinder = ButterKnife.bind(this, view);
        user= FuLiCenterApplication.getInstance().getUser();
        initDate();
        return view;
    }

    private void initDate() {
        mode.loadCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                if(result!=null){
                    ArrayList<CartBean> cartBeen = ResultUtils.array2List(result);

                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
