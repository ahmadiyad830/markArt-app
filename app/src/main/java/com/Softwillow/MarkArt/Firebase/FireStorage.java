package com.Softwillow.MarkArt.Firebase;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.Softwillow.MarkArt.Firebase.FireAuth.FireSettingProfileUser;
import com.Softwillow.MarkArt.SettingApp.DialogAlert;
import com.Softwillow.MarkArt.SettingApp.FileExtension;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireStorageError;
import com.Softwillow.MarkArt.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FireStorage {
    private Context context;
    private ProgressBar prog;
    private FileExtension fileExtension;
    StorageReference reference;

    public FireStorage(Context context, ProgressBar prog) {
        this.context = context;
        this.prog = prog;
    }

    private FireStorage() {


    }

    public void uploadImagePersonal(Uri uri_image, String nameImage) {
        fileExtension = new FileExtension(context);
        reference = FirebaseVar.mStorageRef.child("User/" + FireAuthUserInfo.getUID()
                + "/" + nameImage + "." + fileExtension.fileExtension(uri_image));
        reference.putFile(uri_image).addOnCompleteListener
                (new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if (uri != null) {
//                                        remove because picture talent added if user registration in talent
//                                        FirebaseVar.tableTalent.child(Firebase.getUID()).child("imgPersonal").setValue(uri.toString());

                                        updateProfile(uri);
                                        FirebaseVar.TABLE_USER.child(FireAuthUserInfo.getUID()).child("uriPersonal").setValue(uri.toString());
//                                        Common.user.setUriPersonal(uri.toString());
                                        Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    FireStorageError.getInstance().getError(context, e.getCause());
                                }
                            });
                        }
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                culcolator(taskSnapshot.getBytesTransferred(), taskSnapshot.getTotalByteCount(), prog);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FireStorageError.getInstance().getError(context, e.getCause());
            }
        });
    }

    private void updateProfile(Uri uri) {
        FireSettingProfileUser fireSettingProfileUser = new FireSettingProfileUser();
        UserProfileChangeRequest update = fireSettingProfileUser.setPhotoUri(uri).build();
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        fUser.updateProfile(update)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        Toast.makeText(context, "profile successful", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void uploadImageDrawer(Uri uri_image, String nameImage) {

        fileExtension = new FileExtension(context);
        reference = FirebaseVar.mStorageRef.child("User/" + FireAuthUserInfo.getUID()
                + "/" + nameImage + "." + fileExtension.fileExtension(uri_image));
        reference.putFile(uri_image).addOnCompleteListener
                (new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        final Task<UploadTask.TaskSnapshot> snapshotTask = task;
                        if (task.isSuccessful()) {

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if (uri != null) {
                                        FirebaseVar.TABLE_TALENT.child(FireAuthUserInfo.getUID()).child(FirebaseVar.DRAW).child("imgDrawer").setValue(uri.toString());
                                    } else {
                                        DialogAlert.dialog(context, String.valueOf(R.string.error), snapshotTask.getException().getMessage());
                                    }
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    DialogAlert.dialog(context, String.valueOf(R.string.error), "error");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    FireStorageError.getInstance().getError(context, e.getCause());
                                }
                            });
                        }
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                culcolator(taskSnapshot.getBytesTransferred(), taskSnapshot.getTotalByteCount(), prog);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FireStorageError.getInstance().getError(context, e.getCause());
            }
        });
    }


    private void culcolator(long bytesTransferred, long totalByteCount, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        double progress = (100.0 * bytesTransferred / totalByteCount);
        progressBar.setProgress((int) progress);
    }
}
