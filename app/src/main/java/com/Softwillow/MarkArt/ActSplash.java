package com.Softwillow.MarkArt;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Softwillow.MarkArt.Firebase.Database.FireGetDatabase;
import com.Softwillow.MarkArt.Firebase.FireAuth.FireCredential;
import com.Softwillow.MarkArt.SettingApp.SharedPreferencesApp;
import com.Softwillow.MarkArt.databinding.ActSplashBinding;


public class ActSplash extends AppCompatActivity {
    private ActSplashBinding binding;
    private final Context context = this;
    private SharedPreferencesApp sharedPreferencesApp;
    private Intent intent;



    public void getIntent(Class<?> cls) {
        intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActSplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txtVersion.setText(BuildConfig.VERSION_NAME);

    }


    private void showDialog() {
        new android.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.error))
                .setCancelable(false)
                .setMessage(R.string.error_version)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferencesApp = new SharedPreferencesApp(context);
        getSaveUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnSplashSignIn.setOnClickListener(v -> getIntent(ActLogIn.class));
        binding.btnSplashSignUp.setOnClickListener(v -> getIntent(ActRegistration.class));
    }

    private void getSaveUser() {
        if (sharedPreferencesApp.getSaveMe() && FireCredential.getInstance().isReg(context)) {
            getIntent(MainActivity.class);
        }
    }


}