package com.Softwillow.MarkArt.Firebase.FireAuth;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireAuthCatchError;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.SettingApp.Design;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FireSettingAuth implements FirebaseVar {
    private static FirebaseAuth fAuth;
    private AuthUI fAuthUI;
    private Context context;
    private Class<?> cls;
    private Design design;
    private Activity activity;

    public FireSettingAuth(Context context, Class<?> aClass,Activity activity) {
        this.context = context;
        this.cls = aClass;
        this.activity = activity;
    }

    private FireSettingAuth() {

    }

    private void change(Uri uri, String name)  {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates);

    }

    public static void restPassword(final Context context, String email, final String mSuccess) {
        fAuth = FirebaseAuth.getInstance();
        fAuth.setLanguageCode(Design.getLocale());
        fAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid ->Toast.makeText(context, mSuccess, Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "error\n" + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void signOut() {
        design = new Design();
        fAuthUI = AuthUI.getInstance();
        fAuthUI.signOut(context)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        design.getIntent(context, cls);
                        activity.startActivity(design.getIntent(context,cls));
                        activity.finish();
                    }
        }).addOnFailureListener(e ->FireAuthCatchError.getInstance().getError(e));
    }
    private void getCredential(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
// Get the account
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        if (acct != null) {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("TAG_FIREBASE_RE_AUTH", "Reauthenticated.");
                    }
                }
            });
        }
    }

}
