package com.example.expenceappmvvm.domain.util

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

object UIUtils {

    /**
     * TabLayout change tab animation
     * **/
    fun inFromRightAnimation(): Animation {
        return TranslateAnimation(
            Animation.RELATIVE_TO_SELF, +1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f
        ).apply {
            duration = 150
            interpolator = AccelerateInterpolator()
        }
    }

    fun outToRightAnimation(): Animation {
        return TranslateAnimation(
            Animation.RELATIVE_TO_SELF, -1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f
        ).apply {
            duration = 150
            interpolator = AccelerateInterpolator()
        }
    }

    fun viewScaleUp(view: View) {
        view.animate().scaleXBy(0.2f).setDuration(300).start()
        view.animate().scaleYBy(0.2f).setDuration(300).start()
    }

    fun viewScaleDown(view: View) {
        view.animate().scaleX(1f).setDuration(150).start()
        view.animate().scaleY(1f).setDuration(150).start()
    }
}