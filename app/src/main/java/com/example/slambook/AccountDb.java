package com.example.slambook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AccountDb {

    public static final String TAG = "AccountDb";

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {
            DB_Conn.UserDatabase.COLUMN_USER_ID,
            DB_Conn.UserDatabase.COLUMN_USER_IMAGE,
            DB_Conn.UserDatabase.COLUMN_USER_USERNAME,
            DB_Conn.UserDatabase.COLUMN_USER_PASSWORD,
            DB_Conn.UserDatabase.COLUMN_USER_EMAIL,
            DB_Conn.UserDatabase.COLUMN_USER_BDAY,
            DB_Conn.UserDatabase.COLUMN_USER_FULLNAME,
            DB_Conn.UserDatabase.COLUMN_USER_GENDER
    };

    public AccountDb(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
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


    public Accounts createAccount(byte[] profilePic, String username, String password, String email,
                                  String bday, String fullname, String gender,
                                  String address, String contact, StringBuilder hobbies,
                                  String seques1, String seques2, String seques3) {
        ContentValues values = new ContentValues();
        values.put(DB_Conn.UserDatabase.COLUMN_USER_IMAGE, profilePic);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_USERNAME,username);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_PASSWORD,password);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_EMAIL,email);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_BDAY,bday);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_FULLNAME,fullname);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_GENDER,gender);

        long insertId = mDatabase
                .insert(DB_Conn.UserDatabase.USER_TB, null, values);
        Cursor cursor = mDatabase.query(DB_Conn.UserDatabase.USER_TB, mAllColumns,
                DB_Conn.UserDatabase.COLUMN_USER_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Accounts newCompany = cursorToAccount(cursor);
        cursor.close();
        return newCompany;
    }

    public List<Accounts> getAllAccounts() {
        List<Accounts> listCompanies = new ArrayList<Accounts>();

        Cursor cursor = mDatabase.query(DB_Conn.UserDatabase.USER_TB, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Accounts accounts = cursorToAccount(cursor);
                listCompanies.add(accounts);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return listCompanies;
    }

    public Accounts getAccountId(long id) {
        Cursor cursor = mDatabase.query(DB_Conn.UserDatabase.USER_TB, mAllColumns,
                DB_Conn.UserDatabase.COLUMN_USER_ID + " = ? ",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Accounts accounts = cursorToAccount(cursor);
        return accounts;
    }

    protected Accounts cursorToAccount(Cursor cursor) {
        Accounts accounts = new Accounts();
        accounts.setmId(cursor.getLong(0));
        accounts.setByteUserPofilePic(cursor.getBlob(1));
        accounts.setUsername(cursor.getString(2));
        accounts.setPassword(cursor.getString(3));
        accounts.setEmail(cursor.getString(4));
        accounts.setBday(cursor.getString(5));
        accounts.setFullname(cursor.getString(6));
        accounts.setGender(cursor.getString(7));
        return accounts;
    }
    public boolean CheckUser(String username, String password) {

        // array of columns to fetch
        String[] columns = {
                DB_Conn.UserDatabase.COLUMN_USER_ID
        };
        // selection criteria
        String selection = DB_Conn.UserDatabase.COLUMN_USER_USERNAME + " = ?" + " AND " + DB_Conn.UserDatabase.COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {username, password};
        // query user table with conditions
        Cursor cursor = mDatabase.query(DB_Conn.UserDatabase.USER_TB, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public long Sender(String activeuser){
        // array of columns to fetch
        long rv = -1;
        mDatabase =  mDbHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(DB_Conn.UserDatabase.USER_TB, mAllColumns,
                DB_Conn.UserDatabase.COLUMN_USER_USERNAME + " = ? ",
                new String[] { activeuser }, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                rv = cursor.getLong(cursor.getColumnIndex(DB_Conn.UserDatabase.COLUMN_USER_ID));
            }
        }
        finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return rv;
    }// End oF Curly Braces Sender

}//end of Account Curly Braces
