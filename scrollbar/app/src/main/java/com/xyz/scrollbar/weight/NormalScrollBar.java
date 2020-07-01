package com.xyz.scrollbar.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xyz.scrollbar.R;
import com.xyz.scrollbar.weight.bar.ScrollBar;

/**
 * 没经过封装修改的ScrollBar
 * 如果需要二次修改 建议参考该类
 * <p>
 * created by shenyonghui on 2020/5/20
 */
public class NormalScrollBar extends View {

    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2;

    public interface OnScrollListener {
        void onScrollStateChanged(ScrollBar scrollBar, int newState);
    }

    private Paint paint;
    private int trackColor;
    private int thumbColor;
    private int thumbWidth = -1;
    private RectF container;
    private float percentage = 0f;

    private OnScrollListener onScrollListener;
    private Object targetViewScrollListener;

    public NormalScrollBar(Context context) {
        this(context, null);
    }

    public NormalScrollBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalScrollBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollBar, 0, 0);
        thumbColor = ta.getColor(R.styleable.ScrollBar_thumb_color, 0xFFA0DA3A);
        trackColor = ta.getColor(R.styleable.ScrollBar_track_color, 0xFFEDEDED);
        ta.recycle();
        paint = new Paint();
        container = new RectF();
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
        container.set(getPaddingLeft(), getPaddingTop(), widthSize - getPaddingRight(), heightSize - getPaddingBottom());
        if (thumbWidth <= 0) {
            thumbWidth = (int) (container.right - container.left) / 3;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
        drawThumb(canvas);
    }

    /**
     * 绘制轨道
     */
    private void drawTrack(Canvas canvas) {
        drawLine(canvas, container, trackColor);
    }

    /**
     * 绘制进度
     */
    private void drawThumb(Canvas canvas) {
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

    /**
     * 设置滑动百分比
     */
    public void setCurrentPercentage(float percentage) {
        if (percentage < 0) {
            this.percentage = 0f;
        } else if (percentage > 1) {
            this.percentage = 1f;
        } else {
            this.percentage = percentage;
        }
        invalidate();
    }

    /**
     * 设置滑动块相对总体宽度
     */
    public void setThumbWidthPercentage(float percentage) {
        if (container != null) {
            this.thumbWidth = (int) ((container.right - container.left) * percentage);
            invalidate();
        }
    }
}
