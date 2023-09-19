package com.example.cat2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Second Table CRUD operations
public class StudentDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fees.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "student_fees";
    public static final String COLUMN_REGISTRATION_NUMBER = "registration_number";
    public static final String COLUMN_TOTAL_FEES = "total_fees";
    public static final String COLUMN_FEES_PAID = "fees_paid";
    public static final String COLUMN_FEES_BALANCE = "fees_balance";

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_REGISTRATION_NUMBER + " TEXT PRIMARY KEY," +
                    COLUMN_TOTAL_FEES + " TEXT," +
                    COLUMN_FEES_PAID + " TEXT," +
                    COLUMN_FEES_BALANCE + " TEXT)";

    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
