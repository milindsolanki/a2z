package com.a2z.deliver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DriverDetails implements Serializable {


    private String imageBackDL = null;
    private String imageFrontDL = null;
    private String imageBackRC;
    private String imageFrontRC;
    private String imageID1;
    private String imageIP1;
    private String imageIP2;
    private String imageIP3;
    private String imageIP4;
    private String firstname;
    private String lastname;
    private String mobilenumber;


    public String getImageBackDL() {
        return imageBackDL;
    }

    public void setImageBackDL(String imageBackDL) {
        this.imageBackDL = imageBackDL;
    }

    public String getImageFrontDL() {
        return imageFrontDL;
    }

    public void setImageFrontDL(String imageFrontDL) {
        this.imageFrontDL = imageFrontDL;
    }

    public String getImageBackRC() {
        return imageBackRC;
    }

    public void setImageBackRC(String imageBackRC) {
        this.imageBackRC = imageBackRC;
    }

    public String getImageFrontRC() {
        return imageFrontRC;
    }

    public void setImageFrontRC(String imageFrontRC) {
        this.imageFrontRC = imageFrontRC;
    }

    public String getImageID1() {
        return imageID1;
    }

    public void setImageID1(String imageID1) {
        this.imageID1 = imageID1;
    }

    public String getImageIP1() {
        return imageIP1;
    }

    public void setImageIP1(String imageIP1) {
        this.imageIP1 = imageIP1;
    }

    public String getImageIP2() {
        return imageIP2;
    }

    public void setImageIP2(String imageIP2) {
        this.imageIP2 = imageIP2;
    }

    public String getImageIP3() {
        return imageIP3;
    }

    public void setImageIP3(String imageIP3) {
        this.imageIP3 = imageIP3;
    }

    public String getImageIP4() {
        return imageIP4;
    }

    public void setImageIP4(String imageIP4) {
        this.imageIP4 = imageIP4;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    @Override
    public String toString() {
        return "{" +
                "imageBackDL='" + imageBackDL + '\'' +
                ", imageFrontDL='" + imageFrontDL + '\'' +
                ", imageBackRC='" + imageBackRC + '\'' +
                ", imageFrontRC='" + imageFrontRC + '\'' +
                ", imageID1='" + imageID1 + '\'' +
                ", imageIP1='" + imageIP1 + '\'' +
                ", imageIP2='" + imageIP2 + '\'' +
                ", imageIP3='" + imageIP3 + '\'' +
                ", imageIP4='" + imageIP4 + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mobilenumber='" + mobilenumber + '\'' +
                '}';
    }
}
