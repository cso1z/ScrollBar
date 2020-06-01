package com.xyz.scrollbar.weight.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xyz.scrollbar.R;


/**
 * @author shenyonghui
 */
public class ScrollBar extends View {

    /**
     * 横向滚动
     */
    public static final int HORIZONTAL = 1;

    /**
     * 纵向滚动
     */
    public static final int VERTICAL = 2;


    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2;

    public interface OnScrollListener {
        /**
         * 滑动状态改变时回调
         *
         * @param scrollBar 监听的对象
         * @param newState  滑动状态
         */
        void onScrollStateChanged(ScrollBar scrollBar, int newState);
    }

    private int thumbWidth = -1;

    private BaseScrollBar baseScrollBar;

    /**
     * 默认滚动方式为{@link #HORIZONTAL}
     */
    private int scrollType = HORIZONTAL;

    /**
     * 轨道
     */
    private RectF container;
    private float percentage = 0f;

    private OnScrollListener onScrollListener;
    private Object targetViewScrollListener;

    public ScrollBar(Context context) {
        this(context, null);
    }

    public ScrollBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int trackColor = 0xFFA0DA3A;
        int thumbColor = 0xFFEDEDED;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollBar, 0, 0);
            thumbColor = ta.getColor(R.styleable.ScrollBar_thumb_color, 0xFFA0DA3A);
            trackColor = ta.getColor(R.styleable.ScrollBar_track_color, 0xFFEDEDED);
            scrollType = ta.getInt(R.styleable.ScrollBar_scroll_type, HORIZONTAL);
            ta.recycle();
        }
        if (scrollType == HORIZONTAL) {
            baseScrollBar = new HorizontalScroll();
        } else {
            baseScrollBar = new VerticalScroll();
        }
        baseScrollBar.setThumbColor(thumbColor);
        baseScrollBar.setTrackColor(trackColor);
        container = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
        container.set(getPaddingLeft(), getPaddingTop(), widthSize - getPaddingRight(), heightSize - getPaddingBottom());
        if (thumbWidth == -1) {
            if (scrollType == HORIZONTAL) {
                thumbWidth = (int) (container.right - container.left) / 3;
            } else {
                thumbWidth = (int) (container.bottom - container.top) / 3;
            }
        }
        baseScrollBar.setThumbWidth(thumbWidth);
        Log.e("xx", " thumbWidth:" + thumbWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        baseScrollBar.drawTrack(canvas, container);
        baseScrollBar.drawThumb(canvas, percentage);
    }

    /**
     * 设置当前滑动进度
     *
     * @param percentage 滑动进度 百分比
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
     * 设置显示区域宽度
     *
     * @param percentage 显示区域 与 全部内容区域 比
     */
    public void setThumbWidthPercentage(float percentage) {
        if (percentage == 1) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
        if (container != null) {
            int thumbWidth = (int) ((container.right - container.left) * percentage);
            if (thumbWidth != this.thumbWidth) {
                this.thumbWidth = thumbWidth;
                requestLayout();
                Log.e("xx", "percentage:" + percentage + " thumbWidth:" + thumbWidth);
            }
        }
    }


    public void setThumbHeightPercentage(float percentage) {
        if (percentage == 1) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
        if (container != null) {
            int thumbWidth = (int) ((container.bottom - container.top) * percentage);
            if (thumbWidth != this.thumbWidth) {
                this.thumbWidth = thumbWidth;
                requestLayout();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }


    /**
     * 设置滑动监听
     *
     * @param onScrollListener 滑动监听
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setTargetView(RecyclerView recyclerView) {
        if (recyclerView != null) {
            if (targetViewScrollListener instanceof RecyclerView.OnScrollListener) {
                recyclerView.removeOnScrollListener((RecyclerView.OnScrollListener) targetViewScrollListener);
            } else {
                targetViewScrollListener = new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        if (onScrollListener != null) {
                            onScrollListener.onScrollStateChanged(ScrollBar.this, newState);
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int extent;
                        int range;
                        int offset;
                        if (recyclerView.getLayoutManager().canScrollHorizontally()) {
                            extent = recyclerView.computeHorizontalScrollExtent();
                            range = recyclerView.computeHorizontalScrollRange();
                            offset = recyclerView.computeHorizontalScrollOffset();
                            setThumbWidthPercentage((float) extent / (float) range);
                        } else {
                            extent = recyclerView.computeVerticalScrollExtent();
                            range = recyclerView.computeVerticalScrollRange();
                            offset = recyclerView.computeVerticalScrollOffset();
                            setThumbHeightPercentage((float) extent / (float) range);
                            Log.e("xx", "extent:" + extent + " range:" + range);
                        }
                        setCurrentPercentage((float) offset / (float) (range - extent));
                    }
                };
            }
            recyclerView.addOnScrollListener((RecyclerView.OnScrollListener) targetViewScrollListener);
        }
    }

    public void setTargetView(final ViewPager viewPager) {
        if (viewPager != null) {
            if (targetViewScrollListener instanceof ViewPager.OnPageChangeListener) {
                viewPager.removeOnPageChangeListener((ViewPager.OnPageChangeListener) targetViewScrollListener);
            }
            targetViewScrollListener = new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    PagerAdapter adapter = viewPager.getAdapter();
                    if (adapter != null) {
                        setThumbWidthPercentage(1f / (float) adapter.getCount());
                        setCurrentPercentage((position + positionOffset) / ((float) adapter.getCount() - 1));
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };
            viewPager.addOnPageChangeListener((ViewPager.OnPageChangeListener) targetViewScrollListener);
        }
    }

}
