package com.example.nirbhaya_womensafetyapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class emergency_no extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText  editName,editMobile;
    Button btnAddData, btnUpdate, btnDelete, btnviewAll,btnDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);


        editName = (EditText) findViewById(R.id.editText_name);
        editMobile = (EditText) findViewById(R.id.editText_mobile);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        btnUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
        btnDeleteAll = (Button) findViewById(R.id.button_deleteAll);
        AddData();
        updateData();
        deleteData();
        viewAll();
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteAll();
                Toast.makeText(emergency_no.this, "All Data have been deleted", Toast.LENGTH_LONG).show();

            }
        });
    }
    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().trim().length() > 0 && editMobile.getText().toString().trim().length() > 0) {
                    int isInserted = myDb.insertData(editName.getText().toString(), editMobile.getText().toString());
                    if (isInserted == 1 )
                        Toast.makeText(emergency_no.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    else if(isInserted==3)
                        Toast.makeText(emergency_no.this, "Max 3 Data can be Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(emergency_no.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(emergency_no.this, "Enter The Details then You Can Add the Data", Toast.LENGTH_SHORT).show();
                }
                editName.setText("");
                editMobile.setText("");
            }

        });
    }

    public void viewAll(){
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getAllData();
                if(res!=null && res.getCount() ==0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                if(res!=null) {
                    StringBuilder buffer = new StringBuilder();
                    while (res.moveToNext()) {

                        buffer.append("Name :" + res.getString(1) + "\n");

                        buffer.append("Mobile :" + res.getString(2) + "\n\n");
                    }
                    showMessage("Data", buffer.toString());
                }
            }
        });
    }

    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editName.getText().toString().trim().length() > 0) {
                            boolean isUpdate = myDb.updateData(editName.getText().toString(),
                                    editMobile.getText().toString());
                            if (isUpdate == true)
                                Toast.makeText(emergency_no.this, "Data Update", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(emergency_no.this, "Data could not be Updated", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(emergency_no.this, "Enter The Name to Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editName.getText().toString().trim().length() > 0) {
                            Integer deletedRows = myDb.deleteData(editName.getText().toString());
                            if (deletedRows > 0)
                                Toast.makeText(emergency_no.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(emergency_no.this, "Data could not be Deleted", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(emergency_no.this, "Enter The Name to Delete", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}