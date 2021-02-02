package com.Softwillow.MarkArt.Firebase.Database;

import androidx.annotation.NonNull;

import com.Softwillow.MarkArt.Firebase.FireAuthUserInfo;
import com.Softwillow.MarkArt.Firebase.FireReadDatabase;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Model.Setting;
import com.Softwillow.MarkArt.Model.Talents.Acting;
import com.Softwillow.MarkArt.Model.Talents.Drawer;
import com.Softwillow.MarkArt.Model.Talents.Signing;
import com.Softwillow.MarkArt.Model.Talents.Talent;
import com.Softwillow.MarkArt.Model.Talents.Writer;
import com.Softwillow.MarkArt.Model.User;
import com.Softwillow.MarkArt.Talent.ItemTalent;
import com.google.firebase.database.DataSnapshot;

public class FireDatabase{

    private DataSnapshot snapshot;

    public FireDatabase() {

    }

    public FireDatabase(DataSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public DataSnapshot getSnapshot() {
        return snapshot;
    }


    public boolean isExists(DataSnapshot snapshot){
        return snapshot.exists();
    }
    public boolean isHasChild(DataSnapshot snapshot,String nameChild){
        return snapshot.hasChild(nameChild);

    }


}
