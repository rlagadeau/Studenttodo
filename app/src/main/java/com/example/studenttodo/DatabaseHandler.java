package com.example.studenttodo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "androiddb";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_BIRTHDATE = "birthdate";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_HOURS = "hours";
    private static final String COLUMN_HOURS_ID = "id";
    private static final String COLUMN_HOURS = "hours";
    private static final String COLUMN_SCHOOL = "school";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_SUBJECT = "subject";
    private static final String COLUMN_AMOUNTSTUDS = "amountstuds";
    private static final String COLUMN_REGDATE = "registrationdate";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRSTNAME + " TEXT NOT NULL," +
                COLUMN_LASTNAME + " TEXT NOT NULL," + COLUMN_BIRTHDATE + " TEXT NOT NULL," + COLUMN_ADDRESS +
                " TEXT NOT NULL," + COLUMN_EMAIL + " TEXT NOT NULL," + COLUMN_PASSWORD + " TEXT NOT NULL" + ")";

        String CREATE_TABLE_HOURS = "CREATE TABLE " + TABLE_HOURS + "(" + COLUMN_HOURS_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_HOURS + " TEXT NOT NULL," +
                COLUMN_SCHOOL + " TEXT NOT NULL," + COLUMN_LOCATION + " TEXT NOT NULL," + COLUMN_SUBJECT +
                " TEXT NOT NULL," + COLUMN_AMOUNTSTUDS + " TEXT NOT NULL, " + COLUMN_REGDATE + " TEXT NOT NULL " + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_HOURS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(sqLiteDatabase);
    }

    public boolean addUsers(Users users){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRSTNAME, users.getFirstName());
        contentValues.put(COLUMN_LASTNAME, users.getLastName());
        contentValues.put(COLUMN_BIRTHDATE, users.getBirthDate());
        contentValues.put(COLUMN_ADDRESS, users.getAddress());
        contentValues.put(COLUMN_EMAIL, users.getEmail());
        contentValues.put(COLUMN_PASSWORD, users.getPassword());

        long insert_value = db.insert(TABLE_USERS, null, contentValues);
        db.close();

        return insert_value != -1;
    }

    public boolean selectUserEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT " + COLUMN_EMAIL + " FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " =? ",new String[] {email});

        if(cursor != null){
            if(cursor.moveToFirst()) {
                String db_email_value = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                return email.equals(db_email_value);
            }
        }

        return false;
    }

    public String loginUser(String email, String password){

        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT "+COLUMN_EMAIL+","+COLUMN_PASSWORD+" FROM "+TABLE_USERS+" WHERE "+COLUMN_EMAIL+ "=?",new String[] {email});

        if(cursor != null){
            if(cursor.moveToFirst()) {
                if (email.equals(cursor.getString(0))) {
                    if (password.equals(cursor.getString(1))) {
                        return "login success";
                    } else if (!password.equals(cursor.getString(1))) {
                        return "password incorrect";
                    }
                }
            }
        }else{
            return "not found";
        }

        return "false";
    }

    public void addHourRegInformation(SQLiteDatabase db, String hours, String school, String location, String subject, String amountstuds, String regdate){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HOURS, hours);
        contentValues.put(COLUMN_SCHOOL, school);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_SUBJECT, subject);
        contentValues.put(COLUMN_AMOUNTSTUDS, amountstuds);
        contentValues.put(COLUMN_REGDATE, regdate);

        long insert_value = db.insert(TABLE_HOURS, null, contentValues);
        db.close();
    }
}
