package com.mvvm.lux.burqa.ui.home.adapter.section;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.base.StatelessSection;
import com.mvvm.lux.burqa.model.ComicCommentHeaderViewModel;
import com.mvvm.lux.burqa.model.ComicCommentListViewModel;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.framework.utils.DateUtil;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/10 10:06
 * @Version
 */
public class ComicCommentSection extends StatelessSection {

    private final ComicDesActivity mActivity;
    private ComicResponse comicResponse;
    private final ComicResponse.CommentBean mComment;

    public ComicCommentSection(ComicDesActivity activity, ComicResponse comicResponse) {
        super(R.layout.section_comic_header, R.layout.section_comic_footer, R.layout.section_comic_comment);
        this.comicResponse = comicResponse;
        mComment = comicResponse.getComment();
        mActivity = activity;
    }

    @Override
    public int getContentItemsTotal() {
        return mComment.getLatest_comment().size();
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(ViewDataBinding view) {
        return new HeaderViewholder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        ComicCommentHeaderViewModel viewModel = new ComicCommentHeaderViewModel(mActivity);
        viewModel.comment_counts.set("作品讨论(" + mComment.getComment_count() + ")");
        ((HeaderViewholder) holder).mDataBinding.setVariable(BR.viewModel, viewModel);
        ((HeaderViewholder) holder).mDataBinding.executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(ViewDataBinding view) {
        return new HeaderViewholder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        ComicCommentHeaderViewModel viewModel = new ComicCommentHeaderViewModel(mActivity);
        viewModel.id.set(comicResponse.getId()+"");
        viewModel.comment_counts.set("更多评论(" + mComment.getComment_count() + ")");
        ((HeaderViewholder) holder).mDataBinding.setVariable(BR.viewModel, viewModel);
        ((HeaderViewholder) holder).mDataBinding.executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(ViewDataBinding view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicCommentListViewModel viewModel = new ComicCommentListViewModel(mActivity);
        ComicResponse.CommentBean.LatestCommentBean commentBean = mComment.getLatest_comment().get(position);
        viewModel.avatar.set(commentBean.getAvatar());
        viewModel.content.set(commentBean.getContent());
        viewModel.nickname.set(commentBean.getNickname());
        viewModel.createtime.set(DateUtil.getStringTime(commentBean.getCreatetime()));
        ((ItemViewHolder) holder).mDataBinding.setVariable(BR.viewModel, viewModel);
        ((ItemViewHolder) holder).mDataBinding.executePendingBindings();
    }

    private class HeaderViewholder extends RecyclerView.ViewHolder {
        private ViewDataBinding mDataBinding;

        HeaderViewholder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mDataBinding;

        ItemViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }
}
