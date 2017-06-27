package com.reactnative.photoview;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.fresco.ReactNetworkImageRequest;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;

import javax.annotation.Nullable;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnScaleChangeListener;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

import static com.facebook.react.views.image.ReactImageView.REMOTE_IMAGE_FADE_DURATION_MS;

/**
 * @author alwx (https://github.com/alwx)
 * @version 1.0
 */
public class PhotoView extends PhotoDraweeView {
    private Uri mUri;
    private ReadableMap mHeaders;
    private boolean mIsDirty;
    private boolean mIsLocalImage;
    private Drawable mLoadingImageDrawable;
    private PipelineDraweeControllerBuilder mDraweeControllerBuilder;
    private int mFadeDurationMs = -1;
    private ControllerListener mControllerListener;

    public PhotoView(Context context) {
        super(context);
    }

    public void setSource(@Nullable ReadableMap source,
                          @NonNull ResourceDrawableIdHelper resourceDrawableIdHelper) {
        mUri = null;
        if (source != null) {
            String uri = source.getString("uri");
            try {
                mUri = Uri.parse(uri);
                // Verify scheme is set, so that relative uri (used by static resources) are not handled.
                if (mUri.getScheme() == null) {
                    mUri = null;
                }
                if (source.hasKey("headers")) {
                    mHeaders = source.getMap("headers");
                }
            } catch (Exception e) {
                // ignore malformed uri, then attempt to extract resource ID.
            }
            if (mUri == null) {
                mUri = resourceDrawableIdHelper.getResourceDrawableUri(getContext(), uri);
                mIsLocalImage = true;
            } else {
                mIsLocalImage = false;
            }
        }
        mIsDirty = true;
    }

    public void setLoadingIndicatorSource(@Nullable String name,
                                          ResourceDrawableIdHelper resourceDrawableIdHelper) {
        Drawable drawable = resourceDrawableIdHelper.getResourceDrawable(getContext(), name);
        mLoadingImageDrawable =
                drawable != null ? (Drawable) new AutoRotateDrawable(drawable, 1000) : null;
        mIsDirty = true;
    }

    public void setFadeDuration(int durationMs) {
        mFadeDurationMs = durationMs;
        // no worth marking as dirty if it already rendered..
    }

    public void setShouldNotifyLoadEvents(boolean shouldNotify) {
        if (!shouldNotify) {
            mControllerListener = null;
        } else {
            final EventDispatcher eventDispatcher = ((ReactContext) getContext())
                    .getNativeModule(UIManagerModule.class).getEventDispatcher();
            mControllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onSubmit(String id, Object callerContext) {
                    eventDispatcher.dispatchEvent(
                            new ImageEvent(getId(), ImageEvent.ON_LOAD_START)
                    );
                }

                @Override
                public void onFinalImageSet(
                        String id,
                        @Nullable final ImageInfo imageInfo,
                        @Nullable Animatable animatable) {
                    if (imageInfo != null) {
                        eventDispatcher.dispatchEvent(
                                new ImageEvent(getId(), ImageEvent.ON_LOAD)
                        );
                        eventDispatcher.dispatchEvent(
                                new ImageEvent(getId(), ImageEvent.ON_LOAD_END)
                        );
                        update(imageInfo.getWidth(), imageInfo.getHeight());
                    }
                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    eventDispatcher.dispatchEvent(
                            new ImageEvent(getId(), ImageEvent.ON_ERROR)
                    );
                    eventDispatcher.dispatchEvent(
                            new ImageEvent(getId(), ImageEvent.ON_LOAD_END)
                    );
                }
            };
        }
        mIsDirty = true;
    }

    public void maybeUpdateView(@NonNull PipelineDraweeControllerBuilder builder) {
        if (!mIsDirty) {
            return;
        }

        GenericDraweeHierarchy hierarchy = getHierarchy();
        if (mLoadingImageDrawable != null) {
            hierarchy.setPlaceholderImage(mLoadingImageDrawable, ScalingUtils.ScaleType.CENTER);
        }
        hierarchy.setFadeDuration(
                mFadeDurationMs >= 0
                        ? mFadeDurationMs
                        : mIsLocalImage ? 0 : REMOTE_IMAGE_FADE_DURATION_MS);

        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(mUri)
                .setAutoRotateEnabled(true);

        ImageRequest imageRequest = ReactNetworkImageRequest
                .fromBuilderWithHeaders(imageRequestBuilder, mHeaders);

        mDraweeControllerBuilder = builder;
        mDraweeControllerBuilder.setImageRequest(imageRequest);
        mDraweeControllerBuilder.setAutoPlayAnimations(true);
        mDraweeControllerBuilder.setOldController(getController());
        mDraweeControllerBuilder.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });

        if (mControllerListener != null) {
            mDraweeControllerBuilder.setControllerListener(mControllerListener);
        }

        setController(mDraweeControllerBuilder.build());
        setViewCallbacks();

        mIsDirty = false;
    }

    private void setViewCallbacks() {
        final EventDispatcher eventDispatcher = ((ReactContext) getContext())
                .getNativeModule(UIManagerModule.class).getEventDispatcher();

        setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                WritableMap scaleChange = Arguments.createMap();
                scaleChange.putDouble("x", x);
                scaleChange.putDouble("y", y);
                scaleChange.putDouble("scale", PhotoView.this.getScale());
                eventDispatcher.dispatchEvent(
                        new ImageEvent(getId(), ImageEvent.ON_TAP).setExtras(scaleChange)
                );
            }
        });

        setOnScaleChangeListener(new OnScaleChangeListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                WritableMap scaleChange = Arguments.createMap();
                scaleChange.putDouble("scale", PhotoView.this.getScale());
                scaleChange.putDouble("scaleFactor", scaleFactor);
                scaleChange.putDouble("focusX", focusX);
                scaleChange.putDouble("focusY", focusY);
                eventDispatcher.dispatchEvent(
                        new ImageEvent(getId(), ImageEvent.ON_SCALE).setExtras(scaleChange)
                );
            }
        });

        setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                WritableMap scaleChange = Arguments.createMap();
                scaleChange.putDouble("x", x);
                scaleChange.putDouble("y", y);
                eventDispatcher.dispatchEvent(
                        new ImageEvent(getId(), ImageEvent.ON_VIEW_TAP).setExtras(scaleChange)
                );
            }
        });
    }
}
