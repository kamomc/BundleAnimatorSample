package com.sikmi.bundleanimatorsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.view.ViewGroup;

public class DrawerAnimator extends BaseObservable {
    private static DrawerAnimator instance;
    private final float mDensity;
    private float mT;
    private boolean isOpening;
    private ValueAnimator mAnimator;

    private DrawerAnimator(float density) {
        mDensity = density;
    }

    @Bindable
    public float getT() {
        return mT;
    }

    @Bindable
    public float getDensity() {
        return mDensity;
    }

    public static DrawerAnimator getInstance(Float density) {
        if (instance == null) {
            instance = new DrawerAnimator(density);
        }
        return instance;
    }

    public void toggleDrawer(View imageButton) {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        float toParam = isOpening ? 0.0f : 1.0f;
        mAnimator = ValueAnimator.ofFloat(mT, toParam);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedParam = (float) valueAnimator.getAnimatedValue();
                isOpening = mT < animatedParam;
                mT = animatedParam;
                notifyPropertyChanged(BR.t);
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimator = null;
            }
        });
        mAnimator.start();
    }

    @BindingAdapter("height")
    public static void setHeight(View view, float height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) height;
        view.setLayoutParams(layoutParams);
    }
}
