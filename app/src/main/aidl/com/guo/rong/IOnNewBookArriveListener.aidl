// IOnNewBookArriveListener.aidl
package com.guo.rong;

import com.guo.artpractice.library.Book;

interface IOnNewBookArriveListener {
    void onNewBookArrive(in Book book);
}
