package com.example.gasolineprices;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class grid_adapter extends BaseAdapter {

    Context context;
    List<String> Name;
    int[] image;

    LayoutInflater inflater;

    public grid_adapter(Context context, List<String> Name) {
        this.context = context;
        this.Name = Name;

    }

    @Override
    public int getCount() {
        return Name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){

            convertView = inflater.inflate(R.layout.grid_item,null);

        }


        TextView textView = convertView.findViewById(R.id.item_name);


        textView.setText(Name.get(position));

        return convertView;
    }
}