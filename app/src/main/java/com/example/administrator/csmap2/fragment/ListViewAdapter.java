package com.example.administrator.csmap2.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.csmap2.R;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        ListViewItem listViewItem = listViewItemList.get(position);
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        return convertView;
    }
    @Override
    public long getItemId(int position) {
        return position ;
    }
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
    public void addItem(Drawable icon, String title) {
        ListViewItem item = new ListViewItem();
        item.setIcon(icon);
        item.setTitle(title);
        listViewItemList.add(item);
    }
    public void addItem(String title) {
        ListViewItem item = new ListViewItem();
        item.setTitle(title);
        listViewItemList.add(item);
    }
}
