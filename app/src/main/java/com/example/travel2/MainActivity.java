package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Button btn_main_signUp,btn_main_signIn;
    private EditText edit_main_account,edit_main_password;
    private ImageView img_main_watchPassword;
    private TextView txt_main_forgetPassword;
    private static final String DataBaseName = "UserDataBaseIt_1.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private UserSqlDataBaseHelper sqlDataBaseHelper;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;
    private String name;
    private boolean flag_password;
    private static String[] PERMISSON_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private boolean isPermissionPassed = false;
    private static final int REQUEST_EXTERNAL_STORAGE = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        name = "";
        flag_password = false;
        /**獲取相片讀取權限*/
        verifyStoragePermissions(MainActivity.this);
        getPermission();

        /**建立SQL資料庫*/
        sqlDataBaseHelper = new UserSqlDataBaseHelper(getApplicationContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase();
        /**創建SharedPreferences*/
        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        /**取得SharedPreferences內容*/
        getPrefs.getString("name", "");

        /**宣告各項控鍵*/
        btn_main_signUp = (Button) findViewById(R.id.btn_main_signUp);
        btn_main_signIn = (Button) findViewById(R.id.btn_main_signIn);
        edit_main_account = (EditText) findViewById(R.id.edit_main_account);
        edit_main_password = (EditText) findViewById(R.id.edit_main_password);
        img_main_watchPassword = (ImageView)findViewById(R.id.img_main_watchPassword);
        txt_main_forgetPassword = (TextView) findViewById(R.id.txt_main_forgetPassword);
        btn_main_signUp.setOnClickListener(onClick);
        btn_main_signIn.setOnClickListener(onClick);
        img_main_watchPassword.setOnClickListener(onClick);
        txt_main_forgetPassword.setOnClickListener(onClick);
        edit_main_account.setText("asd");
        edit_main_password.setText("123");
        //edit_main_account.setText("admin");
        //edit_main_password.setText("admin");

    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_main_signUp){
                Log.d(TAG, "onClick: btn_main_signUp");
                /**創建需要啟動的Activity對應的Intent*/
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                /**啟動intent對應的Activity*/
                startActivity(intent);
            }else if (v.getId() == R.id.btn_main_signIn){
                Log.d(TAG, "onClick: btn_main_signIn");
                /**判斷帳號密碼輸入如為admin,則進入管理者模式*/
                if (edit_main_account.getText().toString().equals("admin") &&
                edit_main_password.getText().toString().equals("admin")){
                    Intent intent = new Intent(MainActivity.this,AdminActivity.class);
                    startActivity(intent);
                }else if (checkAccount()){
                    /**判斷帳號密碼輸入進入使用者者模式*/
                    Toast.makeText(getApplicationContext(),"登入成功",Toast.LENGTH_SHORT).show();
                    Intent intentHome = new Intent(MainActivity.this,HomeActivity.class);
                    //intentHome.putExtra("name",name);
                    /**取得SharedPreferences.Editor編輯內容*/
                    editor = getPrefs.edit();
                    /**放入字串，並定義索引為"name"*/
                    editor.putString("name", name);
                    /**提交；提交結果將會回傳一布林值*/
                    /**若不需要提交結果，則可使用.apply()*/
                    editor.apply();
                    startActivity(intentHome);
                }else {
                    Toast.makeText(getApplicationContext(),"帳號或密碼錯誤",Toast.LENGTH_SHORT).show();
                }
                //Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                //startActivity(intent);
            }else if (v.getId() == R.id.img_main_watchPassword){
                Log.d(TAG, "onClick: img_main_watchPassword");
                if(flag_password){
                    edit_main_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag_password = false;
                }else {
                    edit_main_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag_password = true;
                }
            }else if (v.getId() == R.id.txt_main_forgetPassword) {
                Intent intent = new Intent(MainActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        }
    };

    private boolean checkAccount(){

        String account = edit_main_account.getText().toString();
        String password = edit_main_password.getText().toString();

        String loopAccount;
        String loopPassword;

        /**取得資料表*/
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
        Log.d(TAG, "onClick: c.getCount() = "+c.getCount());
        c.moveToFirst();
        /**資料表非空，使用cursor抓資料*/
        for(int i=0;i<c.getCount();i++){
            loopAccount = c.getString(1);
            loopPassword = c.getString(2);
            name = c.getString(3);

            Log.d(TAG, "onClick: account = "+loopAccount);
            Log.d(TAG, "onClick: password = "+loopPassword);
            Log.d(TAG, "onClick: name = "+name);
            if (account.equals(loopAccount) && password.equals(loopPassword)){
                return true;
            }
            c.moveToNext();
            Log.d(TAG, "onClick: FragmentA -------------");
        }
        return false;
    }

    public static void verifyStoragePermissions(Activity activity){
        try {
            int permission = ActivityCompat.checkSelfPermission(activity,"android.permission.WRITE_EXTERNAL_STORAGE");
            if(permission!= PackageManager.PERMISSION_GRANTED){
                /**判断是否已经授予權限**/
                ActivityCompat.requestPermissions(activity,PERMISSON_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, PERMISSON_STORAGE[1])
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{PERMISSON_STORAGE[1]}, 100);
        } else {
            isPermissionPassed = true;
        }
    }
}