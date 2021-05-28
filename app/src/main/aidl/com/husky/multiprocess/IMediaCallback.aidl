// IMediaCallback.aidl
package com.husky.multiprocess;

// Declare any non-default types here with import statements
import com.husky.multiprocess.bean.ParcelableMusic;
import com.husky.multiprocess.bean.ParcelableVideo;

interface IMediaCallback {

    void onMusic(in ParcelableMusic music);

    void onVideo(in ParcelableVideo video);
}
