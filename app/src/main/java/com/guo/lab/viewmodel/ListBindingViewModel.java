package com.guo.lab.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.guo.lab.R;
import com.guo.lab.BR;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by Administrator on 2017/3/17.
 */

public class ListBindingViewModel {
    public ItemBinding<TextViewModel> itemView = ItemBinding.of(BR.itemViewModel, R.layout.item_text);
    public ObservableList<TextViewModel> itemViewModels = new ObservableArrayList<>();

    public ListBindingViewModel() {
        itemViewModels.add(new TextViewModel("武松"));
        itemViewModels.add(new TextViewModel("鲁达"));
    }
}
