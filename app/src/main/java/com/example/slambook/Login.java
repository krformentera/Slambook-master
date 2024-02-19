
package com.example.slambook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    protected EditText usernameEdit;
    protected EditText passwordEdit;
    protected Button loginBtn;
    protected Button registerBtn;
    protected Toast popUp;
    protected String name, pass;
    Accounts[] accountsArray = new Accounts[3];
    protected ArrayList<Accounts> accountsArrayList = new ArrayList<>();
    Intent myIntent;
    Accounts accounts = new Accounts("", R.drawable.woman);

    //SQLite
    protected AccountDb accountDb;
    protected PersonDb personDb;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.accountDb = new AccountDb(this);
        this.personDb = new PersonDb(this);
        helper = new DBHelper(this);
        init();
    }

    private void init()
    {
    accountsArrayList.add(accountsArray[1]);
        //Button
        loginBtn = findViewById(R.id.id_LoginButton);
        registerBtn = findViewById(R.id.id_RegisterButton);

        //EditText
        usernameEdit = findViewById(R.id.edt_username);
        passwordEdit = findViewById(R.id.edt_pass);

        //Listener
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        name = usernameEdit.getText().toString().trim();
        pass = passwordEdit.getText().toString().trim();
        switch (v.getId()) {
            case R.id.id_LoginButton:
                showToast();
                break;
            case R.id.id_RegisterButton:
                showRegistration();
                break;
        }
    }

    public void showToast() {
        if (name.matches("") || pass.matches("")) {
            if (name.matches("")) {
                Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                usernameEdit.setHint("Put your username here!");
            }

            if (pass.matches("")) {
                Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
                passwordEdit.setHint("Put your password here!");
            }
        } else {
            if (accountDb.CheckUser(name,pass)) {
                CustomToast();
                myIntent = new Intent(this, HomeActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Intent();
                startActivity(myIntent);
                finish();
            } else {
                AlertDialag();
            }
        }
    }//end of showToast CURLY BRACES

    public void CustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
        popUp = new Toast(getApplicationContext());
        popUp.setGravity(Gravity.BOTTOM, 0, 50);
        popUp.setDuration(Toast.LENGTH_LONG);
        popUp.setView(layout);
        popUp.show();
    }//end of CustomToast CURLY BRACES

    public void AlertDialag() {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Credential does not match");
        bldg.setMessage("Username or Password is Incorrect!");
        bldg.setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                usernameEdit.setText("");
                passwordEdit.setText("");
            }
        });
        bldg.show();
    }//end of AlertDialag CURLY BRACES

    public void showRegistration() {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Does not have an Account yet?");
        bldg.setMessage("Do you want to go to Registration Form");
        bldg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Login.this, "Going to Registration Form:", Toast.LENGTH_LONG).show();
                Intent loginIntent = new Intent(Login.this, Registration.class);
                startActivity(loginIntent);
            }
        });
        bldg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Login.this, "Cancelled so sad!", Toast.LENGTH_LONG).show();
            }
        });
        bldg.show();
    }//end of showRegistration CURLY BRACES

    public void Intent(){
        myIntent.putExtra("username", name);
        myIntent.putExtra("password", pass);
        long sendUsername = accountDb.Sender(name);
        myIntent.putExtra(HomeActivity.EXTRA_ADDED_ACCOUNT, (Parcelable) accountDb.getAccountId(sendUsername));
        myIntent.putExtra(HomeActivity.EXTRA_ADDED_PERSON, sendUsername);
    }
}
