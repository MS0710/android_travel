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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleListAdapter extends BaseAdapter {
    private String TAG = "ScheduleListAdapter";
    private List<Schedule> list = new ArrayList<>();

    private LayoutInflater listlayoutInflater;
    private Context context;

    public ScheduleListAdapter(Context c, List<Schedule> _list){
        listlayoutInflater = LayoutInflater.from(c);
        this.list = _list;
        this.context = c;
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

        convertView = listlayoutInflater.inflate(R.layout.schedule_list,null);
        Float distance;
        String picturePath;
        //設定自訂樣板上物件對應的資料。
        LinearLayout linear_scheduleList_lly = (LinearLayout) convertView.findViewById(R.id.linear_scheduleList_lly);
        TextView txt_scheduleList_distance = (TextView) convertView.findViewById(R.id.txt_scheduleList_distance);
        ImageView img_scheduleList_picture = (ImageView) convertView.findViewById(R.id.img_scheduleList_picture);
        TextView txt_scheduleList_title = (TextView) convertView.findViewById(R.id.txt_scheduleList_title);
        TextView txt_scheduleList_content = (TextView) convertView.findViewById(R.id.txt_scheduleList_content);

        distance = Float.valueOf(list.get(position).getDistance());
        if (distance == 0){
            linear_scheduleList_lly.setVisibility(View.GONE);
        }else {
            linear_scheduleList_lly.setVisibility(View.VISIBLE);
            txt_scheduleList_distance.setText(list.get(position).getDistance()+"公里");
        }

        picturePath = list.get(position).getPicture().toString();
        Log.d(TAG, "getView: picturePath = "+picturePath);

        if(picturePath.contains("/")){
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            img_scheduleList_picture.setImageBitmap(bitmap);
        }else {
            img_scheduleList_picture.setImageResource(Integer.parseInt(picturePath));
        }
        txt_scheduleList_title.setText(list.get(position).getTitle());
        txt_scheduleList_content.setText(list.get(position).getContent());

        return convertView;
    }
}
