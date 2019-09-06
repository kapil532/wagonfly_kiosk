package kartify.sql;

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
    private static final String DATABASE_NAME = "kartifymanager.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + BeneficiaryContract.BeneficiaryEntry.TABLE_NAME + " (" +
                BeneficiaryContract.BeneficiaryEntry._ID + " TEXT NOT NULL," +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BRAND + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_MANUFACTURER + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_WEIGHT + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_IMAGE_PATH + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_ITEM_QUANTITY + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_PRICE + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_GST + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_OFFERS + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_RETAIL_PRICE + " TEXT NOT NULL " +
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

    public void addBeneficiary(Beneficiary beneficiary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BeneficiaryContract.BeneficiaryEntry._ID, beneficiary.getId());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_NAME, beneficiary.getName());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID, beneficiary.getCode());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_PRICE, beneficiary.getS_price());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_BRAND, beneficiary.getBrand());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_MANUFACTURER, beneficiary.getManufacturer());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_WEIGHT, beneficiary.getWeight());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_IMAGE_PATH, beneficiary.getProduct_image());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_ITEM_QUANTITY, beneficiary.getItem_quantity());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_CATEGORY_NAME, beneficiary.getCategory_name());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_GST, beneficiary.getGst());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_OFFERS, beneficiary.getOffer());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_RETAIL_PRICE, beneficiary.getRetail_price());

        db.insert(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void updateTable(Beneficiary beneficiary,String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_NAME, beneficiary.getName());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID, beneficiary.getCode());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_PRICE, beneficiary.getS_price());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_BRAND, beneficiary.getBrand());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_MANUFACTURER, beneficiary.getManufacturer());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_WEIGHT, beneficiary.getWeight());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_IMAGE_PATH, beneficiary.getProduct_image());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_ITEM_QUANTITY, beneficiary.getItem_quantity());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_CATEGORY_NAME, beneficiary.getCategory_name());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_GST, beneficiary.getGst());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_OFFERS, beneficiary.getOffer());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_RETAIL_PRICE, beneficiary.getRetail_price());


        db.update(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, values, BeneficiaryContract.BeneficiaryEntry._ID+"="  + id, null);
    }


    public boolean checkUser(String email)
    {

        // array of columns to fetch
        String[] columns = {
                BeneficiaryContract.BeneficiaryEntry._ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID + " = ?";

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





    public List<Beneficiary> getAllBeneficiary()
    {
        // array of columns to fetch
        String[] columns = {
                BeneficiaryContract.BeneficiaryEntry._ID,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_NAME,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_PRICE,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_MANUFACTURER,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_WEIGHT,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_CATEGORY_NAME,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_IMAGE_PATH,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_ITEM_QUANTITY,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BRAND,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_GST,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_OFFERS,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_RETAIL_PRICE
        };
        // sorting orders
        String sortOrder =
                BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_NAME ;
        List<Beneficiary> beneficiaryList = new ArrayList<Beneficiary>();

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
                Beneficiary beneficiary = new Beneficiary();
                beneficiary.setId((cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry._ID))));
                beneficiary.setName(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_NAME)));
                beneficiary.setBrand(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_BRAND)));
                beneficiary.setCategory_name(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_CATEGORY_NAME)));
                beneficiary.setCode(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID)));
                beneficiary.setManufacturer(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_MANUFACTURER)));
                beneficiary.setS_price(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_PRICE)));
                beneficiary.setProduct_image(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_IMAGE_PATH)));
                beneficiary.setItem_quantity(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_ITEM_QUANTITY)));
                beneficiary.setWeight(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_WEIGHT)));
                beneficiary.setGst(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_GST)));
                beneficiary.setOffer(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_OFFERS)));
                beneficiary.setRetail_price(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_RETAIL_PRICE)));
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
        Log.e("DATABASE","DATA-->"+quantity+" id "+id);
        ContentValues values = new ContentValues();
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_ITEM_QUANTITY, quantity);
        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL("UPDATE "+BeneficiaryContract.BeneficiaryEntry.TABLE_NAME+" SET "+BeneficiaryContract.BeneficiaryEntry.COLUMN_ITEM_QUANTITY+"='"+quantity+"' WHERE "+BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID+"="+id+"");
        db.update(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME,values,BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID + "=?",new String[]{id});
       db.close();
    }

    public void deleteSingleContact(String id){

         db = this.getWritableDatabase();
        db.delete(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, BeneficiaryContract.BeneficiaryEntry.COLUMN_PRODUCT_ID + "=?", new String[]{id});
        db.close();
        //KEY_NAME is a column name
    }

}
