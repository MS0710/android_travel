package com.example.travel2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DomesticListAdapter extends BaseAdapter {
    private List<Domestic> list = new ArrayList<>();

    private LayoutInflater listlayoutInflater;

    public DomesticListAdapter(Context c, List<Domestic> _list){
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

        convertView = listlayoutInflater.inflate(R.layout.domestic_list,null);

        //設定自訂樣板上物件對應的資料。
        TextView txt_domesticList_title = (TextView) convertView.findViewById(R.id.txt_domesticList_title);
        TextView txt_domesticList_address = (TextView) convertView.findViewById(R.id.txt_domesticList_address);
        TextView txt_domesticList_phone = (TextView) convertView.findViewById(R.id.txt_domesticList_phone);
        TextView txt_domesticList_return = (TextView) convertView.findViewById(R.id.txt_domesticList_return);

        txt_domesticList_title.setText(list.get(position).getTitle());
        txt_domesticList_address.setText(list.get(position).getAddress());
        txt_domesticList_phone.setText(list.get(position).getPhone());
        txt_domesticList_return.setText(list.get(position).getReturnAddress());

        return convertView;
    }
}
