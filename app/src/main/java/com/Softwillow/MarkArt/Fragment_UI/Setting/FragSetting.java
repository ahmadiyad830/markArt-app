package com.Softwillow.MarkArt.Fragment_UI.Setting;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.Softwillow.MarkArt.ActLogIn;
import com.Softwillow.MarkArt.ActSplash;
import com.Softwillow.MarkArt.BuildConfig;
import com.Softwillow.MarkArt.Firebase.FireAuth.FireSettingAuth;
import com.Softwillow.MarkArt.SettingApp.DialogAlert;
import com.Softwillow.MarkArt.Firebase.FireAuthUserInfo;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.Softwillow.MarkArt.Fragment_UI.FragHelp;
import com.Softwillow.MarkArt.MainActivity;
import com.Softwillow.MarkArt.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.Locale;


public class FragSetting extends Fragment {
    private View view;
    private RadioButton radEn, radAr;
    private MaterialButton btnAbout, btnDeveloper, btnArtFoot, btnLogOut, btnHelp, btnPassword;
    private ImageView imagePageFaceBook;
    private FirebaseUser fUser;
    private FirebaseAuth fAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_setting, container, false);

        radAr = view.findViewById(R.id.rad_setting_ar);
        radEn = view.findViewById(R.id.rad_setting_en);
        btnAbout = view.findViewById(R.id.about);
        btnDeveloper = view.findViewById(R.id.btn_email_devloper);
        btnArtFoot = view.findViewById(R.id.btn_email_artFootPrint);
        btnLogOut = view.findViewById(R.id.btn_logOut);
        btnHelp = view.findViewById(R.id.asddd);
        btnPassword = view.findViewById(R.id.change_password);
        imagePageFaceBook = view.findViewById(R.id.btn_share);

        btnPassword.setOnClickListener(v -> {
            if (FireAuthUserInfo.getEmail() != null)
                showDialog();
            else
//                TODO method getCreditienal()
                Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();

        });
        btnHelp.setOnClickListener(v -> startFragment(view));
        btnLogOut.setOnClickListener(v -> mLogOut());
        imagePageFaceBook.setOnClickListener(v -> shareFaceBook());
        checkRadLanguage();
        btnDeveloper.setOnClickListener(view -> {
            getIntent(getString(R.string.email_combany));
        });
        btnArtFoot.setOnClickListener(view -> {
            getIntent(getString(R.string.center_email));
        });
        radEn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (radEn.isChecked()) {
                saveLocale("en");
            }
        });
        radAr.setOnCheckedChangeListener((compoundButton, b) -> {
            if (radAr.isChecked()) {
                saveLocale("ar");
            }
        });

        return view;
    }

    private void showDialog() {

        Button btnCancel, btnOk;
        final EditText edtEmail;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.header, (ViewGroup) getView().getRootView(), false);
        btnOk = view.findViewById(R.id.ok_forget_password);
        btnCancel = view.findViewById(R.id.cancel_forget_password);
        edtEmail = view.findViewById(R.id.email_to_send_password);
        final Dialog dialog = DialogAlert.customDialog(getActivity());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(v ->
                FireSettingAuth.restPassword(getActivity(), edtEmail.getText().toString(), getString(R.string.message_send_password)));
        dialog.setContentView(view);
        dialog.create();
        dialog.show();

    }

    //TODO replace with activity down
    private void startFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.action_fragSetting_to_menu_help_talent);
    }

    private void getIntent(String email) {
        Intent intent = new Intent(getActivity(), Act_Send_Email.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("emailSend", email);
        startActivity(intent);
    }

    private void checkRadLanguage() {
        switch (getLocale()) {
            case "en":
                radEn.setChecked(true);
                break;
            case "ar":
                radAr.setChecked(true);
                break;
        }
    }


    //    TODO move to class design >> move when uses in many place dont woriy
    public static String getLocale() {
        return Locale.getDefault().getLanguage();
    }

    private void saveLocale(String locale){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(locale));
        }else {
            conf.locale = new Locale(locale);
        }
        res.updateConfiguration(conf,dm );
        Intent refresh = new Intent(getContext(), MainActivity.class);
        startActivity(refresh);
    }

    private void mLogOut() {
        DialogAlert.customAlertDialog(getActivity())
                .setTitle(getString(R.string.app_name))
                .setMessage("log out ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("yes", (dialogInterface, i) -> {
                    if (fUser != null && getActivity() != null) {
                        FireSettingAuth fireSettingAuth = new FireSettingAuth(getActivity(), ActSplash.class, getActivity());
                        fireSettingAuth.signOut();
                    }
                })
                .setNegativeButton("no", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }


    private void shareFaceBook() {
        String id = "419070812275373";
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            FireDatabaseCatchError.getInstance().setError("shareFacebook", e.getMessage());
        }
    }

}