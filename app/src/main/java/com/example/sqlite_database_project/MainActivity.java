package com.example.sqlite_database_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addButton,displayAllDataButton,updateDataButton,deleteDataButton;
    private EditText nameEditText,ageEditText,genderEditText,idEditText;

        MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();


        nameEditText = findViewById(R.id.nameEditText_Id);
        ageEditText = findViewById(R.id.ageEditText_Id);
        genderEditText = findViewById(R.id.genderEditText_Id);
        idEditText = findViewById(R.id.idrEditText_Id);
        addButton = findViewById(R.id.addButtonId);
        displayAllDataButton = findViewById(R.id.displayAllDataButtonId);
        updateDataButton = findViewById(R.id.updateDataButtonId);
        deleteDataButton = findViewById(R.id.deleteDataButtonId);

        displayAllDataButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        updateDataButton.setOnClickListener(this);
        deleteDataButton.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String id = idEditText.getText().toString();


//User data insert

        if (v.getId()==R.id.addButtonId){

            long rowId = myDatabaseHelper.insertData(name,age,gender);
            if (rowId==-1){

                Toast.makeText(getApplicationContext(),"Unsuccessfully insert",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getApplicationContext(),"Row "+rowId+" is successfully insert",Toast.LENGTH_SHORT).show();


            }
        }


//Display All Data loop Command


        else if (v.getId()==R.id.displayAllDataButtonId)
        {
            Cursor cursor = myDatabaseHelper.displayAllData();

            if (cursor.getCount()==0){

                //there is no data so we will display message
                showData("Error","No Data Found");
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();

            while (cursor.moveToNext())
            {
                stringBuffer.append("ID : "+cursor.getString(0)+"\n");
                stringBuffer.append("Name : "+cursor.getString(1)+"\n");
                stringBuffer.append("Age : "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender : "+cursor.getString(3)+"\n\n\n");
            }

            showData("ResultSet ", stringBuffer.toString());

        }

//Update Data Command

        else if (v.getId()==R.id.updateDataButtonId)
        {
            Boolean isUpdated =  myDatabaseHelper.updatData(id,name,age,gender);

            if (isUpdated==true)
            {
                Toast.makeText(getApplicationContext(),"Data is updated",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Data is not updated",Toast.LENGTH_SHORT).show();
            }

        }


//Delete Data Command

        else if (v.getId()==R.id.deleteDataButtonId)
        {
            int value =  myDatabaseHelper.deleteData(id);

            if (value>0)
            {
                Toast.makeText(getApplicationContext(),"Data is Deleted",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Data is not Deleted",Toast.LENGTH_SHORT).show();
            }

        }




    }


// Alert Dialog Box Command

    public void showData(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

}