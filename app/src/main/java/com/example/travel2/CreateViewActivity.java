package com.example.travel2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class CreateViewActivity extends AppCompatActivity {
    private String TAG = "CreateViewActivity";
    private ImageView img_createView_picture;
    private EditText edit_createView_name,edit_createView_phone,edit_createView_phoneNote,edit_createView_address,
            edit_createView_content;
    private Button btn_createView_save;
    private Spinner spin_createView_tag1,spin_createView_tag2,spin_createView_tag3;
    private String picturePath,tag1,tag2,tag3;
    private int tag1_position,tag2_position,tag3_position;
    private int IMAGE_REQUEST_CODE = 1;
    private static final String DataBaseName = "ViewDataBaseIt_2.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Attraction";
    private static SQLiteDatabase db;
    private ViewSqlDataBaseHelper viewSqlDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);
        initView();
    }

    private void initView(){
        picturePath = "";
        tag1 = "#休閒農場";
        tag2 = "#一日行程";
        tag3 = "#北部";
        tag1_position = 0;
        tag2_position = 0;
        tag3_position = 0;
        viewSqlDataBaseHelper = new ViewSqlDataBaseHelper(getApplicationContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = viewSqlDataBaseHelper.getWritableDatabase();

        img_createView_picture = (ImageView) findViewById(R.id.img_createView_picture);
        edit_createView_name =  (EditText) findViewById(R.id.edit_createView_name);
        edit_createView_phone =  (EditText) findViewById(R.id.edit_createView_phone);
        edit_createView_phoneNote =  (EditText) findViewById(R.id.edit_createView_phoneNote);
        edit_createView_address =  (EditText) findViewById(R.id.edit_createView_address);
        edit_createView_content =  (EditText) findViewById(R.id.edit_createView_address);
        btn_createView_save = (Button) findViewById(R.id.btn_createView_save);
        btn_createView_save.setOnClickListener(onClick);
        img_createView_picture.setOnClickListener(onClick);

        spin_createView_tag1 = (Spinner) findViewById(R.id.spin_createView_tag1);
        List<String> tagItem1 = Arrays.asList("#休閒農場","#觀光果園","#春","#夏","#秋","#冬");
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_createView_tag1.setAdapter(adapter);
        spin_createView_tag1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spin_createView_tag2 = (Spinner) findViewById(R.id.spin_createView_tag2);
        List<String> tagItem2 = Arrays.asList("#一日行程", "#二日行程");
        ArrayAdapter adapte2 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem2);
        adapte2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_createView_tag2.setAdapter(adapte2);
        spin_createView_tag2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spin_createView_tag3 = (Spinner) findViewById(R.id.spin_createView_tag3);
        List<String> tagItem3 = Arrays.asList("#北部","#中部","#南部","#東部");
        ArrayAdapter adapte3 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem3);
        adapte3.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_createView_tag3.setAdapter(adapte3);
        spin_createView_tag3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            if (v.getId() == R.id.btn_createView_save){
                Log.d(TAG, "onClick: btn_createView_save");
                saveData();
                finish();
            }else if (v.getId() == R.id.img_createView_picture){
                Log.d(TAG, "onClick: img_createView_picture");
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
                img_createView_picture.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        Log.d(TAG, "saveData: ");
        long id;
        String name = edit_createView_name.getText().toString();
        String content = edit_createView_content.getText().toString();
        String phone = edit_createView_phone.getText().toString();
        String phoneNote = edit_createView_phoneNote.getText().toString();
        String address = edit_createView_address.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("view_title", name);
        contentValues.put("view_picture", picturePath);
        contentValues.put("view_tag1", tag1);
        contentValues.put("view_tag2", tag2);
        contentValues.put("view_tag3", tag3);
        contentValues.put("view_content", content);

        contentValues.put("view_phone", phone);
        contentValues.put("view_phoneNote", phoneNote);
        contentValues.put("view_address", address);
        contentValues.put("view_tag1_position", tag1_position);
        contentValues.put("view_tag2_position", tag2_position);
        contentValues.put("view_tag3_position", tag3_position);
        id = db.insert(DataBaseTable, null, contentValues);
        Log.d(TAG, "addNewToolItem: save OK");
    }
}