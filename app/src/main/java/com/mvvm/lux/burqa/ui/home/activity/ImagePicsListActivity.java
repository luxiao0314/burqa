package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListBinding;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListLandBinding;
import com.mvvm.lux.burqa.model.ImagePicsViewModel;
import com.mvvm.lux.burqa.model.db.RealmHelper;
import com.mvvm.lux.framework.base.SwipeBackActivity;
import com.mvvm.lux.framework.manager.router.Router;
import com.mvvm.lux.framework.utils.DateUtil;
import com.mvvm.lux.framework.utils.NetworkUtil;
import com.mvvm.lux.widget.utils.DisplayUtil;


/**
 * @Description 相册方式浏览图片
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/21 15:38
 * @Version 1.0.0
 */
public class ImagePicsListActivity extends SwipeBackActivity {

    private ViewDataBinding mDataBinding;
    private ImagePicsViewModel mViewModel;
    private String mObjId;
    private String mChapterId;
    private int mTagPosition;
    private String mTitle;
    private String mCover;
    private String mChapters;
    private int mPagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.image_pic_fullscreen); //必须在oncreate之前执行
        super.onCreate(savedInstanceState);
        getIntentData();
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_pics_list);
        mViewModel = new ImagePicsViewModel(this, (ActivityImagePicsListBinding) mDataBinding);
        mViewModel.current_position.set(mPagePosition);
        init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (DisplayUtil.isPortrait()) {
            mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_pics_list);
            mViewModel.setDataBinding((ActivityImagePicsListBinding) mDataBinding);
        } else {
            mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_pics_list_land);
            mViewModel.setDataLandBinding((ActivityImagePicsListLandBinding) mDataBinding);
        }
        init();
    }

    private void init() {
        mViewModel.obj_id.set(mObjId);
        mViewModel.chapter_id.set(mChapterId);
        mViewModel.tag_position.set(mTagPosition);
        mViewModel.title.set(mTitle);
        mViewModel.cover.set(mCover);
        mViewModel.chapters.set(mChapters);
        mViewModel.time.set(DateUtil.getCurrentTime(DateUtil.DATETIME_PATTERN_6_2));
        mViewModel.network_status.set(NetworkUtil.getAPNType(this));
        mViewModel.initData();
        mDataBinding.setVariable(BR.viewModel, mViewModel);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mObjId = intent.getStringExtra("obj_id");
        mChapterId = intent.getStringExtra("chapter_id");
        mTagPosition = intent.getIntExtra("tag_position", 0);
        mTitle = intent.getStringExtra("title");
        mCover = intent.getStringExtra("cover");
        mChapters = intent.getStringExtra("chapters");
        mPagePosition = RealmHelper.getInstance()
                .queryPagePosition(Integer.parseInt(mObjId), mTagPosition);
    }

    public static void launch(Activity activity, int chapter_id, int tagPosition, String chapters, String obj_id, String title, String cover) {
        Router.from(activity)
                .putString("chapter_id", chapter_id + "")
                .putInt("tag_position", tagPosition)
                .putString("obj_id", obj_id)
                .putString("chapters", chapters)
                .putString("title", title + "")
                .putString("cover", cover)
                .to(ImagePicsListActivity.class)
                .launch();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Router.pop(this);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        if (mViewModel != null)
            mViewModel.detachView();
        super.onDestroy();
    }
}
