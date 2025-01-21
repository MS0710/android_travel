package com.example.travel2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class CreateStartPlanActivity extends AppCompatActivity {
    private String TAG = "CreateStartPlanActivity";
    private ImageView img_createStart_picture;
    private EditText edit_createStart_name,edit_createStart_content;
    private Spinner spin_createStart_tag1,spin_createStart_tag2,spin_createStart_tag3,
            spin_createStart_day;
    private Button btn_createStart_save;
    private static final String DataBaseName = "ScheduleDataBaseIt_5.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Schedule";
    private static SQLiteDatabase db;
    private ScheduleSqlDataBaseHelper scheduleSqlDataBaseHelper;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;
    private int tag1_position,tag2_position,tag3_position,day_position;
    private String tag1,tag2,tag3,day;
    private String mainTitle,plan_flag,picturePath;
    private int IMAGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_start_plan);
        initView();
    }

    private void initView(){
        tag1 = "#休閒農場";
        tag2 = "#一日行程";
        tag3 = "#北部";
        tag1_position = 0;
        tag2_position = 0;
        tag3_position = 0;
        picturePath = "";
        scheduleSqlDataBaseHelper = new ScheduleSqlDataBaseHelper(getApplicationContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = scheduleSqlDataBaseHelper.getWritableDatabase();

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        mainTitle = getPrefs.getString("mainTitle", "");
        plan_flag = getPrefs.getString("plan_flag", "0");

        img_createStart_picture = (ImageView) findViewById(R.id.img_createStart_picture);
        edit_createStart_name = (EditText) findViewById(R.id.edit_createStart_name);
        edit_createStart_content = (EditText) findViewById(R.id.edit_createStart_content);
        btn_createStart_save = (Button) findViewById(R.id.btn_createStart_save);
        btn_createStart_save.setOnClickListener(onClick);
        img_createStart_picture.setOnClickListener(onClick);

        spin_createStart_tag1 = (Spinner)findViewById(R.id.spin_createStart_tag1);
        List<String> tagItem1 = Arrays.asList("#休閒農場","#觀光果園","#春","#夏","#秋","#冬");
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_createStart_tag1.setAdapter(adapter);
        spin_createStart_tag1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag1_position = position;
                tag1 = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: tag1 = "+tag1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_createStart_tag2 = (Spinner) findViewById(R.id.spin_createStart_tag2);
        List<String> tagItem2 = Arrays.asList("#一日行程", "#二日行程");
        ArrayAdapter adapte2 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem2);
        adapte2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_createStart_tag2.setAdapter(adapte2);
        spin_createStart_tag2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag2_position = position;
                tag2 = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: tag2 = "+tag2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_createStart_tag3 = (Spinner) findViewById(R.id.spin_createStart_tag3);
        List<String> tagItem3 = Arrays.asList("#北部","#中部","#南部","#東部");
        ArrayAdapter adapte3 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem3);
        adapte3.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_createStart_tag3.setAdapter(adapte3);
        spin_createStart_tag3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag3_position = position;
                tag3 = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: tag3 = "+tag3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_createStart_save){
                Log.d(TAG, "onClick: btn_createStart_save");
                editor = getPrefs.edit();
                editor.putString("plan_flag", "1");
                editor.apply();
                saveData();
                finish();
            }if(v.getId() == R.id.img_createStart_picture){
                Log.d(TAG, "onClick: img_createStart_picture");
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex); //获取照片路径
                Log.d(TAG, "onActivityResult: path = "+picturePath);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                img_createStart_picture.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        Log.d(TAG, "saveData: ");
        String title = edit_createStart_name.getText().toString();
        String content = edit_createStart_content.getText().toString();
        long id;
        ContentValues contentValues = new ContentValues();
        contentValues.put("mainTitle",mainTitle);
        contentValues.put("tag1", tag1);
        contentValues.put("tag2", tag2);
        contentValues.put("tag3", tag3);
        contentValues.put("day", "1");
        contentValues.put("distance", "0");
        contentValues.put("picture", picturePath);
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("startEndFlag", "start");
        id = db.insert(DataBaseTable, null, contentValues);
        Log.d(TAG, "addNewToolItem: save OK");
    }
}