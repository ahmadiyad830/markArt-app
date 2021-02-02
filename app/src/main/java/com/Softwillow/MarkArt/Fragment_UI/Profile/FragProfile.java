package com.Softwillow.MarkArt.Fragment_UI.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Softwillow.MarkArt.Model.Talents.Acting;
import com.Softwillow.MarkArt.Model.Talents.Clothes;
import com.Softwillow.MarkArt.Model.Talents.DesignTalent;
import com.Softwillow.MarkArt.Model.Talents.Makeup;
import com.Softwillow.MarkArt.Model.Talents.PhotoTalent;
import com.Softwillow.MarkArt.Model.Talents.Signing;
import com.Softwillow.MarkArt.SettingApp.Design;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.Softwillow.MarkArt.Firebase.FireAuthUserInfo;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Model.Talents.Drawer;
import com.Softwillow.MarkArt.Model.Talents.Talent;
import com.Softwillow.MarkArt.Model.Talents.Writer;
import com.Softwillow.MarkArt.Model.User;
import com.Softwillow.MarkArt.R;
import com.Softwillow.MarkArt.Talent.ItemTalent;
import com.Softwillow.MarkArt.ZoomPictrue.Act_ZoomPicture;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * {@link }
 * {@link #}
 */
public class FragProfile extends Fragment implements FirebaseVar, ItemTalent {
    private ImageView imgPersonal;
    private FloatingActionButton btnUpdateProfile;
    private View view;
    //    fUid how is UID user
    private String fUid;
    private CollapsingToolbarLayout txtName;
    private TextView txtPhone, txtLocation, txtBirth, txtId, txtTypeSex, txtEmail
            ,teamSigning, teamActing, teamDesign, teamMakeup, teamClothes, teamPhoto, teamDrawer, teamWriter;
    private String nameDisplay;
    private Uri uriProfilePhoto;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();


        if (fUser != null) {
            fUid = fUser.getUid();
            nameDisplay = fUser.getDisplayName();
            uriProfilePhoto = fUser.getPhotoUrl();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_profile, container, false);

        txtTypeSex = view.findViewById(R.id.typeSex_profile);
        txtName = view.findViewById(R.id.collapseActionView);
        txtEmail = view.findViewById(R.id.email_profile);
        imgPersonal = view.findViewById(R.id.img_profile);
        txtPhone = view.findViewById(R.id.phone_profile);
        txtLocation = view.findViewById(R.id.location_profile);
        txtBirth = view.findViewById(R.id.birth_profile);
        txtId = view.findViewById(R.id.idPesron_profile);
        btnUpdateProfile = view.findViewById(R.id.btn_profile_Approval);
//        teams
        teamWriter = view.findViewById(R.id.team_writer_profile);
        teamDrawer = view.findViewById(R.id.team_drawer_profile);
        teamSigning = view.findViewById(R.id.team_signing_profile);
        teamActing = view.findViewById(R.id.team_acting_profile);
        teamDesign = view.findViewById(R.id.team_design_profile);
        teamMakeup = view.findViewById(R.id.team_makeup_profile);
        teamClothes = view.findViewById(R.id.team_clothes_profile);
        teamPhoto = view.findViewById(R.id.team_photo_profile);


//        Toast.makeText(getActivity(), FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        txtName.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        txtName.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        UpdateProfile();
    }

    private void getData() {
        TABLE_NATIVE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(TALENT).child(fUid).exists()) {
                    Talent talent = snapshot.child(FirebaseVar.TALENT).child(fUid).getValue(Talent.class);
                    txtLocation.setText(talent.getLocation());
                    txtId.setText(talent.getId());
                }
                if (snapshot.child(USER).child(fUid).exists()) {
                    User user = snapshot.child(USER).child(fUid).getValue(User.class);
                    txtName.setTitle(nameDisplay);
                    if (user.getUriPersonal() != null) {
                        Picasso.get()
                                .load(uriProfilePhoto)
                                .resizeDimen(R.dimen.size_picture_profile, R.dimen.size_picture_profile)
                                .into(imgPersonal);
                        Design.zoomPicture(imgPersonal, getActivity(), Act_ZoomPicture.class, user.getUriPersonal());
                    } else imgPersonal.setImageResource(R.drawable.logo1);
                    txtEmail.setText(user.getEmail());
                    txtBirth.setText(user.getBirthDay());
                    txtPhone.setText(user.getPhone());
                    if (user.getTypeSex().equals("male")) {
                        txtTypeSex.setText(R.string.male);
                    } else {
                        txtTypeSex.setText(R.string.female);
                    }
                }
                if (snapshot.child(TALENT).child(fUid).child(WRITER).exists()) {
                    Writer writer = snapshot.child(FirebaseVar.TALENT).child(fUid).child(FirebaseVar.WRITER).getValue(Writer.class);
                    if (Boolean.parseBoolean(writer.getIsWriter())) {
                        teamWriter.setVisibility(View.VISIBLE);
                    }
                }
                if (snapshot.child(TALENT).child(fUid).child(DRAW).exists()) {
                    Drawer drawer = snapshot.child(TALENT).child(fUid).child(DRAW).getValue(Drawer.class);
                    if (Boolean.parseBoolean(drawer.getIsDrawer())) {
                        teamDrawer.setVisibility(View.VISIBLE);
                    }
                }
                if (snapshot.child(TALENT).child(fUid).child(SIGNING).exists()) {
                    Signing signing = snapshot.child(TALENT).child(fUid).child(SIGNING).getValue(Signing.class);
                    if (Boolean.parseBoolean(signing.getIsSigning())){
                        teamSigning.setVisibility(View.VISIBLE);
                    }
                }
                if (snapshot.child(TALENT).child(fUid).child(ACING).exists()) {
                    Acting acting = snapshot.child(TALENT).child(fUid).child(ACING).getValue(Acting.class);
                    if (Boolean.parseBoolean(acting.getIsActor())){
                        teamActing.setVisibility(View.VISIBLE);
                    }
                }
                if (snapshot.child(TALENT).child(fUid).child(DESIGN).exists()) {
                    DesignTalent signing = snapshot.child(TALENT).child(fUid).child(DESIGN).getValue(DesignTalent.class);
                    if (Boolean.parseBoolean(signing.getIsDesign())){
                        teamDesign.setVisibility(View.VISIBLE);
                    }
                }
                if (snapshot.child(TALENT).child(fUid).child(MAKEUP).exists()) {
                    Makeup makeup = snapshot.child(TALENT).child(fUid).child(MAKEUP).getValue(Makeup.class);
                    if (Boolean.parseBoolean(makeup.getIsMakeup())){
                        teamMakeup.setVisibility(View.VISIBLE);
                    }
                }
                if (snapshot.child(TALENT).child(fUid).child(CLOTHES).exists()) {
                    Clothes clothes = snapshot.child(TALENT).child(fUid).child(CLOTHES).getValue(Clothes.class);
                    if (Boolean.parseBoolean(clothes.getIsClothes())){
                        teamClothes.setVisibility(View.VISIBLE);
                    }
                }
                if (snapshot.child(TALENT).child(fUid).hasChild(PHOTO)) {
                    PhotoTalent photoTalent = snapshot.child(TALENT).child(fUid).child(PHOTO).getValue(PhotoTalent.class);
                    if (Boolean.parseBoolean(photoTalent.getIsPhoto())){
                        teamPhoto.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FireDatabaseCatchError.getInstance().getError(getActivity(), error);
                FireDatabaseCatchError.getInstance().setError(FireAuthUserInfo.getUID(), error.getMessage());
            }
        });
    }

    private void UpdateProfile() {
        btnUpdateProfile.setOnClickListener(view -> getIntent(Act_editProfile.class));
    }

    private void getIntent(Class<?> clas) {
        Intent intent = new Intent(getActivity(), clas);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}