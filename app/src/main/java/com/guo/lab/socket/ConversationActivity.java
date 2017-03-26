package com.guo.lab.socket;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.guo.lab.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {


    private PrintWriter printWriter;
    private BufferedReader reader;
    private Socket socket;
    private TextView state;
    private EditText input;
    private TextView response;


    private static final int CONNECT_STATE = 100;
    private static final int RESPONSE = 200;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case CONNECT_STATE:
                    state.setText(msg.obj.toString());
                    break;
                case RESPONSE:
                    response.setText(msg.obj.toString());
                    LogUtils.d("received " + msg.obj.toString());
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Button send = (Button) findViewById(R.id.send);
        state = (TextView) findViewById(R.id.connect_state);
        response = (TextView) findViewById(R.id.response);
        input = (EditText) findViewById(R.id.input);
        send.setOnClickListener(this);

        startService(new Intent(this, ConversationService.class));

        Observable.just("").observeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        connect();
                    }
                });
    }

    private void connect() {
        while (socket == null) {
            try {

                socket = new Socket("localhost", 8099);
                Message.obtain(handler, CONNECT_STATE, "CONNECTING").sendToTarget();

                //设置autoFlush为true，这样缓存空间未满的时候数据也会输出
                printWriter = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.d("socket connect fail");
                SystemClock.sleep(1000);
                Message.obtain(handler, CONNECT_STATE, "CONNECT FAIL").sendToTarget();
            }
        }
        Message.obtain(handler, CONNECT_STATE, "CONNECTED").sendToTarget();

        try {
            while (!isFinishing()) {
                final String readStr = reader.readLine();
                if (readStr != null) {
                    Message.obtain(handler, RESPONSE, readStr).sendToTarget();
                }
            }
            reader.close();
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            try {
                socket.shutdownInput();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        String str = input.getText().toString().trim();
        if (TextUtils.isEmpty(str)) {
            return;
        }
        printWriter.println(str);
    }
}
