package com.guo.lab.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.guo.lab.R;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by Administrator on 2017/3/17.
 */

public class ListBindingViewModel {
    public ItemView itemView = ItemView.of(com.guo.lab.BR.viewModel, R.layout.item_text);
    public ObservableList<TextViewModel> itemViewModels = new ObservableArrayList<>();

    public ListBindingViewModel() {
        itemViewModels.add(new TextViewModel("武松"));
        itemViewModels.add(new TextViewModel("鲁达"));
    }
}
