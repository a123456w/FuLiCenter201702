package cn.ucai.fulicenter.ui.fiagment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.data.utils.ResultUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends Fragment {


    @BindView(R.id.tvDownHint)
    TextView tvDownHint;
    @BindView(R.id.rvGoods)
    RecyclerView rvGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    Unbinder unbinder;

    NewGoodsAdapter Adapter;
    GridLayoutManager gm;
    DownNewGoodMode mode;

    int catId=I.CAT_ID;
    int pageId=1;
    int pageSize=I.PAGE_SIZE_DEFAULT;

    public GoodsFragment() {
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
        gm=new GridLayoutManager(getContext(), I.COLUM_NUM);
        rvGoods.setLayoutManager(gm);
        rvGoods.setAdapter(Adapter);
        loadData();
        setListener();
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId=1;
                tvDownHint.setVisibility(View.VISIBLE);
                srl.setRefreshing(true);
                loadData();
            }
        });
    }

    private void loadData() {
        mode.DownNewGoodData(getContext(), catId, pageId, pageSize,
                new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        srl.setRefreshing(false);
                        tvDownHint.setVisibility(View.GONE);
                        if(result!=null){
                            ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                            updateUI(list);
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

    }

    private void updateUI(ArrayList<NewGoodsBean> list) {
        if(Adapter==null){
            Adapter=new NewGoodsAdapter(list,getContext());
            rvGoods.setAdapter(Adapter);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
