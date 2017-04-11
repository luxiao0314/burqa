package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.AuthorDesResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.config.MarkAble;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;

import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/2/28 16:36
 * @Version
 */
public class AuthorDesViewModel extends BaseViewModel {

    public ObservableField<String> obj_id = new ObservableField<>();

    public AuthorDesViewModel(Activity activity) {
        super(activity);
        downloadImg.set(false);
        title.set("作者介绍页面");
    }

    public void initData() {
        RetrofitHelper.init()
                ///UCenter/comics/100085704.json
                .getAuthorDes("UCenter/comics/" + obj_id.get() + ".json")
                .compose(RxHelper.handleErr())
                .subscribe(new ProgressSubscriber<AuthorDesResponse>(ServiceTask.create((MarkAble) mActivity)) {
                    @Override
                    public void onNext(AuthorDesResponse response) {
                        AuthorDesListViewModel viewModel = new AuthorDesListViewModel(mActivity);
                        viewModel.avatar.set(response.getCover());
                        viewModel.nickname.set(response.getNickname());
                        viewModel.description.set(response.getDescription());
                        itemViewModel.add(viewModel);
                        itemViewModel.add(new AuthorDesListViewModel(mActivity, response.getData()));
                    }
                });
    }

    // viewModel for RecyclerView
    public final ObservableList<AuthorDesListViewModel> itemViewModel = new ObservableArrayList<>();
    // view layout for RecyclerView
    public final ItemViewSelector<AuthorDesListViewModel> itemView = new BaseItemViewSelector<AuthorDesListViewModel>() {

        @Override
        public void select(ItemView itemView, int position, AuthorDesListViewModel item) {
            itemView.set(BR.viewModel, position == 0 ? R.layout.header_author_des : R.layout.item_author_des);
        }

        @Override
        public int viewTypeCount() {
            return 2;
        }

    };
}
