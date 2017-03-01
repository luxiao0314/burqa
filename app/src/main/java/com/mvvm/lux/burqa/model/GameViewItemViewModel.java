package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.manager.hybrid.BrowserActivity;

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

    public GameViewItemViewModel(Activity activity) {
        super(activity);
    }

    public View.OnClickListener mOnClickListener = view -> {
        String url = "http://duxingxia081.gicp.net/suum/pingAnBinding.do?method=goPingAnBindingPage&systemCode=1026";
        BrowserActivity.launch(mActivity, url, "调试", "paAccountApp");
    };
}
