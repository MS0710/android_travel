package com.example.travel2;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TravelSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TravelSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "TravelSearchFragment";
    private ListView lv_attractions;
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
    private Button btn_travelSearch_all,btn_travelSearch_north,btn_travelSearch_mid,
            btn_travelSearch_south,btn_travelSearch_east,btn_travelSearch_farm,btn_travelSearch_garden,
            btn_travelSearch_spring,btn_travelSearch_summer,btn_travelSearch_fall,btn_travelSearch_winter,
            btn_travelSearch_oneDay,btn_travelSearch_twoDay;
    private EditText edit_travelSearch_input;
    private ImageButton btn_travelSearch_search;
    private int all,north,mid,south,east,farm,garden,spring,summer,fall,winter,oneDay,twoDay;
    public TravelSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelSearchFragment newInstance(String param1, String param2) {
        TravelSearchFragment fragment = new TravelSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_travel_search, container, false);
        viewSqlDataBaseHelper = new ViewSqlDataBaseHelper(getContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = viewSqlDataBaseHelper.getWritableDatabase();

        edit_travelSearch_input = (EditText)view.findViewById(R.id.edit_travelSearch_input);
        btn_travelSearch_search = (ImageButton) view.findViewById(R.id.btn_travelSearch_search);
        btn_travelSearch_all = (Button)view.findViewById(R.id.btn_travelSearch_all);
        btn_travelSearch_north = (Button)view.findViewById(R.id.btn_travelSearch_north);
        btn_travelSearch_mid = (Button)view.findViewById(R.id.btn_travelSearch_mid);
        btn_travelSearch_south = (Button)view.findViewById(R.id.btn_travelSearch_south);
        btn_travelSearch_east = (Button)view.findViewById(R.id.btn_travelSearch_east);

        btn_travelSearch_farm = (Button)view.findViewById(R.id.btn_travelSearch_farm);
        btn_travelSearch_garden = (Button)view.findViewById(R.id.btn_travelSearch_garden);
        btn_travelSearch_spring = (Button)view.findViewById(R.id.btn_travelSearch_spring);
        btn_travelSearch_summer = (Button)view.findViewById(R.id.btn_travelSearch_summer);
        btn_travelSearch_fall = (Button)view.findViewById(R.id.btn_travelSearch_fall);
        btn_travelSearch_winter = (Button)view.findViewById(R.id.btn_travelSearch_winter);
        btn_travelSearch_oneDay = (Button)view.findViewById(R.id.btn_travelSearch_oneDay);
        btn_travelSearch_twoDay = (Button)view.findViewById(R.id.btn_travelSearch_twoDay);
        btn_travelSearch_all.setOnClickListener(onClick);
        btn_travelSearch_north.setOnClickListener(onClick);
        btn_travelSearch_mid.setOnClickListener(onClick);
        btn_travelSearch_south.setOnClickListener(onClick);
        btn_travelSearch_east.setOnClickListener(onClick);
        btn_travelSearch_farm.setOnClickListener(onClick);
        btn_travelSearch_garden.setOnClickListener(onClick);
        btn_travelSearch_spring.setOnClickListener(onClick);
        btn_travelSearch_summer.setOnClickListener(onClick);
        btn_travelSearch_fall.setOnClickListener(onClick);
        btn_travelSearch_winter.setOnClickListener(onClick);
        btn_travelSearch_search.setOnClickListener(onClick);
        btn_travelSearch_oneDay.setOnClickListener(onClick);
        btn_travelSearch_twoDay.setOnClickListener(onClick);

        lv_attractions = (ListView) view.findViewById(R.id.lv_travelSearch_attractions);
        list = new ArrayList<>();
        if (readData()<1){
            setData();
            saveData();
        }
        readData();

        adapter = new AttractionsListAdapter(getActivity(), list);
        lv_attractions.setAdapter(adapter);
        lv_attractions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AttractionsContentActivity.class);
                intent.putExtra("picture", picture[position]);
                intent.putExtra("phone", phone[position]);
                intent.putExtra("phoneNote", phoneNote[position]);
                intent.putExtra("address", address[position]);
                intent.putExtra("tag1", tag[position][0]);
                intent.putExtra("tag2", tag[position][1]);
                intent.putExtra("tag3", tag[position][2]);
                intent.putExtra("content", content[position]);
                startActivity(intent);
            }
        });

        readDataCunt();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void readDataCunt(){
        all = readData();
        btn_travelSearch_all.setText("全部("+all+")");
        north = readOneData("view_tag3","#北部");
        btn_travelSearch_north.setText("北部("+north+")");
        mid = readOneData("view_tag3","#中部");
        btn_travelSearch_mid.setText("中部("+mid+")");
        south = readOneData("view_tag3","#南部");
        btn_travelSearch_south.setText("南部("+south+")");
        east = readOneData("view_tag3","#東部");
        btn_travelSearch_east.setText("東部("+east+")");
        farm = readOneData("view_tag1","#休閒農場");
        btn_travelSearch_farm.setText("休閒農場("+farm+")");
        garden = readOneData("view_tag1","#觀光果園");
        btn_travelSearch_garden.setText("觀光果園("+garden+")");
        spring = readOneData("view_tag1","#春");
        btn_travelSearch_spring.setText("春("+spring+")");
        summer = readOneData("view_tag1","#夏");
        btn_travelSearch_summer.setText("夏("+summer+")");
        fall = readOneData("view_tag1","#秋");
        btn_travelSearch_fall.setText("秋("+fall+")");
        winter = readOneData("view_tag1","#冬");
        btn_travelSearch_winter.setText("冬("+winter+")");
        oneDay = readOneData("view_tag2","#一日行程");
        btn_travelSearch_oneDay.setText("一日行程("+oneDay+")");
        twoDay = readOneData("view_tag2","#二日行程");
        btn_travelSearch_twoDay.setText("二日行程("+twoDay+")");
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_travelSearch_all){
                Log.d(TAG, "onClick: btn_travelSearch_all");
                readAllData();
            }else if(v.getId() == R.id.btn_travelSearch_north){
                Log.d(TAG, "onClick: btn_travelSearch_north");
                readOneData("view_tag3","#北部");
            }else if(v.getId() == R.id.btn_travelSearch_mid){
                Log.d(TAG, "onClick: btn_travelSearch_mid");
                readOneData("view_tag3","#中部");
            }else if(v.getId() == R.id.btn_travelSearch_south){
                Log.d(TAG, "onClick: btn_travelSearch_south");
                readOneData("view_tag3","#南部");
            }else if(v.getId() == R.id.btn_travelSearch_east){
                Log.d(TAG, "onClick: btn_travelSearch_east");
                readOneData("view_tag3","#東部");
            } else if(v.getId() == R.id.btn_travelSearch_farm){
                Log.d(TAG, "onClick: btn_travelSearch_farm");
                readOneData("view_tag1","#休閒農場");
            }else if(v.getId() == R.id.btn_travelSearch_garden){
                Log.d(TAG, "onClick: btn_travelSearch_garden");
                readOneData("view_tag1","#觀光果園");
            }else if(v.getId() == R.id.btn_travelSearch_spring){
                Log.d(TAG, "onClick: btn_travelSearch_spring");
                readOneData("view_tag1","#春");
            }else if(v.getId() == R.id.btn_travelSearch_summer){
                Log.d(TAG, "onClick: btn_travelSearch_summer");
                readOneData("view_tag1","#夏");
            }else if(v.getId() == R.id.btn_travelSearch_fall){
                Log.d(TAG, "onClick: btn_travelSearch_fall");
                readOneData("view_tag1","#秋");
            } else if(v.getId() == R.id.btn_travelSearch_winter){
                Log.d(TAG, "onClick: btn_travelSearch_winter");
                readOneData("view_tag1","#冬");
            }else if(v.getId() == R.id.btn_travelSearch_search){
                Log.d(TAG, "onClick: btn_travelSearch_search");
                searchVagueData();
            }else if(v.getId() == R.id.btn_travelSearch_oneDay){
                Log.d(TAG, "onClick: btn_travelSearch_oneDay");
                readOneData("view_tag2","#一日行程");
            } else if(v.getId() == R.id.btn_travelSearch_twoDay){
                Log.d(TAG, "onClick: btn_travelSearch_twoDay");
                readOneData("view_tag2","#二日行程");
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        readData();
        adapter.notifyDataSetChanged();
    }

    /**建立基本旅遊資料*/
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

    private void readAllData() {
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
            adapter.notifyDataSetChanged();
        }
    }
    private int readOneData(String key,String item) {
        String view_tag3_key = "view_tag3";
        int _id,_tag1_position,_tag2_position,_tag3_position;
        String _title, _picture, _tag1, _tag2, _tag3, _content, _phone, _phoneNote, _address;
        list.clear();

        Cursor c = db.query(DataBaseTable,null,key+"=?",new String[]{item},null,null,null);
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
        adapter.notifyDataSetChanged();
        return c.getCount();
    }
    @SuppressLint("Range")
    private void searchVagueData(){
        Log.d(TAG, "searchVagueData: ");
        String view_title = "view_title";
        String input = edit_travelSearch_input.getText().toString();
        int _id,_tag1_position,_tag2_position,_tag3_position;
        String _title, _picture, _tag1, _tag2, _tag3, _content, _phone, _phoneNote, _address;
        list.clear();

        Cursor c = db.query(DataBaseTable, new String[]{view_title}, view_title+" LIKE ? ",
                new String[] { "%" + input + "%" }, null, null, null);
        Log.d(TAG, "onClick: c.getCount() = " + c.getCount());
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            _id = c.getInt(0);
            _title = c.getString(c.getColumnIndex("view_title"));
            Log.d(TAG, "onClick: _id = " + _id);
            Log.d(TAG, "onClick: _title = " + _title);
            c.moveToNext();
            Log.d(TAG, "onClick: SearchToolActivity -------------");
            Cursor c_in = db.query(DataBaseTable,null,view_title+"=?",new String[]{_title},null,null,null);
            Log.d(TAG, "onClick: c.getCount() = " + c_in.getCount());
            c_in.moveToFirst();
            for (int j = 0; j < c_in.getCount(); j++) {
                _id = c_in.getInt(0);
                _title = c_in.getString(1);
                _picture = c_in.getString(2);
                _tag1 = c_in.getString(3);
                _tag2 = c_in.getString(4);
                _tag3 = c_in.getString(5);
                _content = c_in.getString(6);
                _phone = c_in.getString(7);
                _phoneNote = c_in.getString(8);
                _address = c_in.getString(9);
                _tag1_position = c_in.getInt(10);
                _tag2_position = c_in.getInt(11);
                _tag3_position = c_in.getInt(12);

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
                c_in.moveToNext();
                Log.d(TAG, "onClick: SearchToolActivity -------------");
                picture[j] = _picture;
                title[j] = _title;
                tag[j][0] = _tag1;
                tag[j][1] = _tag2;
                tag[j][2] = _tag3;
                content[j] = _content;
                phone[j] = _phone;
                phoneNote[j] = _phoneNote;
                address[j] = _address;
                Attractions attractions = new Attractions(_id,_picture, _title,_tag1_position,_tag1,
                        _tag2_position, _tag2, _tag3_position,_tag3, _content, _phone,_phoneNote,_address);
                list.add(attractions);
            }
        }
        adapter.notifyDataSetChanged();
    }
}