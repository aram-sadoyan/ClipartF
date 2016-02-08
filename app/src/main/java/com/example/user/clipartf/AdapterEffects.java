package com.example.user.clipartf;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by user on 2/5/16.
 */
public class AdapterEffects extends ArrayAdapter<String> {


    String[] objects;
    Context context;

    public AdapterEffects(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            tv = (TextView) inflater.inflate(R.layout.itemeffects, parent, false);
        } else {
            tv = (TextView) convertView;
        }
        tv.setText(objects[position]);
        if (position % 2 == 0)
            tv.setBackgroundColor(Color.BLACK);
        else
            tv.setBackgroundColor(Color.GRAY);

        return tv;
    }
}