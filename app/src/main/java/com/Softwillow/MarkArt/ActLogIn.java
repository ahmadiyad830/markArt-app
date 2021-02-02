package com.Softwillow.MarkArt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Softwillow.MarkArt.Firebase.FireAuth.FireSettingAuth;
import com.Softwillow.MarkArt.SettingApp.Condition;
import com.Softwillow.MarkArt.SettingApp.Design;
import com.Softwillow.MarkArt.SettingApp.DialogAlert;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Permission.Internet;
import com.Softwillow.MarkArt.SettingApp.SharedPreferencesApp;
import com.Softwillow.MarkArt.databinding.ActLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;


import java.util.Objects;

public class ActLogIn extends AppCompatActivity {
    private ActLogInBinding binding;
    private String email, password;

    private FirebaseAuth fAuth;
    private FirebaseUser fUser;

    private String fUid;
    private Internet internet;
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferencesApp memoryApp;
        memoryApp = new SharedPreferencesApp(context);

        binding.chipSaveMe.setChecked(memoryApp.getSaveMe());
        setSaveMe();

        internet = new Internet(ActLogIn.this, this);
        //        Firebase
        fAuth = FirebaseAuth.getInstance();


        binding.btnLogInLogIn.setOnClickListener(view -> {
            email = binding.edtLogInEmail.getText().toString().trim();
            password = binding.edtLogInPassword.getEditText().getText().toString();
            logIn();
        });

        binding.txtLogInReg.setOnClickListener(view -> intent(ActRegistration.class));

        binding.txtForgetPassword.setOnClickListener(v -> getDialog());

    }

    private void getDialog() {
        Button btnCancel, btnOk;
        final EditText edtEmail;
        LayoutInflater inflater = LayoutInflater.from(ActLogIn.this);
        View view = inflater.inflate(R.layout.header, binding.getRoot(), false);
        btnOk = view.findViewById(R.id.ok_forget_password);
        btnCancel = view.findViewById(R.id.cancel_forget_password);
        edtEmail = view.findViewById(R.id.email_to_send_password);
        final Dialog dialog = DialogAlert.customDialog(ActLogIn.this);
        btnCancel.setOnClickListener(v12 -> dialog.dismiss());
        btnOk.setOnClickListener(v1 ->
                FireSettingAuth.restPassword(ActLogIn.this, edtEmail.getText().toString(), getString(R.string.message_send_password)));
        dialog.setContentView(view);
        dialog.create();
        dialog.show();
    }

    private void setSaveMe() {
        binding.chipSaveMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences preferences = getSharedPreferences("save me", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            if (isChecked) {
                editor.putString("remember", "true");
            } else {
                editor.putString("remember", "false");
            }
            editor.apply();
        });
    }

    private void intent(Class<?> cls) {
        Intent intent = new Intent(ActLogIn.this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void logIn() {
        Condition condition = new Condition(ActLogIn.this, binding.edtLogInEmail, binding.edtLogInPassword);
        if (internet.isConnected() && condition.isEmail(email) && condition.isPassword(password)) {
            binding.progressBarLogIn.setVisibility(View.VISIBLE);
            binding.btnLogInLogIn.setEnabled(false);
            checkEmailAndPassword(email, password);
        }
    }

    //TODO need code send Verified @_@ down lovley
//    TODO need small code please
    private void checkEmailAndPassword(String e, String p) {
        fAuth.setLanguageCode(Design.getLocale());
        fAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        fUser = fAuth.getCurrentUser();
                        if (fUser != null && fUser.isEmailVerified()) {
                            //                    TODO # test isEmailVerified down
                            binding.btnLogInLogIn.setEnabled(false);
                            binding.progressBarLogIn.setVisibility(View.VISIBLE);
                            fUid = fUser.getUid();
                            intent(MainActivity.class);
                        } else if (!task.isSuccessful()) {
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (FirebaseAuthWeakPasswordException e1) {
                                binding.edtLogInPassword.setError(getString(R.string.error_weak_password));
                                binding.edtLogInPassword.requestFocus();
//                                    TODO need getCarditienla()
                            } catch (FirebaseAuthInvalidCredentialsException e2) {
                                binding.edtLogInEmail.setError(getString(R.string.error_email_used));
                                binding.edtLogInEmail.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e3) {
                                binding.edtLogInEmail.setError(getString(R.string.password_invalid));
                                binding.edtLogInEmail.requestFocus();
                            } catch (FirebaseAuthEmailException e1) {
                                binding.edtLogInEmail.setError(getString(R.string.error_email_disable) + "\nor\n" + getString(R.string.error_user_delete));
                                binding.edtLogInEmail.requestFocus();
                            } catch (Exception e4) {
                                String TAG = "Exception";
                                Log.e(TAG, e4.getMessage());
                            }
                            binding.btnLogInLogIn.setEnabled(true);
                            binding.progressBarLogIn.setVisibility(View.INVISIBLE);
                        }
                    }
                }).addOnFailureListener(e12 -> {
            binding.btnLogInLogIn.setEnabled(true);
            binding.progressBarLogIn.setVisibility(View.INVISIBLE);
            Toast.makeText(context, "error\n" + e12.getMessage(), Toast.LENGTH_SHORT).show();
            FirebaseVar.TABLE_NATIVE.child(FirebaseVar.ERROR).child(Build.MODEL).child("checkEmailAndPassword").setValue(e12.getMessage());
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ActLogIn.this, ActSplash.class));
    }
}