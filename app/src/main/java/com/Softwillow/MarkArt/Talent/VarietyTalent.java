package com.Softwillow.MarkArt.Talent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.Softwillow.MarkArt.Firebase.Database.FireDatabase;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.Softwillow.MarkArt.Firebase.FireAuthUserInfo;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Fragment_UI.FragHelp;
import com.Softwillow.MarkArt.MainActivity;
import com.Softwillow.MarkArt.Model.Talents.Acting;
import com.Softwillow.MarkArt.Model.Talents.Clothes;
import com.Softwillow.MarkArt.Model.Talents.DesignTalent;
import com.Softwillow.MarkArt.Model.Talents.Drawer;
import com.Softwillow.MarkArt.Model.Talents.Makeup;
import com.Softwillow.MarkArt.Model.Talents.PhotoTalent;
import com.Softwillow.MarkArt.Model.Talents.Signing;
import com.Softwillow.MarkArt.Model.Talents.Talent;

import com.Softwillow.MarkArt.Model.User;
import com.Softwillow.MarkArt.R;
import com.Softwillow.MarkArt.SettingApp.Condition;
import com.Softwillow.MarkArt.ZoomPictrue.Act_ZoomPicture;
import com.Softwillow.MarkArt.databinding.ActVarietyTalentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class VarietyTalent extends AppCompatActivity implements ItemTalent, FirebaseVar {
    //    we set in here all same talent
    private ActVarietyTalentBinding binding;
    private String ID, location, nameFacebook;
    private Condition condition;
    private FireDatabase database;
    private Talent talent;
    private final Context context = VarietyTalent.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActVarietyTalentBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarVariety);

        getData();
        binding.imgDrawer.setOnClickListener(view ->{
            isRegTalent(Act_ZoomPicture.class);
        });
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
            Intent intent = new Intent(VarietyTalent.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.menu_help_talent) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new FragHelp()).commit();
        } else if (id == R.id.menu_registration_talent) {
            ID = binding.edtID.getText().toString().trim();
            nameFacebook = binding.edtLocation.getText().toString().trim();
            location = binding.edtFacebook.getText().toString().trim();
            condition = new Condition(VarietyTalent.this, binding.edtID, binding.edtLocation, binding.edtFacebook);
            if (condition.isID(ID) && condition.isName(nameFacebook) && condition.isLocation(location) && condition.isProfileUri()) {
                talent = new Talent(ID, location, nameFacebook);
                setData();
                isRegTalent(MainActivity.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private String getTypeTalent() {
        return getIntent().getStringExtra(TYPE);
    }

    private void setData() {
        TABLE_TALENT.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                database = new FireDatabase(snapshot);
                DataSnapshot nativeQuery = snapshot.child(FireAuthUserInfo.getUID());
//                reg in talent but we not know any talent
                if (!database.isHasChild(snapshot, FireAuthUserInfo.getUID())) {
                    TABLE_TALENT.child(FireAuthUserInfo.getUID()).setValue(talent);
                }
                //write code talent
                switch (getTypeTalent()) {
                    case DRAW:
                        if (database.isHasChild(nativeQuery, DRAW)) {
                            Toast.makeText(VarietyTalent.this, getResources().getString(R.string.isRegistration), Toast.LENGTH_SHORT).show();
//                            else not registration in this talent
                        } else setValue(new Drawer());
                        break;
                    case SIGNING:
                        if (database.isHasChild(nativeQuery, SIGNING)) {
                            Toast.makeText(VarietyTalent.this, getResources().getString(R.string.isRegistration), Toast.LENGTH_SHORT).show();
                            isRegTalent(MainActivity.class);
                        } else setValue(new Signing());
                        break;
                    case ACING:
                        if (database.isHasChild(nativeQuery, ACING)) {
                            Toast.makeText(VarietyTalent.this, getResources().getString(R.string.isRegistration), Toast.LENGTH_SHORT).show();
                            isRegTalent(MainActivity.class);
                        }
                        else setValue(new Acting());
                        break;
                    case DESIGN:
                        if (database.isHasChild(nativeQuery, DESIGN)) {
                            Toast.makeText(VarietyTalent.this, getResources().getString(R.string.isRegistration), Toast.LENGTH_SHORT).show();
                            isRegTalent(MainActivity.class);
                        } else setValue(new DesignTalent());
                        break;
                    case MAKEUP:
                        if (database.isHasChild(nativeQuery, MAKEUP)) {
                            Toast.makeText(VarietyTalent.this, getResources().getString(R.string.isRegistration), Toast.LENGTH_SHORT).show();
                            isRegTalent(MainActivity.class);
                        } else setValue(new Makeup());
                        break;
                    case CLOTHES:
                        if (database.isHasChild(nativeQuery, CLOTHES)) {
                            Toast.makeText(VarietyTalent.this, getResources().getString(R.string.isRegistration), Toast.LENGTH_SHORT).show();
                            isRegTalent(MainActivity.class);
                        } else setValue(new Clothes());
                        break;
                    case PHOTO:
                        if (database.isHasChild(nativeQuery, PHOTO)) {
                            Toast.makeText(VarietyTalent.this, getResources().getString(R.string.isRegistration), Toast.LENGTH_SHORT).show();
                            isRegTalent(MainActivity.class);
                        } else setValue(new PhotoTalent());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + getTypeTalent());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FireDatabaseCatchError.getInstance().setError("setDataVarietyTalent", error.getMessage());
                FireDatabaseCatchError.getInstance().getError(VarietyTalent.this, error);
            }
        });
    }

    private void setValue(Object object) {
        TABLE_TALENT.child(FireAuthUserInfo.getUID()).child(getTypeTalent()).setValue(object);
    }

    private void isRegTalent(Class<?> clas) {
        Intent intent = new Intent(VarietyTalent.this, clas);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void getData() {
        Condition condition = new Condition(context);
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (condition.isProfileUri())
            if (fUser != null)
                Picasso.get().load(fUser.getPhotoUrl()).into(binding.imgDrawer);
        TABLE_NATIVE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                talent
                if (snapshot.child(TALENT).hasChild(FireAuthUserInfo.getUID())) {
                    Talent talent = snapshot.child(TALENT).child(FireAuthUserInfo.getUID()).getValue(Talent.class);
                    if (talent != null) {
                        binding.edtFacebook.setEnabled(false);
                        binding.edtFacebook.setText(talent.getNameFacebook());
                        binding.edtLocation.setEnabled(false);
                        binding.edtLocation.setText(talent.getLocation());
                        binding.edtID.setEnabled(false);
                        binding.edtID.setText(talent.getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FireDatabaseCatchError.getInstance().setError("getDataVarietyTalent", error.getMessage());
                FireDatabaseCatchError.getInstance().getError(VarietyTalent.this, error);
            }
        });
    }
}