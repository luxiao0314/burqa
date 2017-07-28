package com.mvvm.lux.framework.manager.dialogs.config;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mvvm.lux.framework.R;
import com.mvvm.lux.framework.http.exception.RetrofitException;
import com.mvvm.lux.framework.http.exception.Throwable;


/**
 * 用于网络请求过程中的内部loading样式
 */
public class LoadingLayout extends FrameLayout implements ServiceCallback{
    private View loadingLayout;
    private View errorLayout, nodataLayout;
    private TextView errorTextView, noDataErrorTextView;
    private OnClickListener mRefreshClickListener;
    private Context context;
    //    private TextView loadingTextView;
    private int loadinglayoutId;
    private int errorLayoutId;
    private int nodataLayoutId;
    private static final String LoadingGifFilePath = "loading.gif";

    private ServiceCallback taskCallback;
    private ImageView noDataErrorImg;

    public LoadingLayout(Context context) {
        super(context);
        this.context = context;
        setUpPartProcessLayout();
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
            if (a != null) {
                loadinglayoutId = a.getResourceId(R.styleable.LoadingLayout_loading_layout, R.layout.loading_layout);
                errorLayoutId = a.getResourceId(R.styleable.LoadingLayout_error_layout_net_fail, R.layout.error_layout_net_fail);
                nodataLayoutId = a.getResourceId(R.styleable.LoadingLayout_error_layout_no_data_fail, R.layout.error_layout_no_data);
            }
        }
        setUpPartProcessLayout();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置外部taskcallback
     *
     * @param callback
     */
    public void setTaskCallback(ServiceCallback callback) {
        this.taskCallback = callback;
    }

    /**
     * 设置重试按钮的点击监听
     *
     * @param listener
     */
    public void setRefreshClickListener(OnClickListener listener) {
        this.mRefreshClickListener = listener;
    }

    /**
     * 功能描述:创建局部刷新布局&错误布局
     */
    protected void setUpPartProcessLayout() {
        LayoutInflater infalter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loadingLayout = infalter.inflate(loadinglayoutId, null);
        SimpleDraweeView img = (SimpleDraweeView) loadingLayout.findViewById(R.id.image);
        addView(loadingLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));

        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse("res://"+getContext().getPackageName()+"/"+R.drawable.gif_loading))
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();

        img.setController(draweeController);
    }

    /**
     * 显示加载中转圈
     */

    public void showProcess() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
            loadingLayout.bringToFront();
        }
        if (errorLayout != null) {
            errorLayout.setVisibility(View.GONE);
        }
        if (nodataLayout != null) {
            nodataLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏加载中的转圈显示
     */
    public void removeProcess() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载失败后的错误信息
     */
    public void showError() {
        if (errorLayout == null) {
            LayoutInflater infalter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            errorLayout = infalter.inflate(errorLayoutId, null);
            errorTextView = (TextView) errorLayout.findViewById(R.id.error_text);
            if (mRefreshClickListener != null) {
                View errorBtn = errorLayout.findViewById(R.id.error_btn);
                if (errorBtn != null) {
                    errorBtn.setOnClickListener(mRefreshClickListener);
                }
            }
            addView(errorLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
        }
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        errorLayout.setVisibility(View.VISIBLE);
        errorLayout.bringToFront();
    }

    /**
     * 显示无数据时的错误信息
     */
    public void showNoDataError() {
        if (nodataLayout == null) {
            LayoutInflater infalter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            nodataLayout = infalter.inflate(nodataLayoutId, null);
            noDataErrorTextView = (TextView) nodataLayout.findViewById(R.id.error_text);
            noDataErrorImg = (ImageView) nodataLayout.findViewById(R.id.error_img);
            if (mRefreshClickListener != null) {
                View errorBtn = nodataLayout.findViewById(R.id.error_btn);
                if (errorBtn != null) {
                    errorBtn.setOnClickListener(mRefreshClickListener);
                }
            }
            addView(nodataLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
        }
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        nodataLayout.setVisibility(View.VISIBLE);
        nodataLayout.bringToFront();
    }

    /**
     * 更新错误提示信息
     *
     * @param str
     */
    public void updateErrorText(String str) {
        if (errorTextView != null) {
            errorTextView.setText(str);
        }
    }

    /**
     * 更新无数据时的错误提示信息
     *
     * @param str
     */
    public void updateNoDataErrorText(String str) {
        if (noDataErrorTextView != null) {
            noDataErrorTextView.setText(str);
        }
    }
/**
     * 更新无数据时的错误提示图标
     *
     * @param imgId
     */
    public void updateNoDataErrorImg(int imgId) {
        if (noDataErrorImg != null) {
            noDataErrorImg.setImageResource(imgId);
        }
    }

    /**
     * 隐藏错误布局
     */
    public void hideErrorLayout() {
        if (errorLayout != null) {
            errorLayout.setVisibility(View.GONE);
        }
        if (nodataLayout != null) {
            nodataLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTaskStart(String serverTag) {
        showProcess();
        if (taskCallback != null) {
            taskCallback.onTaskStart(serverTag);
        }
    }

    @Override
    public void onTaskSuccess(String serverTag) {
        removeProcess();
        if (taskCallback != null) {
            taskCallback.onTaskSuccess(serverTag);
        }
    }

    @Override
    public void onTaskFail(Throwable error, String serverTag) {
        removeProcess();
        showError();
        switch (error.getCode()) {
            case RetrofitException.ERROR.PARSE_ERROR:
                if (TextUtils.isEmpty(error.getMessage())) {
                    updateErrorText("数据异常");
                } else {
                    updateErrorText(error.getMessage());
                }
                break;
            case RetrofitException.ERROR.HTTP_ERROR:
                updateErrorText("网络连接异常");
                break;
            case RetrofitException.ERROR.NETWORD_ERROR:
                updateErrorText("网络连接异常");
                break;
            case RetrofitException.ERROR.TIMEOUT_ERROR:
                updateErrorText("网络连接超时");
                break;
        }
        if (taskCallback != null) {
            taskCallback.onTaskFail(error, serverTag);
        }
    }
}
