package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.widget.SeekBar;

import com.mvvm.lux.burqa.model.event.ProgressEvent;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.rx.RxBus;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/17 19:38
 * @Version
 */
public class ImagePicDialogViewModel extends BaseViewModel {

    public ObservableField<Integer> maxProgress = new ObservableField<>();

    public ImagePicDialogViewModel(Activity activity) {
        super(activity);
    }

    public SeekBar.OnSeekBarChangeListener getSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RxBus.init().postSticky(new ProgressEvent(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }
}
