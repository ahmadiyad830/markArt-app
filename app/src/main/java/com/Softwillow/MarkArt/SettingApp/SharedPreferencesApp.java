package com.Softwillow.MarkArt.SettingApp;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesApp {
    Context context;

    private SharedPreferencesApp() {

    }

    public SharedPreferencesApp(Context context) {
        this.context = context;
    }

    public boolean getSaveMe() {
//TODO Temporarily  because we need replace not all a method just Intent but code setChecked() no
//        down we replace to class settings
        SharedPreferences preferences = context.getSharedPreferences("save me", Context.MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "false");
        return Boolean.parseBoolean(checkbox);
    }

    public void saveValue(String keyName,String value) {
        SharedPreferences preferences = context.getSharedPreferences("save me", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", value);
        editor.apply();
    }

}
