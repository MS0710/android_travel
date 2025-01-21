package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private String TAG = "CommentActivity";
    private AppCompatRatingBar rb_comment_rating;
    private TextView txt_comment_name;
    private EditText edit_comment_content;
    private Button btn_comment_send;
    private ListView lv_comment_list;
    private List<Comment> list;
    private CommentListAdapter commentListAdapter;
    private String name;
    private int ratingBarNum;
    private ImageView img_comment_picture;
    private TextView txt_comment_title,txt_comment_tag1,txt_comment_tag2,txt_comment_tag3,txt_comment_content;
    private String title,picture,tag1,tag2,tag3,content;
    private static final String DataBaseName = "CommentDataBaseIt_1.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Comment";
    private static SQLiteDatabase db;
    private CommentSqlDataBaseHelper commentSqlDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

    private void initView(){
        commentSqlDataBaseHelper = new CommentSqlDataBaseHelper(getApplicationContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = commentSqlDataBaseHelper.getWritableDatabase();

        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        name = getPrefs.getString("name", "");
        ratingBarNum = 1;

        title = getIntent().getStringExtra("title");
        picture = getIntent().getStringExtra("picture");
        tag1 = getIntent().getStringExtra("tag1");
        tag2 = getIntent().getStringExtra("tag2");
        tag3 = getIntent().getStringExtra("tag3");
        content = getIntent().getStringExtra("content");

        img_comment_picture = (ImageView)findViewById(R.id.img_comment_picture);
        txt_comment_title = (TextView) findViewById(R.id.txt_comment_title);
        txt_comment_tag1 = (TextView) findViewById(R.id.txt_comment_tag1);
        txt_comment_tag2 = (TextView) findViewById(R.id.txt_comment_tag2);
        txt_comment_tag3 = (TextView) findViewById(R.id.txt_comment_tag3);
        txt_comment_content = (TextView) findViewById(R.id.txt_comment_content);
        txt_comment_title.setText(title);
        txt_comment_tag1.setText(tag1);
        txt_comment_tag2.setText(tag2);
        txt_comment_tag3.setText(tag3);
        txt_comment_content.setText(content);
        if(picture.contains("R.drawable")){
            Bitmap bitmap = BitmapFactory.decodeFile(picture);
            img_comment_picture.setImageBitmap(bitmap);
        }else {
            img_comment_picture.setImageResource(Integer.parseInt(picture));
        }

        txt_comment_name = (TextView)findViewById(R.id.txt_comment_name);
        edit_comment_content = (EditText)findViewById(R.id.edit_comment_content);
        txt_comment_name.setText(name);
        rb_comment_rating = (AppCompatRatingBar)findViewById(R.id.rb_comment_rating);

        rb_comment_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.d(TAG, "onRatingChanged: ratingBar = " + v);
                ratingBarNum = (int) v;
            }
        });

        lv_comment_list = (ListView) findViewById(R.id.lv_comment_list);
        list = new ArrayList<>();
        /*for (int i=0 ; i<5 ; i++){
            Comment comment = new Comment("Jack",i,"風景如畫");
            list.add(comment);
        }*/
        readCommentData();
        commentListAdapter = new CommentListAdapter(getApplicationContext(),list);
        lv_comment_list.setAdapter(commentListAdapter);

        btn_comment_send = (Button) findViewById(R.id.btn_comment_send);
        btn_comment_send.setOnClickListener(onClick);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btn_comment_send){
                Log.d(TAG, "onClick: ");
                /*Comment comment = new Comment(name,ratingBarNum,edit_comment_content.getText().toString());
                list.add(comment);
                commentListAdapter.notifyDataSetChanged();*/
                saveCommentData();
                ratingBarNum = 1;
                rb_comment_rating.setRating(ratingBarNum);
                edit_comment_content.setText("");
                readCommentData();
                commentListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        readCommentData();
        commentListAdapter.notifyDataSetChanged();
    }

    private void saveCommentData(){
        Log.d(TAG, "saveData: ");
        long id;
        String commentContent = edit_comment_content.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("viewTitle", title);
        contentValues.put("name", name);
        contentValues.put("startNum", ratingBarNum);
        contentValues.put("content", commentContent);
        id = db.insert(DataBaseTable, null, contentValues);
        Log.d(TAG, "addNewToolItem: save OK");
    }

    private void readCommentData(){
        list.clear();
        String viewTitle_key = "viewTitle";
        int id;
        String _viewTitle,_name,_content;
        int _startNum;

        Cursor c = db.query(DataBaseTable,null,viewTitle_key+"=?",new String[]{title},null,null,null);
        Log.d(TAG, "loopToolItem: c.getCount() = "+c.getCount());
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            id = c.getInt(0);
            _viewTitle = c.getString(1);
            _name = c.getString(2);
            _startNum = c.getInt(3);
            _content = c.getString(4);

            Log.d(TAG, "onClick: _id = "+id);
            Log.d(TAG, "onClick: _viewTitle = "+_viewTitle);
            Log.d(TAG, "onClick: _name = "+_name);
            Log.d(TAG, "onClick: _startNum = "+_startNum);
            Log.d(TAG, "onClick: _content = "+_content);
            c.moveToNext();
            Log.d(TAG, "-------------");
            Comment comment = new Comment(_name,_startNum,_content);
            list.add(comment);
        }

    }
}