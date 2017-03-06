package com.mvvm.lux.burqa.manager.bindingadapter.view;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.manager.ReplyCommand;
import com.mvvm.lux.burqa.manager.ResponseCommand;
import com.mvvm.lux.widget.bgabanner.BGABanner;
import com.mvvm.lux.widget.emptyview.EmptyView;
import com.zhy.view.flowlayout.TagFlowLayout;


/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    @BindingAdapter({"clickCommand"})
    public static void clickCommand(View view, final ReplyCommand clickCommand) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCommand != null) {
                    clickCommand.execute();
                }
            }
        });
    }

    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final ReplyCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFocusChangeCommand != null) {
                    onFocusChangeCommand.execute(hasFocus);
                }
            }
        });
    }

    @BindingAdapter({"onTouchCommand"})
    public static void onTouchCommand(View view, final ResponseCommand<MotionEvent, Boolean> onTouchCommand) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (onTouchCommand != null) {
                    return onTouchCommand.execute(event);
                }
                return false;
            }
        });
    }

    @BindingAdapter("initBanner")
    public static void initBanner(BGABanner bannerView, ObservableList<String> bannerEntities) {
        bannerView.setData(R.layout.item_fresco, bannerEntities, null);
        bannerView.setAdapter(new BGABanner.Adapter<CardView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, CardView itemView, String model, int position) {
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_item_fresco_content);
                simpleDraweeView.setImageURI(Uri.parse(model));
            }
        });
    }

    @BindingAdapter("onItemClick")
    public static void onItemClick(BGABanner bannerView, BGABanner.Delegate<CardView, String> listener) {
        if (listener != null) {
            bannerView.setDelegate(listener);
        }
    }

    /**
     * 流式布局的item电击事件
     *
     * @param tagFlowLayout
     * @param listener
     */
    @BindingAdapter("OnTagClick")
    public static void setOnTagClick(TagFlowLayout tagFlowLayout, TagFlowLayout.OnTagClickListener listener) {
        if (listener != null) {
            tagFlowLayout.setOnTagClickListener(listener);
        }
    }

    @BindingAdapter("OnTagSelect")
    public static void OnTagSelect(TagFlowLayout tagFlowLayout, TagFlowLayout.OnSelectListener listener) {
        if (listener != null) {
            tagFlowLayout.setOnSelectListener(listener);
        }
    }

    @BindingAdapter("reload")
    public static void reload(EmptyView emptyView, EmptyView.ReloadOnClickListener listener) {
        if (listener != null)
            emptyView.reload(listener);
    }

    @BindingAdapter("seekBarChangeListener")
    public static void seekBarChangeListener(SeekBar seekBar, SeekBar.OnSeekBarChangeListener listener) {
        if (listener != null)
            seekBar.setOnSeekBarChangeListener(listener);
    }


}

