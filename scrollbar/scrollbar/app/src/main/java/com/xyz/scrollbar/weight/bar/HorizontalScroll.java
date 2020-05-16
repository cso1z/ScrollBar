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
        float thumbLeft = (container.right - container.left - thumbWidth) * percentage;
        RectF thumbRect = new RectF(thumbLeft, container.top, thumbLeft + thumbWidth, container.bottom);
        drawLine(canvas, thumbRect, thumbColor);
    }

    private void drawLine(Canvas canvas, RectF container, int color) {
        paint.setColor(color);
        float diameter = container.bottom - container.top;
        //确定外切矩形范围
        RectF circleRect = new RectF(container.left, container.top, container.left + diameter, container.bottom);
        //绘制圆弧，不含圆心
        canvas.drawArc(circleRect, 90, 180, false, paint);
        RectF rect = new RectF(container.left + diameter / 2, container.top, container.right - diameter / 2, container.bottom);
        canvas.drawRect(rect, paint);
        //确定外切矩形范围
        circleRect = new RectF(container.right - diameter, container.top, container.right, container.bottom);
        //绘制圆弧，不含圆心
        canvas.drawArc(circleRect, 270, 180, false, paint);
    }

}
