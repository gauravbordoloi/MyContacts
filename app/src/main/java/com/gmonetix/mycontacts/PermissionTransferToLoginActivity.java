package com.gmonetix.mycontacts;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import java.lang.*;

public class PermissionTransferToLoginActivity extends RuntimePermission {
    private static final int REQUEST_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermission(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS},R.string.permission,REQUEST_PERMISSION);
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
