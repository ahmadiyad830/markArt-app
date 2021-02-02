package com.Softwillow.MarkArt.Fragment_UI.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.Softwillow.MarkArt.Firebase.Database.FireSetDatabase;
import com.Softwillow.MarkArt.Firebase.FireAuth.FireSettingProfileUser;
import com.Softwillow.MarkArt.Firebase.FireStorage;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Permission.Picture;
import com.Softwillow.MarkArt.R;
import com.Softwillow.MarkArt.ZoomPictrue.Act_ZoomPicture;
import com.Softwillow.MarkArt.databinding.ActEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
public class Act_editProfile extends AppCompatActivity implements FirebaseVar {
    private ActEditProfileBinding binding;
    private Uri uri_image;
    private final Context context = Act_editProfile.this;
    private final Activity activity = this;
    private Picture picture;
    private FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.editEditProfileToolbar);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        picture = new Picture(Act_editProfile.this, this);
        binding.txtAddPictureEditProfile.setOnClickListener(v -> picture.mPermission(CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE));


        zoomPicture();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (fUser != null) {
            binding.nameEditProfile.setText(fUser.getDisplayName());
        }
        if (fUser.getPhotoUrl() != null) {
            Picasso.get().load(fUser.getPhotoUrl()).into(binding.imageEditProfile);
        } else binding.imageEditProfile.setImageResource(R.drawable.logo1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Picture.STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(activity);

            } else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_back_activity) {
            finish();
        } else if (id == R.id.menu_registration_talent) {
            if (isNameProfile() || isImagePersonal()) {
                finish();
                Toast.makeText(Act_editProfile.this, "is change", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Act_editProfile.this, "is not change", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isImagePersonal() {
        if (uri_image != null) {
            uploadImageStorage();
            return true;
        } else return false;
    }

    private boolean isNameProfile() {
        String name = binding.nameEditProfile.getText().toString();
        if (name != null) {
            if (TextUtils.isEmpty(name)) {
                return false;
            } else if (name.length() < 3 || name.length() > 50) {
                return false;
            } else if (fUser.getDisplayName().equals(binding.nameEditProfile.getText().toString())) {
                return false;
            } else {
                FireSettingProfileUser profileUser = new FireSettingProfileUser();
                UserProfileChangeRequest request = profileUser.setDisplayName(name).build();
                fUser.updateProfile(request);
                FireSetDatabase fireSetDatabase = new FireSetDatabase(context);
                fireSetDatabase.setChileTalent(TABLE_NATIVE, USER, "name", name, null, getString(R.string.operation_failed));
                return true;
            }
        } else return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = CropImage.getPickImageResultUri(context, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(context, uri_image)) {
                uri_image = uri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        , 0);
            } else {
                startCrop(uri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null && resultCode == RESULT_OK) {
                uri_image = result.getUri();
                Picasso.get().load(uri_image).into(binding.imageEditProfile);
            }
        }

    }

    private void startCrop(Uri uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(2,2)
                .start(activity);
    }

    private void uploadImageStorage() {
        FireStorage fireStorage = new FireStorage(Act_editProfile.this, binding.progressBarEdit);
        fireStorage.uploadImagePersonal(uri_image, "personal");
    }

    private void zoomPicture() {
        binding.imageEditProfile.setOnClickListener(v -> {
            getIntent(Act_ZoomPicture.class);
        });
    }

    private void getIntent(Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}

/*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(activity);
            uri_image = data.getData();
            if (result != null) {
                uri_image = result.getUri();
            }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_image);
                binding.imageEditProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/