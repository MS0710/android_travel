package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AttractionsContentActivity extends AppCompatActivity {
    private String TAG = "AttractionsContentActivity";
    private ImageView img_attractionsContent_picture;
    private TextView txt_attractionsContent_phone,txt_attractionsContent_phoneNote,txt_attractionsContent_address,
            txt_attractionsContent_tag1,txt_attractionsContent_tag2,txt_attractionsContent_tag3,txt_attractionsContent_content;
    private String picture,phone,phoneNote,address,content;
    private String[] tag = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_content);
        initView();
    }

    private void initView(){
        picture = getIntent().getStringExtra("picture");
        phone = getIntent().getStringExtra("phone");
        phoneNote = getIntent().getStringExtra("phoneNote");
        address = getIntent().getStringExtra("address");
        tag[0] = getIntent().getStringExtra("tag1");
        tag[1] = getIntent().getStringExtra("tag2");
        tag[2] = getIntent().getStringExtra("tag3");
        content = getIntent().getStringExtra("content");
        txt_attractionsContent_phone = (TextView)findViewById(R.id.txt_attractionsContent_phone);
        txt_attractionsContent_phoneNote = (TextView)findViewById(R.id.txt_attractionsContent_phoneNote);
        txt_attractionsContent_address = (TextView)findViewById(R.id.txt_attractionsContent_address);
        txt_attractionsContent_tag1 = (TextView)findViewById(R.id.txt_attractionsContent_tag1);
        txt_attractionsContent_tag2 = (TextView)findViewById(R.id.txt_attractionsContent_tag2);
        txt_attractionsContent_tag3 = (TextView)findViewById(R.id.txt_attractionsContent_tag3);
        txt_attractionsContent_content = (TextView)findViewById(R.id.txt_attractionsContent_content);

        img_attractionsContent_picture = (ImageView) findViewById(R.id.img_attractionsContent_picture);
        img_attractionsContent_picture.setImageResource(Integer.parseInt(picture));
        txt_attractionsContent_phone.setText(phone);
        txt_attractionsContent_phoneNote.setText(phoneNote);
        txt_attractionsContent_address.setText(address);
        if (tag[0].equals(" ")){
            txt_attractionsContent_tag1.setVisibility(View.GONE);
        }else {
            txt_attractionsContent_tag1.setText(tag[0]);
        }
        if (tag[1].equals(" ")){
            txt_attractionsContent_tag2.setVisibility(View.GONE);
        }else {
            txt_attractionsContent_tag2.setText(tag[1]);
        }
        if (tag[2].equals(" ")){
            txt_attractionsContent_tag3.setVisibility(View.GONE);
        }else {
            txt_attractionsContent_tag3.setText(tag[2]);
        }
        txt_attractionsContent_content.setText(content);

    }
}