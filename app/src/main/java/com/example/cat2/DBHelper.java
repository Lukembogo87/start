package com.example.cat2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Userdata.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER_DETAILS = "Userdetails";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CONTACT = "contact";
    private static final String COLUMN_DOB = "dob";

    private static final String TABLE_REGISTRATION = "Registration";
    private static final String COLUMN_REG_NUMBER = "reg_number";
    private static final String COLUMN_TOTAL_FEES = "total_fees";
    private static final String COLUMN_FEES_PAID = "fees_paid";
    private static final String COLUMN_FEES_BALANCE = "fees_balance";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserDetailsTableQuery = "CREATE TABLE " + TABLE_USER_DETAILS +
                "(" + COLUMN_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_CONTACT + " TEXT, " +
                COLUMN_DOB + " TEXT)";
        db.execSQL(createUserDetailsTableQuery);

        String createRegistrationTableQuery = "CREATE TABLE " + TABLE_REGISTRATION +
                "(" + COLUMN_REG_NUMBER + " TEXT PRIMARY KEY, " +
                COLUMN_TOTAL_FEES + " TEXT, " +
                COLUMN_FEES_PAID + " TEXT, " +
                COLUMN_FEES_BALANCE + " TEXT)";
        db.execSQL(createRegistrationTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
        onCreate(db);
    }

    public boolean insertUserData(String name, String contact, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_DOB, dob);
        long result = db.insert(TABLE_USER_DETAILS, null, values);
        return result != -1;
    }


    public boolean updateUserData(String name, String contact, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_DOB, dob);
        int result = db.update(TABLE_USER_DETAILS, values, COLUMN_NAME + "=?", new String[]{name});
        return result != 0;
    }

    public boolean deleteUserData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USER_DETAILS, COLUMN_NAME + "=?", new String[]{name});
        return result != 0;
    }

    public Cursor getUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER_DETAILS, null);
    }
}