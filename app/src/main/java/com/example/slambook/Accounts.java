package com.example.slambook;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Accounts implements Parcelable {

    private Uri uriImageProfile;
    private Bitmap bitmapImageProfile;
    private int intImageProfile;
//    private String username;
//    private String password;
    private String accountName;
    Person[] person;

    //Database
    private long mId;
    private byte[] byteUserPofilePic;
    private String username;
    private String password;
    private String email;
    private String bday;
    private String fullname;
    private String gender;
//    private String address;
//    private int contact;
//    private String stringBuildHobby;
//    private String seques1;
//    private String seques2;
//    private String seques3;


    public Accounts() {}

    public Accounts(byte[] userPofilePic, String username, String password, String email,
                    String bday, String fullname, String gender,
                    String address, int contact, String hobbies,
                    String seques1, String seques2, String seques3){
        this.byteUserPofilePic = userPofilePic;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bday = bday;
        this.fullname = fullname;
        this.gender = gender;
//        this.address = address;
//        this.contact = contact;
//        this.stringBuildHobby = hobbies;
//        this.seques1 = seques1;
//        this.seques2 = seques2;
//        this.seques3 = seques3;
    }

    public Accounts(String username, String password, String accountName, Person[] person){
        this.setUsername(username);
        this.setPassword(password);
        this.setAccountName(accountName);
        this.person = person;
    }

    public Accounts(String accountName, int intImageProfile){
        this.accountName = accountName;
        this.intImageProfile = intImageProfile;
    }

    public Accounts(String accountName, Bitmap profilePic){
        this.accountName = accountName;
        this.bitmapImageProfile = profilePic;
    }

    public Accounts(String accountName, Uri profilePic){
        this.accountName = accountName;
        this.uriImageProfile = profilePic;
    }

    public Accounts(String accountName, byte[] profilePic){
        this.accountName = accountName;
        this.byteUserPofilePic = profilePic;
    }


    public void setBitmapImageProfile(Bitmap bitmapImageProfile) { this.bitmapImageProfile = bitmapImageProfile;}
    public void setIntImageProfile(int intImageProfile) {
        this.intImageProfile = intImageProfile;
    }
    public void setUsername(String username){ this.username = username;}
    public void setPassword(String password){ this.password = password;}
    public void setAccountName(String accountName){ this.accountName = accountName;}
    public void setUriImageProfile(Uri uriImageProfile) {
        this.uriImageProfile = uriImageProfile;
    }

    public void setPerson(Person[] person) {
        this.person = person;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public void setByteUserPofilePic(byte[] byteUserPofilePic) {
        this.byteUserPofilePic = byteUserPofilePic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public void setContact(int contact) {
//        this.contact = contact;
//    }
//
//    public void setListHobbies(String stringBuildHobby) {
//        this.stringBuildHobby = stringBuildHobby;
//    }
//
//    public void setSeques1(String seques1) {
//        this.seques1 = seques1;
//    }
//
//    public void setSeques2(String seques2) {
//        this.seques2 = seques2;
//    }
//
//    public void setSeques3(String seques3) {
//        this.seques3 = seques3;
//    }

    public Uri getUriImageProfile() { return getUriImageProfile(); }
    public Bitmap getBitmapImageProfile() { return bitmapImageProfile; }
    public int getIntImageProfile() {
        return intImageProfile;
    }
    public String getUsername(){
        return  username;
    }
    public String getPassword(){
        return  password;
    }
    public String getAccountName(){
        return accountName;
    }

    public long getmId() {
        return mId;
    }

    public byte[] getByteUserPofilePic() {
        return byteUserPofilePic;
    }

    public String getEmail() {
        return email;
    }

    public String getBday() {
        return bday;
    }

    public String getFullname() {
        return fullname;
    }

    public String getGender() {
        return gender;
    }

//    public String getAddress() {
//        return address;
//    }
//
//    public int getContact() {
//        return contact;
//    }
//
//    public String getStringBuildHobby() {
//        return stringBuildHobby;
//    }
//
//    public String getSeques1() {
//        return seques1;
//    }
//
//    public String getSeques2() {
//        return seques2;
//    }
//
//    public String getSeques3() {
//        return seques3;
//    }

    protected Accounts(Parcel in) {
        this.mId = in.readLong();
        this.byteUserPofilePic = in.createByteArray();
        this.uriImageProfile = (Uri) in.readValue(Uri.class.getClassLoader());
        this.bitmapImageProfile = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        this.intImageProfile = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.accountName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeByteArray(byteUserPofilePic);
        dest.writeValue(uriImageProfile);
        dest.writeValue(bitmapImageProfile);
        dest.writeInt(intImageProfile);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(accountName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Accounts> CREATOR = new Parcelable.Creator<Accounts>() {
        @Override
        public Accounts createFromParcel(Parcel in) {
            return new Accounts(in);
        }

        @Override
        public Accounts[] newArray(int size) {
            return new Accounts[size];
        }
    };

}
