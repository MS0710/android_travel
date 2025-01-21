package com.example.travel2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserSqlDataBaseHelper extends SQLiteOpenHelper {
    private static final String DataBaseName = "UserDataBaseIt_1.db";
    private static final int DataBaseVersion = 1;

    public UserSqlDataBaseHelper(@Nullable Context context, @Nullable String name,
                                 @Nullable SQLiteDatabase.CursorFactory factory,
                                 int version, String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**產生資料表的 SQL 寫在這 onCreate*/
        /** Android 載入時找不到生成的資料庫檔案，就會觸發 onCreate*/
        String SqlTable = "CREATE TABLE IF NOT EXISTS Users (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "account text not null," +
                "password text not null," +
                "name text not null" +
                ")";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /**如果資料庫結構有改變了就會觸發 onUpgrade */
        final String SQL = "DROP TABLE Users";
        sqLiteDatabase.execSQL(SQL);
    }
}
