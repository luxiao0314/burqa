package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.response.AuthorDesResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxSubscriber;

import java.util.List;

import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import rx.Observable;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/2/28 16:42
 * @Version
 */
public class AuthorDesListViewModel extends BaseViewModel {

    public ObservableField<String> nickname = new ObservableField<>();
    public ObservableField<String> description = new ObservableField<>();
    public ObservableField<String> avatar = new ObservableField<>();


    public AuthorDesListViewModel(Activity activity, List<AuthorDesResponse.DataBean> response) {
        super(activity);
        initData(response);
    }

    public AuthorDesListViewModel(Activity activity) {
        super(activity);
    }

    private void initData(List<AuthorDesResponse.DataBean> response) {
        Observable.from(response)
                .subscribe(new RxSubscriber<AuthorDesResponse.DataBean>() {
                    @Override
                    public void onNext(AuthorDesResponse.DataBean dataBean) {
                        ClassifyItemViewModel viewModel = new ClassifyItemViewModel(mActivity);
                        viewModel.cover.set(dataBean.getCover());
                        viewModel.title.set(dataBean.getName());
                        viewModel.id.set(dataBean.getId()+"");
                        itemViewModel.add(viewModel);
                    }
                });
    }

    // viewModel for RecyclerView
    public final ObservableList<ClassifyItemViewModel> itemViewModel = new ObservableArrayList<>();
    // view layout for RecyclerView
    public final ItemViewSelector<ClassifyItemViewModel> itemView = new BaseItemViewSelector<ClassifyItemViewModel>() {

        @Override
        public void select(ItemView itemView, int position, ClassifyItemViewModel item) {
            itemView.set(BR.viewModel, R.layout.adapter_classify_layout);
        }

        @Override
        public int viewTypeCount() {
            return 1;
        }

    };

}
