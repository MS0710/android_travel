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

public class AttractionsListAdapter extends BaseAdapter {
    private String TAG = "AttractionsListAdapter";
    private List<Attractions> list = new ArrayList<>();

    private LayoutInflater listlayoutInflater;

    public AttractionsListAdapter(Context c, List<Attractions> _list){
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

        convertView = listlayoutInflater.inflate(R.layout.attractions_list_layout,null);
        String picturePath = list.get(position).getPicture();

        /**設定自訂樣板上物件對應的資料*/
        ImageView img_attractions_picture = (ImageView) convertView.findViewById(R.id.img_attractions_picture);
        TextView txt_attractions_title = (TextView) convertView.findViewById(R.id.txt_attractions_title);
        TextView txt_attractions_tag1 = (TextView) convertView.findViewById(R.id.txt_attractions_tag1);
        TextView txt_attractions_tag2 = (TextView) convertView.findViewById(R.id.txt_attractions_tag2);
        TextView txt_attractions_tag3 = (TextView) convertView.findViewById(R.id.txt_attractions_tag3);
        TextView txt_attractions_content = (TextView) convertView.findViewById(R.id.txt_attractions_content);

        /**設定風景圖片*/
        Log.d(TAG, "getView: picturePath = "+picturePath);
        if(picturePath.contains("/")){
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            img_attractions_picture.setImageBitmap(bitmap);
        }else {
            img_attractions_picture.setImageResource(Integer.parseInt(picturePath));
        }

        txt_attractions_title.setText(list.get(position).getTitle());
        if(list.get(position).getTag1().equals(" ")){
            txt_attractions_tag1.setVisibility(View.GONE);
        }else {
            txt_attractions_tag1.setText(list.get(position).getTag1());
        }
        if(list.get(position).getTag2().equals(" ")){
            txt_attractions_tag2.setVisibility(View.GONE);
        }else {
            txt_attractions_tag2.setText(list.get(position).getTag2());
        }
        if(list.get(position).getTag3().equals(" ")){
            txt_attractions_tag3.setVisibility(View.GONE);
        }else {
            txt_attractions_tag3.setText(list.get(position).getTag3());
        }

        txt_attractions_content.setText(list.get(position).getContent());

        return convertView;
    }
}
