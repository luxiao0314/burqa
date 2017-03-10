package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    private List<SubjectDesResponse.ComicsBean> mComics;
    private SubjectDesResponse mSubjectDesResponse;
    private SubjectDesAdapter mSubjectDesAdapter;

    public SubjectDesViewModel(Activity activity) {
        super(activity);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position) {
                    case 0:
                        return 1;
                    default:
                        return 1;
                }
            }
        });
        return layoutManager;
    }

    public RecyclerView.Adapter getAdapter() {
        if (mSubjectDesAdapter == null) {
            mSubjectDesAdapter = new SubjectDesAdapter(mActivity, mComics, mSubjectDesResponse);
        }
        return mSubjectDesAdapter;
    }

    public void initData() {
        RetrofitHelper.init()
                .getSubjectDes("subject/" + id.get() + ".json")
                .compose(RxHelper.handleErr())
                .subscribe(new ProgressSubscriber<SubjectDesResponse>(ServiceTask.create((MarkAble) mActivity)) {

                    @Override
                    public void onNext(SubjectDesResponse subjectDesResponse) {
                        mSubjectDesResponse = subjectDesResponse;
                        mComics = subjectDesResponse.getComics();
                        mSubjectDesAdapter.notifyDataSetChanged();
                    }
                });
    }
}
