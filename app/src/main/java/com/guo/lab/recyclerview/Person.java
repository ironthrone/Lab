package com.guo.lab.recyclerview;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by ironthrone on 2017/6/9 0009.
 */

public class Person implements MultiItemEntity{
    public String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
