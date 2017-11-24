package com.guo.lab.storage;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

/**
 * Created by ironthrone on 2017/10/9 0009.
 */

public class CleanCacheAccessibilityService extends AccessibilityService {

    private static final String CACHED_DATA = "Cached data";
    private static final String OK = "OK";
    private static final String APPS = "Apps";

    public static final String CLEAN_CACHE_ACTION = "CLEAN_CACHE";

    private static final int START = 100;
    private static final int CHECK_ROOT_NODE = 101;
    private static final int CLICK_CLEAN = 102;
    private static final int CLICK_OK = 103;
    private static final int FINISH = 104;
    private static final int FINISH_WITH_TWO_BACK = 105;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    Intent intentt = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                    intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentt);
                    sendEmptyMessageDelayed(CHECK_ROOT_NODE, 1050);

                    break;
                case CHECK_ROOT_NODE:

                    AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                    if (rootNode != null) {
                        sendEmptyMessage(CLICK_CLEAN);
                    } else {
                        LogUtils.d("do not find root node");
                        sendEmptyMessage(FINISH);
                    }
                    break;
                case CLICK_CLEAN:

                    List<AccessibilityNodeInfo> cacheNodeList = getNodeInfo(CACHED_DATA);
                    if (!notEmpty(cacheNodeList)) {
                        List<AccessibilityNodeInfo> appsNodeList = getRootInActiveWindow().findAccessibilityNodeInfosByText(APPS);
                        if (notEmpty(appsNodeList)) {
                            AccessibilityNodeInfo recyclerNode = appsNodeList.get(0).getParent().getParent();
                            if (recyclerNode != null) {
                                while (recyclerNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                                    cacheNodeList = getNodeInfo(CACHED_DATA);
                                    if (!notEmpty(cacheNodeList)) {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (notEmpty(cacheNodeList)) {
                        AccessibilityNodeInfo cacheNode = cacheNodeList.get(0);
                        boolean clickResult = cacheNode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        if (clickResult) {
                            sendEmptyMessageDelayed(CLICK_OK, 300);
                        } else {
                            sendEmptyMessageDelayed(FINISH, 100);
                            LogUtils.d("cache click fail");
                        }
                    } else {
                        LogUtils.d("do not find cache view");
                        sendEmptyMessageDelayed(FINISH, 100);
                    }
                    break;
                case CLICK_OK:
                    List<AccessibilityNodeInfo> okNodeList = getNodeInfo(OK);
                    if (notEmpty(okNodeList)) {
                        AccessibilityNodeInfo nodeInfo = okNodeList.get(0);
                        boolean clickResult = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        LogUtils.d(clickResult ? "ok click success" : "ok click fail");
                        if (clickResult) {
                            sendEmptyMessageDelayed(FINISH, 100);
                        } else {
                            sendEmptyMessageDelayed(FINISH_WITH_TWO_BACK, 100);
                        }
                    } else {
                        sendEmptyMessageDelayed(FINISH_WITH_TWO_BACK, 100);
                    }

                    break;
                case FINISH:
                    performGlobalAction(GLOBAL_ACTION_BACK);
                    break;
                case FINISH_WITH_TWO_BACK:
                    performGlobalAction(GLOBAL_ACTION_BACK);
                    performGlobalAction(GLOBAL_ACTION_BACK);
                    break;
            }
        }
    };


    private List<AccessibilityNodeInfo> getNodeInfo(String text) {
        if (getRootInActiveWindow() == null) {
            LogUtils.d("root is null");
            return null;
        }
        return getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
    }

    private static <E> boolean notEmpty(List<E> list) {
        return list != null && list.size() > 0;
    }


    private BroadcastReceiver cleanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handler.sendEmptyMessageDelayed(START, 1000);

//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    List<AccessibilityNodeInfo> nodeInfos = getRootInActiveWindow().findAccessibilityNodeInfosByText("TOAST");
////                    nodeInfos.get(0).getParent().performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                    nodeInfos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                }
//            }, 1000);
        }
    };

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        registerReceiver(cleanReceiver, new IntentFilter(CLEAN_CACHE_ACTION));
        LogUtils.d("clean cache accessibility connect");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cleanReceiver);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {

    }
}
