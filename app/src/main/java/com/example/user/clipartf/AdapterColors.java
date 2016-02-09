package com.example.user.clipartf;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by user on 2/4/16.
 */


public class AdapterColors extends ArrayAdapter<String> {


    String[] objects;
    Context context;

    public AdapterColors(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        TextView btn;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            btn = (TextView) inflater.inflate(R.layout.itemcolors, parent, false);
        } else {
            btn = (TextView) convertView;
        }
        btn.setText(objects[position]);

        btn.setBackgroundColor(Color.GRAY);

        return btn;
    }
}