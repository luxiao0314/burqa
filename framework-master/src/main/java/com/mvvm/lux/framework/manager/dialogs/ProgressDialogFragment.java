package com.mvvm.lux.framework.manager.dialogs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mvvm.lux.framework.R;
import com.mvvm.lux.framework.manager.dialogs.config.BaseTask;
import com.mvvm.lux.framework.manager.dialogs.interfaces.ProgressCancelListener;


/**
 * Simple progress dialog that shows indeterminate progress bar together with message and dialog title (optional).<br/>
 * <p>
 * To show the dialog, start with {@link #createBuilder(BaseTask)}.
 * </p>
 *
 * @author Tomas Vondracek
 */
public class ProgressDialogFragment extends BaseDialogFragment {

    protected final static String ARG_MESSAGE = "message";
    protected final static String ARG_TITLE = "title";
    private static ProgressCancelListener progressCancelListener;   //TODO 静态的造成内存泄漏

    public static ProgressDialogBuilder createBuilder(Context context, FragmentManager fragmentManager) {
        return new ProgressDialogBuilder(context, fragmentManager);
    }

    public static ProgressDialogBuilder createBuilder(BaseTask baseTask) {
        progressCancelListener = baseTask.getDialogExchangeModel().dialogExchangeModelBuilder.progressCancelListener;
        return new ProgressDialogBuilder(baseTask);
    }

    @Override
    protected Builder build(Builder builder) {
        int progressType = getArguments().getInt(SimpleDialogFragment.PROGRESS_TYPE);
        if (progressType == 1) {
            processLoadDataLayout(builder);
        } else if (progressType == 2) {
            sdlProgress(builder);
        } else if (progressType == 3) {
            bukaProgress(builder);
        }
        return builder;
    }

    private void bukaProgress(Builder builder) {
        final LayoutInflater inflater = builder.getLayoutInflater();
        View layoutView = inflater.inflate(R.layout.process_buka_layout, null, false);
        SimpleDraweeView img = (SimpleDraweeView) layoutView.findViewById(R.id.image);
        img.setController(Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse("res://" + getContext().getPackageName() + "/" + R.drawable.buka_loading))
                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                .build());
        builder.setView(layoutView);
        builder.setTitle(getArguments().getCharSequence(ARG_TITLE));
    }

    private void processLoadDataLayout(Builder builder) {
        final LayoutInflater inflater = builder.getLayoutInflater();
        View layoutView = inflater.inflate(R.layout.process_load_data_layout, null, false);
        SimpleDraweeView img = (SimpleDraweeView) layoutView.findViewById(R.id.image);
        TextView mDlgContent = (TextView) layoutView.findViewById(R.id.tip);

        if (!TextUtils.isEmpty(getArguments().getCharSequence(ARG_MESSAGE))) {
            mDlgContent.setText(getArguments().getCharSequence(ARG_MESSAGE));
        }
        img.setController(Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse("res://" + getContext().getPackageName() + "/" + R.drawable.gif_loading))
                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                .build());
        builder.setView(layoutView);
        builder.setTitle(getArguments().getCharSequence(ARG_TITLE));
    }

    private void sdlProgress(Builder builder) {
        final LayoutInflater inflater = builder.getLayoutInflater();
        final View view = inflater.inflate(R.layout.sdl_progress, null, false);
        final TextView tvMessage = (TextView) view.findViewById(R.id.sdl_message);
        tvMessage.setText(getArguments().getCharSequence(ARG_MESSAGE));
        builder.setView(view);
        builder.setTitle(getArguments().getCharSequence(ARG_TITLE));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() == null) {
            throw new IllegalArgumentException("use ProgressDialogBuilder to construct this dialog");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressCancelListener != null) {
            progressCancelListener.onCancelProgress();
        }
    }

    public static class ProgressDialogBuilder extends BaseDialogBuilder<ProgressDialogBuilder> {

        private CharSequence mTitle;
        private CharSequence mMessage;
        private int progressType;

        protected ProgressDialogBuilder(Context context, FragmentManager fragmentManager) {
            super(context, fragmentManager, ProgressDialogFragment.class);
        }

        protected ProgressDialogBuilder(BaseTask baseTask) {
            super(baseTask.getContext(), baseTask.getFragmentManager(), ProgressDialogFragment.class);
        }

        @Override
        protected ProgressDialogBuilder self() {
            return this;
        }

        public ProgressDialogBuilder setTitle(int titleResourceId) {
            mTitle = mContext.getString(titleResourceId);
            return this;
        }


        public ProgressDialogBuilder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        public ProgressDialogBuilder setMessage(int messageResourceId) {
            mMessage = mContext.getString(messageResourceId);
            return this;
        }

        public ProgressDialogBuilder setMessage(CharSequence message) {
            mMessage = message;
            return this;
        }

        public ProgressDialogBuilder setProgressType(int progressType) {
            this.progressType = progressType;
            return this;
        }

        @Override
        protected Bundle prepareArguments() {
            Bundle args = new Bundle();
            args.putCharSequence(SimpleDialogFragment.ARG_MESSAGE, mMessage);
            args.putCharSequence(SimpleDialogFragment.ARG_TITLE, mTitle);
            args.putInt(SimpleDialogFragment.PROGRESS_TYPE, progressType);
            return args;
        }
    }
}
