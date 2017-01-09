package com.mvvm.lux.burqa.ui.home.adapter.section;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.model.ComicHeaderViewModel;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.framework.manager.recycler.sectioned.StatelessSection;
import com.mvvm.lux.framework.utils.CommonUtils;

import java.util.List;

/**
 * @Description 漫画详情页面,上半部分内容
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/8 14:42
 * @Version
 */
public class ComicHeaderSection extends StatelessSection {

    private final ComicDesActivity mActivity;
    private final ComicResponse mComicResponse;

    public ComicHeaderSection(ComicDesActivity activity, ComicResponse comicResponse) {
        super(R.layout.section_comic_des);
        mActivity = activity;
        mComicResponse = comicResponse;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(ViewDataBinding view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicHeaderViewModel viewModel = new ComicHeaderViewModel(mActivity);
        viewModel.cover.set(mComicResponse.getCover());
        viewModel.title.set(mComicResponse.getTitle());
        viewModel.description.set(mComicResponse.getDescription());

        int hot_num = mComicResponse.getHot_num();
        String hotNum = CommonUtils.conNum(hot_num, 2);
        viewModel.hot_num.set("战斗力: " + hotNum + "");  //人气   1111.2万

        int subscribe_num = mComicResponse.getSubscribe_num();
        String subscribeNum = CommonUtils.conNum(subscribe_num, 2);
        viewModel.subscribe_num.set("订阅: " + subscribeNum + "");  //订阅

        viewModel.islong.set(mComicResponse.getIslong() + "");  //是否为长文章
        List<ComicResponse.TypesBean> types = mComicResponse.getTypes();
        StringBuilder buffer = new StringBuilder();
        for (ComicResponse.TypesBean type : types) {
            String tag_name = type.getTag_name();
            buffer.append(tag_name).append("_");
        }
        String com_type = buffer.toString().substring(0, buffer.toString().length() - 1);
        viewModel.authors.set(mComicResponse.getAuthors().get(0).getTag_name() + "/" + com_type); //作者/热血_言情

        ((ItemViewHolder) holder).mDataBinding.setVariable(BR.viewModel, viewModel);
        ((ItemViewHolder) holder).mDataBinding.executePendingBindings();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding mDataBinding;

        ItemViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }
}
