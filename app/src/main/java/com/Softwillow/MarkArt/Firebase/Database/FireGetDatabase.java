package com.Softwillow.MarkArt.Firebase.Database;

import android.os.Build;

import androidx.annotation.NonNull;

import com.Softwillow.MarkArt.BuildConfig;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Model.Setting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FireGetDatabase implements FirebaseVar {
    private String version = BuildConfig.VERSION_NAME;

    public FireGetDatabase() {

    }

    public String getVersion() {
        return version;
    }

    private void setVersion(String version) {
        this.version = version;
    }

    public void getData(){
        TABLE_SETTING.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Setting setting = snapshot.getValue(Setting.class);
                setVersion(setting.getVersionTalent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
