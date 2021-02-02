package com.Softwillow.MarkArt.Model.Talents;


import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Talent implements FirebaseVar {
    private String fullTime;
    private String id;
    private String imgPersonal;
    private String KUID;
    private String isWriter, isDrawer;
    private String imgDrawer;
    private String nameFacebook;


    public String getImgDrawer() {
        return imgDrawer;
    }

    public void setImgDrawer(String imgDrawer) {
        this.imgDrawer = imgDrawer;
    }

    public String getIsWriter() {
        return isWriter;
    }

    public void setIsWriter(String isWriter) {
        this.isWriter = isWriter;
    }

    private String location;
    private String name;
    private String script;

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPersonal() {
        return imgPersonal;
    }

    public void setImgPersonal(String imgPersonal) {
        this.imgPersonal = imgPersonal;
    }

    public String getKUID() {
        return KUID;
    }

    public void setKUID(String KUID) {
        this.KUID = KUID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public Talent() {

    }

    public String getIsDrawer() {
        return isDrawer;
    }

    public void setIsDrawer(String isDrawer) {
        this.isDrawer = isDrawer;
    }


    public void setScript(String script) {
        this.script = script;
    }

    public String getNameFacebook() {
        return nameFacebook;
    }



    //    constructor talent
    public Talent(String id, String location, String nameFacebook) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.location = location;
        this.imgPersonal =firebaseUser.getPhotoUrl().toString();
        this.nameFacebook = nameFacebook;
        this.id = id;
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        this.fullTime = df.format(date);
        this.KUID = FirebaseAuth.getInstance().getUid();

        this.name = firebaseUser.getDisplayName();

    }


}
