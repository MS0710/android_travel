package com.example.travel2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ScheduleSqlDataBaseHelper extends SQLiteOpenHelper {
    private static final String DataBaseName = "ScheduleDataBaseIt_6.db";
    private static final int DataBaseVersion = 1;

    public ScheduleSqlDataBaseHelper(@Nullable Context context, @Nullable String name,
                                     @Nullable SQLiteDatabase.CursorFactory factory,
                                     int version, String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS Schedule (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mainTitle text not null," +
                "tag1 text not null," +
                "tag2 text not null," +
                "tag3 text not null," +
                "day text not null," +
                "distance text not null," +
                "picture text not null," +
                "title text not null," +
                "content text not null," +
                "startEndFlag text not null" +
                ")";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE Schedule";
        sqLiteDatabase.execSQL(SQL);
    }
}
