package com.Softwillow.MarkArt.Fragment_UI.Setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.Softwillow.MarkArt.BuildConfig;
import com.Softwillow.MarkArt.R;
import com.Softwillow.MarkArt.SettingApp.Condition;
import com.Softwillow.MarkArt.databinding.ActSendEmailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Act_Send_Email extends AppCompatActivity {

    private String emailSend, titleMessage, bodyMessage;
    private String[] emailTo;
    private ActSendEmailBinding binding;
    private final Context context = Act_Send_Email.this;
    private Condition condition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActSendEmailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.txtVersionSendEmail.setText(BuildConfig.VERSION_NAME);


        emailSend = getIntent().getStringExtra("emailSend");
        binding.txtEmailTo.setText(emailSend);

        setSupportActionBar(binding.materialToolbar);
        condition = new Condition(context, binding.edtTitleSuggest, binding.edtTitleSuggest);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_back_activity) {
            onBackPressed();
        } else if (id == R.id.menu_registration_talent) {
            titleMessage = binding.edtTitleSuggest.getText().toString().trim();
            bodyMessage = binding.edtBodyEmailSuggest.getText().toString().trim();

            if (titleMessage != null && bodyMessage != null && condition.isName(titleMessage) && condition.isScript(bodyMessage, binding.edtBodyEmailSuggest))
                sendSuggest();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void sendSuggest() {
        String messageBody = binding.edtBodyEmailSuggest.getText().toString();
        emailTo = new String[]{emailSend};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, messageBody + "\n" + getData());
        intent.putExtra(Intent.EXTRA_EMAIL, emailTo);
        startActivity(Intent.createChooser(intent, "email suggest"));
    }

    private List<String> getData() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        List<String> data = new ArrayList<>();
        if (fUser != null && condition.isConnected() && condition.isProfileUri()) {
            data.add(fUser.getDisplayName()+"\n");
            data.add(fUser.getEmail()+"\n");
            data.add(fUser.getPhotoUrl().toString());
        }
        return data;
    }


}