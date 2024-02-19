package com.example.slambook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PersonDb {
    public static final String TAG = "PersonDb";

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {
            DB_Conn.EntryList_Data.COLUMN_ENTRY_ID,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_IMAGE,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_FN,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_MN,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_LN,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_REMARK,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_BDAY,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_GENDER,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_ADDRESS,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_CONTACT,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_HOBBIES,
            DB_Conn.EntryList_Data.COLUMN_ENTRY_GOALS,
            DB_Conn.EntryList_Data.COLUMN_ACCOUNT_ID
    };

    public PersonDb(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Person createPerson(byte[] profilePic, String fn, String md, String ln,
                                String remark, String bday, String gender, String address,
                                String contact, String hobbies, String goals, long accountId) {
        ContentValues values = new ContentValues();
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_IMAGE, profilePic);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_FN, fn);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_MN, md);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_LN, ln);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_REMARK, remark);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_BDAY, bday);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_GENDER, gender);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_ADDRESS, address);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_CONTACT, contact);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_HOBBIES, hobbies);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_GOALS, goals);
        values.put(DB_Conn.EntryList_Data.COLUMN_ACCOUNT_ID, accountId);

        long insertId = mDatabase.insert(DB_Conn.EntryList_Data.Entry_TB, null, values);

        Cursor cursor = mDatabase.query(DB_Conn.EntryList_Data.Entry_TB, mAllColumns,
                DB_Conn.EntryList_Data.COLUMN_ENTRY_ID + " = " + insertId, null, null,
                null, null);

        cursor.moveToFirst();
        Person newPerson = cursorToPerson(cursor);
        cursor.close();
        return newPerson;
    }// end of createPerson



    public List<Person> getAllPeople(long companyId) {
        List<Person> personArrayList = new ArrayList<Person>();

        Cursor cursor = mDatabase.query(DB_Conn.EntryList_Data.Entry_TB, mAllColumns,
                DB_Conn.EntryList_Data.COLUMN_ACCOUNT_ID + " = ?",
                new String[] { String.valueOf(companyId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Person employee = cursorToPerson(cursor);
            personArrayList.add(employee);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return personArrayList;
    }

    public List<Person> getPeopleOfAccount(long companyId) {
        List<Person> personArrayList = new ArrayList<Person>();

        Cursor cursor = mDatabase.query(DB_Conn.EntryList_Data.Entry_TB, mAllColumns,
                DB_Conn.EntryList_Data.COLUMN_ENTRY_ID + " = ?",
                new String[] { String.valueOf(companyId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Person employee = cursorToPerson(cursor);
            personArrayList.add(employee);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return personArrayList;
    }// end of getPeopleOfAccount

    private Person cursorToPerson(Cursor cursor) {
        Person person = new Person();
        person.setId(cursor.getLong(0));
        person.setByteProfilePic(cursor.getBlob(1));
        person.setFn(cursor.getString(2));
        person.setMn(cursor.getString(3));
        person.setLn(cursor.getString(4));
        person.setRemark(cursor.getString(5));
        person.setBirthday(cursor.getString(6));
        person.setGender(cursor.getString(7));
        person.setAddress(cursor.getString(8));
        person.setContact(cursor.getString(9));
        person.setHobbies(cursor.getString(10));
        person.setGoals(cursor.getString(11));

        return person;
    }//end of Curly Braces CursorToPerson

    public boolean DeletePerson(long personId) {
        //long id = person.getId();
        return mDatabase.delete(DB_Conn.EntryList_Data.Entry_TB,
                DB_Conn.EntryList_Data.COLUMN_ENTRY_ID + "=" + personId,
                null) > 0;
    }//end of Curly Brace deletePerson

    public void UpdatePerson(
            long getId, byte[] profilePic, String fn, String md, String ln,
            String remark, String bday, String gender, String address,
            String contact, String hobbies, String goals)
    {
        ContentValues values = new ContentValues();
//        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_ID, personId);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_IMAGE, profilePic);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_FN, fn);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_MN, md);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_LN, ln);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_REMARK, remark);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_BDAY, bday);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_GENDER, gender);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_ADDRESS, address);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_CONTACT, contact);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_HOBBIES, hobbies);
        values.put(DB_Conn.EntryList_Data.COLUMN_ENTRY_GOALS, goals);

        mDatabase.update(DB_Conn.EntryList_Data.Entry_TB, values,
                DB_Conn.EntryList_Data.COLUMN_ENTRY_ID + " = ?",
                new String[]{String.valueOf(getId)});

//        Person updatePerson = cursorToPerson(cursor);
//        cursor.close();
//        return updatePerson;
    }

    public long SenderPerson(String activeuser){
        // array of columns to fetch
        long rv = -1;
        mDatabase =  mDbHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(DB_Conn.EntryList_Data.Entry_TB, mAllColumns,
                DB_Conn.EntryList_Data.COLUMN_ENTRY_FN + " = ? ",
                new String[] { activeuser }, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                rv = cursor.getLong(cursor.getColumnIndex(DB_Conn.EntryList_Data.COLUMN_ENTRY_ID));
            }
        }
        finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return rv;
    }// End oF Curly Braces Sender

}//end of PeronDb
