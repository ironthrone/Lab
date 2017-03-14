package com.guo.artpractice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.guo.art.ILibrary;

import java.util.Arrays;
import java.util.List;

public class LibraryService extends Service {
    public LibraryService() {
    }

    private ILibrary library = new ILibrary.Stub() {

        String[] books = {"android","algebra","calculus"};
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<String> getBookList() throws RemoteException {
            return Arrays.asList(books);
        }


        @Override
        public boolean searchBook(String name) throws RemoteException {
            for (String book : books) {
                if (name.equals(book)) {
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return library.asBinder();
    }
}
