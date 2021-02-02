package com.Softwillow.MarkArt.Model;

import android.os.Build;

import java.util.Calendar;

public class User {
    public String getKUid() {
        return KUid;
    }

    public void setKUid(String KUid) {
        this.KUid = KUid;
    }

    private String KUid;

    private String name;
    private String email;
    private String phone;
    private String password;
    private String birthDay;
    private String typeSex;
    private String location;
    private String personID;
    private String uriPersonal;
    private String uriDrawer;
    private String shutDown;
    private String theText;
    private String IsStaff;
    private String fullTime;
    private String modelPhone;

    public String getModelPhone() {
        return modelPhone;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getShutDown() {
        return shutDown;
    }

    public void setShutDown(String shutDown) {
        this.shutDown = shutDown;
    }

    public String getUriDrawer() {
        return uriDrawer;
    }

    public void setUriDrawer(String uriDrawer) {
        this.uriDrawer = uriDrawer;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String phone, String birthDay, String typeSex, String location, String personID, String uriPersonal) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDay = birthDay;
        this.typeSex = typeSex;
        this.location = location;
        this.personID = personID;
        this.uriPersonal = uriPersonal;
    }

    public User(String name, String email, String phone, String birthDay, String typeSex, String location, String personID, String uriPersonal, String theText) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDay = birthDay;
        this.typeSex = typeSex;
        this.location = location;
        this.personID = personID;
        this.uriPersonal = uriPersonal;
        this.theText = theText;
    }


    public String getTheText() {
        return theText;
    }

    public void setTheText(String theText) {
        this.theText = theText;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUriPersonal() {
        return uriPersonal;
    }

    public void setUriPersonal(String uriPersonal) {
        this.uriPersonal = uriPersonal;
    }


    public String getTypeSex() {
        return typeSex;
    }

    public void setTypeSex(String typeSex) {
        this.typeSex = typeSex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User() {
    }

    public User(String name, String password, String email, String phone, String birthDay, String typeSex, String fUid) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthDay = birthDay;
        this.typeSex = typeSex;
        this.KUid = fUid;
        this.IsStaff = "false";
        this.fullTime = Calendar.getInstance().getTime().toString();
        this.modelPhone = Build.MODEL;
    }


}
