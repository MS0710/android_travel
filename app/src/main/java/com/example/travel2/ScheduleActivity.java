package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    private String TAG = "ScheduleActivity";
    private ListView lv_schedule_list,lv_schedule_TwoList;
    private List<Schedule> list;
    private List<Schedule> twoList;
    private ScheduleListAdapter scheduleListAdapter;
    private ScheduleListAdapter scheduleTwoListAdapter;
    private static final String DataBaseName = "ScheduleDataBaseIt_5.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Schedule";
    private static SQLiteDatabase db;
    private ScheduleSqlDataBaseHelper scheduleSqlDataBaseHelper;
    private LinearLayout lly_schedule_twoDay;
    private String mTitle;
    private TextView txt_schedule_mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initView();
    }

    private void initView(){
        mTitle = getIntent().getStringExtra("MainTitle");
        scheduleSqlDataBaseHelper = new ScheduleSqlDataBaseHelper(getApplicationContext(),DataBaseName,
                null,DataBaseVersion,DataBaseTable);
        db = scheduleSqlDataBaseHelper.getWritableDatabase();

        txt_schedule_mainTitle = (TextView) findViewById(R.id.txt_schedule_mainTitle);
        txt_schedule_mainTitle.setText(mTitle);
        lly_schedule_twoDay = (LinearLayout)findViewById(R.id.lly_schedule_twoDay);
        lv_schedule_list = (ListView) findViewById(R.id.lv_schedule_list);
        list = new ArrayList<>();
        twoList = new ArrayList<>();

        readData();

        scheduleListAdapter = new ScheduleListAdapter(getApplicationContext(),list);
        lv_schedule_list.setAdapter(scheduleListAdapter);

        lv_schedule_TwoList = (ListView) findViewById(R.id.lv_schedule_TwoList);

        scheduleTwoListAdapter = new ScheduleListAdapter(getApplicationContext(),twoList);
        lv_schedule_TwoList.setAdapter(scheduleTwoListAdapter);
    }

    private void readData(){
        String mainTitle_key = "mainTitle";
        String _mainTitle,_tag1,_tag2,_tag3,_day,_distance,_picture,_title,_content,_startEndFlag;
        int id;


        Cursor c = db.query(DataBaseTable,null,mainTitle_key+"=?",new String[]{mTitle},null,null,null);
        Log.d(TAG, "loopToolItem: c.getCount() = "+c.getCount());
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            id = c.getInt(0);
            _mainTitle = c.getString(1);
            _tag1 = c.getString(2);
            _tag2 = c.getString(3);
            _tag3 = c.getString(4);
            _day = c.getString(5);
            _distance = c.getString(6);
            _picture = c.getString(7);
            _title = c.getString(8);
            _content = c.getString(9);
            _startEndFlag = c.getString(10);

            Log.d(TAG, "onClick: _id = "+id);
            Log.d(TAG, "onClick: _mainTitle = " + _mainTitle);
            Log.d(TAG, "onClick: _tag1 = " + _tag1);
            Log.d(TAG, "onClick: _tag2 = " + _tag2);
            Log.d(TAG, "onClick: _tag3 = " + _tag3);
            Log.d(TAG, "onClick: _day = " + _day);
            Log.d(TAG, "onClick: _distance = " + _distance);
            Log.d(TAG, "onClick: _picture = " + _picture);
            Log.d(TAG, "onClick: _title = " + _title);
            Log.d(TAG, "onClick: _content = " + _content);
            Log.d(TAG, "onClick: _startEndFlag = " + _startEndFlag);
            c.moveToNext();
            Log.d(TAG, "onClick: SearchToolActivity -------------");
            if (_day.equals("1")){
                Schedule schedule = new Schedule(_day,_distance,_picture,_title,_content);
                list.add(schedule);
                if (_startEndFlag.equals("end") && _day.equals("1")){
                    lly_schedule_twoDay.setVisibility(View.GONE);
                }else {
                    lly_schedule_twoDay.setVisibility(View.VISIBLE);
                }
            }else if (_day.equals("2")){
                Schedule schedule = new Schedule(_day,_distance,_picture,_title,_content);
                twoList.add(schedule);
            }
        }

    }
}