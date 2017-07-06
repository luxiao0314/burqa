package com.mvvm.lux.burqa.manager.hybrid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.just.library.LogUtils;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.utils.Logger;

public class WebActivity extends BaseActivity {

    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private Toolbar mToolbar;
    private TextView mTitleTextView;
    private AlertDialog mAlertDialog;
    private String title;
    private String url;
    private String injectedName;

    public static void launch(Activity activity, String url, String title, String injectedName) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("injectedName", injectedName);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, String url,String injectedName) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", "");
        intent.putExtra("injectedName", injectedName);
        activity.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getIntentData();
        initViews();
        initWebView();
    }

    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .setWebLayout(new WebLayout(this))
                .createAgentWeb()//
                .ready()
                .go(url);
        mAgentWeb.getJsInterfaceHolder()
                .addJavaObject(injectedName, new AndroidInterface(mAgentWeb, this));
    }

    private void initViews() {
        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mTitleTextView = (TextView) this.findViewById(R.id.toolbar_title);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(title);
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> showDialog());
    }

    private void getIntentData() {
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
            injectedName = getIntent().getStringExtra("injectedName");
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Logger.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","progress:"+newProgress);
        }
    };

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mTitleTextView != null)
                mTitleTextView.setText(title);
        }
    };

    private void showDialog() {

        if (mAlertDialog == null)
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("再逛逛", (dialog, which) -> {
                        if (mAlertDialog != null)
                            mAlertDialog.dismiss();
                    })//
                    .setPositiveButton("确定", (dialog, which) -> {

                        if (mAlertDialog != null)
                            mAlertDialog.dismiss();

                        WebActivity.this.finish();
                    }).create();
        mAlertDialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtils.i("Info", "result:" + requestCode + " result:" + resultCode);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
