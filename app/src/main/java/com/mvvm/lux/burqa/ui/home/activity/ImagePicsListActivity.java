package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.event.ProgressEvent;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.ui.sub.ImagePicDialogFragment;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.manager.router.Router;
import com.mvvm.lux.framework.rx.RxBus;
import com.mvvm.lux.framework.utils.DateUtil;
import com.mvvm.lux.framework.utils.NetworkUtil;
import com.mvvm.lux.widget.utils.DisplayUtil;

import java.util.ArrayList;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;
import progress.CircleProgress;
import progress.enums.CircleStyle;
import rx.functions.Action1;

import static com.mvvm.lux.burqa.R.id.advert_tv;

/**
 * 相册方式浏览图片
 */
public class ImagePicsListActivity extends BaseActivity {
    static final String TAG = ImagePicsListActivity.class.getSimpleName();
    private ArrayList<String> mUrls = new ArrayList<>();
    private TextView mAdvertv;
    private int urlistsize;
    private ViewPager mViewPager;
    private TextView mChapterTitle;
    private TextView mNetworkLogo;
    private TextView mTime;
    private int mPosition;

    public static void launch(Activity activity, String obj_id, int chapter_id, String chapter_title, int position) {
        Router.from(activity)
                .putString("obj_id", obj_id)
                .putString("chapter_id", chapter_id + "")
                .putInt("position", position)
                .putString("chapter_title", chapter_title + "")
                .to(ImagePicsListActivity.class)
                .launch();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_pics_list_layout);

        mChapterTitle = (TextView) findViewById(R.id.chapter_title);
        mNetworkLogo = (TextView) findViewById(R.id.network_logo);
        mTime = (TextView) findViewById(R.id.time);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdvertv = (TextView) findViewById(advert_tv);

        initEvent();

        // 没有任何url时，直接return跳走，UI交互上是用户根本进不来
        Intent intent = getIntent();
        String obj_id = intent.getStringExtra("obj_id");
        String chapter_id = intent.getStringExtra("chapter_id");
        String chapter_title = intent.getStringExtra("chapter_title");
        int position = intent.getIntExtra("position", 0);
        initData(obj_id, chapter_id, position, chapter_title);
    }

    private void initEvent() {
        RxBus.init().toObservable(ProgressEvent.class)
                .subscribe(new Action1<ProgressEvent>() {

                    @Override
                    public void call(ProgressEvent progressEvent) {
                        refreshCurrentPosition(progressEvent.mProgress);
                    }
                });
    }

    private void initData(String obj_id, String chapter_id, int position, String chapter_title) {
        mChapterTitle.setText(chapter_title);
        mTime.setText(DateUtil.getCurrentTime(DateUtil.DATETIME_PATTERN_6_2));
        mNetworkLogo.setText(NetworkUtil.getAPNType(this));

        RetrofitHelper.init()
                .getChapter("chapter/" + obj_id + "/" + chapter_id + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<ComicPageResponse>(ServiceTask.create(this)) {
                    @Override
                    public void onNext(ComicPageResponse comicPageResponse) {
                        mUrls.addAll(comicPageResponse.getPage_url());
                        if (!checkIllegal()) {
                            finish();
                            return;
                        }
                        urlistsize = mUrls.size();
                        mViewPager.setAdapter(new MyPagerAdapter(ImagePicsListActivity.this, mUrls));
                        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
                        mViewPager.setOffscreenPageLimit(3);
                        refreshCurrentPosition(position);
                    }
                });
    }

    private boolean checkIllegal() {
        return mUrls != null && mUrls.size() > 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mPosition = position;
            refreshCurrentPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    class MyPagerAdapter extends PagerAdapter {
        private Context mContext = null;
        private ArrayList<String> mUrls;

        public MyPagerAdapter(Context context, ArrayList<String> urls) {
            this.mContext = context;
            this.mUrls = urls;
        }

        public int getCount() {
            return mUrls == null ? 0 : mUrls.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String imageURL = mUrls.get(position);
            View contentview = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, container, false);
            final PhotoDraweeView photoView = (PhotoDraweeView) contentview.findViewById(R.id.photoview);
            photoView.setOnPhotoTapListener(onPhotoTapListener);

            new CircleProgress  //加载圆形进度条
                    .Builder()
                    .setStyle(CircleStyle.FAN)
                    .setProgressColor(getResources().getColor(R.color.orange_trans))
                    .setCustomText((position + 1) + "")
                    .setCircleRadius(DisplayUtil.dp2px(15))
                    .build()
                    .injectFresco(photoView);

            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(imageURL);
            controller.setOldController(photoView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
                    photoView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoView.setController(controller.build());
            container.addView(contentview);
            return contentview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    private OnPhotoTapListener onPhotoTapListener = new OnPhotoTapListener() {
        @Override
        public void onPhotoTap(View view, float x, float y) {
            if (view instanceof PhotoDraweeView) {
                PhotoDraweeView photoView = (PhotoDraweeView) view;
                if (photoView.getScale() > photoView.getMinimumScale()) {
                    photoView.setScale(photoView.getMinimumScale(), true);
                } else {
                    ImagePicDialogFragment.show(ImagePicsListActivity.this, urlistsize, mPosition);
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

    void refreshCurrentPosition(int position) {
        mViewPager.setCurrentItem(position, false);
        mAdvertv.setText("" + (position + 1) + "/" + urlistsize);
    }
}
