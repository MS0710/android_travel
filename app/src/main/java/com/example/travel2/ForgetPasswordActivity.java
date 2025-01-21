package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {
    private String TAG = "ForgetPasswordActivity";
    private Button btn_forget_search;
    private EditText edit_forget_account;
    private TextView txt_forget_password;
    private static final String DataBaseName = "UserDataBaseIt_1.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private UserSqlDataBaseHelper sqlDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
    }

    private void initView(){
        sqlDataBaseHelper = new UserSqlDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase();

        edit_forget_account = (EditText)findViewById(R.id.edit_forget_account);
        txt_forget_password = (TextView) findViewById(R.id.txt_forget_password);

        btn_forget_search = (Button) findViewById(R.id.btn_forget_search);
        btn_forget_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAccount()){
                    Log.d(TAG, "onClick: 查詢成功");
                }else {
                    Toast.makeText(getApplicationContext(),"查無帳號",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean checkAccount(){

        String name;
        String account = edit_forget_account.getText().toString();

        String loopAccount;
        String loopPassword;

        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
        Log.d(TAG, "onClick: c.getCount() = "+c.getCount());
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            loopAccount = c.getString(1);
            loopPassword = c.getString(2);
            name = c.getString(3);
            Log.d(TAG, "onClick: account = "+loopAccount);
            Log.d(TAG, "onClick: password = "+loopPassword);
            Log.d(TAG, "onClick: name = "+name);
            if (account.equals(loopAccount)){
                txt_forget_password.setText(loopPassword);
                return true;
            }
            c.moveToNext();
            Log.d(TAG, "onClick: FragmentA -------------");
        }
        return false;
    }
}