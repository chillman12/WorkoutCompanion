package com.example.finalproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.model.ItemSlideMenu;

import java.util.List;

public class SlidingMenuAdapter extends BaseAdapter {

    private Context context;
    private List<ItemSlideMenu> listitem;

    public SlidingMenuAdapter(Context context, List<ItemSlideMenu> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.item_sliding_menu,null);
        ImageView img = (ImageView)v.findViewById(R.id.item_img);
        TextView tv = (TextView)v.findViewById(R.id.item_title);

        ItemSlideMenu item = listitem.get(position);
        img.setImageResource(item.getImgId());
        tv.setText(item.getTitle());

        return v;
    }
}
