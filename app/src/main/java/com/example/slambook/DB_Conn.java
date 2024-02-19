package com.example.slambook;

import android.provider.BaseColumns;

public class DB_Conn {

    public static class UserDatabase implements BaseColumns
    {
        //registration table
        public static String USER_TB = "table_user";
        //Column name
        public static String COLUMN_USER_ID = "user_id";
        public static String COLUMN_USER_IMAGE = "user_image";
        public static final String COLUMN_USER_USERNAME = "user_username";
        public static String COLUMN_USER_PASSWORD = "user_password";
        public static String COLUMN_USER_EMAIL = "user_email";
        public static String COLUMN_USER_BDAY = "user_bday";
        public static String COLUMN_USER_FULLNAME = "user_fullname";
        public static String COLUMN_USER_GENDER = "user_gender";
    }

    public static class EntryList_Data implements BaseColumns{
        //entrylist table
        public static String Entry_TB = "table_entry";
        //column names
        public static String COLUMN_ENTRY_ID = UserDatabase.COLUMN_USER_ID;
        public static String COLUMN_ENTRY_IMAGE = "entry_image";
        public static String COLUMN_ENTRY_FN = "entry_fn";
        public static String COLUMN_ENTRY_MN = "entry_mn";
        public static String COLUMN_ENTRY_LN = "entry_ln";
        public static String COLUMN_ENTRY_REMARK = "entry_remark";
        public static String COLUMN_ENTRY_BDAY = "entry_bday";
        public static String COLUMN_ENTRY_GENDER = "entry_gender";
        public static String COLUMN_ENTRY_ADDRESS = "entry_address";
        public static String COLUMN_ENTRY_CONTACT = "entry_contact";
        public static String COLUMN_ENTRY_HOBBIES = "entry_hobbies";
        public static String COLUMN_ENTRY_GOALS = "entry_goals";
        public static final String COLUMN_ACCOUNT_ID = "account_id";
    }
}
