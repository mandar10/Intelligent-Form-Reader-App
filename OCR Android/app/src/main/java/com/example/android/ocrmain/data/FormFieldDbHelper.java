package com.example.android.ocrmain.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.ocrmain.data.FormFieldsList.*;

public class FormFieldDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "collist.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 2;

    // Constructor
    public FormFieldDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + FormFieldsEntry.TABLE_NAME + " (" +
                FormFieldsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FormFieldsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FormFieldsEntry.COLUMN_X + " INTEGER NOT NULL, " +
                FormFieldsEntry.COLUMN_Y + " INTEGER NOT NULL," +
                FormFieldsEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                FormFieldsEntry.COLUMN_FORMID + " INTEGER NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FormFieldsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}