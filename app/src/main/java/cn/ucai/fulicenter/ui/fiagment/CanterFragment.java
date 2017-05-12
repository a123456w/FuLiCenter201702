package cn.ucai.fulicenter.ui.fiagment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.data.bean.MessageBean;
import cn.ucai.fulicenter.data.bean.User;
import cn.ucai.fulicenter.data.net.DownUserMode;
import cn.ucai.fulicenter.data.net.OnCompleteListener;
import cn.ucai.fulicenter.data.utils.ImageLoader;
import cn.ucai.fulicenter.ui.activity.SetUserActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class CanterFragment extends Fragment {

    @BindView(R.id.ivAvatares)
    ImageView ivAvatares;
    @BindView(R.id.tvNick)
    TextView tvNick;
    Unbinder unbinder;
    User user;
    @BindView(R.id.tvCount)
    TextView tvCount;
    DownUserMode mode;
    int collectCount=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canter, null);
        unbinder = ButterKnife.bind(this, view);
        mode=new DownUserMode();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        tvCount.setText(String.valueOf(collectCount));
        user = FuLiCenterApplication.getInstance().getUser();
        if (user != null) {
            tvNick.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), ivAvatares);
            initCollectCount();
        }


    }

    private void initCollectCount() {
        mode.loadCollectsCount(getContext(), user.getMuserName(), new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result!=null&&result.isSuccess()){
                    collectCount=Integer.parseInt(result.getMsg());
                }else{
                    collectCount=0;
                }
                tvCount.setText(String.valueOf(collectCount));
            }

            @Override
            public void onError(String error) {
                collectCount=0;
                tvCount.setText(String.valueOf(collectCount));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tvSetCenter, R.id.ivAvatares, R.id.tvNick})
    public void onViewClicked(View view) {
        startActivity(new Intent(getContext(), SetUserActivity.class));
    }

    @OnClick(R.id.rlCollectCount)
    public void onCollectCountClick(View v) {

    }
}
