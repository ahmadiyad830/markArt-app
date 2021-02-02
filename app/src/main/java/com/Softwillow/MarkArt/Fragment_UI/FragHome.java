package com.Softwillow.MarkArt.Fragment_UI;

import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.Softwillow.MarkArt.Firebase.FireAuthUserInfo;
import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Model.Setting;
import com.Softwillow.MarkArt.R;
import com.Softwillow.MarkArt.Talent.ActWriter;
import com.Softwillow.MarkArt.Talent.ItemTalent;
import com.Softwillow.MarkArt.Talent.VarietyTalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;




public class FragHome extends Fragment implements ItemTalent, FirebaseVar, View.OnClickListener{
    private ImageView imgWriter, imgDraw, imgSigning, imgActor, imgPhoto, imgDesign, imgMakeup, imgClothes;
    private View view;
    private TextView txtIsOpen;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);

        imgWriter = view.findViewById(R.id.img_typeTalent_writer);
        imgDraw = view.findViewById(R.id.img_typeTalent_drawer);
        imgActor = view.findViewById(R.id.img_typeTalent_actor);
        imgSigning = view.findViewById(R.id.img_typeTalent_signing);
        imgPhoto = view.findViewById(R.id.img_photo);
        imgDesign = view.findViewById(R.id.img_design);
        imgMakeup = view.findViewById(R.id.img_makeup);
        imgClothes = view.findViewById(R.id.img_clothes);
        txtIsOpen = view.findViewById(R.id.txt_regPortal);

        imgWriter.setOnClickListener(this);
        imgDraw.setOnClickListener(this);
        imgActor.setOnClickListener(this);
        imgPhoto.setOnClickListener(this);
        imgSigning.setOnClickListener(this);
        imgDesign.setOnClickListener(this);
        imgMakeup.setOnClickListener(this);
        imgClothes.setOnClickListener(this);


//        start fragment help
        view.findViewById(R.id.ic_help_home).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_fragHome_to_menu_help_talent));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        TABLE_NATIVE.addValueEventListener(valueEventListener);
    }

    private ValueEventListener valueEventListener = TABLE_NATIVE.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            DataSnapshot snapTalent = snapshot.child(TALENT).child(FireAuthUserInfo.getUID());
//                if not open
            if (!isOpen(snapshot)) {
                txtIsOpen.setText(R.string.notOpen);
                imgWriter.setEnabled(false);
                imgDraw.setEnabled(false);
                imgActor.setEnabled(false);
                imgSigning.setEnabled(false);
                imgActor.setEnabled(false);
                imgDesign.setEnabled(false);
                imgMakeup.setEnabled(false);
                imgPhoto.setEnabled(false);
                imgClothes.setEnabled(false);

            } else if (isOpen(snapshot))
                txtIsOpen.setText(R.string.open);
            else {
//                    if user reg in talent disable this talent
                if (snapTalent.hasChild(WRITER))
                    imgWriter.setEnabled(false);
                if (snapTalent.hasChild(DRAW))
                    imgDraw.setEnabled(false);
                if (snapTalent.hasChild(SIGNING))
                    imgSigning.setEnabled(false);
                if (snapTalent.hasChild(ACING))
                    imgActor.setEnabled(false);
                if (snapTalent.hasChild(DESIGN))
                    imgDesign.setEnabled(false);
                if (snapTalent.hasChild(MAKEUP))
                    imgMakeup.setEnabled(false);
                if (snapTalent.hasChild(PHOTO))
                    imgPhoto.setEnabled(false);
                if (snapTalent.hasChild(CLOTHES))
                    imgClothes.setEnabled(false);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            FireDatabaseCatchError.getInstance().getError(getActivity(), error);
            if (FireAuthUserInfo.getUID() != null)
                FireDatabaseCatchError.getInstance().setError("home", error.getMessage());
        }
    });

    private boolean isOpen(DataSnapshot snapshot) {
//        return true if open
//        false if not open
        Setting setting;
        setting = snapshot.child(SETTING).getValue(Setting.class);
        return setting != null && Boolean.parseBoolean(setting.getIsOpen());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        if (id == R.id.img_typeTalent_writer) {
            intent = new Intent(getActivity(), ActWriter.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.img_typeTalent_drawer) {
            intent = new Intent(getActivity(), VarietyTalent.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(TYPE, DRAW);
            startActivity(intent);
        } else if (id == R.id.img_typeTalent_actor) {
            intent = new Intent(getActivity(), VarietyTalent.class);
            intent.putExtra(TYPE, ACING);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.img_typeTalent_signing) {
            intent = new Intent(getActivity(), VarietyTalent.class);
            intent.putExtra(TYPE, SIGNING);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.img_photo) {
            intent = new Intent(getActivity(), VarietyTalent.class);
            intent.putExtra(TYPE, PHOTO);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.img_design) {
            intent = new Intent(getActivity(), VarietyTalent.class);
            intent.putExtra(TYPE, DESIGN);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.img_makeup) {
            intent = new Intent(getActivity(), VarietyTalent.class);
            intent.putExtra(TYPE, MAKEUP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.img_clothes) {
            intent = new Intent(getActivity(), VarietyTalent.class);
            intent.putExtra(TYPE, CLOTHES);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TABLE_NATIVE.removeEventListener(valueEventListener);
    }
}