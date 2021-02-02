package com.Softwillow.MarkArt;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.Softwillow.MarkArt.SettingApp.Design;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Model.Setting;
import com.Softwillow.MarkArt.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements FirebaseVar {
    private ActivityMainBinding binding;
    private NavController controller;
    private final Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        testIfUserRegistration();


    }




    private void testIfUserRegistration() {
//        what is that fuck i am but in the bast \/_*
        if (FirebaseAuth.getInstance().getCurrentUser() == null || !Design.isConnected(MainActivity.this))
            finish();
        else if (FirebaseAuth.getInstance().getCurrentUser() != null && !FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())
            finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        controller = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, controller);
        binding.bottomNavigationView.setItemIconTintList(null);

        controller.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (Integer.parseInt(destination.getLabel().toString())) {
                case 1:
                    binding.bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_emerald, getTheme()));

                    break;
                case 2:
                    binding.bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_green_sea, getTheme()));

                    break;
                case 3:
                    binding.bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_midnight_blue, getTheme()));

                    break;
                case 4:
                    binding.bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_turquoise, getTheme()));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Integer.parseInt(destination.getLabel().toString()));
            }
//            binding.bottomNavigationView.setBackgroundResource(R.drawable.custom_bottom_navigation);

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
       TABLE_NATIVE.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TABLE_NATIVE.removeEventListener(valueEventListener);
    }

    private ValueEventListener valueEventListener = FirebaseVar.TABLE_NATIVE.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.child(FirebaseVar.SETTING).exists()) {
                Setting setting = snapshot.child(FirebaseVar.SETTING).getValue(Setting.class);
                if (setting != null && !BuildConfig.VERSION_NAME.equals(setting.getVersionTalent())) {
                    showDialog();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            FireDatabaseCatchError.getInstance().getError(context, error);
            FireDatabaseCatchError.getInstance().setError("getVersion", error.getMessage());
        }
    });

    private void showDialog() {
        new android.app.AlertDialog.Builder(context)
                .setTitle(getString(R.string.error))
                .setCancelable(false)
                .setMessage(R.string.error_version)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}