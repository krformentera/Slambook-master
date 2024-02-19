package com.example.slambook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewEntry extends AppCompatActivity {

    private PersonDb personDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_entry_list);
        this.personDb = new PersonDb(this);
        Init();
    }

    private void Init (){
        Bundle bundle = this.getIntent().getExtras();

        Person person = bundle.getParcelable("ViewList");
        ImageView profilePicImageView = findViewById(R.id.profile_pic);
        TextView textViewName = findViewById(R.id.txt_fn);
        TextView textViewRemark = findViewById(R.id.txt_remark);
        TextView textViewBday = findViewById(R.id.txt_bday);
        TextView textViewGender = findViewById(R.id.txt_gender);
        TextView textViewAddress = findViewById(R.id.txt_address);
        TextView textViewContact = findViewById(R.id.txt_contact);
        TextView textViewHobbies = findViewById(R.id.txt_hobbies);
        TextView textViewGoals = findViewById(R.id.txt_goals);
//
//        if (person.getBitmapImage()!=null) {
//            profilePicImageView.setImageBitmap(person.getBitmapImage());
//        } else {
//            profilePicImageView.setImageResource(person.getProfilePic());
//        }
        byte[] byteImg = person.getByteProfilePic();
        Bitmap bitmapImages = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
        profilePicImageView.setImageBitmap(bitmapImages);
        String fullname = person.getFn() + " " + person.getMn() + " " + person.getLn();
        textViewName.setText(fullname);
        textViewRemark.setText(person.getRemark());
        textViewBday.setText(person.getBirthday());
        textViewGender.setText(person.getGender());
        textViewAddress.setText(person.getAddress());
        textViewContact.setText(person.getContact());
        textViewHobbies.setText(person.getHobbies());
        textViewGoals.setText(person.getGoals());

        Button btnUpdate = findViewById(R.id.btn_addEntry);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
