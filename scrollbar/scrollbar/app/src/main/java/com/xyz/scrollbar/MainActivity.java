package com.xyz.scrollbar;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.xyz.scrollbar.weight.ClickItemView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shenyonghui
 */
public class MainActivity extends AppCompatActivity {

    private static Map<String, Class> map;

    static {
        map = new ArrayMap<>();
        map.put("横向列表滑动条", RecycleViewHorizontalBarActivity.class);
        map.put("纵向列表滑动条", RecycleViewVerticalBarActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout container = new LinearLayout(this);
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.VERTICAL);
        setContentView(container);
        for (Map.Entry<String, Class> entry : map.entrySet()) {
            String title = entry.getKey();
            Class clazz = entry.getValue();
            ClickItemView itemView = new ClickItemView(this);
            itemView.setData(title, clazz);
            itemView.requestLayout();
            container.addView(itemView);
        }
    }

}
