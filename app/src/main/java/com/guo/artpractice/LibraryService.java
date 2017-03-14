package com.guo.artpractice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;


import com.guo.rong.ILibrary;

import java.util.ArrayList;
import java.util.List;

public class LibraryService extends Service {
    public LibraryService() {
    }

    private ILibrary library = new ILibrary.Stub() {

        List<Book> books = new ArrayList<>();

        {
            books.add(new Book("Guo", "Android", 34.0));
            books.add(new Book("Rong", "History", 44.0));
            books.add(new Book("Jin", "TongDian", 54.0));
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return books;
        }

        @Override
        public Book searchBook(String book) throws RemoteException {
            for (Book boo :
                    books) {
                if (boo.getName().equals(book)) {
                    return boo;
                }
            }
            return null;
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        return library.asBinder();
    }
}
