package cn.ucai.fulicenter.ui.fiagment;


import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.data.bean.BoutiqueBean;
import cn.ucai.fulicenter.data.bean.CategoryChildBean;
import cn.ucai.fulicenter.data.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.net.DownNewGoodMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.net.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.data.net.adapter.GategoryAdapter;
import cn.ucai.fulicenter.data.utils.L;
import cn.ucai.fulicenter.data.utils.ResultUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    DownNewGoodMode mode;
    ProgressDialog dialog;
    GategoryAdapter adapter;
    Unbinder   unbinder;
    List<CategoryGroupBean> groupList=new ArrayList<>();
    List<List<CategoryChildBean>> childList=new ArrayList<>();
    @BindView(R.id.elv)
    ExpandableListView elv;
    @BindView(R.id.tvNoMore)
    TextView tvNoMore;

    @OnClick(R.id.tvNoMore)
    public void onClick(View v) {
        dialog.show();
    }

    public CategoryFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mode = new DownNewGoodMode();
        initDialog();
        downGroupload();
    }

    private void downGroupload() {
        mode.DownCategoryGorup(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                Log.i("main",""+result.length);
                setlistVisibility(true);
                if (result != null) {
                    groupList = ResultUtils.array2List(result);

                    for (int i=0;i<groupList.size();i++) {
                        childList.add(new ArrayList<CategoryChildBean>());
                        downChildload(groupList.get(i).getId(),i);
                    }
                }
            }
            @Override
            public void onError(String error) {
                dialog.dismiss();
                setlistVisibility(false);
                Log.e("main", error.toString());
            }
        });
    }
    int pageId=0;
    private void downChildload(int id, final int index) {
        mode.DownCategoryChild(getContext(),id ,new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                pageId++;
                setlistVisibility(true);
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ResultUtils.array2List(result);
                    childList.set(index,list);
                }
                if(pageId==groupList.size()){
                    dialog.dismiss();
                    updateUI();
                }

            }
            @Override
            public void onError(String error) {
                dialog.dismiss();
                setlistVisibility(false);
                Log.e("main", error.toString());
            }
        });
    }

    private void updateUI() {
        if(adapter==null){
            adapter = new GategoryAdapter(groupList,childList,getContext());
            elv.setAdapter(adapter);
        }

    }


    private void initDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.load_more));
        dialog.show();
    }
    void setlistVisibility(boolean visibility) {
        tvNoMore.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
