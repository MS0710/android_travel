package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private String TAG = "AdminActivity";
    private Button btn_admin_addNew;
    private ListView lv_admin_list;
    private AttractionsListAdapter adapter;
    private List<Attractions> list;
    private String[] picture = new String[15];
    private String[] title = new String[15];
    private String[][] tag = new String[15][3];
    private int[][] tag_position = new int[15][3];
    private String[] content = new String[15];
    private String[] phone = new String[15];
    private String[] phoneNote = new String[15];
    private String[] address = new String[15];
    private static final String DataBaseName = "ViewDataBaseIt_3.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Attraction";
    private static SQLiteDatabase db;
    private ViewSqlDataBaseHelper viewSqlDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
    }

    private void initView(){
        viewSqlDataBaseHelper = new ViewSqlDataBaseHelper(getApplicationContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = viewSqlDataBaseHelper.getWritableDatabase();

        btn_admin_addNew = (Button) findViewById(R.id.btn_admin_addNew);
        lv_admin_list = (ListView)findViewById(R.id.lv_admin_list);
        btn_admin_addNew.setOnClickListener(onClick);

        list = new ArrayList<>();
        if (readData()<1){
            setData();
            saveData();
        }
        readData();
        adapter = new AttractionsListAdapter(getApplicationContext(), list);
        lv_admin_list.setAdapter(adapter);
        lv_admin_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getApplicationContext(), "" + i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("id", list.get(i).getId());
                intent.putExtra("title", list.get(i).getTitle());
                intent.putExtra("picture", list.get(i).getPicture());
                intent.putExtra("phone", list.get(i).getPhone());
                intent.putExtra("phoneNote", list.get(i).getPhoneNote());
                intent.putExtra("address", list.get(i).getAddress());
                intent.putExtra("tag1_position", list.get(i).getTag1_position());
                intent.putExtra("tag1", list.get(i).getTag1());
                intent.putExtra("tag2_position", list.get(i).getTag2_position());
                intent.putExtra("tag2", list.get(i).getTag2());
                intent.putExtra("tag3_position", list.get(i).getTag3_position());
                intent.putExtra("tag3", list.get(i).getTag3());
                intent.putExtra("content", list.get(i).getContent());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        readData();
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_admin_addNew){
                Log.d(TAG, "onClick: btn_admin_addNew");
                Intent intent = new Intent(getApplicationContext(), CreateViewActivity.class);
                startActivity(intent);
            }
        }
    };

    private void setData() {
        int i;
        i = 0;
        picture[i] = String.valueOf(R.drawable.view1);
        title[i] = "淡水老街";
        tag_position[i][0] = 3;
        tag[i][0] = "#夏";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 0;
        tag[i][2] = "#北部";
        content[i] = getString(R.string.view1_content);
        phone[i] = "02-2626-7613";
        phoneNote[i] = "（淡水旅遊服務中心）";
        address[i] = "新北市淡水區中正路";

        i = 1;
        picture[i] = String.valueOf(R.drawable.view2);
        title[i] = "士林夜市";
        tag_position[i][0] = 4;
        tag[i][0] = "#秋";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 0;
        tag[i][2] = "#北部";
        content[i] = getString(R.string.view2_content);
        phone[i] = "無";
        phoneNote[i] = "";
        address[i] = "臺北市士林區大東路、大南路、文林路、基河路";

        i = 2;
        picture[i] = String.valueOf(R.drawable.view3);
        title[i] = "九份";
        tag_position[i][0] = 3;
        tag[i][0] = "#夏";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 0;
        tag[i][2] = "#北部";
        content[i] = getString(R.string.view3_content);
        phone[i] = "02-2406-3270";
        phoneNote[i] = "(九份遊客中心)";
        address[i] = "新北市瑞芳區基山街、輕便路、汽車路、豎崎路一帶";

        i = 3;
        picture[i] = String.valueOf(R.drawable.view4);
        title[i] = "野柳地質公園";
        tag_position[i][0] = 2;
        tag[i][0] = "#春";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 0;
        tag[i][2] = "#北部";
        content[i] = getString(R.string.view4_content);
        phone[i] = "02-2492-2016（野柳遊客中心）";
        phoneNote[i] = "（野柳遊客中心）";
        address[i] = "新北市萬里區港東路167-1號";

        i = 4;
        picture[i] = String.valueOf(R.drawable.view5);
        title[i] = "旗津";
        tag_position[i][0] = 3;
        tag[i][0] = "#夏";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 2;
        tag[i][2] = "#南部";
        content[i] = getString(R.string.view5_content);
        phone[i] = "07-7995678";
        phoneNote[i] = "（高雄市政府觀光局）";
        address[i] = "高雄市旗津區";

        i = 5;
        picture[i] = String.valueOf(R.drawable.view6);
        title[i] = "陽明山國家公園";
        tag_position[i][0] = 2;
        tag[i][0] = "#春";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 0;
        tag[i][2] = "#北部";
        content[i] = getString(R.string.view6_content);
        phone[i] = "02-2861-3601";
        phoneNote[i] = "";
        address[i] = "臺北市北投區竹子湖路1-20號";

        i = 6;
        picture[i] = String.valueOf(R.drawable.view7);
        title[i] = "烏來";
        tag_position[i][0] = 5;
        tag[i][0] = "#冬";
        tag_position[i][1] = 1;
        tag[i][1] = "#二日行程";
        tag_position[i][2] = 0;
        tag[i][2] = "#北部";
        content[i] = getString(R.string.view7_content);
        phone[i] = "02-2661-6355";
        phoneNote[i] = "（烏來遊客中心）";
        address[i] = "新北市烏來區烏來街45-1號";

        i = 7;
        picture[i] = String.valueOf(R.drawable.view8);
        title[i] = "西子灣";
        tag_position[i][0] = 3;
        tag[i][0] = "#夏";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 2;
        tag[i][2] = "#南部";
        content[i] = getString(R.string.view8_content);
        phone[i] = "07-5250005";
        phoneNote[i] = "";
        address[i] = "高雄市鼓山區蓮海路51號";

        i = 8;
        picture[i] = String.valueOf(R.drawable.view9);
        title[i] = "高美濕地";
        tag_position[i][0] = 4;
        tag[i][0] = "#秋";
        tag_position[i][1] = 1;
        tag[i][1] = "#二日行程";
        tag_position[i][2] = 1;
        tag[i][2] = "#中部";
        content[i] = getString(R.string.view9_content);
        phone[i] = "04-26565810";
        phoneNote[i] = "";
        address[i] = "臺中市清水區大甲溪出海口";

        i = 9;
        picture[i] = String.valueOf(R.drawable.view10);
        title[i] = "臺北龍山寺";
        tag_position[i][0] = 2;
        tag[i][0] = "#春";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 0;
        tag[i][2] = "#北部";
        content[i] = getString(R.string.view10_content);
        phone[i] = "02-2302-5162";
        phoneNote[i] = "";
        address[i] = "臺北市萬華區廣州街211號";

        i = 10;
        picture[i] = String.valueOf(R.drawable.view11);
        title[i] = "勝興車站";
        tag_position[i][0] = 2;
        tag[i][0] = "#春";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 1;
        tag[i][2] = "#中部";
        content[i] = getString(R.string.view11_content);
        phone[i] = "037-878599";
        phoneNote[i] = "";
        address[i] = "苗栗縣三義鄉勝興村14鄰勝興88號";

        i = 11;
        picture[i] = String.valueOf(R.drawable.view12);
        title[i] = "蓮池潭";
        tag_position[i][0] = 2;
        tag[i][0] = "#春";
        tag_position[i][1] = 0;
        tag[i][1] = "#一日行程";
        tag_position[i][2] = 2;
        tag[i][2] = "#南部";
        content[i] = getString(R.string.view12_content);
        phone[i] = "07-5883242";
        phoneNote[i] = "";
        address[i] = "高雄市左營區蓮潭路";
    }


    private void saveData() {
        Log.d(TAG, "saveData: ");
        long id;
        for (int i = 0; i < 12; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("view_title", title[i]);
            contentValues.put("view_picture", picture[i]);
            contentValues.put("view_tag1", tag[i][0]);
            contentValues.put("view_tag2", tag[i][1]);
            contentValues.put("view_tag3", tag[i][2]);
            contentValues.put("view_content", content[i]);
            contentValues.put("view_phone", phone[i]);
            contentValues.put("view_phoneNote", phoneNote[i]);
            contentValues.put("view_address", address[i]);
            contentValues.put("view_tag1_position", tag_position[i][0]);
            contentValues.put("view_tag2_position", tag_position[i][1]);
            contentValues.put("view_tag3_position", tag_position[i][2]);
            id = db.insert(DataBaseTable, null, contentValues);
            Log.d(TAG, "addNewToolItem: save OK");
        }
    }

    private int readData() {
        int _id,_tag1_position,_tag2_position,_tag3_position;
        String _title, _picture, _tag1, _tag2, _tag3, _content, _phone, _phoneNote, _address;
        list.clear();

        Cursor c = db.query(DataBaseTable, null, null, null, null, null, null);
        Log.d(TAG, "onClick: c.getCount() = " + c.getCount());
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            _id = c.getInt(0);
            _title = c.getString(1);
            _picture = c.getString(2);
            _tag1 = c.getString(3);
            _tag2 = c.getString(4);
            _tag3 = c.getString(5);
            _content = c.getString(6);
            _phone = c.getString(7);
            _phoneNote = c.getString(8);
            _address = c.getString(9);
            _tag1_position = c.getInt(10);
            _tag2_position = c.getInt(11);
            _tag3_position = c.getInt(12);

            Log.d(TAG, "onClick: _id = " + _id);
            Log.d(TAG, "onClick: _title = " + _title);
            Log.d(TAG, "onClick: _picture = " + _picture);
            Log.d(TAG, "onClick: _tag1 = " + _tag1);
            Log.d(TAG, "onClick: _tag2 = " + _tag2);
            Log.d(TAG, "onClick: _tag3 = " + _tag3);
            Log.d(TAG, "onClick: _content = " + _content);
            Log.d(TAG, "onClick: _phone = " + _phone);
            Log.d(TAG, "onClick: _phoneNote = " + _phoneNote);
            Log.d(TAG, "onClick: _address = " + _address);
            Log.d(TAG, "onClick: _tag1_position = " + _tag1_position);
            Log.d(TAG, "onClick: _tag2_position = " + _tag2_position);
            Log.d(TAG, "onClick: _tag3_position = " + _tag3_position);
            c.moveToNext();
            Log.d(TAG, "onClick: SearchToolActivity -------------");
            picture[i] = _picture;
            title[i] = _title;
            tag[i][0] = _tag1;
            tag[i][1] = _tag2;
            tag[i][2] = _tag3;
            content[i] = _content;
            phone[i] = _phone;
            phoneNote[i] = _phoneNote;
            address[i] = _address;
            Attractions attractions = new Attractions(_id,_picture, _title,_tag1_position,_tag1,
                    _tag2_position, _tag2, _tag3_position,_tag3, _content, _phone,_phoneNote,_address);
            list.add(attractions);

        }
        return c.getCount();
    }
}