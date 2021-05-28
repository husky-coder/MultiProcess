package com.husky.multiprocess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.husky.multiprocess.bean.ParcelableMusic;
import com.husky.multiprocess.bean.ParcelableVideo;
import com.husky.multiprocess.service.ProcessService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private IMediaInterface mediaInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bindService:
                bindService();
                break;
            case R.id.unBindService:
                unBindService();
                break;
            case R.id.media:
                media();
                break;
            default:
        }
    }

    /**
     * 一般api
     */
    private void media() {
        Log.d(TAG, "media>>" + mediaInterface);
        if (mediaInterface != null) {
            try {
                mediaInterface.requestMusic();
                mediaInterface.requestVideo();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 绑定远程服务
     */
    private void bindService() {
        Intent intent = new Intent(this, ProcessService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑远程服务和反注册回调
     */
    private void unBindService() {
        // isBinderAlive() 判断 Binder 是否死亡
        if (mediaInterface != null && mediaInterface.asBinder().isBinderAlive()) {
            try {
                // 反注册回调
                mediaInterface.unregisterCallbackListener(iMediaCallback);
                mediaInterface = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        // 解绑
        unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected>>");
            mediaInterface = IMediaInterface.Stub.asInterface(iBinder);
            try {
                // 注册回调
                mediaInterface.registerCallbackListener(iMediaCallback);
                // 设置 Binder 死亡代理 DeathRecipient 对象
                mediaInterface.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected>>");
            // TODO
        }
    };

    private IMediaCallback.Stub iMediaCallback = new IMediaCallback.Stub() {
        @Override
        public void onMusic(ParcelableMusic music) throws RemoteException {
            Log.d(TAG, "onMusic>>" + music);

        }

        @Override
        public void onVideo(ParcelableVideo video) throws RemoteException {
            Log.d(TAG, "onVideo>>" + video);

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied>>");
            if (mediaInterface != null) {
                // Binder 死亡的情况下，解除该代理
                mediaInterface.asBinder().unlinkToDeath(this, 0);
                mediaInterface = null;
            }

            // TODO 重连服务或其他操作

        }
    };
}
