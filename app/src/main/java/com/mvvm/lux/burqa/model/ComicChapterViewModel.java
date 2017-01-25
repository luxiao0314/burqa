package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.GridLayoutManager;

import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.adapter.ChaptersAdapter;
import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/25 17:03
 * @Version
 */
public class ComicChapterViewModel extends BaseViewModel {

    public ObservableField<String> chapters_title = new ObservableField<>();

    public ObservableField<String> last_updatetime = new ObservableField<>();

    public ObservableField<Integer> objId = new ObservableField<>();

    public ObservableField<String> cover = new ObservableField<>();

    public ObservableList<ComicResponse.ChaptersBean.DataBean> data = new ObservableArrayList<>();

    private ChaptersAdapter mAdapter;

    public GridLayoutManager layoutManager() {
        return new GridLayoutManager(mActivity, 4);
    }

    public ComicChapterViewModel(Activity activity) {
        super(activity);
    }

    public ChaptersAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ChaptersAdapter(mActivity, data, objId.get(), cover.get(),title.get());
        }
        return mAdapter;
    }
}
