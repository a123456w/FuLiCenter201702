package cn.ucai.fulicenter.ui.fiagment;


import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.CartBean;
import cn.ucai.fulicenter.data.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.DownUserMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.CartAdapter;
import cn.ucai.fulicenter.data.utils.ResultUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    @BindView(R.id.tvDownHint)
    TextView tvDownHint;
    @BindView(R.id.rvGoods)
    RecyclerView rvGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    Unbinder unbinder;
    @BindView(R.id.tvNoMore)
    TextView tvNoMore;

    CartAdapter Adapter;
    GridLayoutManager gm;
    DownUserMode mode;
    ProgressDialog dialog;
    ArrayList<CartBean> list = new ArrayList<>();
    @BindView(R.id.btnClearing)
    Button btnClearing;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvSave)
    TextView tvSave;
    @BindView(R.id.rlClearing)
    RelativeLayout rlClearing;


  /*  int catId=I.CAT_ID;
    int pageSize=I.PAGE_SIZE_DEFAULT;*/

    public CartFragment() {
    }

    /* public CartFragment(int catId) {
         this.catId=catId;
     }*/
    @OnClick(R.id.tvNoMore)
    public void onClick(View v) {
        dialog.show();
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
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
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.load_more));
        dialog.show();
    }

    private void initView() {
        mode = new DownUserMode();
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
              /*  if(Adapter==null||position==Adapter.getItemCount()-1){
                    return I.COLUM_NUM;
                }*/
                return 2;
            }
        });
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
                setVisibility(true);
                loadData();
            }
        });
    }

    void setVisibility(boolean Visibility) {
        srl.setRefreshing(Visibility);
        tvDownHint.setVisibility(Visibility ? View.VISIBLE : View.GONE);
    }

    ;

    void setlistVisibility(boolean visibility, boolean isEinty) {
        tvNoMore.setText(isEinty ? R.string.no_more : R.string.no_cart);
        tvNoMore.setVisibility(visibility ? View.GONE : View.VISIBLE);
        srl.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rlClearing.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void loadData() {
        if (FuLiCenterApplication.getInstance().isLogined()) {
            User user = FuLiCenterApplication.getInstance().getUser();
            mode.loadCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    setVisibility(false);
                    setlistVisibility(true, true);
                    list.clear();
                    if (result != null) {
                        list.addAll(ResultUtils.array2List(result));
                        Log.i("main", "loadData.lsit=" + list);
                        Log.i("main", "loadData.result=" + result.length);
                        updateUI();
                        dismissDialog();
                        Log.i("main", result.length + "");
                        if (list.size() == 0) {
                            setlistVisibility(false, false);
                        }
                    } else {
                        dismissDialog();
                        setlistVisibility(false, true);
                    }
                }

                @Override
                public void onError(String error) {
                    list.clear();
                    setVisibility(false);
                    dismissDialog();
                    setlistVisibility(false, true);
                    Log.e("main", error.toString());
                }
            });
        }
    }
    public void sumPrice(){
        int sumPrice=0;
        int savePrice=0;
        if(list.size()>0){
            for (CartBean cartBean : list) {
                GoodsDetailsBean goods = cartBean.getGoods();
                if(goods!=null){
                    sumPrice += getPrice(goods.getCurrencyPrice())*cartBean.getCount();
                    savePrice+=(getPrice(goods.getCurrencyPrice())-getPrice(goods.getRankPrice()))
                            *cartBean.getCount();
                }
            }
        }
        tvTotal.setText(String.valueOf(sumPrice));
        tvSave.setText(String.valueOf(savePrice));
    }

    private int getPrice(String currencyPrice) {
        String price = currencyPrice.substring(currencyPrice.indexOf("￥"));
        return Integer.parseInt(price);
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void updateUI() {
        if (Adapter == null) {
            Adapter = new CartAdapter(getContext(), list);
            rvGoods.setAdapter(Adapter);
        } else {
            Adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    /*public void shorAdapter(int shortBy){
        if(Adapter!=null){
            Adapter.shortByList(shortBy);
        }
    }*/
}