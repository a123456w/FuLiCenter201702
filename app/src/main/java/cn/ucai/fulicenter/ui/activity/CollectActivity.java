package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import cn.ucai.fulicenter.data.bean.CollectBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.DownUserMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.CollectAdapter;
import cn.ucai.fulicenter.data.utils.ResultUtils;

/**
 * Created by Administrator on 2017/5/13 0013.
 */
public class CollectActivity extends AppCompatActivity {
    @BindView(R.id.rvCollect)
    RecyclerView rvCollect;
    @BindView(R.id.tvDownHint)
    TextView tvDownHint;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tvNoMore)
    TextView tvNoMore;
    Unbinder bind;
    CollectAdapter mAdapter;
    GridLayoutManager gm;
    //List<NewGoodsBean> mList;
    DownUserMode mode;
    User user;
    int pageId = 1;
    int pageSize = 10;
    @BindView(R.id.ivTitle)
    ImageView ivTitle;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.RelativeLayout)
    android.widget.RelativeLayout RelativeLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        bind = ButterKnife.bind(this);
        initView();
        loadData();
        setListener();

    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setVisibilityboolean(true);
                pageId = 1;
                loadData();
            }
        });
        rvCollect.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = gm.findLastVisibleItemPosition();
                if (mAdapter != null && RecyclerView.SCROLL_STATE_IDLE == newState && mAdapter.getItemCount() - 1 == lastPosition && mAdapter.isMore()) {
                    pageId++;
                    loadData();
                }
            }
        });
    }

    private void setVisibilityboolean(boolean bl) {
        tvDownHint.setVisibility(bl ? View.VISIBLE : View.GONE);
        srl.setRefreshing(bl);
    }

    private void initView() {
        gm = new GridLayoutManager(CollectActivity.this, 2);
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(mAdapter==null||position==mAdapter.getItemCount()-1){
                    return I.COLUM_NUM;
                }
                return 1;
            }
        });
        rvCollect.setLayoutManager(gm);
    }

    private void loadData() {
        mode = new DownUserMode();
        user = FuLiCenterApplication.getInstance().getUser();
        mode.findCollects(CollectActivity.this, user.getMuserName(), String.valueOf(pageId), String.valueOf(pageSize),
                new OnCompleteListener<CollectBean[]>() {
                    @Override
                    public void onSuccess(CollectBean[] result) {
                        if (result != null) {
                            ArrayList<CollectBean> list = ResultUtils.array2List(result);
                            updateUi(list);
                        }
                        mAdapter.setFootertext("没有更多数据");
                        Log.i("main", "mAdapter.setFootertext(\"没有更多数据\");");

                    }

                    @Override
                    public void onError(String error) {
                        setVisibilityboolean(false);
                    }
                });
    }

    private void updateUi(ArrayList<CollectBean> list) {
        if (mAdapter == null) {
            mAdapter = new CollectAdapter(CollectActivity.this, list);
            rvCollect.setAdapter(mAdapter);
        } else {
            setVisibilityboolean(false);
            mAdapter.setFootertext("加载更多");
            mAdapter.setDate(list);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    @OnClick(R.id.CollectLinearLayout)
    public void onCollectLinearLayout() {
        Log.i("main","onCollectLinearLayout+ mAdapter.setCancel(true);");
        mAdapter.setCancel(true);
    }
}
