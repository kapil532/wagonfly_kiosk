package com.wagonfly.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import kartify.model.Beneficiary;
import kartify.sql.BeneficiaryContract;
import kartify.sql.DatabaseHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private TaskDbHelper DBHelper;
    private SQLiteDatabase db;
    public TaskDbHelper(Context context)
    {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_STATUS + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }


    //---opens the database---
    public TaskDbHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    public  void deleteList()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        try {

            db.execSQL("DELETE FROM " + TaskContract.TaskEntry.TABLE);

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }


    public void addBeneficiary(String text) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_STATUS,"0");
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE,text);
        db.insert(TaskContract.TaskEntry.TABLE, null, values);
        db.close();
    }

    public void updateTable(int id,String status)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TaskContract.TaskEntry.COL_STATUS,status);
        db.update(TaskContract.TaskEntry.TABLE, values, TaskContract.TaskEntry._ID+"="  + id, null);
    }


    public void deleteTask(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,  TaskContract.TaskEntry._ID+"="  + id,null);
    }

    public List<TodoListPojo> getTodoList()
    {
        // array of columns to fetch
        String[] columns = {
               TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COL_STATUS,
                TaskContract.TaskEntry.COL_TASK_TITLE

        };
        // sorting orders
        String sortOrder =
                TaskContract.TaskEntry._ID ;
        List<TodoListPojo> beneficiaryList = new ArrayList<TodoListPojo>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        if (cursor.moveToFirst()) {
            do {
                TodoListPojo beneficiary = new TodoListPojo();
                beneficiary.setStatus((cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_STATUS))));
                beneficiary.setId((cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry._ID))));
                beneficiary.setTask(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)));
                // Adding user record to list
                beneficiaryList.add(beneficiary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return beneficiaryList;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

}
