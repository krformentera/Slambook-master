package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Registration extends AppCompatActivity implements View.OnClickListener, DialogOthersGender.DialogOthersGenderListener {
    //Camera
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private ImageView imageViewCapture;
    private byte[] selectedImage = null;

    //Edit Text
    private EditText editUsername;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private EditText editEmailAddress;
    private EditText editLn;
    private EditText editFn;
    private EditText editMn;
//    private EditText editStreet;
//    private EditText editHouseNum;
//    private EditText editPhone;
//    private EditText editSecurityQ1;
//    private EditText editSecurityQ2;
//    private EditText editSecurityQ3;

    //Radio Button
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private RadioButton rbOthers;

    //Button
    private Button btnRegister;
    private Button btnLogin;
    private EditText editClickableBday;

    //CheckBox
//    private CheckBox[] checkHobbies = new CheckBox[10];

    //Username -> Bday
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String bday;

    //FullName
    private String lastName;
    private String firstName;
    private String middleName;
    private String fullName;
    private String gender = "";

    //Address
    private String street;
    private String houseNum;
    private String barangay;
    private String municipality;
    private String province;
    private String address;
    SpinnersActivity mySpinners = new SpinnersActivity(this);

    //Contact -> Security Question
    private String contact;
    private String selected;
    private StringBuilder stringBuildHobby = new StringBuilder();
    private List<String> listHobbies = new ArrayList<String>();
    private String securityQ1;
    private String securityQ2;
    private String securityQ3;
    private String secQuesText1;
    private String secQuesText2;
    private String secQuesText3;

    DatePickerDialog dateDialog;

    Accounts accounts = new Accounts("", R.drawable.woman);

    //SQLite
    DBHelper dbconn;
    boolean checkInsertOnce;

    private AccountDb accountDb;
    Accounts createdAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        this.accountDb = new AccountDb(this);
        try {
            Uri uri = Uri.parse("android.resource://com.example.slambook/drawable/unisex");
            InputStream stream = getContentResolver().openInputStream(uri);
            selectedImage = getBytes(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialization();
    }

    public void initialization(){
        //Username -> Bday
        editUsername = findViewById(R.id.edttxt_usrname);
        editPassword = findViewById(R.id.edttxt_pass);
        editConfirmPassword = findViewById(R.id.edttxt_cnfrmpass);
        editEmailAddress = findViewById(R.id.edttxt_email);
        editClickableBday = findViewById(R.id.birthday);

        //Full Name
        editLn = findViewById(R.id.edttxt_lastName);
        editFn = findViewById(R.id.edttxt_firstName);
        editMn = findViewById(R.id.edttxt_middleName);

        //Gender
        rbMale = findViewById(R.id.rd_m);
        rbFemale = findViewById(R.id.rd_f);
        rbOthers = findViewById(R.id.rd_o);

//        //Address
//        editStreet = findViewById(R.id.edttxt_street);
//        editHouseNum = findViewById(R.id.edttxt_housenmbr);
//        mySpinners.MySpinner();
//
//        //Contact -> Security Question
//        editPhone = findViewById(R.id.edit_text_phone);
//        checkHobbies[0] = findViewById(R.id.id_Sleepingcheckbox);
//        checkHobbies[1] = findViewById(R.id.id_DancingcheckBox);
//        checkHobbies[2] = findViewById(R.id.id_CookingcheckBox);
//        checkHobbies[3] = findViewById(R.id.id_SingingcheckBox);
//        checkHobbies[4] = findViewById(R.id.id_BakingcheckBox);
//        checkHobbies[5] = findViewById(R.id.id_GardeningcheckBox);
//        checkHobbies[6] = findViewById(R.id.id_WritingcheckBox);
//        checkHobbies[7] = findViewById(R.id.id_ReadingcheckBox);
//        checkHobbies[8] = findViewById(R.id.id_WatchingcheckBox);
//        checkHobbies[9] = findViewById(R.id.id_PunchingcheckBox);
//        editSecurityQ1 = findViewById(R.id.id_SQ1answer);
//        editSecurityQ2 = findViewById(R.id.id_SQ2answer);
//        editSecurityQ3 = findViewById(R.id.id_SQ3answer);

        //Button
        btnRegister = findViewById(R.id.id_registerButton);
        btnLogin = findViewById(R.id.id_loginButton);
        //Camera
        imageViewCapture = findViewById(R.id.capture);

        //listener
        imageViewCapture.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        editClickableBday.setOnClickListener(this);
        OnClickRadioButton();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_registerButton:
                Validation();
                break;
            case R.id.id_loginButton:
                OpenHomeActivity();
            case R.id.birthday:
                datepickerdialog();
                break;
//            case R.id.id_Sleepingcheckbox:
//            case R.id.id_DancingcheckBox:
//            case R.id.id_CookingcheckBox:
//            case R.id.id_SingingcheckBox:
//            case R.id.id_BakingcheckBox:
//            case R.id.id_GardeningcheckBox:
//            case R.id.id_WritingcheckBox:
//            case R.id.id_ReadingcheckBox:
//            case R.id.id_WatchingcheckBox:
//            case R.id.id_PunchingcheckBox:
//                CheckBox cb1 = (CheckBox) v;
//                selected = cb1.getText().toString();
//                if(cb1.isChecked()){
//                    listHobbies.add(selected);
//                }else{
//                    listHobbies.remove(selected);
//                }
//            break;
            case R.id.capture:
                CaptureImage();
            break;
        }

    }

    public void OnClickRadioButton(){
        final RadioGroup group= (RadioGroup) findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rd_m:
                        gender = rbMale.getText().toString();
                        break;
                    case R.id.rd_f:
                        gender = rbFemale.getText().toString();
                        break;
                    case R.id.rd_o:
                        openDialog();
                        break;
                }
            }
        });
    }

    public void openDialog() {
        DialogOthersGender exampleDialog = new DialogOthersGender();
        exampleDialog.show(getSupportFragmentManager(), "gender others dialog");
    }

    @Override
    public void applyTexts(String username) {
        rbOthers.setText(username);
        gender = rbOthers.getText().toString();
    }

    public void datepickerdialog(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker v, int year, int month, int dayOfMonth) {
                editClickableBday.setText(month+1+"/"+dayOfMonth+"/"+year);
            }
        }, year, month,day);
        editClickableBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
    }

    public void Validation(){
        //Username -> Bday
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        confirmPassword = editConfirmPassword.getText().toString().trim();
        email = editEmailAddress.getText().toString().trim();
        bday = editClickableBday.getText().toString();
        //Full Name
        lastName = editLn.getText().toString().trim();
        firstName = editFn.getText().toString().trim();
        middleName = editMn.getText().toString().trim();
        fullName = lastName + firstName + middleName;

        //Address
//        street = editStreet.getText().toString().trim();
//        houseNum = editHouseNum.getText().toString().trim();
        barangay = mySpinners.Barangay();
        municipality = mySpinners.Municipality();
        province = mySpinners.Province();
        address = street + " " + houseNum + " " + barangay + "," + municipality + "," + province;
        //Contact -> Security Question
//        contact = editPhone.getText().toString();
//        securityQ1 = editSecurityQ1.getText().toString().trim();
//        securityQ2 = editSecurityQ2.getText().toString().trim();
//        securityQ3 = editSecurityQ3.getText().toString().trim();
//        secQuesText1 = mySpinners.SecQ1Method();
        secQuesText2 = mySpinners.SecQ2Method();
        secQuesText3 = mySpinners.SecQ3Method();

        if(!password.equals(confirmPassword)){
            ShowPasswordDialog();
        }else if(IsError()){
             ShowErrorDialog();
            //listHobbies.clear();
        }
        else{
            for (String s : listHobbies)
            {
                stringBuildHobby.append(s).toString();
                stringBuildHobby.append(" ");
            }
            if(!checkInsertOnce)
            {
                // add the account to database
                createdAccount = accountDb.createAccount(
                        selectedImage,username,password,email,
                        bday,fullName,gender,address,contact,stringBuildHobby,
                        securityQ1,securityQ2,securityQ3);

            }
             ShowSuccessDialog();
        }
    }// end of ValidationMethod


    boolean IsError(){
        if( username.equals("") || password.equals("") ||
                confirmPassword.equals("") || email.equals("") ||
                bday.equals("") || gender.equals("")){
            return true;
        }else{
            return false;
        }
    }

    public void ShowErrorDialog(){
        String user = "Username is Missing!\n";
        String pass = "\nPassword is Missing!\n";
        String confirmPass = "\nConfirmPassword is Missing!\n";
        String emailAdd = "\nEmail Address is Missing!\n";
        String birthday = "\nBirthday is Missing!\n";
        String gendrs = "\nGender is Missing!\n";
        String fn = "\nFirstName is Missing!\n";
        String ln = "\nLastName is Missing!\n";
//        String strt = "\nStreet is Missing!\n";
//        String houseNumber = "\nHouse Number is Missing!\n";
//        String phone = "\nContact is Missing!\n";
//        String hby = "\nHobby is Missing!\n";
//        String q1 = "\nSecurity Question 1 is Missing!\n";
//        String q2 = "\nSecurity Question 2 is Missing!\n";
//        String q3 = "\nSecurity Question 3 is Missing!\n";

        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Error/s!");
        error.setMessage(   (username.matches("") ? user : user.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (password.matches("") ? pass : pass.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (confirmPassword.matches("") ? confirmPass : confirmPass.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (email.matches("") ? emailAdd : emailAdd.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (bday.matches("") ? birthday : birthday.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (gender.matches("") ? gendrs : gendrs.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (firstName.matches("") ? fn : fn.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (lastName.matches("") ? ln : ln.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")));

        error.setPositiveButton("Okay",null);
        error.show();
    }


    public void ShowSuccessDialog(){
        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Success!");
        error.setMessage("Username: \n" + username + "\n" +
                "\nPassword: \n" + password + "\n" +
                "\nEmailAddress: \n" + email + "\n" +
                "\nBirthday: \n" + bday + "\n" +
                (fullName.matches("") ? "\nFullName:*\n" : "\nFullName: \n" +(fullName = lastName + " " + firstName + " " + middleName)) + "\n" +
                "\nGender: \n" + gender);
        error.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stringBuildHobby.delete(0, stringBuildHobby.length());
                Toast.makeText(getApplicationContext(), "Registration Was Successful!", Toast.LENGTH_SHORT).show();
                btnLogin.setEnabled(true);
                btnLogin.setAlpha(1);
            }
        });
        error.show();
    }// end of ShowSuccessDialog

    public void ShowPasswordDialog(){
        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Password Error!");
        error.setMessage("Password and Confirm Password did not match");
        error.setPositiveButton("Okay",null);
        error.show();
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
                    imageViewCapture.setImageBitmap(photo);
                    try {
                        SaveImage(photo, "happy");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        InputStream iStream =   getContentResolver().openInputStream(uri);
                        selectedImage = getBytes(iStream);
                        imageViewCapture.setImageURI(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }//switch
    }// End of ActivityResult Curly Braces

    private void SaveImage(Bitmap bitmap, @NonNull String name) throws IOException {
        boolean saved;
        OutputStream fos;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = this.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "SLAMBOOK");
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(imageUri);
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + "SLAMBOOK";

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            File image = new File(imagesDir, name + ".png");
            fos = new FileOutputStream(image);

        }
        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
    }

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


    private void CaptureImage()
    {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
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


    private void OpenHomeActivity()
    {
        Intent intent = new Intent(Registration.this, HomeActivity.class);
        intent.putExtra(HomeActivity.EXTRA_ADDED_ACCOUNT, createdAccount);
        intent.putExtra("RegisteredUser", username);
        startActivity(intent);
        finish();
    }

}

