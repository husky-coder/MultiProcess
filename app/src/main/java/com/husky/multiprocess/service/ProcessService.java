package com.husky.multiprocess.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.husky.multiprocess.IMediaCallback;
import com.husky.multiprocess.IMediaInterface;
import com.husky.multiprocess.bean.ParcelableMusic;
import com.husky.multiprocess.bean.ParcelableVideo;

public class ProcessService extends Service {

    private static final String TAG = "ProcessService";

    // 系统提供的专门用于保存、删除跨进程 listener 的类，线程安全
    private RemoteCallbackList<IMediaCallback> iMediaCallbacks = new RemoteCallbackList<>();

    private IBinder binder = new IMediaInterface.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            /**
             * 权限校验方式一
             * 在onTransact中检验
             * com.husky.multiprocess.permission.REMOTE_SERVICE_PERMISSION 远程服务权限字符串
             */
//            if (checkCallingOrSelfPermission("com.husky.multiprocess.permission.REMOTE_SERVICE_PERMISSION")
//                    == PackageManager.PERMISSION_DENIED) {
//                Log.d(TAG, "onTransact>>权限校验失败！");
//                return false;
//            }
//
//            /**
//             * 包名校验
//             */
//            String packageName = null;
//            // 获取客户端包名
//            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
//            if (packages != null && packages.length > 0) {
//                packageName = packages[0];
//            }
//            // 校验包名
//            if (packageName == null || !packageName.startsWith("com.husky.multiprocess")) {
//                Log.d(TAG, "onTransact>>权限校验失败：" + packageName);
//                return false;
//            }
//            Log.d(TAG, "onTransact>>权限校验成功：" + packageName);

            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public void requestMusic() throws RemoteException {
            Log.d(TAG, "requestMusic>>");
            callbackMusic();
        }

        @Override
        public void requestVideo() throws RemoteException {
            Log.d(TAG, "requestVideo>>");
            callbackVideo();
        }

        @Override
        public void registerCallbackListener(IMediaCallback iMediaCallback) throws RemoteException {
            Log.d(TAG, "registerCallbackListener>>");
            iMediaCallbacks.register(iMediaCallback);
        }

        @Override
        public void unregisterCallbackListener(IMediaCallback iMediaCallback) throws RemoteException {
            Log.d(TAG, "unregisterCallbackListener>>");
            iMediaCallbacks.unregister(iMediaCallback);
        }
    };

    private void callbackMusic() {
        // beginBroadcast和finishBroadcast一定要配对使用
        final int counts = iMediaCallbacks.beginBroadcast();
        for (int i = 0; i < counts; i++) {
            IMediaCallback iMediaCallback = iMediaCallbacks.getBroadcastItem(i);
            if (iMediaCallback != null) {
                try {
                    ParcelableMusic music = new ParcelableMusic();
                    music.setName("musicName");
                    music.setDuration(3523532L);
                    iMediaCallback.onMusic(music);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        iMediaCallbacks.finishBroadcast();
    }

    private void callbackVideo() {
        // beginBroadcast和finishBroadcast一定要配对使用
        final int counts = iMediaCallbacks.beginBroadcast();
        for (int i = 0; i < counts; i++) {
            IMediaCallback iMediaCallback = iMediaCallbacks.getBroadcastItem(i);
            if (iMediaCallback != null) {
                try {
                    ParcelableVideo video = new ParcelableVideo();
                    video.setName("videoName");
                    video.setDuration(3526235L);
                    video.setWidth(720);
                    video.setHeight(1280);
                    iMediaCallback.onVideo(video);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        iMediaCallbacks.finishBroadcast();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        /**
         * 权限校验方式二
         * 在onBind中检验
         * com.husky.multiprocess.permission.REMOTE_SERVICE_PERMISSION 远程服务权限字符串
         */
        if (checkCallingOrSelfPermission("com.husky.multiprocess.permission.REMOTE_SERVICE_PERMISSION")
                == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "onTransact>>权限校验失败！");
            return null;
        }
        Log.d(TAG, "onTransact>>权限校验成功！");

        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate>>");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand>>");
        return super.onStartCommand(intent, flags, startId);
    }
}
