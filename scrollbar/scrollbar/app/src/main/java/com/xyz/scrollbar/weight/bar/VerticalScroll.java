package com.xyz.scrollbar.weight.bar;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

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
        float thumbTop = (container.bottom - container.top - thumbWidth) * percentage;
        RectF thumbRect = new RectF(container.left, thumbTop, container.right, thumbTop + thumbWidth);
        drawLine(canvas, thumbRect, thumbColor);
        Log.e("xx", "percentage:" + percentage);
    }

    private void drawLine(Canvas canvas, RectF container, int color) {
        paint.setColor(color);
        //计算半圆直径
        float diameter = container.right - container.left;
        //确定外切矩形范围
        RectF circleRect = new RectF(container.left, container.top, container.right, container.top + diameter);
        //绘制圆弧，不含圆心
        canvas.drawArc(circleRect, 180, 180, false, paint);
        //直线区域
        RectF rect = new RectF(container.left, container.top + diameter / 2, container.right, container.bottom - diameter / 2);
        canvas.drawRect(rect, paint);
        //确定外切矩形范围
        circleRect = new RectF(container.left, container.bottom - diameter, container.right, container.bottom);
        //绘制圆弧，不含圆心
        canvas.drawArc(circleRect, 0, 180, false, paint);
    }

}
