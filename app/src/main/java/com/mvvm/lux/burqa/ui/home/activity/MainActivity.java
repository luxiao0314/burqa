package com.mvvm.lux.burqa.ui.home.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.github.mzule.activityrouter.router.Routers;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityMainBinding;
import com.mvvm.lux.burqa.model.MainViewModel;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.manager.hybrid.BrowserActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void initView() {
        ActivityMainBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new MainViewModel(this, dataBinding);
        dataBinding.setViewModel((MainViewModel) mViewModel);

        dataBinding.include.toolbar.setTitle("");
        setSupportActionBar(dataBinding.include.toolbar);

        mDrawerLayout = dataBinding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, dataBinding.include.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);    //这句话是隐藏自带图标的

        dataBinding.navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_local_manga) {
            Routers.open(this, "lux://needLogin");   //登录页面
        } else if (id == R.id.nav_history) {    //历史观看
            ComicClassifyActivity.launch(this, "", "历史记录");
        } else if (id == R.id.nav_download) {   //下载页面
//            Routers.open(this, "lux://JsAndroidActivity?jsRouter=AnimeNewsPages");
            BrowserActivity.launch(this, "file:///android_asset/bilibili/index.html", "哔哩哔哩","bilibili");
        } else if (id == R.id.nav_user) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            //返回的时候让app以按home键的形式进入后台
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
