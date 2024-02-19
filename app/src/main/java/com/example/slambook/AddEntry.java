package com.example.slambook;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AddEntry extends AppCompatActivity implements View.OnClickListener, DialogOthersGender.DialogOthersGenderListener {

    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private byte[] selectedImage;

    private ImageView imageViewPic;
    private EditText editTextFn;
    private EditText editTextMn;
    private EditText editTextLn;
    private EditText editTextRemark;
    private EditText editTextBday;
    private RadioButton radioFemale;
    private RadioButton radioMale;
    private RadioButton radioOtherGender;
    private EditText editTextAddress;
    private EditText editTextContact;
    private EditText editTextHobbies;
    private EditText editTextGoals;
    private Button addBtn;
    private Button cancelBtn;
    private String gender = "female";
    private String getGender = "";

    private String fullName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String remark;
    private String birthday;
    private String address;
    private String contact;
    private String hobbies;
    private String goals;

    DatePickerDialog dateDialog;
    Person person;
    //SQLite
    private PersonDb personDb;
    private AccountDb accountDb;
    Accounts accounts;
    Intent intentUpdateList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_entry);

        this.accountDb = new AccountDb(this);
        this.personDb = new PersonDb(this);

        try {
            Uri uri = Uri.parse("android.resource://com.example.slambook/drawable/unisex");
            InputStream stream = getContentResolver().openInputStream(uri);
            selectedImage = getBytes(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Init();
    }

    private void Init(){
        imageViewPic = findViewById(R.id.add_capture);
        editTextFn = findViewById(R.id.edt_fn);
        editTextMn = findViewById(R.id.edt_mn);
        editTextLn = findViewById(R.id.edt_ln);
        editTextRemark = findViewById(R.id.remark);
        editTextBday = findViewById(R.id.birthday);
        radioFemale = (RadioButton) findViewById(R.id.rd_female);
        radioMale = (RadioButton)findViewById(R.id.rd_male);
        radioOtherGender = (RadioButton)findViewById(R.id.rd_others);
        editTextAddress = findViewById(R.id.address);
        editTextContact = findViewById(R.id.contactnum);
        editTextHobbies = findViewById(R.id.hobbies);
        editTextGoals = findViewById(R.id.goals);
        addBtn = findViewById(R.id.btn_addEntry);
        cancelBtn = findViewById(R.id.btn_cancel);

        if(radioFemale.isChecked()){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioFemale.setText(capFirstLetter + gender.substring(1));
            radioFemale.setChecked(true);
            getGender = radioFemale.getText().toString();
        }else if(radioMale.isChecked()){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioMale.setText(capFirstLetter + gender.substring(1));
            radioMale.setChecked(true);
            getGender = radioMale.getText().toString();
        }else if(radioOtherGender.isChecked()){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioOtherGender.setText(capFirstLetter + gender.substring(1));
            radioOtherGender.setChecked(true);
            getGender = radioOtherGender.getText().toString();
        }

        imageViewPic.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        //Gender Listener
        radioFemale.setOnClickListener(this);
        radioMale.setOnClickListener(this);
        radioOtherGender.setOnClickListener(this);

        //Date Listener
        editTextBday.setOnClickListener(this);
    }//End of Init

    public void datepickerdialog(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker v, int year, int month, int dayOfMonth) {
                editTextBday.setText(month+1+"/"+dayOfMonth+"/"+year);
            }
        }, year, month,day);
        editTextBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
    }

    public void Validation(){

        firstName = editTextFn.getText().toString();
        middleName = editTextMn.getText().toString();
        lastName = editTextLn.getText().toString();
        remark = editTextRemark.getText().toString();
        birthday = editTextBday.getText().toString();
        address = editTextAddress.getText().toString();
        contact = editTextContact.getText().toString();
        hobbies = editTextHobbies.getText().toString();
        goals = editTextGoals.getText().toString();

        if(IsError()){
            ShowErrorDialog();
        }else{
            AddList();
        }

    }

    boolean IsError(){
        if( firstName.equals("") || middleName.equals("") ||
            lastName.equals("") || remark.equals("") ||
            birthday.equals("") || address.equals("") ||
            contact.equals("") || gender.equals("") ||
            hobbies.isEmpty() || goals.equals("")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_addEntry:
                Validation();
                break;
            case R.id.add_capture:
                CaptureImage();
                break;
            case R.id.birthday:
                datepickerdialog();
                break;
            case R.id.rd_female:
                getGender = radioFemale.getText().toString();
                break;
            case R.id.rd_male:
                getGender = radioMale.getText().toString();
                break;
            case R.id.rd_others:
                openDialog();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }//End of OnClick

    public void openDialog() {
        DialogOthersGender exampleDialog = new DialogOthersGender();
        exampleDialog.show(getSupportFragmentManager(), "gender others dialog");
    }

    @Override
    public void applyTexts(String username) {
        radioOtherGender.setText(username);
        getGender = radioOtherGender.getText().toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    selectedImage = stream.toByteArray();
                    imageViewPic.setImageBitmap(photo);
                }
                break;
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        InputStream iStream = getContentResolver().openInputStream(uri);
                        selectedImage = getBytes(iStream);
                        imageViewPic.setImageURI(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }//switch
    }

    private void CaptureImage()
    {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            DialogForImages();
        }
    }

    private void DialogForImages()
    {
        String[] items = {"Take Photo", "Choose from gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Take Photo")){
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }else if(items[which].equals("Choose from gallery")){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , GALLERY_REQUEST);
                }else{
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public void AddList(){
        intentUpdateList = new Intent();
        String getFn = editTextFn.getText().toString();
        String getMn = editTextMn.getText().toString();
        String getLn = editTextLn.getText().toString();
        fullName = getFn + " " + getMn + " " + getLn;
        String getRemark = editTextRemark.getText().toString();
        String getBday = editTextBday.getText().toString();
        String getAddress = editTextAddress.getText().toString();
        String getContact = editTextContact.getText().toString();
        String getHobbies = editTextHobbies.getText().toString();
        String getGoals = editTextGoals.getText().toString();

        Bundle bundle = this.getIntent().getExtras();
        long accountID = bundle.getLong("add_person",0);

        person = personDb.createPerson(selectedImage,getFn,getMn,
                getLn,getRemark,getBday,
                getGender, getAddress,getContact,
                getHobbies,getGoals,accountID);

        intentUpdateList.putExtra("new_person", person);
        setResult(RESULT_OK, intentUpdateList);
        finish();
    }
    //=====================================================
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void ShowErrorDialog(){
        String str_fn = "First Name is Missing!\n";
        String str_mn = "\nMiddle Name is Missing!\n";
        String str_ln = "\nLast Name is Missing!\n";
        String str_remark = "\nRemark is Missing!\n";
        String str_birthday = "\nBirthday is Missing!\n";
        String str_genders = "\nGender is Missing!\n";
        String str_address = "\nAddress is Missing!\n";
        String str_contact = "\nContact is Missing!\n";
        String str_hobbies = "\nHobbies is Missing!\n";
        String str_goals = "\nGoals is Missing!\n";

        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Missing In Action!");
        error.setMessage((firstName.matches("") ? str_fn : str_fn.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (middleName.matches("") ? str_mn : str_mn.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (lastName.matches("") ? str_ln : str_ln.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (remark.matches("") ? str_remark : str_remark.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (birthday.matches("") ? str_birthday : str_birthday.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (getGender.matches("") ? str_genders : str_genders.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                //(address.matches("") ? str_address : str_address.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                //(contact.matches("") ? str_contact : str_contact.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (hobbies.matches("") ? str_hobbies : str_hobbies.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")));
                //(goals.matches("") ? str_goals : str_goals.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")));

        error.setPositiveButton("Okay",null);
        error.show();
    }


}//End of Activity
