package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.manager.ReplyCommand;
import com.mvvm.lux.burqa.model.response.SubjectResopnse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:35
 * @Version
 */
public class GameViewModel extends BaseViewModel {

    private int page = 0;
    private List<SubjectResopnse> mSubjectResopnse = new ArrayList<>();
    public final ViewStyle viewStyle = new ViewStyle();

    public GameViewModel(Activity activity) {
        super(activity);
        initData(1);
    }

    public class ViewStyle {
        public final ObservableBoolean isRefreshing = new ObservableBoolean(true);
    }

    //刷新数据
    public final ReplyCommand onRefreshCommand = new ReplyCommand<>(() -> {
        initData(1);
    });

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> initData(1);

    //加载更多
    public final ReplyCommand<Integer> onLoadMoreCommand = new ReplyCommand<>(p -> initData(2));

    // viewModel for RecyclerView
    public final ObservableList<GameViewItemViewModel> itemViewModel = new ObservableArrayList<>();
    // view layout for RecyclerView
    public final ItemViewSelector<GameViewItemViewModel> itemView = new BaseItemViewSelector<GameViewItemViewModel>() {
        @Override
        public void select(ItemView itemView, int position, GameViewItemViewModel itemViewModel) {
            itemView.set(BR.viewModel, R.layout.item_game_view);
        }
    };


    /**
     * @param type 1: 刷新 , 2 : 加载更多
     */
    private void initData(int type) {
        RetrofitHelper.init()
                .getSubject("subject/0/" + page + ".json")
                .compose(RxHelper.handleErr())
                .subscribe(new RxSubscriber<List<SubjectResopnse>>() {

                    @Override
                    public void onNext(List<SubjectResopnse> subjectResopnses) {
                        viewStyle.isRefreshing.set(false);
                        if (type == 1) {
                            page = 0;
                            mSubjectResopnse = subjectResopnses;
                        } else {
                            page += 1;
                            mSubjectResopnse.addAll(subjectResopnses);
                        }
                        for (SubjectResopnse subjectResopnse : mSubjectResopnse) {
                            GameViewItemViewModel viewModel = new GameViewItemViewModel(mActivity);
                            viewModel.create_time.set(DateUtil.getStringTime(subjectResopnse.getCreate_time()));
                            viewModel.title.set(subjectResopnse.getTitle());
                            viewModel.img.set(subjectResopnse.getSmall_cover());
                            GameViewModel.this.itemViewModel.add(viewModel);
                        }
                    }
                });
    }

}
