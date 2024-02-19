package com.example.slambook;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Person implements Parcelable {

    private long id;
    private byte[] byteProfilePic;
    private int profilePic;
    private Bitmap bitmapImage;
    private String fn;
    private String mn;
    private String ln;
    private String name;
    private String remark;
    private String birthday;
    private String gender;
    private String address;
    private String contact;
    private String hobbies;
    private String goals;
    private Accounts accounts;

    public Person() {}

    public Person(byte[] profilePic, String fn, String mn, String ln, String remark, String birthday,
                  String gender, String address, String contact, String hobbies,
                  String goals) {
        this.byteProfilePic = profilePic;
        this.fn = fn;
        this.mn = mn;
        this.ln = ln;
        this.remark = remark;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.hobbies = hobbies;
        this.goals = goals;
    }

//    public Person(int profilePic, String name, String remark, String birthday, String gender, String address, String contact, String hobbies, String goals) {
//        this.profilePic = profilePic;
//        this.name = name;
//        this.remark = remark;
//        this.birthday = birthday;
//        this.gender = gender;
//        this.address = address;
//        this.contact = contact;
//        this.hobbies = hobbies;
//        this.goals = goals;
//    }

//    public Person(Bitmap bitmapImage, String name, String remark, String birthday, String gender, String address, String contact, String hobbies, String goals) {
//        this.bitmapImage = bitmapImage;
//        this.name = name;
//        this.remark = remark;
//        this.birthday = birthday;
//        this.gender = gender;
//        this.address = address;
//        this.contact = contact;
//        this.hobbies = hobbies;
//        this.goals = goals;
//    }

    //=======================SETTERSSSSSSSSSSS
    public void setId(long id) {
        this.id = id;
    }

    public void setByteProfilePic(byte[] byteProfilePic) {
        this.byteProfilePic = byteProfilePic;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public void setBitmapImage(Bitmap bitmapImage) { this.bitmapImage = bitmapImage; }

    public void setProfilePic(int profilePic) { this.profilePic = profilePic;}

    public void setAccountName(String accountName) {
        this.name = accountName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public void setGoals(String goals) { this.goals = goals; }

    public void setAccounts(Accounts accounts) { this.accounts = accounts; }

    //=======================GETTERSSSSSSSSSS
    public long getId() {
        return id;
    }
    public byte[] getByteProfilePic() {
        return byteProfilePic;
    }

    public String getFn() {
        return fn;
    }

    public String getMn() {
        return mn;
    }

    public String getLn() {
        return ln;
    }

    public Bitmap getBitmapImage() { return bitmapImage; }

    public int getProfilePic() {
        return profilePic;
    }

    public String getAccountName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getGoals() { return goals; }

    public Accounts getAccounts() { return accounts; }

    // Parcelable
    public Person(Parcel in){
//        this.profilePic = in.readInt();
//        this.bitmapImage = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        this.id = in.readLong();
        this.byteProfilePic = in.createByteArray();
        this.fn = in.readString();
        this.mn = in.readString();
        this.ln = in.readString();
        this.remark = in.readString();
        this.birthday = in.readString();
        this.gender = in.readString();
        this.address = in.readString();
        this.contact = in.readString();
        this.hobbies = in.readString();
        this.goals = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeByteArray(this.byteProfilePic);
        parcel.writeString(this.fn);
        parcel.writeString(this.mn);
        parcel.writeString(this.ln);
        parcel.writeString(this.remark);
        parcel.writeString(this.birthday);
        parcel.writeString(this.gender);
        parcel.writeString(this.address);
        parcel.writeString(this.contact);
        parcel.writeString(this.hobbies);
        parcel.writeString(this.goals);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
