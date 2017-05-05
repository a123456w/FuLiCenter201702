package cn.ucai.fulicenter.ui.fiagment;


import android.app.ProgressDialog;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.data.utils.ResultUtils;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;


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
    @BindView(R.id.tvNoMore)
    TextView tvNoMore;

    NewGoodsAdapter Adapter;
    GridLayoutManager gm;
    DownNewGoodMode mode;
    ProgressDialog dialog;


    int catId=I.CAT_ID;
    int pageId=1;
    int pageSize=I.PAGE_SIZE_DEFAULT;

    public GoodsFragment() {
    }
    @OnClick(R.id.tvNoMore)
    public void onClick(View v){
        dialog.show();
        loadData();
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
        initView();
        initDialog();
        loadData();
        setListener();
    }

    private void initDialog() {
        dialog=new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.load_more));
        dialog.show();
    }

    private void initView() {
        mode=new DownNewGoodMode();
        gm=new GridLayoutManager(getContext(),I.COLUM_NUM);

        rvGoods.setLayoutManager(gm);
        rvGoods.setAdapter(Adapter);
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
                setVisibility(true);
                loadData();
            }
        });
        rvGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = gm.findLastVisibleItemPosition();
                if(Adapter!=null&&lastVisibleItemPosition==Adapter.getItemCount()-1
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
    void setVisibility(boolean Visibility){
        srl.setRefreshing(Visibility);
        tvDownHint.setVisibility(Visibility?View.VISIBLE:View.GONE);
    };
    void setlistVisibility(boolean visibility){
        tvNoMore.setVisibility(visibility?View.GONE:View.VISIBLE);
        srl.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

    private void loadData() {
        mode.DownNewGoodData(getContext(), catId, pageId, pageSize,
                new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        setVisibility(false);
                        setlistVisibility(true);
                        if(result!=null){
                            ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                            updateUI(list);
                            dialog.dismiss();
                            Log.i("main",result.length+"");
                        }else {
                            if(Adapter==null){
                                setlistVisibility(false);
                            }

                        }
                        if(Adapter!=null){
                            setlistVisibility(true);
                            Adapter.setMroe(result.length==pageSize&&result!=null);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        dialog.dismiss();
                       setlistVisibility(false);
                        Log.e("main",error.toString());
                    }
                });

    }

    private void updateUI(ArrayList<NewGoodsBean> list) {
        if(Adapter==null){
            Adapter=new NewGoodsAdapter(list,getContext(),gm);
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
