package com.example.user.clipartf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ClipView clipView;
    FrameLayout background;
    ImageView imageClip;
    EditText editText;
    private ArrayList<ClipView> clips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = (FrameLayout) findViewById(R.id.backround);

        findViewById(R.id.button).setOnClickListener(dropClip);
        findViewById(R.id.button2).setOnClickListener(dropText);
        findViewById(R.id.button3).setOnClickListener(checkParams);

    }

    View.OnClickListener dropClip = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imageClip = new ImageView(getApplicationContext());
            editText = new EditText(getApplicationContext());
            imageClip.setImageResource(R.drawable.item1);
            editText = null;
            clipView = new ClipView(getApplicationContext(), background,imageClip,editText);
            clips.add(clipView);
            clipView.setArray(clips);
            clipView.addClipToArraylist();
        }
    };

    View.OnClickListener dropText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            editText = new EditText(getApplicationContext());
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editText.setText("");

            clipView = new ClipView(getApplicationContext(), background,null,editText);
            clips.add(clipView);
            clipView.setArray(clips);
            clipView.addClipToArraylist();

        }
    };


    View.OnClickListener checkParams = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for (int i = 0; i < clips.size(); i++) {
                float X = clips.get(i).clip.getX() + Constants.MARGIN/2;////margin
                float Y = clips.get(i).clip.getY() + Constants.MARGIN/2;////margin
                if (clips.get(i).clip.getClipImage() != null) {
                    Log.d("clipParams", "getX = " + X + " getY =" + Y + " RotationAngel =" + clips.get(i).clip.getRotation() + " clipWidth= " + clips.get(i).clip.getLayoutParams().width + " clipHeight= " + clips.get(i).clip.getLayoutParams().height);
                } else if (clips.get(i).clip.textV != null) {
                    Log.d("clipParams", "TEXT " + "getX = " + clips.get(i).clip.getX() + " getY =" + clips.get(i).clip.getY() + " RotationAngel =" + clips.get(i).clip.getRotation() + " textSize= " + clips.get(i).clip.getTextsize() + " TEXT = " + clips.get(i).clip.getText());

                }
            }
        }
    };

}
