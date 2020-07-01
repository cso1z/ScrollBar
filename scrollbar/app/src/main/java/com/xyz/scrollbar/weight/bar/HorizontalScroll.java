package com.xyz.scrollbar.weight.bar;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 横向滑动条
 *
 * @author shenyonghui
 */
public class HorizontalScroll extends BaseScrollBar {

    /**
     * 绘制轨道
     */
    @Override
    protected void drawTrack(Canvas canvas, RectF container) {
        drawLine(canvas, container, trackColor);
        this.container = container;
    }

    /**
     * 绘制进度
     */
    @Override
    protected void drawThumb(Canvas canvas, float percentage) {
        float thumbLeft = (container.right - container.left - thumbLength) * percentage;
        RectF thumbRect = new RectF(thumbLeft, container.top, thumbLeft + thumbLength, container.bottom);
        drawLine(canvas, thumbRect, thumbColor);
    }

    private void drawLine(Canvas canvas, RectF container, int color) {
        paint.setColor(color);
        float radius = (container.bottom - container.top) / 2f;
        canvas.drawRoundRect(container, radius, radius, paint);
    }

}
