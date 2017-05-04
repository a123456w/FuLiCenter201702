package cn.ucai.fulicenter.ui.fiagment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        initView();
        loadData();
        setListener();

    }

    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
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
        rvGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = gm.findLastVisibleItemPosition();
                if(lastVisibleItemPosition==Adapter.getItemCount()-1
                        &&newState==RecyclerView.SCROLL_STATE_IDLE
                        &&Adapter.isMroe()){
                    pageId++;
                    loadData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
                        Adapter.setMroe(result.length==pageSize&&result!=null);

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main",error.toString());
                    }
                });

    }

    private void updateUI(ArrayList<NewGoodsBean> list) {
        if(Adapter==null){
            Adapter=new NewGoodsAdapter(list,getContext());
            rvGoods.setAdapter(Adapter);
        }else {
            Adapter.addDate(list);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
