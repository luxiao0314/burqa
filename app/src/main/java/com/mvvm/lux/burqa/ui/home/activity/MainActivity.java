package com.mvvm.lux.burqa.ui.home.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityMainBinding;
import com.mvvm.lux.burqa.model.MainViewModel;
import com.mvvm.lux.framework.base.BaseActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ActivityMainBinding mDataBinding;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void initView() {
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new MainViewModel(this,mDataBinding);
        mDataBinding.setViewModel((MainViewModel) mViewModel);

        mDataBinding.include.toolbar.setTitle("");
        setSupportActionBar(mDataBinding.include.toolbar);

        mDrawerLayout = mDataBinding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mDataBinding.include.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mDataBinding.navView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_local_manga) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_download) {

        } else if (id == R.id.nav_user) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
