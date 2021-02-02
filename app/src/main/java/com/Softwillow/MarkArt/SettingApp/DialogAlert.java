package com.Softwillow.MarkArt.SettingApp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.firebase.database.DataSnapshot;

public class DialogAlert {
    public static AlertDialog.Builder customAlertDialog(Context context) {
        return new AlertDialog.Builder(context);
    }
    public static Dialog customDialog(Context context){
        return new Dialog(context);
    }
    public static void dialog(Context context, String title, String message) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
