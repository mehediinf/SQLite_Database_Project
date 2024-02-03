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

    private Button addButton,displayAllDataButton;
    private EditText nameEditText,ageEditText,genderEditText;

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
        addButton = findViewById(R.id.addButtonId);
        displayAllDataButton = findViewById(R.id.displayAllDataButtonId);

        displayAllDataButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();


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


        if (v.getId()==R.id.displayAllDataButtonId)
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
                stringBuffer.append("Gender : "+cursor.getString(3)+"\n");
            }

            showData("ResultSet ", stringBuffer.toString());

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