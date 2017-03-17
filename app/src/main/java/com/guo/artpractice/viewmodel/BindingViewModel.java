package com.guo.artpractice.viewmodel;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.guo.artpractice.R;
import com.guo.artpractice.model.Book;
import com.guo.artpractice.model.BookNormal;

/**
 * Created by Administrator on 2017/3/15.
 */

public class BindingViewModel {
    public ObservableInt androidVisibility;
    public Book tianlong = new Book("天龙八部", 32);
    public ObservableField<String> observableWord = new ObservableField<>("杏子林中");
    public ObservableField<BookNormal> observableBook = new ObservableField<>(new BookNormal("天龙八部", 32));
    public ObservableArrayMap<String, String> observableArrayMap = new ObservableArrayMap<>();

    public BindingViewModel() {
        androidVisibility = new ObservableInt(View.VISIBLE);
        observableArrayMap.put("author", "金庸");
        observableArrayMap.put("position", "临江月");
    }


    public void toggleVisible(View view) {
        switch (view.getId()) {
            case R.id.toggleVisible:
        androidVisibility.set(androidVisibility.get() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        observableWord.set("虽万千人吾往矣");
                break;
            case R.id.changeAuthor:
//                tianlong.setAuthor("jinyong");
                observableBook.get().author = "悄立雁门，绝壁无余字";
                break;
        }
    }
}
