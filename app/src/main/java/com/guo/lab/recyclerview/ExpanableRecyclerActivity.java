package com.guo.lab.recyclerview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.guo.lab.R;
import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ExpanableRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanable_recycler);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        List<Level0Item> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Level0Item level0Item = new Level0Item("leader");
            for (int j = 0; j < 5; j++) {
                level0Item.addSubItem(new Person("soldier"));
            }
            data.add(level0Item);
        }

        HorizontalDividerItemDecoration dividerItemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .sizeProvider(new FlexibleDividerDecoration.SizeProvider() {
                    @Override
                    public int dividerSize(int position, RecyclerView parent) {
                        int type = parent.getAdapter().getItemViewType(position);
                        int result = 0;
                        switch (type) {
                            case 0:
                                result = SizeUtils.dp2px(10);
                                break;
                            case 1:
                                result = 1;
                                break;
                        }
                        return result;
                    }
                })

                .marginProvider(new HorizontalDividerItemDecoration.MarginProvider() {
                    private int symmetryMargin(int position, RecyclerView parent) {
                        int type = parent.getAdapter().getItemViewType(position);
                        int result = 0;
                        switch (type) {
                            case 0:
                                result = 0;
                                break;
                            case 1:
                                result = 30;
                                break;
                        }

                        return result;
                    }

                    @Override
                    public int dividerLeftMargin(int position, RecyclerView parent) {
                        return symmetryMargin(position, parent);
                    }

                    @Override
                    public int dividerRightMargin(int position, RecyclerView parent) {
                        return symmetryMargin(position, parent);
                    }
                }).color(Color.GRAY)
                .build();

//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.addItemDecoration(new AnItemDecoration());

        RecyclerView.Adapter adapter = new ExpandableItemAdapter(data);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
