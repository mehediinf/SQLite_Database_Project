package com.example.sqlite_database_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "student_detail";
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String GENDER = "Gender";
    private static final int VERSION_NUMBER = 2;

    //Database Table Create Command
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(225),"+AGE+" INTEGER,"+GENDER+" VARCHAR(20) );";

    //Database Table Drop Command
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
    private static final String SELECT_ALL = "SELECT * FROM " +TABLE_NAME;

    private Context context;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);

        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


//এই খানে CREATE_TABLE variable call করার হইছে

        try
        {
            Toast.makeText(context,"onCreate is called ",Toast.LENGTH_SHORT).show();
            db.execSQL(CREATE_TABLE);

        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }


    }

    //Database Table Drop Command

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context,"onUpgrade is called ",Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);

        }catch (Exception e){

            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }


    }

//Database insert command
    public long insertData(String name,String age,String gender){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);

        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        return rowId;
    }


//Display All Data Command
    public Cursor displayAllData(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        return cursor;
    }


//Update Data Command

    public boolean updatData(String id,String name,String age,String gender)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);

        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ?",new String[] {id});
        return true;
    }


//Delete Data Command

    public int deleteData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,ID+" = ?",new String[]{id});

    }

}
