package com.example.slambook;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class EditEntry extends AppCompatActivity implements View.OnClickListener, DialogOthersGender.DialogOthersGenderListener {

    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
//    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
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
    private Button updateBtn;
    private Button cancelBtn;
    private String gender = "";
    private String getGender = "";

    private String name;
    private String remark;
    private String birthday;
    private String address;
    private String contact;
    private String hobbies;
    private String goals;

    Person person;
    PersonDb personDb;

    int positionOfPeopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entry_list);
        this.personDb = new PersonDb(this);
        Init();
    }

    private void Init(){
        Intent intent = getIntent();
        positionOfPeopleList = intent.getIntExtra(HomeActivity.POSITION, 0);

        person = intent.getParcelableExtra(HomeActivity.PEOPLE_LIST);

        byte[] newProfile = person.getByteProfilePic();
        String fn = person.getFn();
        String mn = person.getMn();
        String ln = person.getLn();
        String remark = person.getRemark();
        String birthday = person.getBirthday();
        gender = person.getGender().toLowerCase();
        String address = person.getAddress();
        String contact = person.getContact();
        String hobbies = person.getHobbies();
        String goals = person.getGoals();

        imageViewPic = findViewById(R.id.capture);
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
        updateBtn = findViewById(R.id.btn_editEntry);
        cancelBtn = findViewById(R.id.btn_cancel);


//        if(person.getBitmapImage() != null){
//            imageViewPic.setImageBitmap(newProfile);
//        }else{
//            imageViewPic.setImageResource(profilePic);
//        }
        Bitmap bitmapImages = BitmapFactory.decodeByteArray(newProfile, 0, newProfile.length);
        imageViewPic.setImageBitmap(bitmapImages);
        editTextFn.setText(fn);
        editTextMn.setText(mn);
        editTextLn.setText(ln);
        editTextRemark.setText(remark);
        if(gender.equals("female")){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioFemale.setText(capFirstLetter + gender.substring(1));
            radioFemale.setChecked(true);
            getGender = radioFemale.getText().toString();
        }else if(gender.equals("male")){
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioMale.setText(capFirstLetter + gender.substring(1));
            radioMale.setChecked(true);
            getGender = radioMale.getText().toString();
        }else{
            char capFirstLetter = gender.toUpperCase().charAt(0);
            radioOtherGender.setText(capFirstLetter + gender.substring(1));
            radioOtherGender.setChecked(true);
            getGender = radioOtherGender.getText().toString();
        }

        editTextBday.setText(birthday);
        editTextAddress.setText(address);
        editTextContact.setText(contact);
        editTextHobbies.setText(hobbies);
        editTextGoals.setText(goals);

        imageViewPic.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        radioFemale.setOnClickListener(this);
        radioMale.setOnClickListener(this);
        radioOtherGender.setOnClickListener(this);
    }//End of Init

    public void Validation(){

        name = editTextFn.getText().toString();
        remark = editTextRemark.getText().toString();
        birthday = editTextBday.getText().toString();
        address = editTextAddress.getText().toString();
        contact = editTextContact.getText().toString();
        hobbies = editTextHobbies.getText().toString();
        goals = editTextGoals.getText().toString();

        if(IsError()){
            ShowErrorDialog();
        }else{
            UpdateList();
        }

    }

    boolean IsError(){
        if( name.equals("") || remark.equals("") ||
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
            case R.id.btn_editEntry:
                Validation();
                break;
            case R.id.capture:
                CaptureImage();
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
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageViewPic.setImageBitmap(photo);
            person.setBitmapImage(photo);
        }
    }



    public void UpdateList(){
        Bitmap bitmap = ((BitmapDrawable) imageViewPic.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        String getFn = editTextFn.getText().toString();
        String getMn = editTextMn.getText().toString();
        String getLn = editTextLn.getText().toString();
        String fullname = getFn + " " + getMn +" " + getLn;
        String getRemark = editTextRemark.getText().toString();
        String getBday = editTextBday.getText().toString();
        String getAddress = editTextAddress.getText().toString();
        String getContact = editTextContact.getText().toString();
        String getHobbies = editTextHobbies.getText().toString();
        String getGoals = editTextGoals.getText().toString();

        person.setByteProfilePic(imageInByte);
        person.setFn(getFn);
        person.setRemark(getRemark);
        person.setGender(getGender);
        person.setBirthday(getBday);
        person.setAddress(getAddress);
        person.setContact(getContact);
        person.setHobbies(getHobbies);
        person.setGoals(getGoals);
        Intent intentUpdateList = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        long personId = bundle.getLong("person_id");
        personDb.UpdatePerson(personId,imageInByte,getFn,getMn,getLn,getRemark,getGender,getBday,
                            getAddress,getContact,getHobbies,getGoals);

        intentUpdateList.putExtra("update_list", positionOfPeopleList);
        intentUpdateList.putExtra("edit_person", person);
        setResult(RESULT_OK, intentUpdateList);
        finish();
    }


    public void ShowErrorDialog(){
        String str_name = "Name is Missing!\n";
        String str_remark = "\nRemark is Missing!\n";
        String str_birthday = "\nBirthday is Missing!\n";
        String str_genders = "\nGender is Missing!\n";
        String str_address = "\nAddress Address is Missing!\n";
        String str_contact = "\nContact is Missing!\n";
        String str_hobbies = "\nHobbies is Missing!\n";
        String str_goals = "\nGoals is Missing!\n";


        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setTitle("Error/s!");
        error.setMessage(   (name.matches("") ? str_name : str_name.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (remark.matches("") ? str_remark : str_remark.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (birthday.matches("") ? str_birthday : str_birthday.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (gender.matches("") ? str_genders : str_genders.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (address.matches("") ? str_address : str_address.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (contact.matches("") ? str_contact : str_contact.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (hobbies.matches("") ? str_hobbies : str_hobbies.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")) +
                (goals.matches("") ? str_goals : str_goals.replaceAll("[\n]", "").replaceAll("([a-z])", "").replaceAll("([A-Z])", "").replaceAll("!", "")));

        error.setPositiveButton("Okay",null);
        error.show();
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


}//End of Activity
