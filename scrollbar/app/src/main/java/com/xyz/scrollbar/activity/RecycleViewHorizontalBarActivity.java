package com.xyz.scrollbar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xyz.scrollbar.R;
import com.xyz.scrollbar.weight.Adapter;
import com.xyz.scrollbar.weight.bar.ScrollBar;

/**
 * 横向进度条
 * @author shenyonghui
 */
public class RecycleViewHorizontalBarActivity extends Activity {
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_horiontal_scrollbar);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        ScrollBar scrollBar = findViewById(R.id.scroll_bar);
        adapter = new Adapter();

        recyclerView.setAdapter(adapter);
        scrollBar.setTargetView(recyclerView);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData();
//                scrollBar.requestLayout();
            }
        });
    }

}
