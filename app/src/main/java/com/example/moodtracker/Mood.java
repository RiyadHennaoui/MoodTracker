package com.example.moodtracker;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

public enum Mood {

    SUPER_HAPPY(R.color.banana_yellow, R.drawable.smiley_super_happy),
    HAPPY(R.color.light_sage, R.drawable.smiley_happy),
    NORMAL(R.color.cornflower_blue_65, R.drawable.smiley_normal),
    SAD(R.color.warm_grey, R.drawable.smiley_sad),
    DISAPPOINTED(R.color.faded_red, R.drawable.smiley_disappointed);

    @ColorRes
    int colorRes;
    @DrawableRes
    int drawableRes;

    @ColorRes
    public int getColorRes() {

        return colorRes;
    }

    @DrawableRes
    public int getDrawableRes() {
        return drawableRes;
    }

    Mood(@ColorRes int colorRes, @DrawableRes int drawableRes) {

        this.colorRes = colorRes;
        this.drawableRes = drawableRes;

    }

}
