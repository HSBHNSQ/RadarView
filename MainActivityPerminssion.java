package com.heshaobo.scaledrawtest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_MULTIPLE_PERMISSION = 987;
    private static final int REQUEST_PERMISSION_SETTING = 76548;

//----------------------------------------------------------------------

//----------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ddd = (Button)findViewById(R.id.permissionButton);
        ddd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
    }

    private void requestPermission(){

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this,

                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            Log.d(LOG_TAG,"允许");

        }else {
            List<String> permissionsNeeded = new ArrayList<String>();
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionsNeeded.add(Manifest.permission.CAMERA);
            ActivityCompat.requestPermissions(this,
                    permissionsNeeded.toArray(new String[permissionsNeeded.size()]),
                    REQUEST_MULTIPLE_PERMISSION);
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_MULTIPLE_PERMISSION) {
            // for each permission check if the user granted/denied them
            // you may want to group the rationale in a single dialog,
            // this is just an example
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
                    if (! showRationale) {
                        // user also CHECKED "never ask again"
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting
                        AlertDialog dialog = new AlertDialog.Builder(this)
                                .setMessage("该相册需要赋予访问存储的权限，不开启将无法正常工作！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create();
                        Log.d(LOG_TAG,"ActivityCompat.shouldShowRequestPermissionRationale(this,permission)");
                        dialog.show();
                        return;
                    } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
//                        showRationale(permission, R.string.permission_denied_contacts);
                        // user did NOT check "never ask again"
                        // this is a good place to explain the user
                        // why you need the permission and ask if he wants
                        // to accept it (the rationale)
                        Log.d(LOG_TAG,"Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)");
                    } else if ( Manifest.permission.CAMERA.equals(permission)) {
                        Log.d(LOG_TAG,"Manifest.permission.CAMERA.equals(permission)");
                    }
                }
            }
        }
    }

}
