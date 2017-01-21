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
    private static int sUrlistsize;
    private static int sCurrentPosition;
    private static String sChapterTitle;

    public static void show(FragmentActivity activity, int urlistsize, int currentPosition, String chapterTitle) {
        sUrlistsize = urlistsize;
        sCurrentPosition = currentPosition;
        sChapterTitle = chapterTitle;
        new ImagePicDialogFragment().show(activity.getSupportFragmentManager(), TAG);
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
        FragmentImagePicListBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_image_pic_list, null, false);
        ImagePicDialogViewModel viewModel = new ImagePicDialogViewModel(getActivity(), this);
        viewModel.maxProgress.set(sUrlistsize);
        viewModel.progress.set(sCurrentPosition);
        viewModel.adver_tv.set((sCurrentPosition + 1) + "/" + sUrlistsize);
        viewModel.chapter_title.set(sChapterTitle);
        dataBinding.setViewModel(viewModel);
        builder.setView(dataBinding.getRoot());
        return builder;
    }
}
