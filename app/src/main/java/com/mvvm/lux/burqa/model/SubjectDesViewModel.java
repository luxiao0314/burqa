package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.databinding.ActivitySubjectDesBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.SubjectDesResponse;
import com.mvvm.lux.burqa.ui.sub.adapter.SubjectDesAdapter;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.config.MarkAble;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/10 16:14
 * @Version
 */
public class SubjectDesViewModel extends BaseViewModel {

    public ObservableField<String> id = new ObservableField<>();
    private ObservableList<SubjectDesResponse.ComicsBean> mComics = new ObservableArrayList<>();
    private ActivitySubjectDesBinding mDataBinding;

    public SubjectDesViewModel(Activity activity, ActivitySubjectDesBinding dataBinding) {
        super(activity);
        mDataBinding = dataBinding;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        return layoutManager;
    }

    public SubjectDesAdapter getAdapter = new SubjectDesAdapter(mActivity, mComics);

    public void initData(ViewDataBinding headView) {
        RetrofitHelper.init()
                .getSubjectDes("subject/" + id.get() + ".json")
                .compose(RxHelper.handleErr())
                .subscribe(new ProgressSubscriber<SubjectDesResponse>(ServiceTask.create((MarkAble) mActivity)) {

                    @Override
                    public void onNext(SubjectDesResponse subjectDesResponse) {
                        refreshView(subjectDesResponse, headView);
                        List<SubjectDesResponse.ComicsBean> comics = subjectDesResponse.getComics();
                        getAdapter.setNewData(comics);
                        mDataBinding.recyclerView.setAdapter(getAdapter);   //?????????????????????不明白这里为什么一定要使用recycleView设置adapter
                    }
                });
    }

    private void refreshView(SubjectDesResponse subjectDesResponse, ViewDataBinding headView) {
        title.set(subjectDesResponse.getTitle());
        SubjectDesListViewModel viewModel = new SubjectDesListViewModel(mActivity);
        viewModel.title_img.set(subjectDesResponse.getMobile_header_pic());
        viewModel.title.set(subjectDesResponse.getTitle());
        viewModel.title_des.set(subjectDesResponse.getDescription());
        headView.setVariable(BR.viewModel, viewModel);
        getAdapter.addHeaderView(headView.getRoot());
    }

}
