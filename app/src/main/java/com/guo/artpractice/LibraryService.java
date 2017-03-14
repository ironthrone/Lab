package com.guo.artpractice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;


import com.guo.rong.ILibrary;
import com.guo.rong.IOnNewBookArriveListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LibraryService extends Service {
    private static final String TAG = LibraryService.class.getSimpleName();

    public LibraryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return library.asBinder();
    }

    private ILibrary library = new ILibrary.Stub() {

        List<Book> books = new CopyOnWriteArrayList<>();

        {
            books.add(new Book("Guo", "Android", 34.0));
            books.add(new Book("Rong", "History", 44.0));
            books.add(new Book("Jin", "TongDian", 54.0));
        }

        //专门的注册监听器列表对象
        RemoteCallbackList<IOnNewBookArriveListener> remoteCallbackList = new RemoteCallbackList<>();

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

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book == null) return;
            if (!books.contains(book)) {

                books.add(book);
                //对RemoteCallbackList遍历和获取某个条目的时候必须这么用
                int N = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < N; i++) {
                    remoteCallbackList.getBroadcastItem(i).onNewBookArrive(book);
                }
                remoteCallbackList.finishBroadcast();
            }
        }

        @Override
        public void registerOnNewBookArriveListener(IOnNewBookArriveListener listener) throws RemoteException {
            boolean result = remoteCallbackList.register(listener);
            Log.d(TAG, result ? "register success" : "register fail");
        }

        @Override
        public void unregisterOnNewBookArriveListener(IOnNewBookArriveListener listener) throws RemoteException {
            boolean result = remoteCallbackList.unregister(listener);
            Log.d(TAG, result ? "unregister success" : "unregister fail");

        }

    };

}
