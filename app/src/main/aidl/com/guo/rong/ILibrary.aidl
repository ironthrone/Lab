// ILibrary.aidl
package com.guo.rong;

import com.guo.artpractice.library.Book;
import com.guo.rong.IOnNewBookArriveListener;

interface ILibrary {
    List<Book> getBookList();
    Book searchBook(in String book);
    void addBook(in Book book);
    void registerOnNewBookArriveListener(in IOnNewBookArriveListener listener);
    void unregisterOnNewBookArriveListener(in IOnNewBookArriveListener listener);
}
