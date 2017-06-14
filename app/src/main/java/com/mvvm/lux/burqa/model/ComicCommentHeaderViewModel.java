package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.mvvm.lux.burqa.ui.react_native.CommentReactActivity;
import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/10 10:21
 * @Version
 */
public class ComicCommentHeaderViewModel extends BaseViewModel {

    public ObservableField<String> comment_counts = new ObservableField<>();

    public ObservableField<String> id = new ObservableField<>();

    public ComicCommentHeaderViewModel(Activity activity) {
        super(activity);
    }

    /**
     * 点击跳转到rn的评论列表
     */
    public View.OnClickListener commnetClick = view -> {
//        new RnEvent().sendEvent(id.get());
        CommentReactActivity.launch(mActivity,"lux://ReactActivity?obj_id=" + id.get());
    };
}
