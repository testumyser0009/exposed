package com.wz.xposedalipay.network.fileprogress;

import okhttp3.RequestBody;

public interface UploadCallbacks {
        void onProgressUpdate(int percentage);

        void onError(RequestBody errBody);

        void onFinish();
    }