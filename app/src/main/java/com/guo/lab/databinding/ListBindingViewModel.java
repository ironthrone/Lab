package com.guo.lab.databinding;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;


/**
 * Created by Administrator on 2017/3/17.
 */

public class ListBindingViewModel {
//    public ItemBinding<TextViewModel> itemView = ItemBinding.of(BR.itemViewModel, R.layout.item_text);
    public ObservableList<TextViewModel> itemViewModels = new ObservableArrayList<>();

    public ListBindingViewModel() {
        itemViewModels.add(new TextViewModel("武松"));
        itemViewModels.add(new TextViewModel("鲁达"));
    }
}
