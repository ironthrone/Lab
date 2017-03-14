// ILibrary.aidl
package com.guo.rong;

import com.guo.artpractice.Book;
// Declare any non-default types here with import statements

interface ILibrary {
    List<Book> getBookList();
    Book searchBook(in String book);
}
