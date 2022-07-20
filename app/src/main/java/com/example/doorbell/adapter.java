package com.example.doorbell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] text;
    private int[] images;

    public adapter(Context c,String[] text,int[] images){
        context=c;
        this.text=text;
        this.images=images;
    }
    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=inflater.inflate(R.layout.row_item,null);
        }
        ImageView imgview = convertView.findViewById(R.id.image_view);
        TextView textview = convertView.findViewById(R.id.textView);
        imgview.setImageResource(images[position]);
        textview.setText(text[position]);
        return convertView;
    }
}

