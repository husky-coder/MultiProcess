// IMediaInterface.aidl
package com.husky.multiprocess;

// Declare any non-default types here with import statements
import com.husky.multiprocess.IMediaCallback;

interface IMediaInterface {

    void requestMusic();

    void requestVideo();

    void registerCallbackListener(IMediaCallback iMediaCallback);

    void unregisterCallbackListener(IMediaCallback iMediaCallback);
}
