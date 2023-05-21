package com.example.foodies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Database_Name = "Foodies.db";
    public static final String Table_Name = "users";
    public static final String CART_Table_Name = "cart";
    public static final String col_name = "user_name";
    public static final String col_email = "user_email";
    public static final String col_password = "user_password";
    public static final String cart_col_id = "cart_id";
    public static final String cart_col_name = "cart_name";
    public static final String cart_col_quantity = "cart_quantity";
    public static final String cart_col_price = "cart_price";
    public static final String cart_col_total_price="cart_total_price";


    public static final String history_col_name = "history_name";
    public static final String history_col_quantity = "history_quantity";
    public static final String history_col_price = "history_price";
    public static final String history_col_email = "history_email";
    public static final String history_TABLE_NAME="history";
    public static final String history_col_total_price="history_total_price";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the user table
        String create_table_query = "CREATE TABLE " + Table_Name + " (" +
                col_name + " TEXT, " +
                col_email + " TEXT PRIMARY KEY, " +
                col_password + " TEXT" +
                ")";
        db.execSQL(create_table_query);

        String create_cart_table = "CREATE TABLE " + CART_Table_Name + " (" +
                cart_col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                cart_col_name + " TEXT, " +
                cart_col_quantity + " TEXT, " +
                cart_col_price + " TEXT," +
                cart_col_total_price+" TEXT"+
                ")";


        db.execSQL(create_cart_table);

        String createHistoryTableQuery = "CREATE TABLE " + history_TABLE_NAME + " (" +
                history_col_email + " TEXT, " +
                history_col_name + " TEXT, " +
                history_col_price + " TEXT, " +
                history_col_quantity + " TEXT," +
                history_col_total_price+" TEXT"+
                ")";
        db.execSQL(createHistoryTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " + CART_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+history_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_name, name);
        contentValues.put(col_email, email);
        contentValues.put(col_password, password);
        long result = db.insert(Table_Name, null, contentValues);

        if (result == -1) {
            // Insertion failed
            return false;
        } else {
            // Insertion succeeded
            return true;
        }
    }


    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_Name + " WHERE " + col_email + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public boolean checkLoginCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_Name + " WHERE " + col_email + " = ? AND " + col_password + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean isValidLogin = cursor.getCount() > 0;
        cursor.close();
        return isValidLogin;
    }

    public Cursor getAllData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_Name + " WHERE " + col_email + " = ?", new String[]{email});
        return res;
    }
    public boolean insertDataCart(String name, String price, String quantity,String total_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(cart_col_name, name);
        contentValues.put(cart_col_price, price);
        contentValues.put(cart_col_quantity, quantity);
        contentValues.put(cart_col_total_price,total_price);
        long result = db.insert(CART_Table_Name, null, contentValues);

        if (result == -1) {
            // Insertion failed
            return false;
        } else {
            // Insertion succeeded
            return true;
        }
    }

    public boolean insertDataHistory(String email, String name, String price, String quantity,String total_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(history_col_email,email);
        contentValues.put(history_col_name, name);
        contentValues.put(history_col_price, price);
        contentValues.put(history_col_quantity, quantity);
        contentValues.put(history_col_total_price,total_price);
        long result = db.insert(history_TABLE_NAME, null, contentValues);

        if (result == -1) {
            // Insertion failed
            return false;
        } else {
            // Insertion succeeded
            return true;
        }
    }

    public Cursor getAllHistory(String tmp_email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + history_TABLE_NAME + " WHERE " + history_col_email + "=?", new String[]{tmp_email});
        return res;
    }

    public Cursor getAllCart( ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + CART_Table_Name,null);
        return res;
    }

    public boolean ClearCart(){

        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(CART_Table_Name,null,null);
        if (result==-1){
            return false;
        }
        else
            return true;
    }

}
