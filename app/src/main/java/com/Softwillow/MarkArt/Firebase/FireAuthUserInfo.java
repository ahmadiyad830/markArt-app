package com.Softwillow.MarkArt.Firebase;

import android.os.Build;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireAuthUserInfo {


    public static String getUID() {
        FirebaseUser fUser;
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        if (fUser!=null){
            return fUser.getUid();
        }
        return Build.DEVICE;
    }
    public static String getEmail() {
        FirebaseUser fUser;
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        if (fUser!=null){
            return fUser.getEmail();
        }
        return "null";
    }
}
//get uid from constarctonr or arguoment
//use set and get