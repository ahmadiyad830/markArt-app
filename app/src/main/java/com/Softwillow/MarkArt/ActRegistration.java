package com.Softwillow.MarkArt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.Softwillow.MarkArt.SettingApp.Condition;
import com.Softwillow.MarkArt.SettingApp.Design;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Model.User;
import com.Softwillow.MarkArt.databinding.ActRegBinding;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ActRegistration extends AppCompatActivity implements FirebaseVar {

    private String typeSex, name, phone, birth, email, password, fUid;
    private FirebaseAuth fAuth;
    private User user;
    private FirebaseUser fUser;
    private Design design;
    private final Context context = ActRegistration.this;
    private Condition condition;
    private ActRegBinding binding;
//    private ValueEventListener valueEventListener;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        design = new Design(ActRegistration.this, binding.txtRegBirth);
        design.getDate();

//        Firebase
        fAuth = FirebaseAuth.getInstance();

        binding.radRegMale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                typeSex = "male";
                Toast.makeText(context, "male", Toast.LENGTH_SHORT).show();
            }

        });
        binding.radRegFemale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                typeSex = "female";
                Toast.makeText(context, "female", Toast.LENGTH_SHORT).show();
            }
        });

        binding.txtRegReg.setOnClickListener(view -> {
            name = binding.edtRegName.getText().toString().trim();
            phone = binding.edtRegPhone.getText().toString().trim();
            email = binding.edtRegEmail.getText().toString().trim();
            password = binding.edtRegPassword.getEditText().getText().toString().trim();
            birth = design.getDate();
            //TODO txt.setEnable(false) need to test
//            replace internet with condition and remove all condition here because need to add him from class Condition
            condition = new Condition(context, binding.edtRegName, binding.edtRegEmail, binding.edtRegPassword);
            if (condition.isConnected()
                    && condition.isTypeSex(typeSex, binding.radRegMale, binding.radRegFemale)
                    && condition.isName(name)
                    && condition.isPhone(phone, binding.edtRegPhone)
                    && condition.isBirth(binding.txtRegBirth, birth)
                    && condition.isEmail(email)
                    && condition.isPassword(password)) {
                binding.progressBarReg.setVisibility(View.VISIBLE);
                registration();
            }

        });

        binding.txtRegGoLogIn.setOnClickListener(view -> {
            startActivity(new Intent(ActRegistration.this, ActLogIn.class));
            finish();
        });

    }

    private void registration() {
//        TODO method need small code
        binding.txtRegReg.setEnabled(false);
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                fUser = fAuth.getCurrentUser();
                assert fUser != null;
                fUid = fUser.getUid();
                user = new User(name, password, email, phone, birth, typeSex, fUid);
                fUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        FirebaseVar.TABLE_USER.child(fUser.getUid()).setValue(user).addOnCompleteListener(task11 -> {
                            if (task11.isSuccessful()) {
                                binding.txtRegReg.setEnabled(true);
                                binding.progressBarReg.setVisibility(View.VISIBLE);
//                                            FireSettingProfileUser profileUser = new FireSettingProfileUser();
//                                            UserProfileChangeRequest update = profileUser.setDisplayName(binding.edtRegName.getText().toString()).build();
                                UserProfileChangeRequest.Builder request = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name);
                                fUser.updateProfile(request.build());
                                startActivity(new Intent(ActRegistration.this, ActLogIn.class));
                                finish();
                            }
                        }).addOnFailureListener(e -> {
                            binding.txtRegReg.setEnabled(false);
                            binding.progressBarReg.setVisibility(View.INVISIBLE);
                            Toast.makeText(ActRegistration.this, "error", Toast.LENGTH_SHORT).show();
                            FireDatabaseCatchError.getInstance().setError("field_write_data", e.getMessage());
                        });
                    }

                }).addOnFailureListener(e -> {
                    binding.txtRegReg.setEnabled(false);
                    binding.progressBarReg.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActRegistration.this, "error", Toast.LENGTH_SHORT).show();
                    FireDatabaseCatchError.getInstance().setError("create_user_reg", e.getMessage());
                });
            } else if (!task.isSuccessful()) {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthWeakPasswordException e1) {
                    binding.edtRegPassword.setError(getString(R.string.error_weak_password));
                    binding.edtRegPassword.requestFocus();
                } catch (FirebaseAuthInvalidCredentialsException e2) {
                    binding.edtRegEmail.setError(getString(R.string.error_email_used));
                    binding.edtRegEmail.requestFocus();
                } catch (FirebaseAuthUserCollisionException e3) {
                    binding.edtRegEmail.setError(getString(R.string.password_invalid));
                    binding.edtRegEmail.requestFocus();
                } catch (FirebaseAuthEmailException e1) {
                    binding.edtRegEmail.setError(getString(R.string.error_email_disable) + "\nor\n" + getString(R.string.error_user_delete));
                    binding.edtRegEmail.requestFocus();
                } catch (Exception e4) {
                    String TAG = "Exception";
                    Log.e(TAG, e4.getMessage());
                }
                binding.txtRegReg.setEnabled(true);
                binding.progressBarReg.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(e -> {
            binding.txtRegReg.setEnabled(false);
            binding.progressBarReg.setVisibility(View.INVISIBLE);
            Toast.makeText(ActRegistration.this, "error", Toast.LENGTH_SHORT).show();
            FireDatabaseCatchError.getInstance().setError("create_user_reg", e.getMessage());
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ActRegistration.this, ActSplash.class));
    }
}