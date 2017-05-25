package com.mvvm.lux.framework.manager.hybrid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.mvvm.lux.framework.R;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.utils.CommonUtils;
import com.mvvm.lux.framework.utils.StatusBarUtil;

/**
 * @Description 与h5交互的webView
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/2/27 14:34
 * @Version 1.0.0
 */
public class BrowserActivity extends BaseActivity implements IWebPageView, DownloadListener {

    private String mTitle;
    private String mUrl;
    private String mInjectedName;
    private ProgressBar mProgressBar;
    private FrameLayout videoFullView;
    private WebView webView;
    private InjectedChromeClient mWebChromeClient;
    public boolean mProgress90;
    public boolean mPageFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getIntentData();
        initTitle();
        webSettings();
        initWebView();
        webView.loadUrl(mUrl);
    }

    private void webSettings() {
        WebSettings ws = webView.getSettings();
        //自动打开窗口
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        //提高渲染的优先级
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 启动应用缓存
        ws.setAppCacheEnabled(false);
        //设置缓存路径,大小
        ws.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCacheDir = Environment.getExternalStorageDirectory().getPath() + "/WebView_pingAn_binding";
        ws.setAppCachePath(appCacheDir);
        //支持插件
        ws.setPluginState(WebSettings.PluginState.ON);
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放,但是不显示+号
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片--
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);
        //设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)
        ws.setTextZoom(100);
        //首先阻塞图片，让图片不显示,页面加载好以后，在放开图片,true不显示图片
        ws.setBlockNetworkImage(true);
        //设置WebView运行中的一个文件方案被允许访问其他文件方案中的内容，允许访问文件
        ws.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ws.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            ws.setLoadsImagesAutomatically(true);
        } else {
            ws.setLoadsImagesAutomatically(false);
        }
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    private void initWebView() {
        mProgressBar.setVisibility(View.VISIBLE);
        // 缩放比例 1
        webView.setInitialScale(1);
        //去掉垂直滚动条
        webView.setVerticalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        //移除系统开放的JS接口,使用onJsPrompt方法做js_android交互
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            dealJavascriptLeak();
        }
        // 与js交互
        mWebChromeClient = new InjectedChromeClient(this, mInjectedName, HostJsScope.class);
        webView.setWebChromeClient(mWebChromeClient);
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.setDownloadListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void dealJavascriptLeak() {
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
    }

    private void initTitle() {
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorPrimary), 0);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
        webView = (WebView) findViewById(R.id.webview_detail);
        videoFullView = (FrameLayout) findViewById(R.id.video_fullView);
        Toolbar titleToolBar = (Toolbar) findViewById(R.id.title_tool_bar);
        setSupportActionBar(titleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        titleToolBar.setTitle(mTitle);
        titleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getIntentData() {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra("title");
            mUrl = getIntent().getStringExtra("url");
            mInjectedName = getIntent().getStringExtra("injectedName");
        }
    }

    @Override
    public void hindProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void startProgress() {
        startProgress90();
    }

    @Override
    public void showWebView() {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindWebView() {
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void fullViewAddView(View view) {
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        videoFullView = new FullscreenHolder(BrowserActivity.this);
        videoFullView.addView(view);
        decor.addView(videoFullView);
    }

    @Override
    public void hindVideoFullView() {
        videoFullView.setVisibility(View.GONE);
    }

    @Override
    public void showVideoFullView() {
        videoFullView.setVisibility(View.VISIBLE);
    }

    @Override
    public void progressChanged(int newProgress) {
        if (mProgress90) {
            int progress = newProgress * 100;
            if (progress > 900) {
                mProgressBar.setProgress(progress);
                if (progress == 1000) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void addImageClickListener() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        // 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "var array=new Array();" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
//                       "array[i]=objs[i].getAttribute(\\\"src\\\");"+
//                       "objs[i].onclick=function(){window." + mInjectedName + ".imageClick(array.toString(),i);}" + //获取全部图片
                "objs[i].onclick=function(){window." + mInjectedName + ".imageClick(this.getAttribute(\"src\"));}" +    //点击获取单张图片
//                     "objs[i].onclick=function(){alert(this.getAttribute(\"has_link\"));}" +
                //                 "objs[i].onclick=function(){window." + mInjectedName + ".imageClick(this.getAttribute(\"src\"),this.getAttribute(\"has_link\"));}" +
                "}" +
                "})()");

//        webView.loadUrl("javascript:(function(){ " + "var objs = document.getElementsByTagName(\"img\");"
//                + " var array=new Array(); " + " for(var j=0;j<objs.length;j++){ " + "array[j]=objs[j].src;" + " }  "
//                + "for(var i=0;i<objs.length;i++){"
//                + "objs[i].onclick=function(){  window.imageClick.imageClick(array.toString(),i);" + "}  " + "}    })()");

//         遍历所有的a节点,将节点里的属性传递过去(属性自定义,用于页面跳转)
        webView.loadUrl("javascript:(function(){" +
                "var objs =document.getElementsByTagName(\"a\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                "objs[i].onclick=function(){" +
                "window." + mInjectedName + ".textClick(this.getAttribute(\"type\"),this.getAttribute(\"item_pk\"));}" +
                "}" +
                "})()");
    }

    /**
     * 进度条 假装加载到90%
     */
    public void startProgress90() {
        for (int i = 0; i < 900; i++) {
            final int progress = i + 1;
            mProgressBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(progress);
                    if (progress == 900) {
                        mProgress90 = true;
                        if (mPageFinish) {
                            startProgress90to100();
                        }
                    }
                }
            }, (i + 1) * 2);
        }
    }

    /**
     * 进度条 加载到100%
     */
    public void startProgress90to100() {
        for (int i = 900; i <= 1000; i++) {
            final int progress = i + 1;
            mProgressBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(progress);
                    if (progress == 1000) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }, (i + 1) * 2);
        }
    }


    public FrameLayout getVideoFullView() {
        return videoFullView;
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 上传图片之后的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == InjectedChromeClient.FILECHOOSER_RESULTCODE) {
            mWebChromeClient.mUploadMessage(intent, resultCode);
        } else if (requestCode == InjectedChromeClient.FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            mWebChromeClient.mUploadMessageForAndroid5(intent, resultCode);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //全屏播放退出全屏
            if (mWebChromeClient.inCustomView()) {
                hideCustomView();
                return true;

                //返回网页上一页
            } else if (webView.canGoBack()) {
                webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                webView.goBack();
                return true;

                //退出网页
            } else {
                webView.loadUrl("about:blank");
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
        webView.resumeTimers();
        // 设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoFullView.removeAllViews();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
    }

    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   title
     */
    public static void launch(Context mContext, String mUrl, String mTitle) {
        Intent intent = new Intent(mContext, BrowserActivity.class);
        intent.putExtra("url", mUrl);
        intent.putExtra("title", mTitle);
        mContext.startActivity(intent);
    }

    public static void launch(Context mContext, String mUrl, String mTitle, String injectedName) {
        Intent intent = new Intent(mContext, BrowserActivity.class);
        intent.putExtra("url", mUrl);
        intent.putExtra("title", mTitle);
        intent.putExtra("injectedName", injectedName);
        mContext.startActivity(intent);
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
