package com.mvvm.lux.framework.manager.hybrid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.mvvm.lux.framework.R;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.widget.utils.DisplayUtil;

import java.util.ArrayList;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;
import progress.CircleProgress;
import progress.enums.CircleStyle;

/**
 * 相册方式浏览图片
 */
public class ImagePicsListActivity extends BaseActivity {
    static final String TAG = ImagePicsListActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private ArrayList<String> mUrls;
    private TextView madvertv;
    private int mPosition, urlistsize;
    public static final String KEY_INTENT_DATA_URL = "keyImgurls";
    public static final String KEY_INTENT_DATA_POS = "keyImgposi";

    /**
     * 显示图片列表
     *
     * @param context
     * @param urls
     * @param position
     */
    public static void entryGallery(Context context, ArrayList<String> urls, int position) {
        Intent intent = new Intent(context, ImagePicsListActivity.class);
        intent.putStringArrayListExtra(KEY_INTENT_DATA_URL, urls);
        intent.putExtra(KEY_INTENT_DATA_POS, position);
        context.startActivity(intent);
    }

    /**
     * 显示当前图片
     *
     * @param context
     * @param url
     * @param position
     */
    public static void entryGallery(Context context, String url, int position) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url);
        Intent intent = new Intent(context, ImagePicsListActivity.class);
        intent.putStringArrayListExtra(KEY_INTENT_DATA_URL, urls);
        intent.putExtra(KEY_INTENT_DATA_POS, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_pics_list_layout);
        // 没有任何url时，直接return跳走，UI交互上是用户根本进不来
        Intent intent = getIntent();
        mUrls = intent.getStringArrayListExtra(KEY_INTENT_DATA_URL);
        mPosition = intent.getIntExtra(KEY_INTENT_DATA_POS, 0);
        if (!checkIllegal()) {
            finish();
            return;
        }
        urlistsize = mUrls.size();
        initViews();
    }

    private void initViews() {
        madvertv = (TextView) findViewById(R.id.advert_tv);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPagerAdapter(this, mUrls));
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setCurrentItem(mPosition);
        mViewPager.setOffscreenPageLimit(3);
        refreshCurrentPosition(mPosition);
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
            refreshCurrentPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class MyPagerAdapter extends PagerAdapter {
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
            String url = mUrls.get(position);
            View contentview = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, container, false);
            PhotoDraweeView photoView = (PhotoDraweeView) contentview.findViewById(R.id.photoview);
            photoView.setOnViewTapListener(mOnPhotoTapListener);

            new CircleProgress  //加载圆形进度条
                    .Builder()
                    .setStyle(CircleStyle.FAN)
                    .setProgressColor(mContext.getResources().getColor(R.color.white_trans))
                    .setCustomText((position + 1) + "")
                    .setTextSize(DisplayUtil.dp2px(mContext, 14))
                    .setCircleRadius(DisplayUtil.dp2px(mContext, 20))
                    .build()
                    .injectFresco(photoView);

            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse(url))
                    .setResizeOptions(new ResizeOptions(DisplayUtil.getScreenWidth(mContext), DisplayUtil.getScreenHeight(mContext)))
                    .build();

            photoView.setController(Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request).build());
            photoView.setPhotoUri(Uri.parse(url));
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
                    finish();
                }
            }
        }
    };

    private OnViewTapListener mOnPhotoTapListener = new OnViewTapListener() {
        @Override
        public void onViewTap(View view, float x, float y) {
            if (view instanceof PhotoDraweeView) {
                PhotoDraweeView photoView = (PhotoDraweeView) view;
                if (photoView.getScale() > photoView.getMinimumScale()) {
                    photoView.setScale(photoView.getMinimumScale(), true);
                } else {
                    finish();
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

    void refreshCurrentPosition(int mPosition) {
        madvertv.setText("" + (mPosition + 1) + "/" + urlistsize);
    }
}
