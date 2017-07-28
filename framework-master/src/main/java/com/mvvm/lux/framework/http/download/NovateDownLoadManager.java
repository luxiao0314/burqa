//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mvvm.lux.framework.http.download;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.mvvm.lux.framework.http.exception.Throwable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class NovateDownLoadManager {
    private DownLoadCallBack callBack;
    public static final String TAG = "Novate:DownLoadManager";
    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    private static String PNG_CONTENTTYPE = "image/png";
    private static String JPG_CONTENTTYPE = "image/jpg";
    private static String fileSuffix = "";
    private Handler handler;
    public static boolean isDownLoading = false;
    public static boolean isCancel = false;
    private static NovateDownLoadManager sInstance;

    public NovateDownLoadManager(DownLoadCallBack callBack) {
        this.callBack = callBack;
        this.handler = new Handler(Looper.getMainLooper());
    }

    public static synchronized NovateDownLoadManager getInstance(DownLoadCallBack callBack) {
        if (sInstance == null) {
            sInstance = new NovateDownLoadManager(callBack);
        }

        return sInstance;
    }

    public boolean writeResponseBodyToDisk(String path, String name, Context context, ResponseBody body) {
        Log.d("Novate:DownLoadManager", "contentType:>>>>" + body.contentType().toString());
        if (!TextUtils.isEmpty(name)) {
            String e = "";
            if (!name.contains(".")) {
                e = body.contentType().toString();
                if (e.equals(APK_CONTENTTYPE)) {
                    fileSuffix = ".apk";
                } else if (e.equals(PNG_CONTENTTYPE)) {
                    fileSuffix = ".png";
                } else if (e.equals(JPG_CONTENTTYPE)) {
                    fileSuffix = ".jpg";
                } else {
                    fileSuffix = body.contentType().subtype();
                }
                name = name + fileSuffix;
            }
        } else {
            name = System.currentTimeMillis() + fileSuffix;
        }

        if (path == null) {
            path = context.getExternalFilesDir((String) null) + File.separator + name;
        }

        Log.d("Novate:DownLoadManager", "path:-->" + path);

        try {
            File file = new File(path,name);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            InputStream inputStream = null;
            FileOutputStream outputStream = null;

            boolean fileSize;
            try {
                byte[] e1 = new byte[4096];
                final long fileTotalSize = body.contentLength();
                long fileSizeDownloaded = 0L;
                Log.d("Novate:DownLoadManager", "file length: " + fileTotalSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int finalName = inputStream.read(e1);
                    if (finalName == -1 || isCancel) {
                        outputStream.flush();
                        Log.d("Novate:DownLoadManager", "file downloaded: " + fileSizeDownloaded + " of " + fileTotalSize);
                        isDownLoading = false;
                        if (this.callBack != null) {
                            final String finalName2 = name;
                            final String finalPath = path;
                            this.handler.post(new Runnable() {
                                public void run() {
                                    NovateDownLoadManager.this.callBack.onSucess(finalPath, finalName2, fileTotalSize);
                                }
                            });
                            Log.d("Novate:DownLoadManager", "file downloaded: " + fileSizeDownloaded + " of " + fileTotalSize);
                            Log.d("Novate:DownLoadManager", "file downloaded: is sucess");
                        }

                        return true;
                    }

                    isDownLoading = true;
                    outputStream.write(e1, 0, finalName);
                    fileSizeDownloaded += (long) finalName;
                    Log.d("Novate:DownLoadManager", "file download: " + fileSizeDownloaded + " of " + fileTotalSize);
                    if (this.callBack != null && this.callBack != null) {
                        final long bytesWritten = fileSizeDownloaded;
                        this.handler.postDelayed(new Runnable() {
                            public void run() {
                                NovateDownLoadManager.this.callBack.onProgress(fileTotalSize,bytesWritten);
                            }
                        }, 200L);
                    }
                }
            } catch (IOException var20) {
                this.finalonError(var20);
                fileSize = false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

            }

            return fileSize;
        } catch (IOException var22) {
            this.finalonError(var22);
            return false;
        }
    }

    private void finalonError(final Exception e) {
        this.handler.post(new Runnable() {
            public void run() {
                NovateDownLoadManager.this.callBack.onError(new Throwable(e, 100));
            }
        });
//        if(this.callBack != null) {
//            if(Utils.checkMain()) {
//                this.callBack.onError(new Throwable(e, 100));
//            } else {
//                this.handler.post(new Runnable() {
//                    public void run() {
//                        NovateDownLoadManager.this.callBack.onError(new Throwable(e, 100));
//                    }
//                });
//            }
//
//        }
    }
}
