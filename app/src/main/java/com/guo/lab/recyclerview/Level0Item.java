package com.guo.lab.recyclerview;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by ironthrone on 2017/6/9 0009.
 */

public class Level0Item extends AbstractExpandableItem<Person> implements MultiItemEntity {
    public String title;

    public Level0Item(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
