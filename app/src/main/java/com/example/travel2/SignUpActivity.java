package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private String TAG = "SignUpActivity";
    private EditText edit_signUp_account,edit_signUp_password,edit_signUp_checkPassword,
            edit_signUp_name;
    private Button btn_signUp_ok;
    private TextView btn_signUp_tip;
    private static final String DataBaseName = "UserDataBaseIt_1.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private UserSqlDataBaseHelper sqlDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }

    private void initView(){
        sqlDataBaseHelper = new UserSqlDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase();

        edit_signUp_account = (EditText) findViewById(R.id.edit_signUp_account);
        edit_signUp_password = (EditText) findViewById(R.id.edit_signUp_password);
        edit_signUp_checkPassword = (EditText) findViewById(R.id.edit_signUp_checkPassword);
        edit_signUp_name = (EditText) findViewById(R.id.edit_signUp_name);
        btn_signUp_ok = (Button) findViewById(R.id.btn_signUp_ok);
        btn_signUp_tip = (TextView) findViewById(R.id.btn_signUp_tip);

        btn_signUp_ok.setOnClickListener(onClick);

    }
    
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_signUp_ok){
                Log.d(TAG, "onClick: ");
                if(edit_signUp_account.getText().toString().equals("admin")){
                    btn_signUp_tip.setText("此帳號不能使用");
                }else if(checkInputNull()){
                    if(checkRepeatPassword()){
                        addNewItem();
                        Toast.makeText(getApplicationContext(),"註冊完成",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        btn_signUp_tip.setText("請確認密碼是否輸入正確");
                    }
                }
            }
        }
    };

    private boolean checkInputNull(){
        if (edit_signUp_account.getText().toString().isEmpty()){
            btn_signUp_tip.setText("請輸入帳號");
            return false;
        }
        if (edit_signUp_password.getText().toString().isEmpty()){
            btn_signUp_tip.setText("請輸入密碼");
            return false;
        }
        if (edit_signUp_checkPassword.getText().toString().isEmpty()){
            btn_signUp_tip.setText("請輸入確認密碼");
            return false;
        }
        if (edit_signUp_name.getText().toString().isEmpty()){
            btn_signUp_tip.setText("請輸入姓名");
            return false;
        }
        return true;
    }

    private boolean checkRepeatPassword(){
        String password = edit_signUp_password.getText().toString();
        String checkPassword = edit_signUp_checkPassword.getText().toString();

        if (password.equals(checkPassword)){
            btn_signUp_tip.setText("OK");
            return true;
        }else {
            btn_signUp_tip.setText("fail");
            return false;
        }
    }

    private void addNewItem(){
        /**新增一筆資料*/
        String account = edit_signUp_account.getText().toString();
        String password = edit_signUp_password.getText().toString();
        String name = edit_signUp_name.getText().toString();
        /**判斷帳號是否重複*/
        if (checkItem(account)){
            long id;
            ContentValues contentValues = new ContentValues();
            contentValues.put("account",account);
            contentValues.put("password",password);
            contentValues.put("name",name);
            id = db.insert(DataBaseTable,null,contentValues);
            Log.d(TAG, "addNewItem: save OK");
        }else {
            btn_signUp_tip.setText("帳號已被註冊");
        }
    }

    private boolean checkItem(String account){
        String loopAccount;

        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
        Log.d(TAG, "onClick: c.getCount() = "+c.getCount());
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            loopAccount = c.getString(1);
            Log.d(TAG, "onClick: account = "+c.getString(1));
            Log.d(TAG, "onClick: password = "+c.getString(2));
            Log.d(TAG, "onClick: name = "+c.getString(3));
            if (loopAccount.equals(account)){
                return false;
            }
            c.moveToNext();
            Log.d(TAG, "onClick: FragmentA -------------");
        }
        return true;
    }
}