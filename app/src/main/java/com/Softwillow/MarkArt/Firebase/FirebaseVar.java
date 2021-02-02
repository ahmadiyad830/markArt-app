package com.Softwillow.MarkArt.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public interface FirebaseVar {
    //    name child
    String USER = "User";
    String TALENT = "Talent";
    String DRAW = "drawer";
    String WRITER = "writer";
    String SETTING = "Setting";
    String IS_OPEN = "isOpen";
    String ERROR = "Error";
    // tables database
    FirebaseDatabase DATA_NORMAL = FirebaseDatabase.getInstance();

    DatabaseReference TABLE_NATIVE = DATA_NORMAL.getReference();
    DatabaseReference TABLE_ERROR = DATA_NORMAL.getReference().child(ERROR);
    DatabaseReference TABLE_USER = DATA_NORMAL.getReference().child(USER);
    DatabaseReference TABLE_TALENT = DATA_NORMAL.getReference().child(TALENT);
    DatabaseReference TABLE_SETTING = DATA_NORMAL.getReference().child(SETTING);
    //    file storage
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();


}
