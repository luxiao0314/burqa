package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.SeekBar;

import com.mvvm.lux.burqa.model.event.ProgressEvent;
import com.mvvm.lux.burqa.model.event.SwitchModeEvent;
import com.mvvm.lux.burqa.ui.sub.ImagePicDialogFragment;
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

    public ObservableField<Integer> progress = new ObservableField<>(0);

    public ObservableField<String> adver_tv = new ObservableField<>();

    public ObservableField<String> chapter_title = new ObservableField<>();

    private ImagePicDialogFragment mImagePicDialogFragment;

    public ImagePicDialogViewModel(Activity activity, ImagePicDialogFragment imagePicDialogFragment) {
        super(activity);
        mImagePicDialogFragment = imagePicDialogFragment;
    }

    public SeekBar.OnSeekBarChangeListener getSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                adver_tv.set((progress + 1) + "/" + maxProgress.get());
                RxBus.init().postSticky(new ProgressEvent(progress));
            }
        };
    }

    public View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mImagePicDialogFragment.dismissAllowingStateLoss();
        }
    };

    public View.OnClickListener onLandscapeModeCLick() {
        return view -> {
            RxBus.init().postSticky(new SwitchModeEvent());
        };
    }
}
