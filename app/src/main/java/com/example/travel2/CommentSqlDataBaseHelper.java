package com.example.travel2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CommentSqlDataBaseHelper extends SQLiteOpenHelper {
    private static final String DataBaseName = "CommentDataBaseIt_1.db";
    private static final int DataBaseVersion = 1;

    public CommentSqlDataBaseHelper(@Nullable Context context, @Nullable String name,
                                 @Nullable SQLiteDatabase.CursorFactory factory,
                                 int version, String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS Comment (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "viewTitle text not null," +
                "name text not null," +
                "startNum INTEGER not null," +
                "content text not null" +
                ")";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE Comment";
        sqLiteDatabase.execSQL(SQL);
    }
}
