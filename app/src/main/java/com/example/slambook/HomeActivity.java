package com.example.slambook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager myLayoutManager;

    private RecyclerView recyclerViewAccount;
    private RecyclerView.LayoutManager myLayoutManagerAccount;

    public static final String POSITION = "com.example.slambook.POSITION";
    public static final String PEOPLE_LIST = "com.example.slambook.PEOPLE_LIST";
    //Add the Person objects to an ArrayList
    ArrayList<Person> peopleList = new ArrayList<>();

    PersonListAdapter personListAdapter;
    //Add the Accounts objects to an ArrayList
    ArrayList<Accounts> accountList = new ArrayList<>();

    AccountListAdapter accountListAdapter;
    public static final int REQUEST_CODE_ADD_COMPANY = 40;
    public static final String EXTRA_ADDED_ACCOUNT = "extra_key_added_account";
    public static final String EXTRA_ADDED_PERSON = "extra_key_added_person";

    //Database
    private AccountDb accountDb;
    private PersonDb personDb;
    long createdPerson;
    long sendRegister;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_list);

        this.accountDb = new AccountDb(this);
        this.personDb = new PersonDb(this);
        Init();
    }

    protected void Init(){
        Intent intent = getIntent();

        if(intent != null){
            Accounts createdAccount = intent.getParcelableExtra(EXTRA_ADDED_ACCOUNT);
            createdPerson = intent.getLongExtra(EXTRA_ADDED_PERSON,0);

            if(intent.hasExtra("RegisteredUser")){
                String registeredUsername = intent.getStringExtra("RegisteredUser");
                sendRegister = accountDb.Sender(registeredUsername);
            }

            if(peopleList != null){
                peopleList = (ArrayList<Person>) personDb.getAllPeople(createdPerson);
            }
            accountList.add(createdAccount);


            recyclerView = findViewById(R.id.recycleView);
            recyclerView.setHasFixedSize(true);
            myLayoutManager = new LinearLayoutManager(this);
            personListAdapter = new PersonListAdapter(this, peopleList);
            recyclerView.setLayoutManager(myLayoutManager);
            recyclerView.setAdapter(personListAdapter);
            personListAdapter.setOnItemClickListener(new PersonListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String personFn = peopleList.get(position).getFn();
                    long getPersonId = personDb.SenderPerson(personFn);

                    Intent intentToViewEntryAct = new Intent(HomeActivity.this, ViewEntry.class);
                        intentToViewEntryAct.putExtra("ViewPerson", getPersonId);
                        intentToViewEntryAct.putExtra("ViewList", peopleList.get(position));
                        startActivity(intentToViewEntryAct);
                }
            });

            personListAdapter.setOnClickListener(new PersonListAdapter.OnClickListener() {
                @Override
                public void OnClickListener(int position) {
                    DeleteList(position);
                }
            });

            personListAdapter.setOnClickListener2(new PersonListAdapter.OnClickListener() {
                @Override
                public void OnClickListener(int position) {
                    String personFn = peopleList.get(position).getFn();
                    long getPersonId = personDb.SenderPerson(personFn);
                    Intent intentToEditEntryAct = new Intent(HomeActivity.this, EditEntry.class);
                    intentToEditEntryAct.putExtra(POSITION, position);
                    intentToEditEntryAct.putExtra("person_id", getPersonId);
                    intentToEditEntryAct.putExtra(PEOPLE_LIST, peopleList.get(position));
                    startActivityForResult(intentToEditEntryAct, 1);
                }
            });

            recyclerViewAccount = findViewById(R.id.recycleView_account);
            recyclerViewAccount.setHasFixedSize(true);
            myLayoutManagerAccount = new LinearLayoutManager(this);
            accountListAdapter = new AccountListAdapter(this, accountList);
            recyclerViewAccount.setLayoutManager(myLayoutManagerAccount);
            recyclerViewAccount.setAdapter(accountListAdapter);
            accountListAdapter.setOnClickListener(new PersonListAdapter.OnClickListener() {
                @Override
                public void OnClickListener(int position) {
                    Logout();
                }
            });
        }

        Button addEntry = findViewById(R.id.btn_add_new_entry);

        addEntry.setOnClickListener(this);
    }// end of Curly braces INIT

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                int position = data.getIntExtra("update_list", 0);
                Person edit_person = data.getParcelableExtra("edit_person");

                peopleList.set(position, edit_person);
                personListAdapter.notifyDataSetChanged();
                //data.removeExtra(String.valueOf(edit_person));
            }
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                if(data.getExtras() != null){
                    Person person = data.getParcelableExtra("new_person");
//                    long getRowPeople = data.getLongExtra("get_row_people",0);
//                    if(peopleList != null){
//                        peopleList = (ArrayList<Person>) personDb.getAllPeople(getRowPeople);
//                        personListAdapter.notifyDataSetChanged();
//                    }
                    peopleList.add(0,person);
                    personListAdapter.notifyDataSetChanged();
                }
            }
        }//end of if requestCode 2
    }

    public void Logout() {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Are you sure you want to logout?");
        bldg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               startActivity(new Intent(HomeActivity.this, Login.class));
            }
        });
        bldg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HomeActivity.this, "Cancelled so sad!", Toast.LENGTH_LONG).show();
            }
        });
        bldg.show();
    }//end of Logout CURLY BRACES


    public void DeleteList(int position) {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Are you sure you want to delete?");
        bldg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String personFn = peopleList.get(position).getFn();
                long getPersonId = personDb.SenderPerson(personFn);
                personDb.DeletePerson(getPersonId);
                peopleList.remove(position);
                personListAdapter.notifyDataSetChanged();
            }
        });
        bldg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HomeActivity.this, "Cancel!", Toast.LENGTH_LONG).show();
            }
        });
        bldg.show();
    }//end of Logout CURLY BRACES

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_new_entry:
                AddNewEntry();
            break;
        }
    }

    public void AddNewEntry(){
        Intent addEntryIntent = new Intent(HomeActivity.this, AddEntry.class);
        if(sendRegister == 0){
            addEntryIntent.putExtra("add_person", createdPerson);
//            Log.d("happy", "goods ahh");
        }else{
            addEntryIntent.putExtra("add_person", sendRegister);
//            Log.d("happy", String.valueOf(sendRegister));
        }

        startActivityForResult(addEntryIntent, 2);
    }

}