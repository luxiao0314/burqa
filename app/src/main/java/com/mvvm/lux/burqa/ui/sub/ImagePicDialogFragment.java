/*
 * Copyright 2013 Inmite s.r.o. (www.inmite.eu).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mvvm.lux.burqa.ui.sub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.FragmentImagePicListBinding;
import com.mvvm.lux.burqa.model.ImagePicDialogViewModel;
import com.mvvm.lux.framework.manager.dialogs.SimpleDialogFragment;
import com.mvvm.lux.framework.manager.dialogs.config.BaseTask;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/17 19:25
 * @Version 1.0.0
 */
public class ImagePicDialogFragment extends SimpleDialogFragment {

    public static String TAG = "jayne";

    public static void show(FragmentActivity activity, int urlistsize, int currentPosition, String chapterTitle) {
        Bundle bundle = new Bundle();
        bundle.putInt("urlistsize",urlistsize);
        bundle.putInt("currentPosition",currentPosition);
        bundle.putString("chapterTitle",chapterTitle);
        ImagePicDialogFragment imagePicDialogFragment = new ImagePicDialogFragment();
        imagePicDialogFragment.setArguments(bundle);
        imagePicDialogFragment.show(activity.getSupportFragmentManager(), TAG);
    }

    public static void show(BaseTask baseTask) {
        new ImagePicDialogFragment().show(baseTask.getFragmentManager(), TAG);
    }

    @Override
    public int getTheme() {
        return R.style.image_pic_translucent;
    }

    @Override
    public Builder build(Builder builder) {
        int urlistsize = getArguments().getInt("urlistsize");
        int currentPosition = getArguments().getInt("currentPosition");
        String chapterTitle = getArguments().getString("chapterTitle");

        FragmentImagePicListBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_image_pic_list, null, false);
        ImagePicDialogViewModel viewModel = new ImagePicDialogViewModel(getActivity(), this);
        viewModel.maxProgress.set(urlistsize);
        viewModel.progress.set(currentPosition);
        viewModel.adver_tv.set((currentPosition + 1) + "/" + urlistsize);
        viewModel.chapter_title.set(chapterTitle);
        dataBinding.setViewModel(viewModel);
        builder.setView(dataBinding.getRoot());
        return builder;
    }
}
