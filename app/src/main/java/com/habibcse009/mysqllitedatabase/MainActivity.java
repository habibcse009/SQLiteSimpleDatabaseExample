package com.habibcse009.mysqllitedatabase;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etxId, etxName, etxCell;
    Button btnSave, btnView, btnUpdate, btnDelete;
    SqliteDB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etxId = findViewById(R.id.txt_id);
        etxName = findViewById(R.id.txt_name);
        etxCell = findViewById(R.id.txt_cell);
        btnSave = findViewById(R.id.btn_save);
        btnView = findViewById(R.id.btn_view);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        //Initialize font
        Typeface tf = Typeface.createFromAsset(getAssets(), "Milkshake.ttf");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "aqua.ttf");
        btnDelete.setTypeface(tf2);
        btnView.setTypeface(tf2);
        btnUpdate.setTypeface(tf2);
        btnSave.setTypeface(tf2);
        etxName.setTypeface(tf1);
        etxId.setTypeface(tf1);
        etxCell.setTypeface(tf1);

        mydb = new SqliteDB(MainActivity.this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etxId.getText().toString();
                String name = etxName.getText().toString();
                String cell = etxCell.getText().toString();
                if (id.isEmpty()) {
                    etxId.setError("Please enter Id.");
                    etxId.requestFocus();
                } else if (name.isEmpty()) {
                    etxName.setError("Please enter Name.");
                    etxName.requestFocus();
                } else if (cell.isEmpty()) {
                    etxCell.setError("Please enter Cell.");
                    etxCell.requestFocus();

                } else if (cell.length() != 11) {
                    etxCell.setError("Please enter Correct Cell Number.");
                    etxCell.requestFocus();
                } else {
                    boolean check = mydb.insertData(id, name, cell);
                    if (check == true) {
                        Toast.makeText(MainActivity.this, "Data Insert Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    // for show data
    public void showData(View v) {
        Cursor result = mydb.viewData();
        if (result.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer buffer = new StringBuffer();
            result.moveToFirst();    //for printing first row
            do {
                buffer.append("\nId: " + result.getString(0));
                buffer.append("\nName: " + result.getString(1));
                buffer.append("\nCell: " + result.getString(2));
            } while (result.moveToNext());
            displayData("Information", buffer.toString());
        }
    }

    private void displayData(String information, String s) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(information);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage(s);
        dialog.show();
    }

    public void update(View v) {
        String id = etxId.getText().toString();
        String name = etxName.getText().toString();
        String cell = etxCell.getText().toString();
        if (id.isEmpty()) {
            etxId.setError("Please enter Id.");
            etxId.requestFocus();
        } else if (name.isEmpty()) {
            etxName.setError("Please enter Name.");
            etxName.requestFocus();
        } else if (cell.isEmpty()) {
            etxCell.setError("Please enter Cell.");
            etxCell.requestFocus();

        } else if (cell.length() != 11) {
            etxCell.setError("Please enter Correct Cell Number.");
            etxCell.requestFocus();
        } else {
            boolean check = mydb.updateData(id, name, cell);
            if (check == true) {
                Toast.makeText(MainActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void delete(View v) {
        String id = etxId.getText().toString();
        int check = mydb.deleteData(id);
        if (check == 1) {
            Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Data is Not Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
