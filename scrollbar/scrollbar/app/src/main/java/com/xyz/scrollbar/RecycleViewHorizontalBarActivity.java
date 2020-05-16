package com.xyz.scrollbar;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xyz.scrollbar.weight.Adapter;
import com.xyz.scrollbar.weight.bar.ScrollBar;

/**
 * @author shenyonghui
 */
public class RecycleViewHorizontalBarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_horiontal_scrollbar);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        ScrollBar scrollBar = findViewById(R.id.scroll_bar);
        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        scrollBar.setTargetView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
