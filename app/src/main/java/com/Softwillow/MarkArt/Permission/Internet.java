package com.Softwillow.MarkArt.Permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.Softwillow.MarkArt.R;

public class Internet {
    public static final int INTERNET_PERMISSION_CODE = 1;
    private Context context;
    private Activity activity;

    private Internet() {

    }

    public Internet(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setCancelable(false)
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);
                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);

        }
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetWork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            if (!(wifi != null && wifi.isConnected() || mobileNetWork != null && mobileNetWork.isConnected())) {
//                internet not connected
                new AlertDialog.Builder(context)
                        .setTitle("Permission needed")
                        .setCancelable(false)
                        .setMessage("This permission is needed because of this and that")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                activity.finish();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return true;
        } else if (!(wifi != null && wifi.isConnected() || mobileNetWork != null && mobileNetWork.isConnected())) {
//                internet not connected
            requestStoragePermission();
            String tryAgain = context.getResources().getString(R.string.check_internet);
            Toast.makeText(context, tryAgain, Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            return false;
        }
    }

}
