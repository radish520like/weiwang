package com.hhsj.ilive.oss_library;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.util.HashMap;

/**
 * Created by mOss on 2015/12/7 0007.
 * 支持普通上传，普通下载
 */
public class OssService {

    public OSS mOss;
    private String mBucket;
    private UIDisplayer mDisplayer;
    private String mCallbackAddress;

    private OnUploadListener mOnUploadListener;

    public OssService(OSS oss, String bucket, UIDisplayer uiDisplayer) {
        this.mOss = oss;
        this.mBucket = bucket;
        this.mDisplayer = uiDisplayer;
    }

    public OssService(Context context) {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(OSSConfig.OSS_ACCESS_KEY_ID,OSSConfig.OSS_ACCESS_KEY_SECRET,"");
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        this.mOss = new OSSClient(context.getApplicationContext(), OSSConfig.OSS_ENDPOINT, credentialProvider, conf);
        this.mBucket = OSSConfig.BUCKET_NAME;
    }

    public void initOss(OSS _oss) {
        this.mOss = _oss;
    }

    public void setCallbackAddress(String callbackAddress) {
        this.mCallbackAddress = callbackAddress;
    }

    public void asyncPutImage(String object, String localFile) {
        final long upload_start = System.currentTimeMillis();
        OSSLog.logDebug("upload start");

        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }

        // 构造上传请求
        OSSLog.logDebug("create PutObjectRequest ");
        PutObjectRequest put = new PutObjectRequest(mBucket, object, localFile);
        put.setCRC64(OSSRequest.CRC64Config.YES);
        if (mCallbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", mCallbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "filename=${object}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                System.out.println("abc : progress " + request + " -- " + currentSize + " --- " + totalSize);
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
                if(mOnUploadListener != null){
                    mOnUploadListener.onProgress(progress);
                }
//                mDisplayer.updateProgress(progress);
//                mDisplayer.displayInfo("上传进度: " + String.valueOf(progress) + "%");
            }
        });

        OSSLog.logDebug(" asyncPutObject ");
        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                System.out.println("abc : success = " + "Bucket: " + mBucket
                        + "\nObject: " + request.getObjectKey()
                        + "\nETag: " + result.getETag()
                        + "\nRequestId: " + result.getRequestId()
                        + "\nCallback: " + result.getServerCallbackReturnBody());


                if(mOnUploadListener != null){
                    mOnUploadListener.onSuccess(mOss.presignPublicObjectURL(mBucket, request.getObjectKey()));
                }

                Log.d("PutObject", "UploadSuccess");

                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

                long upload_end = System.currentTimeMillis();
                OSSLog.logDebug("upload cost: " + (upload_end - upload_start) / 1000f);
//                mDisplayer.uploadComplete();
//                mDisplayer.displayInfo("Bucket: " + mBucket
//                        + "\nObject: " + request.getObjectKey()
//                        + "\nETag: " + result.getETag()
//                        + "\nRequestId: " + result.getRequestId()
//                        + "\nCallback: " + result.getServerCallbackReturnBody());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }
                if(mOnUploadListener != null){
                    mOnUploadListener.onFailure();
                }
//                mDisplayer.uploadFail(info);
//                mDisplayer.displayInfo(info);
            }
        });
    }

    public interface OnUploadListener{
        void onSuccess(String url);
        void onFailure();
        void onProgress(int percent);
    }

    public void setOnUploadListener(OnUploadListener listener){
        this.mOnUploadListener = listener;
    }
}
