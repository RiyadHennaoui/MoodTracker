package com.example.moodtracker;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

public enum Mood {

    SUPER_HAPPY(R.color.banana_yellow, R.drawable.smiley_super_happy, 1f),
    HAPPY(R.color.light_sage, R.drawable.smiley_happy, 0.8f),
    NORMAL(R.color.cornflower_blue_65, R.drawable.smiley_normal, 0.6f),
    SAD(R.color.warm_grey, R.drawable.smiley_sad, 0.4f),
    DISAPPOINTED(R.color.faded_red, R.drawable.smiley_disappointed, 0.2f);

    @ColorRes
    private int colorRes;
    @DrawableRes
    private int drawableRes;
    private float moodPercentage;

    @ColorRes
    public int getColorRes() {

        return colorRes;
    }

    @DrawableRes
    public int getDrawableRes() {
        return drawableRes;
    }

    Mood(@ColorRes int colorRes, @DrawableRes int drawableRes, float moodPercentage) {

        this.colorRes = colorRes;
        this.drawableRes = drawableRes;
        this.moodPercentage=moodPercentage;

    }

    public float getMoodPercent(){ return moodPercentage;}

}
