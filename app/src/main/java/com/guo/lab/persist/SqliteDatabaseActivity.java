package com.guo.lab.persist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.guo.lab.R;

import java.util.Random;

public class SqliteDatabaseActivity extends AppCompatActivity {

    private static final String TAG = SqliteDatabaseActivity.class.getSimpleName();
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);
        dbHelper = new DBHelper(this);

    }


    public void click(View view) {
        switch (view.getId()) {
            case R.id.add:
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        Random random = new Random();
                        for (int i = 0; i < 10000; i++) {
                            String name = "guo" + random.nextInt(Integer.MAX_VALUE);
                            dbHelper.getWritableDatabase().execSQL("insert into people ( 'name','age') values ('"
                                    + name + "','" + random.nextInt(100) + "');");
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        Toast.makeText(SqliteDatabaseActivity.this, "inserted 10000 at " + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                    }
                }
                        .execute();
                break;
        }
    }
}
