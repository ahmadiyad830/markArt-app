package com.Softwillow.MarkArt.SettingApp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.Softwillow.MarkArt.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Condition {
    private Context context;
    private EditText editID, editLocation, editName, edtEmail, edtScript;
    private TextInputLayout edtPassword;
    private EditText edtGender, edtPhone;

    public Condition(Context context, EditText editName, TextInputLayout edtPassword, EditText edtEmail, EditText edtPhone) {
        this.context = context;
        this.editName = editName;
        this.edtPassword = edtPassword;
        this.edtEmail = edtEmail;
        this.edtPhone = edtPhone;
    }
//    private String name,ID,location;

    public Condition(Context context, EditText editName, EditText edtPhone) {
        this.context = context;
        this.editName = editName;
        this.edtPhone = edtPhone;
    }

    private Condition() {

    }


    public Condition(Context context) {
        this.context = context;
    }

    /*{@Link Variety}*/
    public Condition(Context context, EditText editID, EditText editLocation, EditText editName) {
        this.context = context;
        this.editID = editID;
        this.editLocation = editLocation;
        this.editName = editName;
    }

    //    /*{@Link logIn}*/
    public Condition(Context context, EditText edtEmail, TextInputLayout edtPassword) {
        this.context = context;
        this.edtPassword = edtPassword;
        this.edtEmail = edtEmail;
    }

    public Condition(Context context, EditText editName, EditText edtEmail, TextInputLayout edtPassword) {
        this.context = context;
        this.editName = editName;
        this.edtEmail = edtEmail;
        this.edtPassword = edtPassword;
    }

    public boolean isPhone(String phone, EditText editPhone) {
        if (TextUtils.isEmpty(phone)) {
            editPhone.setError(context.getResources().getString(R.string.error));
            return false;
        } else if (phone.length() != 10) {
            editPhone.setError(context.getString(R.string.error_number));
            return false;
        } else return true;
    }

    public boolean isTypeSex(String typeSex, RadioButton radioButton, RadioButton radioButton1) {
        if (TextUtils.isEmpty(typeSex)) {
            radioButton.setError(context.getResources().getString(R.string.error));
            radioButton1.setError(context.getResources().getString(R.string.error));
            return false;
        }else {
            return true;
        }
    }

    public boolean isBirth(TextView txtRegBirth, String birth) {
        if (TextUtils.isEmpty(birth)) {
            txtRegBirth.setError(context.getResources().getString(R.string.error));
            return false;
        } else if (birth.equals("")) {
            txtRegBirth.setError(context.getResources().getString(R.string.error));
            return false;
        } else if (birth.length() < 6) {
            txtRegBirth.setError(context.getResources().getString(R.string.add_your_birth));
            return false;
        } else if (txtRegBirth != null && birth != null) {
            return true;
        } else return true;
    }

    public boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError(context.getResources().getString(R.string.error));
            return false;
        } else if (email.length() > 100) {
            edtEmail.setError(context.getString(R.string.error_email));
            return false;
        } else {
            return true;
        }
    }

    public boolean isPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError(context.getResources().getString(R.string.error));
            return false;
        } else {
            return true;
        }
    }

    public boolean isName(String name) {
        if (TextUtils.isEmpty(name)) {
            editName.setError(context.getResources().getString(R.string.error));
            return false;
        } else if (name.length() <= 2 || name.length() > 50) {
            editName.setError(context.getString(R.string.error_name));
            return false;
        } else return true;
    }

    public boolean isScript(String script, EditText editScript) {
        if (TextUtils.isEmpty(script)) {
            editScript.setError(context.getResources().getString(R.string.error));
            return false;
        } else if (script.length() > 2000) {
            editScript.setError(context.getResources().getString(R.string.bigScript));
            return false;
        } else {
            return true;
        }
    }

    public boolean isLocation(String location) {
        if (TextUtils.isEmpty(location)) {
            editLocation.setError(context.getResources().getString(R.string.error));
            return false;
        } else {
            return true;
        }
    }

    public boolean isID(String ID) {
        if (TextUtils.isEmpty(ID)) {
            editID.setError(context.getResources().getString(R.string.error));
            return false;
        } else if (ID.length() != 10) {
            editID.setError(context.getResources().getString(R.string.numberID));
            return false;
        } else {
            return true;
        }

    }

    public boolean isProfileUri() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getPhotoUrl() != null) {
            return true;
        } else {
            DialogAlert.customAlertDialog(context)
                    .setTitle(R.string.error)
                    .setMessage(R.string.picturePersonal)
                    .setPositiveButton("OK", (dialog, which) -> {
//                Navigation.findNavController(view).navigate(R.id.acti);
                        dialog.dismiss();
                    }).create().show();
            return false;
        }
    }

    public boolean isProgress(ProgressBar progress, Activity window) {
        int count = progress.getProgress();
        if (count != 100) {
            window.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            return true;
        } else {
            window.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            return false;
        }
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getActiveNetworkInfo();
        NetworkInfo mobileNetWork = connectivityManager.getActiveNetworkInfo();
        if (wifi != null) {
            // connected to the internet
            if (wifi.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (mobileNetWork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to mobile data
                return true;
            }
        } else {
            String tryAgain = context.getResources().getString(R.string.check_internet);
            Toast.makeText(context, tryAgain, Toast.LENGTH_SHORT).show();
            return false;
            // not connected to the internet
        }
        String tryAgain = context.getResources().getString(R.string.check_internet);
        Toast.makeText(context, tryAgain, Toast.LENGTH_SHORT).show();
        return false;
    }
}
