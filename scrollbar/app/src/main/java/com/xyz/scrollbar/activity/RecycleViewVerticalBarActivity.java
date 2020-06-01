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
 * @author shenyonghui
 */
public class RecycleViewVerticalBarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vertical_scrollbar);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        final ScrollBar scrollBar = findViewById(R.id.scroll_bar);
        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        scrollBar.setTargetView(recyclerView);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData();
                scrollBar.requestLayout();
            }
        });
    }

}
