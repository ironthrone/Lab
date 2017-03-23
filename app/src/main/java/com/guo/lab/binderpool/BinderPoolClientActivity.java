package com.guo.lab.binderpool;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.utils.ToastUtils;
import com.guo.lab.R;
import com.guo.lab.library.Book;
import com.guo.rong.ILibrary;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BinderPoolClientActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BinderPoolClientActivity.class.getSimpleName();
    private ILibrary library;
    private ExecutorService singleExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool_client);

        singleExecutor = Executors.newSingleThreadExecutor();
        singleExecutor.submit(new Runnable() {
            @Override
            public void run() {
                library = ILibrary.Stub.asInterface(BinderPool.with(BinderPoolClientActivity.this).getBinder(BinderPool.TYPE_LIBRARY));

            }
        });
        findViewById(R.id.all_book)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_book:
                singleExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (library != null) {

                                List<Book> bookList = library.getBookList();
                                Log.d(TAG, bookList.toString());
                            } else {
                                ToastUtils.showShortToastSafe("Please hold on");
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BinderPool.with(this).clear();
        singleExecutor.shutdown();
    }
}
