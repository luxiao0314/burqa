package com.mvvm.lux.framework;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mvvm.lux.framework.http.RetrofitExcuter;
import com.mvvm.lux.framework.http.fresco.ImageLoaderConfig;
import com.orhanobut.hawk.Hawk;

import java.util.LinkedList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

/**
 * Created by WangChao on 2016/10/12.
 */

public class BaseApplication extends Application {

    /**
     * 应用创建activity集合 在创建activity时调用addActivity方法将新创建的活动添加到集合中
     */
    private static LinkedList<Activity> activitys = new LinkedList<>();

    public static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        init();
    }

    private void init() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               //设置线程优先级,不与主线程抢资源
               Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
               //初始化retrofit
               RetrofitExcuter.init();
               //初始化fresco
               Fresco.initialize(getAppContext(), ImageLoaderConfig.getImagePipelineConfig(getAppContext()));
               //初始化realm
               initRealm();
               //初始化缓存
               Hawk.init(sInstance).build();

               //蒲公英crash上报
               //PgyCrashManager.register(this);
               //初始化内存泄漏检测
               //LeakCanary.install(this);
               //初始化过度绘制检测
               //BlockCanary.install(this, new AppBlockCanaryContext()).start();
           }
       }).start();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("lux.realm")
                .schemaVersion(1)
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static synchronized BaseApplication getAppContext() {
        return sInstance;
    }

    /**
     * 添加活动到应用活动集合中
     *
     * @param activity 活动对象
     */
    public void registerActivity(Activity activity) {
        if (null != activitys) {
            activitys.add(activity);
        }
    }

    /**
     * 结束活动集合中的一个对象
     *
     * @param activity
     */
    public void unregisterActivity(Activity activity) {
        if (null != activitys && null != activity) {
            int position = activitys.indexOf(activity);
            if (position >= 0) {
                activitys.remove(position);
            }
            activity.finish();
        }
    }

    /**
     * 结束活动的Activity
     */
    public void exitApp() {
        if (null != activitys) {
            synchronized (activitys) {
                for (Activity activity : activitys) {
                    activity.finish();
                }
            }
            activitys.clear();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 结束最后一个活动之前的Activity
     */
    public static void killBeforeActivitys() {
        if (null != activitys) {
            int size = activitys.size() - 1;
            for (int i = 0; i < size; i++) {
                Activity activity = activitys.get(i);
                activity.finish();
            }
            System.out.println(activitys.size());
        }
    }

    /**
     * 获取队列中最后一个activity
     *
     * @return ACTIVITY
     */
    public static Activity lastActivity() {
        Activity activity = null;
        if (null != activitys) {
            activity = activitys.getLast();
        }
        return activity;
    }
}
