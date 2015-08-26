package com.galleriafrique.util.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.galleriafrique.R;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by osifo on 8/3/15.
 */
public class BounceAnimation {

    private static long delay = 1000;

    public static void dropViewAnimation(Context context, final View view) {
        final AnimationEndListener animationEndListener = (AnimationEndListener) context;

        Animation animTranslate = AnimationUtils.loadAnimation(context, R.anim.abc_fade_in);
        animTranslate.setStartOffset(delay);
        animTranslate.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                if (animationEndListener != null) {
                    animationEndListener.animationEnded();
                }
            }
        });
        view.startAnimation(animTranslate);
    }
}
