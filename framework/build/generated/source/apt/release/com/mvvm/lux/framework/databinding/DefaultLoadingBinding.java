package com.mvvm.lux.framework.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
public abstract class DefaultLoadingBinding extends ViewDataBinding {
    public final android.widget.LinearLayout loadMoreProgress;
    public final android.widget.TextView loadingText;
    protected DefaultLoadingBinding(android.databinding.DataBindingComponent bindingComponent, android.view.View root_, int localFieldCount
        , android.widget.LinearLayout loadMoreProgress
        , android.widget.TextView loadingText
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.loadMoreProgress = loadMoreProgress;
        this.loadingText = loadingText;
    }
    public static DefaultLoadingBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DefaultLoadingBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DefaultLoadingBinding bind(android.view.View view) {
        return null;
    }
    public static DefaultLoadingBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static DefaultLoadingBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static DefaultLoadingBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}