package com.example.travel2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TravelPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TravelPlanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "TravelPlanFragment";
    private ListView lv_travelPlan_list,lv_travelSearch_schedule;
    private Button btn_travelPlan_create;
    private List<ScheduleShort> list;
    private String[] mainTitle = new String[20];
    private String[][] tag = new String[20][3];
    private String[][] data = new String[20][6];
    private static final String DataBaseName = "ScheduleDataBaseIt_5.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Schedule";
    private static SQLiteDatabase db;
    private ScheduleSqlDataBaseHelper scheduleSqlDataBaseHelper;
    private ScheduleShortListAdapter scheduleShortListAdapter;
    private List<Schedule> schedule_list;

    public TravelPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelPlanFragment newInstance(String param1, String param2) {
        TravelPlanFragment fragment = new TravelPlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travel_plan, container, false);
        scheduleSqlDataBaseHelper = new ScheduleSqlDataBaseHelper(getContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = scheduleSqlDataBaseHelper.getWritableDatabase();

        btn_travelPlan_create = (Button) view.findViewById(R.id.btn_travelPlan_create);
        btn_travelPlan_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatePlanActivity.class);
                startActivity(intent);
            }
        });
        lv_travelPlan_list = (ListView) view.findViewById(R.id.lv_travelPlan_list);

        list = new ArrayList<>();
        if (readData()<1){
            setData();
            saveData();
        }
        readData();
        scheduleShortListAdapter = new ScheduleShortListAdapter(getActivity(), list);
        lv_travelPlan_list.setAdapter(scheduleShortListAdapter);
        lv_travelPlan_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ScheduleActivity.class);
                intent.putExtra("MainTitle", list.get(position).getMainTitle());
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        readData();
        scheduleShortListAdapter.notifyDataSetChanged();
    }

    private void setData(){
        int i;
        i=0;
        mainTitle[i] = "雲林文化古蹟一日遊";
        tag[i][0] = "春";
        tag[i][1] = "一日行程";
        tag[i][2] = "中部";
        data[i][0] = "1";
        data[i][1] = "0";
        data[i][2] = String.valueOf(R.drawable.sview1_1);
        data[i][3] = "西螺大橋";
        data[i][4] = getString(R.string.sview1_1_content);
        data[i][5] = "start";

        i=1;
        mainTitle[i] = "雲林文化古蹟一日遊";
        tag[i][0] = "春";
        tag[i][1] = "一日行程";
        tag[i][2] = "中部";
        data[i][0] = "1";
        data[i][1] = "31.4256";
        data[i][2] = String.valueOf(R.drawable.sview1_2);
        data[i][3] = "北港朝天宮";
        data[i][4] = getString(R.string.sview1_2_content);
        data[i][5] = "mid";

        i=2;
        mainTitle[i] = "雲林文化古蹟一日遊";
        tag[i][0] = "春";
        tag[i][1] = "一日行程";
        tag[i][2] = "中部";
        data[i][0] = "1";
        data[i][1] = "20.2621";
        data[i][2] = String.valueOf(R.drawable.sview1_3);
        data[i][3] = "台西漁港";
        data[i][4] = getString(R.string.sview1_3_content);
        data[i][5] = "end";
        //////////////////////
        i=3;
        mainTitle[i] = "奮起湖老街樂齡一日遊";
        tag[i][0] = "春";
        tag[i][1] = "一日行程";
        tag[i][2] = "中部";
        data[i][0] = "1";
        data[i][1] = "0";
        data[i][2] = String.valueOf(R.drawable.sview2_1);
        data[i][3] = "阿里山國家風景區-觸口遊客中心";
        data[i][4] = getString(R.string.sview2_1_content);
        data[i][5] = "start";

        i=4;
        mainTitle[i] = "奮起湖老街樂齡一日遊";
        tag[i][0] = "春";
        tag[i][1] = "一日行程";
        tag[i][2] = "中部";
        data[i][0] = "1";
        data[i][1] = "8.2047";
        data[i][2] = String.valueOf(R.drawable.sview2_2);
        data[i][3] = "隙頂象山觀景臺";
        data[i][4] = getString(R.string.sview2_2_content);
        data[i][5] = "mid";
        i=5;
        mainTitle[i] = "奮起湖老街樂齡一日遊";
        tag[i][0] = "春";
        tag[i][1] = "一日行程";
        tag[i][2] = "中部";
        data[i][0] = "1";
        data[i][1] = "8.7287";
        data[i][2] = String.valueOf(R.drawable.sview2_3);
        data[i][3] = "奮起湖風景區";
        data[i][4] = getString(R.string.sview2_3_content);
        data[i][5] = "end";
        //////////////////////
        i=6;
        mainTitle[i] = "九份金瓜石二日遊";
        tag[i][0] = "春";
        tag[i][1] = "二日行程";
        tag[i][2] = "北部";
        data[i][0] = "1";
        data[i][1] = "0";
        data[i][2] = String.valueOf(R.drawable.sview3_1);
        data[i][3] = "九份";
        data[i][4] = getString(R.string.sview3_1_content);
        data[i][5] = "start";

        i=7;
        mainTitle[i] = "九份金瓜石二日遊";
        tag[i][0] = "春";
        tag[i][1] = "二日行程";
        tag[i][2] = "北部";
        data[i][0] = "2";
        data[i][1] = "1.4819";
        data[i][2] = String.valueOf(R.drawable.sview3_2);
        data[i][3] = "黃金博物館";
        data[i][4] = getString(R.string.sview3_2_content);
        data[i][5] = "end";
        //////////////////////
        i=8;
        mainTitle[i] = "臺北都會一日遊";
        tag[i][0] = "秋";
        tag[i][1] = "一日行程";
        tag[i][2] = "北部";
        data[i][0] = "1";
        data[i][1] = "0";
        data[i][2] = String.valueOf(R.drawable.sview4_1);
        data[i][3] = "總統府";
        data[i][4] = getString(R.string.sview4_1_content);
        data[i][5] = "start";

        i=9;
        mainTitle[i] = "臺北都會一日遊";
        tag[i][0] = "秋";
        tag[i][1] = "一日行程";
        tag[i][2] = "北部";
        data[i][0] = "1";
        data[i][1] = "0.9507";
        data[i][2] = String.valueOf(R.drawable.sview4_2);
        data[i][3] = "國立歷史博物館";
        data[i][4] = getString(R.string.sview4_2_content);
        data[i][5] = "mid";

        i=10;
        mainTitle[i] = "臺北都會一日遊";
        tag[i][0] = "秋";
        tag[i][1] = "一日行程";
        tag[i][2] = "北部";
        data[i][0] = "1";
        data[i][1] = "1.2986";
        data[i][2] = String.valueOf(R.drawable.sview4_3);
        data[i][3] = "臺北龍山寺";
        data[i][4] = getString(R.string.sview4_3_content);
        data[i][5] = "mid";

        i=11;
        mainTitle[i] = "臺北都會一日遊";
        tag[i][0] = "秋";
        tag[i][1] = "一日行程";
        tag[i][2] = "北部";
        data[i][0] = "1";
        data[i][1] = "0.2173";
        data[i][2] = String.valueOf(R.drawable.sview4_4);
        data[i][3] = "華西街觀光夜市";
        data[i][4] = getString(R.string.sview4_4_content);
        data[i][5] = "end";
        //////////////////////
        i=12;
        mainTitle[i] = "高雄夜市二日遊";
        tag[i][0] = "秋";
        tag[i][1] = "二日行程";
        tag[i][2] = "南部";
        data[i][0] = "1";
        data[i][1] = "0";
        data[i][2] = String.valueOf(R.drawable.sview5_1);
        data[i][3] = "壽山國家自然公園";
        data[i][4] = getString(R.string.sview5_1_content);
        data[i][5] = "start";

        i=13;
        mainTitle[i] = "高雄夜市二日遊";
        tag[i][0] = "秋";
        tag[i][1] = "二日行程";
        tag[i][2] = "南部";
        data[i][0] = "1";
        data[i][1] = "3.645";
        data[i][2] = String.valueOf(R.drawable.sview5_2);
        data[i][3] = "高雄市立美術館";
        data[i][4] = getString(R.string.sview5_2_content);
        data[i][5] = "mid";

        i=14;
        mainTitle[i] = "高雄夜市二日遊";
        tag[i][0] = "秋";
        tag[i][1] = "二日行程";
        tag[i][2] = "南部";
        data[i][0] = "1";
        data[i][1] = "3.0176";
        data[i][2] = String.valueOf(R.drawable.sview5_3);
        data[i][3] = "六合觀光夜市";
        data[i][4] = getString(R.string.sview5_3_content);
        data[i][5] = "mid";

        i=14;
        mainTitle[i] = "高雄夜市二日遊";
        tag[i][0] = "秋";
        tag[i][1] = "二日行程";
        tag[i][2] = "南部";
        data[i][0] = "2";
        data[i][1] = "3.7062";
        data[i][2] = String.valueOf(R.drawable.sview5_4);
        data[i][3] = "西子灣";
        data[i][4] = getString(R.string.sview5_4_content);
        data[i][5] = "mid";

        i=15;
        mainTitle[i] = "高雄夜市二日遊";
        tag[i][0] = "秋";
        tag[i][1] = "二日行程";
        tag[i][2] = "南部";
        data[i][0] = "2";
        data[i][1] = "0.8288";
        data[i][2] = String.valueOf(R.drawable.sview5_5);
        data[i][3] = "打狗英國領事館";
        data[i][4] = getString(R.string.sview5_5_content);
        data[i][5] = "mid";

        i=16;
        mainTitle[i] = "高雄夜市二日遊";
        tag[i][0] = "秋";
        tag[i][1] = "二日行程";
        tag[i][2] = "南部";
        data[i][0] = "2";
        data[i][1] = "4.1304";
        data[i][2] = String.valueOf(R.drawable.sview5_6);
        data[i][3] = "興中觀光夜市";
        data[i][4] = getString(R.string.sview5_6_content);
        data[i][5] = "end";
    }

    private void saveData() {
        Log.d(TAG, "saveData: ");
        long id;
        for (int i = 0; i < 17; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("mainTitle", mainTitle[i]);
            contentValues.put("tag1", tag[i][0]);
            contentValues.put("tag2", tag[i][1]);
            contentValues.put("tag3", tag[i][2]);
            contentValues.put("day", data[i][0]);
            contentValues.put("distance", data[i][1]);
            contentValues.put("picture", data[i][2]);
            contentValues.put("title", data[i][3]);
            contentValues.put("content", data[i][4]);
            contentValues.put("startEndFlag", data[i][5]);
            id = db.insert(DataBaseTable, null, contentValues);
            Log.d(TAG, "addNewToolItem: save OK");
        }
    }

    private int readData() {
        String _mainTitle,_tag1,_tag2,_tag3,_day,_distance,_picture,_title,_content,_startEndFlag;
        list.clear();

        Cursor c = db.query(DataBaseTable, null, null, null, null, null, null);
        Log.d(TAG, "onClick: c.getCount() = " + c.getCount());
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
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
            if(_startEndFlag.equals("start")){
                ScheduleShort scheduleShort = new ScheduleShort(_mainTitle,_picture,
                        _tag1,_tag2,_tag3);
                list.add(scheduleShort);
            }
        }
        return c.getCount();
    }
}