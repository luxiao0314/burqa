package com.mvvm.lux.burqa.ui.sub.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvvm.lux.framework.base.BaseActivity;

/**
 * Created by CaoDongping on 4/8/16.
 */
public class NotFoundActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("404");
        textView.setGravity(Gravity.CENTER);
        setContentView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
