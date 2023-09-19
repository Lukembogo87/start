package com.example.cat2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_SECOND = "second_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REG_NO = "reg_no";
    public static final String COLUMN_TOTAL_FEES = "total_fees";
    public static final String COLUMN_FEES_PAID = "fees_paid";
    public static final String COLUMN_FEES_BALANCE = "fees_balance";

    private static final String CREATE_TABLE_SECOND = "CREATE TABLE " + TABLE_SECOND + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_REG_NO + " TEXT,"
            + COLUMN_TOTAL_FEES + " REAL,"
            + COLUMN_FEES_PAID + " REAL,"
            + COLUMN_FEES_BALANCE + " REAL)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SECOND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECOND);
        onCreate(db);
    }

    public long insertData(String regNo, float totalFees, float feesPaid, float feesBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REG_NO, regNo);
        values.put(COLUMN_TOTAL_FEES, totalFees);
        values.put(COLUMN_FEES_PAID, feesPaid);
        values.put(COLUMN_FEES_BALANCE, feesBalance);
        long insertedId = db.insert(TABLE_SECOND, null, values);
        db.close();
        return insertedId;
    }

    public int updateData(long id, String regNo, float totalFees, float feesPaid, float feesBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REG_NO, regNo);
        values.put(COLUMN_TOTAL_FEES, totalFees);
        values.put(COLUMN_FEES_PAID, feesPaid);
        values.put(COLUMN_FEES_BALANCE, feesBalance);
        int rowsAffected = db.update(TABLE_SECOND, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public int deleteData(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_SECOND, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_SECOND, null, null, null, null, null, null);
    }
}