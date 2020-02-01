package com.siddarthglobalschool.Util;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class RequestUserPermission {

    private Activity activity;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET

    };

    public RequestUserPermission(Activity activity) {
        this.activity = activity;
    }
    public void verifyStoragePermissions() {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

}
