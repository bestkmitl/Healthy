package com.example.support.healthy_134.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.support.healthy_134.sleep.SleepTimeItem;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "myFitness.db";
    public static final String TABLE = "sleep_time";
    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String START_TIME = "start";
    public static final String END_TIME = "endtime";
    public static final String DURATION = "duration";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sleep_time (_id integer primary key autoincrement, date varchar(25), start varchar(25), endtime varchar(25), duration varchar(25))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sleep_time");
        onCreate(db);
    }

    public void insertSleepTime(SleepTimeItem sleepTimeItem) {
        // get database
        SQLiteDatabase db = getWritableDatabase();

        // put values into row
        ContentValues row = new ContentValues();
        row.put(DATE, sleepTimeItem.getDateString());
        row.put(START_TIME, sleepTimeItem.getStartTime());
        row.put(END_TIME, sleepTimeItem.getEndTime());
        row.put(DURATION, sleepTimeItem.getDuration());

        // insert to database
        db.insert(TABLE, null, row);
    }

    public ArrayList<SleepTimeItem> getAllItem() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SleepTimeItem> sleepTimeItems = new ArrayList<>();

        Cursor data = db.rawQuery("select * from sleep_time", null);
        while (data.moveToNext()) {
            SleepTimeItem item = new SleepTimeItem();
            item.setId(data.getInt(0));
            item.setDateString(data.getString(1));
            item.setStartTime(data.getString(2));
            item.setEndTime(data.getString(3));
            item.setDuration(data.getString(4));
            sleepTimeItems.add(item);
        }

        return sleepTimeItems;
    }

    public int getCountRow()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("select * from sleep_time", null);
        return data.getCount();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TABLE);
    }

    public void delete(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, "_id=" + id, null);
    }

    public void update(SleepTimeItem item)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(ID, item.getId());
        row.put(DATE, item.getDateString());
        row.put(START_TIME, item.getStartTime());
        row.put(END_TIME, item.getEndTime());
        row.put(DURATION, item.getDuration());

        db.update(TABLE, row, "_id=" + item.getId(), null);
    }
}