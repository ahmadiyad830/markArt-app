package com.Softwillow.MarkArt.SettingApp;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import com.Softwillow.MarkArt.MainActivity;
import com.Softwillow.MarkArt.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Design {
    private TextView textView;
    private DatePickerDialog.OnDateSetListener setListener;
    private Context context;
    private Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private String date;


    public Design(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public Design(Context context) {
        this.context = context;
    }

    public Design() {
    }

    //get Date use this code like to get birth day
    public String getDate() {
        textView.setOnClickListener(view -> {
            try {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
                EditText editText = new EditText(context);
                editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                DialogAlert.customAlertDialog(context)
                        .setTitle(R.string.app_name)
                        .setView(editText)
                        .setMessage(R.string.add_your_birth)
                        .setPositiveButton("ok", (dialog, which) -> {
                            date = editText.getText().toString().trim();
                            if (date != null)
                                textView.setText(date);
                        }).create().show();
            }

        });
        setListener = (datePicker, year, month, day) -> {
            date = year + "/" + (month+1) + "/" + day;
            textView.setText(date);
        };
        return date;
    }

    //    check if internet is Connected
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetWork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected() || (mobileNetWork != null && mobileNetWork.isConnected()))) {
            return true;
        } else {
            String tryAgain = context.getResources().getString(R.string.check_internet);
            Toast.makeText(context, tryAgain, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    //    make notification
//    TODO : use if admen acceptance user and intent to fragment profile i don't know how just do it ok go go shit
    public void notificationView() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("verseurl");
        bigText.setBigContentTitle("Today's Bible Verse");
        bigText.setSummaryText("Text in detail");
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(R.string.successful)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
        builder.show();
    }

    public static void zoomPicture(ImageView imageView, final Context packageContext, final Class<?> cls, final String url) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageContext.startActivity(new Intent(packageContext, cls).putExtra("url_personla", url));
            }
        });
    }

    public static String getLocale() {
        return Locale.getDefault().getLanguage();
    }

    public Intent getIntent(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

//    TODO MAKE method return boolean test if user has credential or not
    /*private void sigOutError(@NonNull DatabaseError error,) {
        Toast.makeText(ActSplash.this, "error\n" + error.getMessage(), Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
    }*/
}
