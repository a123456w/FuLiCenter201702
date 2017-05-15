package cn.ucai.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cn.ucai.fulicenter.data.bean.NewGoodsBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.DownUserMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.CollectAdapter;
import cn.ucai.fulicenter.data.net.adapter.NewGoodsAdapter;
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
    @BindView(R.id.CollectLinearLayout)
    LinearLayout CollectLinearLayout;
    ArrayList<CollectBean> mCollectList;


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
                setlistVisibility(true);
                pageId = 1;
                loadData();
            }
        });
        rvCollect.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = gm.findLastVisibleItemPosition();
                if (mAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.getItemCount() - 1 == lastPosition && mAdapter.isMore()) {
                    pageId++;
                    loadData();
                }
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

    private void initView() {
        tvTitle.setText(R.string.collect_title);
        gm = new GridLayoutManager(CollectActivity.this, 2);
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter == null || position == mAdapter.getItemCount() - 1) {
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
        mode.findCollects(CollectActivity.this, user.getMuserName(), String.valueOf(pageId), String.valueOf(10),
                new OnCompleteListener<CollectBean[]>() {
                    @Override
                    public void onSuccess(CollectBean[] result) {
                        setVisibility(false);
                        setlistVisibility(true);
                        if(result!=null){
                            ArrayList<CollectBean> list = ResultUtils.array2List(result);
                            updateUi(list);
                        }else {
                            if(mAdapter==null){
                                setlistVisibility(false);
                            }
                        }
                        if(mAdapter!=null){
                            setlistVisibility(true);
                            mAdapter.setMore(result.length>0&&result!=null);
                        }
                        mAdapter.setFootertext("没有更多数据");
                    }
                    @Override
                    public void onError(String error) {
                        setlistVisibility(false);
                    }
                });
    }




    private void updateUi(ArrayList<CollectBean> list) {

        if(mAdapter==null){
            mCollectList=new ArrayList<>();
            mCollectList.addAll(list);
            mAdapter=new CollectAdapter(this,mCollectList);
            rvCollect.setAdapter(mAdapter);
        }else {
            if(pageId==1){
                mCollectList.clear();
                mCollectList.addAll(list);
                mAdapter.setDate(list);
            }else {
                mCollectList.addAll(list);
                Log.i("main","updateUi="+mCollectList.size());
                mAdapter.notifyDataSetChanged();
            }

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
        Log.i("main", "onCollectLinearLayout+ mAdapter.setCancel(true);");
        mAdapter.setCancel(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==I.REQUEST_CODE_GO_DELETE && resultCode==RESULT_OK ){
            int goodId = data.getIntExtra(I.Goods.KEY_GOODS_ID, 0);
            boolean isCollect = data.getBooleanExtra(I.Goods.KEY_IS_COLLECT, true);
            if(!isCollect){
                mCollectList.remove(new CollectBean(goodId));
                Log.i("main", "CollectActivity.mCollectList:" + mCollectList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
