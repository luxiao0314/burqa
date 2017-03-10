package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.mvvm.lux.burqa.ui.sub.activity.SubjectDesActivity;
import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/1 10:45
 * @Version
 */
public class GameViewItemViewModel extends BaseViewModel {

    public ObservableField<String> create_time = new ObservableField<>();
    public ObservableField<String> img = new ObservableField<>();
    public ObservableField<Integer> id = new ObservableField<>();

    public GameViewItemViewModel(Activity activity) {
        super(activity);
    }

    public View.OnClickListener mOnClickListener = view -> {
//        String url = "http://duxingxia081.gicp.net/suum/pingAnBinding.do?method=goPingAnBindingPage&systemCode=1026";
//        BrowserActivity.launch(mActivity, url, "调试", "paAccountApp");

//        Routers.open(mActivity, "lux://authordes?sOpen=false");
        // 跳转并携带参数
        SubjectDesActivity.launch(mActivity,id.get());
    };
}
