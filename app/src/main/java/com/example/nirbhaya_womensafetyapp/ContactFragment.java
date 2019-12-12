package com.example.nirbhaya_womensafetyapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    DatabaseHelper myDb;

    EditText editName,editMobile;
    Button btnAddData, btnUpdate, btnDelete, btnviewAll,btnDeleteAll;
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Emergency Contacts");
        View view=inflater.inflate(R.layout.activity_emergency_no, container, false);
        myDb = new DatabaseHelper(getActivity());
        editName = (EditText) view.findViewById(R.id.editText_name);
        editMobile = (EditText) view.findViewById(R.id.editText_mobile);
        btnAddData = (Button) view.findViewById(R.id.button_add);
        btnviewAll = (Button) view.findViewById(R.id.button_viewAll);
        btnUpdate = (Button) view.findViewById(R.id.button_update);
        btnDelete = (Button) view.findViewById(R.id.button_delete);
        btnDeleteAll = (Button) view.findViewById(R.id.button_deleteAll);
        Button b= (Button) view.findViewById(R.id.button_add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editName.getText().toString().trim().length() > 0 && editMobile.getText().toString().trim().length() > 0) {
                    int isInserted = myDb.insertData(editName.getText().toString(), editMobile.getText().toString());
                    if (isInserted == 1 )
                        Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
                    else if(isInserted==3)
                        Toast.makeText(getActivity(), "Max 3 Data can be Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getActivity(), "Data not Inserted", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getActivity(), "Enter The Details then You Can Add the Data", Toast.LENGTH_SHORT).show();
                }
                editName.setText("");
                editMobile.setText("");
            }
        });
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
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editName.getText().toString().trim().length() > 0) {
                            boolean isUpdate = myDb.updateData(editName.getText().toString(),
                                    editMobile.getText().toString());
                            if (isUpdate == true)
                                Toast.makeText(getActivity(), "Data Update", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getActivity(), "Data could not be Updated", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Enter The Name to Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editName.getText().toString().trim().length() > 0) {
                            Integer deletedRows = myDb.deleteData(editName.getText().toString());
                            if (deletedRows > 0)
                                Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getActivity(), "Data could not be Deleted", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Enter The Name to Delete", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteAll();
                Toast.makeText(getActivity(), "All Data have been deleted", Toast.LENGTH_LONG).show();

            }
        });
        
        return view;
        //Intent i=new Intent(getActivity(),emergency_no.class);
        //startActivity(i);
    }
    private void showMessage(String title, String message) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}