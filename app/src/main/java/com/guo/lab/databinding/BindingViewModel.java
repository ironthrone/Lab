package com.guo.lab.databinding;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.guo.lab.R;


/**
 * Created by Administrator on 2017/3/15.
 *
 * This model has data and event handler
 */

public class BindingViewModel {
    private static final String TAG = BindingViewModel.class.getSimpleName();

    //control visibility, show how data changes ui by observable pattern, in other words, how ui observe data
    public ObservableInt androidVisibility;
    public ObservableField<String> observableWord = new ObservableField<>("杏子林中");
    public ObservableField<BookNormal> observableBookField = new ObservableField<>(new BookNormal("天龙八部", 32));

    public ObservableField<String> upload = new ObservableField<>();
    public ObservableArrayMap<String, String> observableArrayMap = new ObservableArrayMap<>();


    public BookObservable observableBook = new BookObservable("笑傲江湖", 32);

    public BindingViewModel() {
        androidVisibility = new ObservableInt(View.VISIBLE);
        observableArrayMap.put("name", "上官金虹");
        observableArrayMap.put("weapon", "龙凤环");

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggleVisible:
                androidVisibility.set(androidVisibility.get() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                break;
            case R.id.custom_observable_object:
                //change a field of observed object
                observableBook.setName("多情剑客无情剑");
                // change value of a observed field
                observableBookField.set(new BookNormal("三少爷的剑",33));
                break;
            case R.id.get_edit_text:

                break;
            case R.id.observable_map:
                observableArrayMap.setValueAt(observableArrayMap.indexOfKey("name"), "吕凤先");
                observableArrayMap.setValueAt(observableArrayMap.indexOfKey("weapon"), "银戟");
                break;
        }
    }
}
