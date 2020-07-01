package com.xyz.scrollbar.weight.bar;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 纵向滑动
 *
 * @author shenyonghui
 */
public class VerticalScroll extends BaseScrollBar {

    @Override
    void drawTrack(Canvas canvas, RectF container) {
        drawLine(canvas, container, trackColor);
        this.container = container;
    }

    @Override
    void drawThumb(Canvas canvas, float percentage) {
        float thumbTop = (container.bottom - container.top - thumbLength) * percentage;
        RectF thumbRect = new RectF(container.left, thumbTop, container.right, thumbTop + thumbLength);
        drawLine(canvas, thumbRect, thumbColor);
    }

    private void drawLine(Canvas canvas, RectF container, int color) {
        paint.setColor(color);
        float radius = (container.bottom - container.top) / 2f;
        canvas.drawRoundRect(container, radius, radius, paint);
    }

}
