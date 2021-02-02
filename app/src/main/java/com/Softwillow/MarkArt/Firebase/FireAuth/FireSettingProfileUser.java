package com.Softwillow.MarkArt.Firebase.FireAuth;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FireSettingProfileUser extends UserProfileChangeRequest.Builder {

    public FireSettingProfileUser() {

    }


    @Nullable
    @Override
    public String getDisplayName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    @NonNull
    @Override
    public UserProfileChangeRequest.Builder setDisplayName(@Nullable String s) {
        return super.setDisplayName(s);

    }

    @Nullable
    @Override
    public Uri getPhotoUri() {
        return FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
    }

    @NonNull
    @Override
    public UserProfileChangeRequest.Builder setPhotoUri(@Nullable Uri uri) {

        return super.setPhotoUri(uri);
    }
}
