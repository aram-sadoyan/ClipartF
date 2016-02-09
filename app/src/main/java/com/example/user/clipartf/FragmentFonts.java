package com.example.user.clipartf;

/**
 * Created by user on 2/1/16.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class FragmentFonts extends Fragment {


    public interface onSomeEventListener {
        public void eventForFonts(int font);
    }

    onSomeEventListener someEventListener1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener1 = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    static final String[] fonts = new String[]{"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.one, null);

        final GridView gv = (GridView) v.findViewById(R.id.gvFonts);

        AdapterFonts adapter = new AdapterFonts(getActivity(),
                R.layout.itemfonts, fonts);

        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("fvcvcvcv", "Font pos " + position + " id " + id);
                someEventListener1.eventForFonts(position);
            }
        });

        return v;
    }
}