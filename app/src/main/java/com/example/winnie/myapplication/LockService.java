package com.example.winnie.myapplication;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Winnie on 4/9/2016.
 */
public class LockService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter mScreenOnFilter = new IntentFilter();
        mScreenOnFilter.addAction("android.intent.action.SCREEN_ON");
        mScreenOnFilter.addAction("android.intent.action.SCREEN_OFF");

        LockService.this.registerReceiver(mScreenOnReceiver, mScreenOnFilter);
    }
    private KeyguardManager mKeyguardManager;
    private KeyguardManager.KeyguardLock mKeyguardLock;

    private BroadcastReceiver mScreenOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {

            if (intent.getAction().equals("android.intent.action.SCREEN_ON") || intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                mKeyguardLock = mKeyguardManager.newKeyguardLock("unlock");
                mKeyguardLock.disableKeyguard();
                Intent intent1= new Intent(LockService.this, ShowLock.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }

        }
    };

    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mScreenOnReceiver);
        mKeyguardLock.reenableKeyguard();
        // 在此重新启动,使服务常驻内存
        startService(new Intent(this, LockService.class));
    }

}