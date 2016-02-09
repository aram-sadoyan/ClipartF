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

public class FragmentEffects extends Fragment {


    public interface onSomeEventListener {
        public void eventForEffects(int effect);
    }

    onSomeEventListener someEventListener;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    static final String[] effects = new String[]{"A", "B", "C", "D", "E",
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
        View v = inflater.inflate(R.layout.effects, null);

        final GridView gv = (GridView) v.findViewById(R.id.gvEffects);

        AdapterEffects adapter = new AdapterEffects(getActivity(),
                R.layout.itemeffects, effects);

        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("fvcvcvcv", "effect pos  " + position + " id " + id);
                someEventListener.eventForEffects(position);
            }
        });
        return v;
    }
}