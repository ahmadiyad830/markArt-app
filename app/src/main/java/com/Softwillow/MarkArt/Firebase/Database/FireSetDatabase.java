package com.Softwillow.MarkArt.Firebase.Database;

import android.content.Context;
import android.widget.Toast;

import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.Softwillow.MarkArt.Firebase.FireAuthUserInfo;
import com.google.firebase.database.DatabaseReference;

public class FireSetDatabase{

    private final Context context;
    private DatabaseReference reference;

    public  FireSetDatabase(Context context) {
        this.context = context;
    }

    public void writeOnTalent(DatabaseReference reference, String tableName, Object object, String textSuccessful, String nameTableFailure) {
        reference.child(tableName).child(FireAuthUserInfo.getUID()).setValue(object).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, textSuccessful, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> FireDatabaseCatchError.getInstance().setError(nameTableFailure, e.getMessage()));
    }
    public void setChileTalent(DatabaseReference reference, String tableName,String childName, Object object, String textSuccessful, String nameTableFailure) {
        reference.child(tableName).child(FireAuthUserInfo.getUID()).child(childName).setValue(object).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, textSuccessful, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> FireDatabaseCatchError.getInstance().setError(nameTableFailure, e.getMessage()));
    }
    public void queryData(DatabaseReference query,Object object,String textSuccessful,String textFailure){
        setQuery(query).setValue(object).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(context, textSuccessful, Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(e->{
            FireDatabaseCatchError.getInstance().setError(textFailure, e.getMessage());
        });
    }
    private DatabaseReference setQuery(DatabaseReference reference){
        this.reference = reference;
        return this.reference;
    }
}
