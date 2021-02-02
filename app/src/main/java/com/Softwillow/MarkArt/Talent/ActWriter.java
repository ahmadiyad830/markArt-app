package com.Softwillow.MarkArt.Talent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Softwillow.MarkArt.Firebase.Database.FireSetDatabase;
import com.Softwillow.MarkArt.Firebase.FireAuthUserInfo;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.MainActivity;
import com.Softwillow.MarkArt.Model.Talents.Talent;
import com.Softwillow.MarkArt.Model.Talents.Writer;
import com.Softwillow.MarkArt.Model.User;
import com.Softwillow.MarkArt.R;
import com.Softwillow.MarkArt.Fragment_UI.Setting.Act_Send_Email;
import com.Softwillow.MarkArt.SettingApp.Condition;
import com.Softwillow.MarkArt.databinding.ActWriterBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ActWriter extends AppCompatActivity implements FirebaseVar{

    private String ID, location, script, nameFacebook;
    private Talent talent;
    private Writer writer;
    private ActWriterBinding binding;
    private final Context context = ActWriter.this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActWriterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.editWriterToolbar);



        if (FireAuthUserInfo.getUID().isEmpty()) {
            startActivity(new Intent(ActWriter.this, Act_Send_Email.class));
            finish();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_registration_talent) {
            ID = binding.edtWriterID.getText().toString().trim();
            location = binding.edtWriterLocation.getText().toString().trim();
            script = binding.edtWriterScript.getText().toString().trim();
            nameFacebook = binding.edtWriterFacebook.getText().toString().trim();
            Condition condition = new Condition(context, binding.edtWriterID, binding.edtWriterLocation, binding.edtWriterFacebook);
            if (condition.isID(ID) && condition.isLocation(location) && condition.isScript(script, binding.edtWriterScript) && condition.isName(nameFacebook) && condition.isProfileUri()) {
                binding.progressBarWriter.setVisibility(View.VISIBLE);
                talent = new Talent(ID, location, nameFacebook);
                writer = new Writer(script, "false");
                upload();
            }

        } else if (id == R.id.menu_back_activity) {
            Intent intent = new Intent(ActWriter.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO : replace variable static to method getData finish
    private void getData() {
        FirebaseVar.TABLE_NATIVE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(FirebaseVar.TALENT).child(FireAuthUserInfo.getUID()).exists()) {
                    Talent talent = snapshot.child(FirebaseVar.TALENT).child(FireAuthUserInfo.getUID()).getValue(Talent.class);
                    binding.edtWriterID.setText(talent.getId());
                    binding.edtWriterLocation.setText(talent.getLocation());
                    binding.edtWriterID.setEnabled(false);
                    binding.edtWriterFacebook.setEnabled(false);
                    binding.edtWriterFacebook.setText(talent.getNameFacebook());
                    binding.edtWriterLocation.setEnabled(false);
                }
                if (snapshot.child(FirebaseVar.USER).child(FireAuthUserInfo.getUID()).exists()) {
                    User user = snapshot.child(FirebaseVar.USER).child(FireAuthUserInfo.getUID()).getValue(User.class);
                    Picasso.get().load(user.getUriPersonal()).into(binding.imgWriterImgPersonal);
                    binding.imgWriterImgPersonal.setEnabled(false);
                }
                if (snapshot.child(FirebaseVar.WRITER).child(FireAuthUserInfo.getUID()).exists()) {
                    binding.edtWriterScript.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FireDatabaseCatchError.getInstance().setError("getData_writer", error.getMessage());
                FireDatabaseCatchError.getInstance().getError(ActWriter.this, error);
            }
        });
    }

    private void upload() {
        binding.progressBarWriter.setVisibility(View.VISIBLE);
        FirebaseVar.TABLE_TALENT.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FireSetDatabase database = new FireSetDatabase(context);
                DatabaseReference query = TABLE_TALENT.child(FireAuthUserInfo.getUID());
                if (snapshot.child(FireAuthUserInfo.getUID()).exists()) {
                    binding.progressBarWriter.setVisibility(View.VISIBLE);
                    if (snapshot.child(FireAuthUserInfo.getUID()).hasChild("writer")) {
                        binding.progressBarWriter.setVisibility(View.VISIBLE);
                        binding.editWriterToolbar.setEnabled(false);
                        finish();
                    } else {
                        binding.progressBarWriter.setVisibility(View.INVISIBLE);
                        database.queryData(query.child(WRITER),writer,getResources().getString(R.string.regTalent),"set_Writer");
                        finish();
                    }
                } else {
                    binding.progressBarWriter.setVisibility(View.INVISIBLE);
                    database.queryData(query,talent,getResources().getString(R.string.regTalent),"set_Talent_writer");
                    database.queryData(query.child(WRITER),writer,getResources().getString(R.string.regTalent),"set_Writer");
                    finish();
                }
            }
            //TODO if talent null because talent is gloable variable >> can know if talent has error or not when use test

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FireDatabaseCatchError.getInstance().setError("set_writer", error.getMessage());
                FireDatabaseCatchError.getInstance().getError(ActWriter.this, error);
            }
        });
    }

}

/**/
