package com.example.travel2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleShortListAdapter extends BaseAdapter {
    private String TAG = "ScheduleShortListAdapter";
    private List<ScheduleShort> list = new ArrayList<>();

    private LayoutInflater listlayoutInflater;

    public ScheduleShortListAdapter(Context c, List<ScheduleShort> _list){
        listlayoutInflater = LayoutInflater.from(c);
        this.list = _list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = listlayoutInflater.inflate(R.layout.sceduleshort_list_layout,null);
        String picturePath = list.get(position).getPicture();

        //設定自訂樣板上物件對應的資料。
        ImageView img_scheduleShort_picture = (ImageView) convertView.findViewById(R.id.img_scheduleShort_picture);
        TextView txt_scheduleShort_title = (TextView) convertView.findViewById(R.id.txt_scheduleShort_title);
        TextView txt_scheduleShort_tag1 = (TextView) convertView.findViewById(R.id.txt_scheduleShort_tag1);
        TextView txt_scheduleShort_tag2 = (TextView) convertView.findViewById(R.id.txt_scheduleShort_tag2);
        TextView txt_scheduleShort_tag3 = (TextView) convertView.findViewById(R.id.txt_scheduleShort_tag3);

        Log.d(TAG, "getView: picturePath = "+picturePath);
        if(picturePath.contains("/")){
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            img_scheduleShort_picture.setImageBitmap(bitmap);
        }else {
            img_scheduleShort_picture.setImageResource(Integer.parseInt(picturePath));
        }

        txt_scheduleShort_title.setText(list.get(position).getMainTitle());
        if(list.get(position).getTag1().equals(" ")){
            txt_scheduleShort_tag1.setVisibility(View.GONE);
        }else {
            txt_scheduleShort_tag1.setText(list.get(position).getTag1());
        }
        if(list.get(position).getTag2().equals(" ")){
            txt_scheduleShort_tag2.setVisibility(View.GONE);
        }else {
            txt_scheduleShort_tag2.setText(list.get(position).getTag2());
        }
        if(list.get(position).getTag3().equals(" ")){
            txt_scheduleShort_tag3.setVisibility(View.GONE);
        }else {
            txt_scheduleShort_tag3.setText(list.get(position).getTag3());
        }

        return convertView;
    }
}
