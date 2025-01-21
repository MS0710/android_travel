package com.example.travel2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ViewSqlDataBaseHelper extends SQLiteOpenHelper {
    private static final String DataBaseName = "ViewDataBaseIt_4.db";
    private static final int DataBaseVersion = 1;

    public ViewSqlDataBaseHelper(@Nullable Context context, @Nullable String name,
                                 @Nullable SQLiteDatabase.CursorFactory factory,
                                 int version, String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS Attraction (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "view_title text not null," +
                "view_picture text not null," +
                "view_tag1 text not null," +
                "view_tag2 text not null," +
                "view_tag3 text not null," +
                "view_content text not null," +
                "view_phone text not null," +
                "view_phoneNote text not null," +
                "view_address text not null," +
                "view_tag1_position INTEGER not null," +
                "view_tag2_position INTEGER not null," +
                "view_tag3_position INTEGER not null" +
                ")";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE Attraction";
        sqLiteDatabase.execSQL(SQL);
    }
}
