package com.example.ordersystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String CREATE_USER="create table User ("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"password text,"
            +"qq text,"
            +"email text,"
            +"type integer,"
            +"headportrait integer)";
    public static final String CREATE_DISH="create table Dish ("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"price integer,"
            +"introduce text,"
            +"number integer,"
            +"sale integer)";
    public static final String CREATE_SERVER="create table Server ("
            +"id integer primary key autoincrement,"
            +"account text,"
            +"password text)";
    public static final String CREATE_COOK="create table Cook ("
            +"id integer primary key autoincrement,"
            +"account text,"
            +"password text)";
    public static final String CREATE_USERORDER="create table UserOrder ("
            +"id integer primary key autoincrement,"
            +"user text,"
            +"orderid text,"
            +"total integer,"
            +"time text,"
            +"state text)";
    public static final String CREATE_ORDER="create table Orders ("
            +"id integer primary key autoincrement,"
            +"orderid text,"
            +"dishname text,"
            +"number integer,"
            +"unitprice integer)";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DISH);
        db.execSQL(CREATE_SERVER);
        db.execSQL(CREATE_COOK);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_USERORDER);
        db.execSQL(CREATE_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        
    }
}
