package com.xyz.scrollbar.weight.bar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author shenyonghui
 */
public abstract class BaseScrollBar {

    Paint paint;
    int trackColor;
    int thumbColor;
    int thumbLength;
    RectF container;

    BaseScrollBar() {
        paint = new Paint();
    }

    public void setTrackColor(int color) {
        this.trackColor = color;
    }

    public void setThumbColor(int color) {
        this.thumbColor = color;
    }

    public void setThumbLength(int thumbLength) {
        this.thumbLength = thumbLength;
    }


    /**
     * 绘制轨道
     *
     * @param canvas    画布
     * @param container 轨道 Rect
     */
    abstract void drawTrack(Canvas canvas, RectF container);

    /**
     * 绘制进度
     *
     * @param canvas     画布
     * @param percentage 进度百分比
     */
    abstract void drawThumb(Canvas canvas, float percentage);

}