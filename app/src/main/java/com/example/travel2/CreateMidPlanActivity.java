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

public class CreateMidPlanActivity extends AppCompatActivity {
    private String TAG = "CreateMidPlanActivity";
    private ImageView img_createMid_picture;
    private EditText edit_createMid_name,edit_createMid_distance,edit_createMid_content;
    private Spinner spin_createMid_day,spin_createMid_startEndFlag;
    private Button btn_createMid_save;
    private static final String DataBaseName = "ScheduleDataBaseIt_5.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Schedule";
    private static SQLiteDatabase db;
    private ScheduleSqlDataBaseHelper scheduleSqlDataBaseHelper;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;
    private int IMAGE_REQUEST_CODE = 1;
    private String picturePath,mainTitle,plan_flag,day,startEndFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mid_plan);
        initView();
    }

    private void initView(){
        picturePath = "";
        scheduleSqlDataBaseHelper = new ScheduleSqlDataBaseHelper(getApplicationContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = scheduleSqlDataBaseHelper.getWritableDatabase();

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        mainTitle = getPrefs.getString("mainTitle", "");
        plan_flag = getPrefs.getString("plan_flag", "0");

        img_createMid_picture = (ImageView) findViewById(R.id.img_createMid_picture);
        btn_createMid_save = (Button) findViewById(R.id.btn_createMid_save);
        img_createMid_picture.setOnClickListener(onClick);
        btn_createMid_save.setOnClickListener(onClick);

        edit_createMid_name = (EditText) findViewById(R.id.edit_createMid_name);
        edit_createMid_distance = (EditText) findViewById(R.id.edit_createMid_distance);
        edit_createMid_content = (EditText) findViewById(R.id.edit_createMid_content);

        spin_createMid_day = (Spinner)findViewById(R.id.spin_createMid_day);
        List<String> tagItem1 = Arrays.asList("1","2");
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_createMid_day.setAdapter(adapter);
        spin_createMid_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //tag1_position = position;
                day = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: tag1 = "+day);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_createMid_startEndFlag = (Spinner)findViewById(R.id.spin_createMid_startEndFlag);
        List<String> tagItem2 = Arrays.asList("中間點","終點");
        ArrayAdapter adapte2 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem2);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_createMid_startEndFlag.setAdapter(adapte2);
        spin_createMid_startEndFlag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //tag1_position = position;
                String flag;
                flag = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: tag1 = "+flag);
                if(flag.equals("中間點")){
                    startEndFlag = "mid";
                }else if(flag.equals("終點")){
                    startEndFlag = "end";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_createMid_save){
                Log.d(TAG, "onClick: btn_createMid_save");
                saveData();
                finish();
            }else if(v.getId() == R.id.img_createMid_picture){
                Log.d(TAG, "onClick: img_createMid_picture");
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
                img_createMid_picture.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        Log.d(TAG, "saveData: ");
        String title = edit_createMid_name.getText().toString();
        String content = edit_createMid_content.getText().toString();
        String distance = edit_createMid_distance.getText().toString();
        long id;
        ContentValues contentValues = new ContentValues();
        contentValues.put("mainTitle",mainTitle);
        contentValues.put("tag1", "");
        contentValues.put("tag2", "");
        contentValues.put("tag3", "");
        contentValues.put("day", day);
        contentValues.put("distance", distance);
        contentValues.put("picture", picturePath);
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("startEndFlag", startEndFlag);
        id = db.insert(DataBaseTable, null, contentValues);
        Log.d(TAG, "addNewToolItem: save OK");
    }
}