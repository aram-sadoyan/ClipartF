package com.example.user.clipartf;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ClipView clipView;
    FrameLayout background;
    ImageView imageClip, imagview;
    EditText editText;
    private ArrayList<ClipView> clips = new ArrayList<>();
    String gifUrl1 = "https://media.giphy.com/media/KdxMHWRXlMDYI/giphy.gif";
    String gifUrl2 = "https://media3.giphy.com/media/5ELFnK86mtFDO/200.gif";
    String gifUrl3 = "https://media4.giphy.com/media/11ruYKqYRD6FrO/200_s.gif";
    String gifUrl4 = "https://media3.giphy.com/media/ErRhwTBpzGKEo/200.gif";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background = (FrameLayout) findViewById(R.id.backround);
        imagview = (ImageView) findViewById(R.id.imageView);
        Glide.with(this)
                .load(gifUrl4)
                .asGif()
                .into(imagview);
        findViewById(R.id.button).setOnClickListener(dropClip);
        findViewById(R.id.button2).setOnClickListener(dropText);
        findViewById(R.id.button3).setOnClickListener(checkParams);

    }

    View.OnClickListener dropClip = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imageClip = new ImageView(getApplicationContext());
            imageClip.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getApplicationContext())
                    .load(gifUrl2)
                    .asGif()
                    .into(imageClip);
            clipView = new ClipView(getApplicationContext(), background, imageClip, null);
            clips.add(clipView);
            clipView.setArray(clips);
            clipView.addClipToArraylist();
        }
    };

    View.OnClickListener dropText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editText = new EditText(getApplicationContext());
            clipView = new ClipView(getApplicationContext(), background, null, editText);
            clips.add(clipView);
            clipView.setArray(clips);
            clipView.addClipToArraylist();
        }
    };


    View.OnClickListener checkParams = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for (int i = 0; i < clips.size(); i++) {
                float X = clips.get(i).clip.getX() + Constants.MARGIN / 2;////margin
                float Y = clips.get(i).clip.getY() + Constants.MARGIN / 2;////margin
                float xForText = clips.get(i).clip.getX() + Constants.MARGIN / 2;////margin
                float yForText = clips.get(i).clip.getY() + Constants.MARGIN / 2;////margin
                if (clips.get(i).clip.getClipImage() != null) {
                    Log.d("clipParams", "getX = " + X + " getY =" + Y + " RotationAngel =" + clips.get(i).clip.getRotation() + " clipWidth= " + clips.get(i).clip.getLayoutParams().width + " clipHeight= " + clips.get(i).clip.getLayoutParams().height);
                } else if (clips.get(i).clip.textV != null) {
                    Log.d("clipParams", "TEXT " + "getX = " + clips.get(i).clip.getX() + " getY =" + clips.get(i).clip.getY() + " RotationAngel =" + clips.get(i).clip.getRotation() + " textSize= " + clips.get(i).clip.getTextsize() + " TEXT = " + clips.get(i).clip.getText()+" TextColor= "+clips.get(i).clip.getTxtColor());
                }
            }
        }
    };


}
