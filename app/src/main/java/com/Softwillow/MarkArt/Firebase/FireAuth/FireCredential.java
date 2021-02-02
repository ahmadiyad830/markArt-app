package com.Softwillow.MarkArt.Firebase.FireAuth;

import android.content.Context;

import com.Softwillow.MarkArt.SettingApp.Design;
import com.google.firebase.auth.FirebaseAuth;

public class FireCredential {

    private FireCredential() {

    }
    public static FireCredential getInstance(){
        return new FireCredential();
    }


    public boolean isReg(Context context) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        return fAuth.getCurrentUser() != null && fAuth.getCurrentUser().isEmailVerified() && Design.isConnected(context);
    }
}
