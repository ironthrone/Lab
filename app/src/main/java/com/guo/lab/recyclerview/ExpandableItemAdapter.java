package com.guo.lab.recyclerview;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.guo.lab.R;

import java.util.List;

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public ExpandableItemAdapter(List data) {
        super(data);
        addItemType(0, R.layout.level_0);
        addItemType(1, R.layout.level_1);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case 0:

                final Level0Item level0Item = (Level0Item) item;
                holder.setText(R.id.title, level0Item.title);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (level0Item.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case 1:
                holder.setText(R.id.text, ((Person) item).name);

                break;
        }
    }
}