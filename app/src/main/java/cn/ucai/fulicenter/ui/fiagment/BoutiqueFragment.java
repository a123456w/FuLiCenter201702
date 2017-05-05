package cn.ucai.fulicenter.ui.fiagment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.data.net.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.data.utils.ResultUtils;


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

    LinearLayoutManager llm;
    DownNewGoodMode mode;
    ProgressDialog dialog;
    BoutiqueAdapter adapter;

    @OnClick(R.id.tvNoMore)
    public void onClick(View v){
        dialog.show();
        download();
    }

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
        download();
        initView();
        initDialog();
        setListener();
    }



    private void initDialog() {
        dialog=new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.load_more));
        dialog.show();
    }

    private void initView() {
        mode=new DownNewGoodMode();
        llm=new LinearLayoutManager(getContext());
        rvGoods.setLayoutManager(llm);
        rvGoods.setAdapter(adapter);
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
                setVisibility(true);
                download();
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



    private void download() {
        mode=new DownNewGoodMode();
        mode.DownBoutinue(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                setVisibility(false);
                setlistVisibility(true);
                Log.i("main",result.length+"");
                if(result!=null){
                    ArrayList<BoutiqueBean> list = ResultUtils.array2List(result);
                    updateUI(list);
                    dialog.dismiss();
                }else {
                    if(adapter ==null){
                        setlistVisibility(false);
                    }
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
    private void updateUI(ArrayList<BoutiqueBean> list) {
        Log.i("main","list="+list.toString());
        if(adapter ==null){
            adapter =new BoutiqueAdapter(getContext(),list);
            rvGoods.setAdapter(adapter);
        }else {

            setlistVisibility(true);
            adapter.initData(list);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
