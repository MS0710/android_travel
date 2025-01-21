package com.example.travel2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends BaseAdapter {
    private List<Comment> list = new ArrayList<>();

    private LayoutInflater listlayoutInflater;

    public CommentListAdapter(Context c, List<Comment> _list){
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

        convertView = listlayoutInflater.inflate(R.layout.comment_list,null);
        //設定自訂樣板上物件對應的資料。
        TextView txt_commentList_name = (TextView) convertView.findViewById(R.id.txt_commentList_name);
        TextView txt_commentList_comment = (TextView) convertView.findViewById(R.id.txt_commentList_comment);
        RatingBar rb_commentList_rating = (RatingBar) convertView.findViewById(R.id.rb_commentList_rating);
        txt_commentList_name.setText(list.get(position).getName());
        rb_commentList_rating.setRating(list.get(position).getStar());
        txt_commentList_comment.setText(list.get(position).getComment());
        return convertView;
    }
}
