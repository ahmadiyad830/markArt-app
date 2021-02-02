package com.Softwillow.MarkArt.ZoomPictrue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.Softwillow.MarkArt.Firebase.FireAuth.FireSettingAuth;
import com.Softwillow.MarkArt.Firebase.FireAuth.FireSettingProfileUser;
import com.Softwillow.MarkArt.R;
import com.Softwillow.MarkArt.databinding.ActZoomPictureBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Act_ZoomPicture extends AppCompatActivity {
    private ActZoomPictureBinding binding;
    private PhotoViewAttacher attacher;
    private final Uri photoUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActZoomPictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarZoom);
        attacher = new PhotoViewAttacher(binding.zoomImg);



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (photoUri != null) {
            Picasso.get().load(photoUri).into(binding.zoomImg);
            attacher.update();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_back_activity_black) finish();
        return super.onOptionsItemSelected(item);
    }
}