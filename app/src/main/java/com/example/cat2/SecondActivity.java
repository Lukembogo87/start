package com.example.cat2;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private EditText registrationNumberEditText;
    private EditText totalFeesEditText;
    private EditText feesPaidEditText;
    private EditText feesBalanceEditText;
    private Button insertDataButton;
    private Button updateDataButton;
    private Button deleteDataButton;
    private Button viewDataButton;
    private StudentDBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Initialize views
        registrationNumberEditText = findViewById(R.id.registrationNumber);
        totalFeesEditText = findViewById(R.id.totalFees);
        feesPaidEditText = findViewById(R.id.feesPaid);
        feesBalanceEditText = findViewById(R.id.feesBalance);
        insertDataButton = findViewById(R.id.insertData);
        updateDataButton = findViewById(R.id.updateData);
        deleteDataButton = findViewById(R.id.deleteData);
        viewDataButton = findViewById(R.id.viewData);

        // Initialize DBHelper
        dbHelper = new StudentDBHelper(this);

        insertDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        updateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        deleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData();
            }
        });

    }

    private void insertData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String registrationNumber = registrationNumberEditText.getText().toString();
        String totalFees = totalFeesEditText.getText().toString();
        String feesPaid = feesPaidEditText.getText().toString();
        String feesBalance = feesBalanceEditText.getText().toString();

        values.put(StudentDBHelper.COLUMN_REGISTRATION_NUMBER, registrationNumber);
        values.put(StudentDBHelper.COLUMN_TOTAL_FEES, totalFees);
        values.put(StudentDBHelper.COLUMN_FEES_PAID, feesPaid);
        values.put(StudentDBHelper.COLUMN_FEES_BALANCE, feesBalance);

        long result = db.insert(StudentDBHelper.TABLE_NAME, null, values);

        if (result == -1) {
            Toast.makeText(this, "Error inserting data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
    }

    private void updateData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String registrationNumber = registrationNumberEditText.getText().toString();
        String totalFees = totalFeesEditText.getText().toString();
        String feesPaid = feesPaidEditText.getText().toString();
        String feesBalance = feesBalanceEditText.getText().toString();

        values.put(StudentDBHelper.COLUMN_TOTAL_FEES, totalFees);
        values.put(StudentDBHelper.COLUMN_FEES_PAID, feesPaid);
        values.put(StudentDBHelper.COLUMN_FEES_BALANCE, feesBalance);

        int rowsAffected = db.update(StudentDBHelper.TABLE_NAME, values,
                StudentDBHelper.COLUMN_REGISTRATION_NUMBER + " = ?",
                new String[]{registrationNumber});

        if (rowsAffected > 0) {
            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "No data found with the specified registration number", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String registrationNumber = registrationNumberEditText.getText().toString();

        int rowsAffected = db.delete(StudentDBHelper.TABLE_NAME,
                StudentDBHelper.COLUMN_REGISTRATION_NUMBER + " = ?",
                new String[]{registrationNumber});

        if (rowsAffected > 0) {
            Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "No data found with the specified registration number", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(StudentDBHelper.TABLE_NAME, null,
                null, null, null, null, null);

        StringBuilder data = new StringBuilder();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String registrationNumber = cursor.getString(cursor.getColumnIndex(StudentDBHelper.COLUMN_REGISTRATION_NUMBER));
            @SuppressLint("Range") String totalFees = cursor.getString(cursor.getColumnIndex(StudentDBHelper.COLUMN_TOTAL_FEES));
            @SuppressLint("Range") String feesPaid = cursor.getString(cursor.getColumnIndex(StudentDBHelper.COLUMN_FEES_PAID));
            @SuppressLint("Range") String feesBalance = cursor.getString(cursor.getColumnIndex(StudentDBHelper.COLUMN_FEES_BALANCE));

            data.append("Registration Number: ").append(registrationNumber).append("\n");
            data.append("Total Fees: ").append(totalFees).append("\n");
            data.append("Fees Paid: ").append(feesPaid).append("\n");
            data.append("Fees Balance: ").append(feesBalance).append("\n\n");
        }

        if (data.length() > 0) {
            showAlertDialog("Data", data.toString());
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    private void clearFields() {
        registrationNumberEditText.setText("");
        totalFeesEditText.setText("");
        feesPaidEditText.setText("");
        feesBalanceEditText.setText("");
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
