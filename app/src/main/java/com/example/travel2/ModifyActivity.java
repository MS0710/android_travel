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

public class ModifyActivity extends AppCompatActivity {
    private String TAG = "ModifyActivity";
    private ImageView img_modify_picture;
    private EditText edit_modify_name,edit_modify_phone,edit_modify_phoneNote,edit_modify_address,
            edit_modify_content;
    private Button btn_modify_save;
    private Spinner spin_modify_tag1,spin_modify_tag2,spin_modify_tag3;
    private String picturePath;
    private int IMAGE_REQUEST_CODE = 1;
    private static final String DataBaseName = "ViewDataBaseIt_3.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Attraction";
    private static SQLiteDatabase db;
    private ViewSqlDataBaseHelper viewSqlDataBaseHelper;
    private int id;
    private String title,phone,phoneNote,address,tag1,tag2,tag3,content;
    private int tag1_position,tag2_position,tag3_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        initView();
    }

    private void initView(){
        id = getIntent().getIntExtra("id",0);
        title = getIntent().getStringExtra("title");
        picturePath = getIntent().getStringExtra("picture");
        phone = getIntent().getStringExtra("phone");
        phoneNote = getIntent().getStringExtra("phoneNote");
        address = getIntent().getStringExtra("address");
        tag1_position = getIntent().getIntExtra("tag1_position",0);
        tag1 = getIntent().getStringExtra("tag1");
        tag2_position = getIntent().getIntExtra("tag2_position",0);
        tag2 = getIntent().getStringExtra("tag2");
        tag3_position = getIntent().getIntExtra("tag3_position",0);
        tag3 = getIntent().getStringExtra("tag3");
        content = getIntent().getStringExtra("content");
        Log.d(TAG, "initView: address = "+address);
        Log.d(TAG, "initView: content = "+content);
        Log.d(TAG, "initView: picturePath = "+picturePath);
        Log.d(TAG, "initView: tag2_position = "+tag2_position);
        Log.d(TAG, "initView: tag2 = "+tag2);

        viewSqlDataBaseHelper = new ViewSqlDataBaseHelper(getApplicationContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = viewSqlDataBaseHelper.getWritableDatabase();

        img_modify_picture = (ImageView) findViewById(R.id.img_modify_picture);
        edit_modify_name =  (EditText) findViewById(R.id.edit_modify_name);
        edit_modify_phone =  (EditText) findViewById(R.id.edit_modify_phone);
        edit_modify_phoneNote =  (EditText) findViewById(R.id.edit_modify_phoneNote);
        edit_modify_address =  (EditText) findViewById(R.id.edit_modify_address);
        edit_modify_content =  (EditText) findViewById(R.id.edit_modify_content);
        btn_modify_save = (Button) findViewById(R.id.btn_modify_save);
        btn_modify_save.setOnClickListener(onClick);
        img_modify_picture.setOnClickListener(onClick);

        spin_modify_tag1 = (Spinner) findViewById(R.id.spin_modify_tag1);
        List<String> tagItem = Arrays.asList("#休閒農場","#觀光果園","#春","#夏","#秋","#冬");
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_modify_tag1.setAdapter(adapter);
        spin_modify_tag1.setSelection(tag1_position);
        spin_modify_tag1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spin_modify_tag2 = (Spinner) findViewById(R.id.spin_modify_tag2);
        List<String> tagItem2 = Arrays.asList("#一日行程", "#二日行程");
        ArrayAdapter adapter2 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_modify_tag2.setAdapter(adapter2);
        spin_modify_tag2.setSelection(tag2_position);
        spin_modify_tag2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spin_modify_tag3 = (Spinner) findViewById(R.id.spin_modify_tag3);
        List<String> tagItem3 = Arrays.asList("#北部","#中部","#南部","#東部");
        ArrayAdapter adapter3 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_layout , tagItem3);
        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spin_modify_tag3.setAdapter(adapter3);
        spin_modify_tag3.setSelection(tag3_position);
        spin_modify_tag3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        edit_modify_name.setText(title);
        edit_modify_phone.setText(phone);
        edit_modify_phoneNote.setText(phoneNote);
        edit_modify_address.setText(address);
        edit_modify_content.setText(content);
        if(picturePath.contains("/")){
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            img_modify_picture.setImageBitmap(bitmap);
        }else {
            img_modify_picture.setImageResource(Integer.parseInt(picturePath));
        }

    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_modify_save){
                Log.d(TAG, "onClick: btn_modify_save");
                updateData();
                finish();
            }else if (v.getId() == R.id.img_modify_picture){
                Log.d(TAG, "onClick: img_modify_picture");
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
                img_modify_picture.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }
        }
    }
    private void updateData(){
        title = edit_modify_name.getText().toString();
        content = edit_modify_content.getText().toString();
        phone = edit_modify_phone.getText().toString();
        phoneNote = edit_modify_phoneNote.getText().toString();
        address = edit_modify_address.getText().toString();
        Log.d(TAG, "updateData: phone = "+phone);

        ContentValues contentValues = new ContentValues();
        contentValues.put("view_title",title);
        contentValues.put("view_picture",picturePath);
        contentValues.put("view_tag1",tag1);
        contentValues.put("view_tag2",tag2);
        contentValues.put("view_tag3",tag3);
        contentValues.put("view_content",content);
        contentValues.put("view_phone",phone);
        contentValues.put("view_phoneNote",phoneNote);
        contentValues.put("view_address",address);
        contentValues.put("view_tag1_position",tag1_position);
        contentValues.put("view_tag2_position",tag2_position);
        contentValues.put("view_tag3_position",tag3_position);
        db.update(DataBaseTable,contentValues,"_id="+"'"+id+"'",null);
    }
}