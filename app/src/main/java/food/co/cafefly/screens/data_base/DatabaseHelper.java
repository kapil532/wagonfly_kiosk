package food.co.cafefly.screens.data_base;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kartify.model.Beneficiary;

/**
 * Created by delaroy on 5/10/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{


    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION = 11;

    // Database Name
    private static final String DATABASE_NAME = "cafefly.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + BeneficiaryContract.BeneficiaryEntry.TABLE_NAME + " (" +
                BeneficiaryContract.BeneficiaryEntry.REST_ID + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.ITEM_ID + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.ITEM_NAME + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.ITEM_QUANTITY + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.ITEM_PRICE + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.ITEM_EXTRAS_ID + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.ITEM_TYPE + " TEXT NOT NULL"+
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }
    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + BeneficiaryContract.BeneficiaryEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //---opens the database---
    public DatabaseHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {

        //Drop User Table if exist

        db1.execSQL(DROP_BENEFICIARY_TABLE);

        // Create tables again
        onCreate(db1);

    }

    public  void deleteCart()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        try {

            db.execSQL("DELETE FROM " + BeneficiaryContract.BeneficiaryEntry.TABLE_NAME);

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }
    //Method to create beneficiary records

    public void addBeneficiary(ItemAddPojo beneficiary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BeneficiaryContract.BeneficiaryEntry.REST_ID, beneficiary.getREST_ID());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_ID, beneficiary.getITEM_ID());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_NAME, beneficiary.getITEM_NAME());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_QUANTITY, beneficiary.getITEM_QUANTITY());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_PRICE, beneficiary.getITEM_PRICE());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_EXTRAS_ID, beneficiary.getITEM_EXTRAS_ID());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_TYPE, beneficiary.getITEM_TYPE());

        db.insert(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void updateTable(ItemAddPojo beneficiary,String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(BeneficiaryContract.BeneficiaryEntry.REST_ID, beneficiary.getREST_ID());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_ID, beneficiary.getITEM_ID());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_NAME, beneficiary.getITEM_NAME());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_QUANTITY, beneficiary.getITEM_QUANTITY());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_PRICE, beneficiary.getITEM_PRICE());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_EXTRAS_ID, beneficiary.getITEM_EXTRAS_ID());
        values.put(BeneficiaryContract.BeneficiaryEntry.ITEM_TYPE, beneficiary.getITEM_TYPE());


        db.update(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, values, BeneficiaryContract.BeneficiaryEntry.ITEM_ID+"="  + id, null);
    }


    public boolean checkUser(String email)
    {

        // array of columns to fetch
        String[] columns = {
                BeneficiaryContract.BeneficiaryEntry._ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = BeneficiaryContract.BeneficiaryEntry.ITEM_ID + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        Cursor cursor = db.query(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0)
        {
            return true;
        }

        return false;
    }





    public List<ItemAddPojo> getAllBeneficiary()
    {
        // array of columns to fetch
        String[] columns = {
                BeneficiaryContract.BeneficiaryEntry.REST_ID,
                BeneficiaryContract.BeneficiaryEntry.ITEM_ID,
                BeneficiaryContract.BeneficiaryEntry.ITEM_NAME,
                BeneficiaryContract.BeneficiaryEntry.ITEM_QUANTITY,
                BeneficiaryContract.BeneficiaryEntry.ITEM_PRICE,
                BeneficiaryContract.BeneficiaryEntry.ITEM_EXTRAS_ID,
                BeneficiaryContract.BeneficiaryEntry.ITEM_TYPE

        };
        // sorting orders
        String sortOrder =
                BeneficiaryContract.BeneficiaryEntry.ITEM_NAME ;
        List<ItemAddPojo> beneficiaryList = new ArrayList<ItemAddPojo>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemAddPojo beneficiary = new ItemAddPojo();
                beneficiary.setREST_ID(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.REST_ID)));
                beneficiary.setITEM_ID(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.ITEM_ID)));
                beneficiary.setITEM_NAME(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.ITEM_NAME)));
                beneficiary.setITEM_QUANTITY(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.ITEM_QUANTITY)));
                beneficiary.setITEM_PRICE(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.ITEM_PRICE)));
                beneficiary.setITEM_EXTRAS_ID(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.ITEM_EXTRAS_ID)));
                beneficiary.setITEM_TYPE(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.ITEM_TYPE)));

                // Adding user record to list
                beneficiaryList.add(beneficiary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return beneficiaryList;
    }

    public  void updateParticularData(String quantity,String id)
    {
        Log.d("DATABASE","DATA-->"+quantity+" id "+id);
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE "+ BeneficiaryContract.BeneficiaryEntry.TABLE_NAME+" SET "+ BeneficiaryContract.BeneficiaryEntry.ITEM_QUANTITY+"='"+quantity+"' WHERE "+ BeneficiaryContract.BeneficiaryEntry.ITEM_ID+"="+id+"");
       db.close();
    }

    public void deleteSingleContact(String id){

         db = this.getWritableDatabase();
        db.delete(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, BeneficiaryContract.BeneficiaryEntry.ITEM_ID + "=?", new String[]{id});
        db.close();
        //KEY_NAME is a column name
    }

}
