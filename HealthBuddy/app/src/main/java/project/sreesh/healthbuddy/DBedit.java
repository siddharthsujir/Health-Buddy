package project.sreesh.healthbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import project.sreesh.healthbuddy.DB.info;

public class DBedit extends SQLiteOpenHelper {
    public static final int database_version=1;
    public String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS "+ info.TABLE_NAME +"("+ DB.info.USER_NAME+" TEXT ,"+info.PASSWORD+" TEXT ,"+info.MAIL_ID+" TEXT ,"+info.QUESTION+" TEXT ,"+info.ANSWER+" TEXT );";
    public String CREATE_HOTEL = "CREATE TABLE IF NOT EXISTS "+ info.TABLE2_NAME +"("+ info.HOTEL_LA+" TEXT ,"+info.HOTEL_LO+" TEXT );";
    public String CREATE_CALORIE = "CREATE TABLE IF NOT EXISTS "+ info.TABLE3_NAME +"("+ info.DATE+" TEXT ,"+info.CALB+" TEXT ,"+info.CALE+" TEXT ,"+info.CALC+" TEXT ,"+info.CALT+" TEXT );";
    public String CREATE_GYM = "CREATE TABLE IF NOT EXISTS "+ info.TABLE4_NAME +"("+ info.GYM_LA+" TEXT ,"+info.GYM_LO+" TEXT );";
    public String CREATE_FOOD = "CREATE TABLE IF NOT EXISTS "+ info.TABLE5_NAME +"("+ info.FOOD+" TEXT ,"+info.CALORIES+" TEXT );";


    public DBedit(Context context) {
        super(context, info.DATABASE_NAME, null, database_version);
        Log.d("Processing Database Operations", "Database Created");
        // TODO Auto-generated constructor stub

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_HOTEL);
        System.out.print("2");db.execSQL(CREATE_CALORIE);
        System.out.print("3");db.execSQL(CREATE_GYM);
        System.out.print("4");db.execSQL(CREATE_FOOD);
        Log.d("Processing Database Operations", "Tables Created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void inserth(DBedit e, String lat, String lon)
    {
        SQLiteDatabase SQ = e.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(info.HOTEL_LA, lat);
        cv.put(info.HOTEL_LO, lon);
        SQ.insert(info.TABLE2_NAME, null, cv);
        Log.d("Processing Database Operations", "Row Inserted");
    }

    public void insertg(DBedit e, String lat, String lon)
    {
        SQLiteDatabase SQ = e.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(info.GYM_LA, lat);
        cv.put(info.GYM_LO, lon);
        SQ.insert(info.TABLE4_NAME, null, cv);
        Log.d("Processing Database Operations", "Row Inserted");
    }


    public void insertf(DBedit e, String food, String calories)
    {
        SQLiteDatabase SQ = e.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(info.FOOD, food);
        cv.put(info.CALORIES, calories);
        SQ.insert(info.TABLE5_NAME, null, cv);
        Log.d("Processing Database Operations", "Row Inserted");
    }

    public void insertc(DBedit e, String date, String calb, String cale, String calc, String calt)
    {
        SQLiteDatabase SQ = e.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(info.DATE, date);
        cv.put(info.CALB, calb);
        cv.put(info.CALE, cale);
        cv.put(info.CALC, calc);
        cv.put(info.CALT, calt);
        SQ.insert(info.TABLE3_NAME, null, cv);
        Log.d("Processing Database Operations", "Row Inserted");
    }

    public void insert(DBedit e, String name, String password, String mail, String question, String answer)
    {
        SQLiteDatabase SQ = e.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(info.USER_NAME, name);
        cv.put(info.PASSWORD, password);
        cv.put(info.MAIL_ID, mail);
        cv.put(info.QUESTION, question);
        cv.put(info.ANSWER, answer);
        SQ.insert(info.TABLE_NAME, null, cv);
        Log.d("Processing Database Operations", "Row Inserted");
    }

    public Cursor pull(DBedit e)
    {
        SQLiteDatabase SQ = e.getReadableDatabase();
        String[] columns = {info.USER_NAME, info.PASSWORD, info.MAIL_ID, info.QUESTION, info.ANSWER};
        Cursor CR = SQ.query(info.TABLE_NAME, columns, null, null, null, null, null);
        return CR;
    }

    public Cursor pullh(DBedit e)
    {
        SQLiteDatabase SQ = e.getReadableDatabase();
        String[] columns = {info.HOTEL_LA, info.HOTEL_LO};
        Cursor CR = SQ.query(info.TABLE2_NAME, columns, null, null, null, null, null);
        return CR;
    }

    public Cursor pullf(DBedit e)
    {
        SQLiteDatabase SQ = e.getReadableDatabase();
        String[] columns = {info.FOOD, info.CALORIES};
        Cursor CR = SQ.query(info.TABLE5_NAME, columns, null, null, null, null, null);
        return CR;
    }

    public Cursor pullc(DBedit e)
    {
        SQLiteDatabase SQ = e.getReadableDatabase();
        String[] columns = {info.DATE, info.CALB, info.CALE, info.CALC, info.CALT};
        Cursor CR = SQ.query(info.TABLE3_NAME, columns, null, null, null, null, null);
        return CR;
    }

    public Cursor pullg(DBedit e)
    {
        SQLiteDatabase SQ = e.getReadableDatabase();
        String[] columns = {info.GYM_LA, info.GYM_LO};
        Cursor CR = SQ.query(info.TABLE4_NAME, columns, null, null, null, null, null);
        return CR;
    }

}