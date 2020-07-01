package com.xyz.scrollbar.weight;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.xyz.scrollbar.R;

import java.util.Locale;

/**
 * 可点击Item
 *
 * @author shenyonghui
 */
public class ClickItemView extends AppCompatTextView implements View.OnClickListener {


    private Class aClass;

    public ClickItemView(Context context) {
        this(context, null);
    }

    public ClickItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        setGravity(Gravity.CENTER);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.app_bg));
        setOnClickListener(this);
    }

    public void setData(String text, Class clazz) {
        if (text != null) {
            setText(text);
        }
        this.aClass = clazz;
    }

    @Override
    public void onClick(View v) {
        if (aClass != null) {
            Intent intent = new Intent(v.getContext(), aClass);
            if (getContext().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                v.getContext().startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), String.format(Locale.CANADA, "无法找到%s", aClass.getClass().getName()), Toast.LENGTH_SHORT);
            }

        }
    }
}
