package com.Softwillow.MarkArt.Firebase;

import androidx.annotation.NonNull;

import com.Softwillow.MarkArt.Model.Talents.Drawer;
import com.Softwillow.MarkArt.Model.Talents.Talent;
import com.Softwillow.MarkArt.Model.Talents.Writer;
import com.Softwillow.MarkArt.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FireReadDatabase implements FirebaseVar {
    private String table;
    private Class model;


    protected FireReadDatabase (){

    }

    private FireReadDatabase(String table,Class model){
        this.model = model;
        this.table = table;
    }
//    method getAllData
    public static FireReadDatabase getInstance(){
        return new FireReadDatabase();
    }
    public static FireReadDatabase getInstanceAllData(String table,Class cla){
        return new FireReadDatabase(table,cla);


    }



    public void getSingleTable(@NonNull DatabaseReference reference , final String table, final Class<?>model){

       reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllData(){
        FirebaseVar.TABLE_NATIVE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(FirebaseVar.TALENT).child(FireAuthUserInfo.getUID()).exists()){
                    Talent talent = snapshot.child(FirebaseVar.TALENT).child(FireAuthUserInfo.getUID()).getValue(Talent.class);
                }
                if (snapshot.child(FirebaseVar.USER).child(FireAuthUserInfo.getUID()).exists()){
                    User user = snapshot.child(FirebaseVar.USER).child(FireAuthUserInfo.getUID()).getValue(User.class);

                }
                if (snapshot.child(FirebaseVar.TALENT).child(FireAuthUserInfo.getUID()).child(FirebaseVar.WRITER).exists()){
                    Writer writer = snapshot.child(FirebaseVar.TALENT).child(FireAuthUserInfo.getUID()).child(FirebaseVar.WRITER).getValue(Writer.class);

                }
                if(snapshot.child(FirebaseVar.TALENT).child(FireAuthUserInfo.getUID()).child(FirebaseVar.DRAW).exists()){
                    Drawer drawer = snapshot.child(FirebaseVar.TALENT).child(FireAuthUserInfo.getUID()).child(FirebaseVar.DRAW).getValue(Drawer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
