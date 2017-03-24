package com.guo.lab.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.blankj.utilcode.utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ConversationService extends Service {
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private boolean serverDestroy = false;

    public ConversationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newCachedThreadPool();
        new Thread(new Runnable() {
            @Override
            public void run() {
                listen();

            }
        });
    }

    private void listen() {

        try {
            serverSocket = new ServerSocket(8099);
            LogUtils.d("server open success");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d("server open fail");
        }

        while (!serverDestroy) {
            Socket comeScoket = null;
            try {
                comeScoket = serverSocket.accept();
                executorService.execute(new ProcessRequest(comeScoket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private  class ProcessRequest implements Runnable {

        Socket socket;

        private ProcessRequest(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                boolean close = false;
                while (!close&& !serverDestroy) {

                    String request = null;
                    request = bufferedReader.readLine();
                    if (request.equals("stop")) {
                        socket.close();
                        close = true;
                    }
                    printWriter.write(request);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serverDestroy = true;
        executorService.shutdownNow();
    }
}
