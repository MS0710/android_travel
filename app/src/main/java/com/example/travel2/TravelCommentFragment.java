package com.example.travel2;

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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TravelCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TravelCommentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "TravelCommentFragment";
    private ListView lv_travelComment_attractions;
    private AttractionsListAdapter adapter;
    private List<Attractions> list;
    private String[] picture = new String[6];
    private String[] title = new String[6];
    private String[][] tag = new String[6][3];
    private String[] content = new String[6];
    private String[] phone = new String[6];
    private String[] phoneNote = new String[6];
    private String[] address = new String[6];
    private static final String DataBaseName = "ViewDataBaseIt_3.db";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Attraction";
    private static SQLiteDatabase db;
    private ViewSqlDataBaseHelper viewSqlDataBaseHelper;

    public TravelCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelCommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelCommentFragment newInstance(String param1, String param2) {
        TravelCommentFragment fragment = new TravelCommentFragment();
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
        View view = inflater.inflate(R.layout.fragment_travel_comment, container, false);
        viewSqlDataBaseHelper = new ViewSqlDataBaseHelper(getContext(), DataBaseName,
                null, DataBaseVersion, DataBaseTable);
        db = viewSqlDataBaseHelper.getWritableDatabase();

        lv_travelComment_attractions = (ListView) view.findViewById(R.id.lv_travelComment_attractions);
        list = new ArrayList<>();
        readData();
        adapter = new AttractionsListAdapter(getActivity(),list);
        lv_travelComment_attractions.setAdapter(adapter);

        lv_travelComment_attractions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getContext(),""+i,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("title",list.get(i).getTitle());
                intent.putExtra("picture",list.get(i).getPicture());
                intent.putExtra("tag1",list.get(i).getTag1());
                intent.putExtra("tag2",list.get(i).getTag2());
                intent.putExtra("tag3",list.get(i).getTag3());
                intent.putExtra("content",list.get(i).getContent());
                startActivity(intent);
            }
        });
        return view;
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
            /*picture[i] = _picture;
            title[i] = _title;
            tag[i][0] = _tag1;
            tag[i][1] = _tag2;
            tag[i][2] = _tag3;
            content[i] = _content;
            phone[i] = _phone;
            phoneNote[i] = _phoneNote;
            address[i] = _address;*/
            Attractions attractions = new Attractions(_id,_picture, _title,_tag1_position,_tag1,
                    _tag2_position, _tag2, _tag3_position,_tag3, _content, _phone,_phoneNote,_address);
            list.add(attractions);
        }
        return c.getCount();
    }
}