package com.example.fiscalitics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class ViewAnimation {

    //Animation action for the floating action button
    public static boolean animateFab(final View v, Boolean animate){
        v.animate().setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        return animate;
    }
}
