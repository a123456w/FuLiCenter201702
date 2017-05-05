package cn.ucai.fulicenter.ui.fiagment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;


public class BoutiqueFragment extends Fragment {


    @BindView(R.id.tvDownHint)
    TextView tvDownHint;
    @BindView(R.id.rvGoods)
    RecyclerView rvGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tvNoMore)
    TextView tvNoMore;
    Unbinder unbinder;
    DownNewGoodMode mode;

    public BoutiqueFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mode=new DownNewGoodMode();
        mode.DownBoutinue(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                Log.i("main",result.length+"");
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
