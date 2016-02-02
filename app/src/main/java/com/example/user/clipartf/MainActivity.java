package com.example.user.clipartf;


import android.app.Activity;
import android.app.ActivityGroup;
import android.app.FragmentTransaction;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Window mRootWindow;
    ClipView clipView;
    FrameLayout background, options;
    ImageView imageClip, imagview;
    EditText editText;
    boolean allowForFix = true;
    boolean hiden = true;
    private ArrayList<ClipView> clips = new ArrayList<>();
    String gifUrl1 = "https://media.giphy.com/media/KdxMHWRXlMDYI/giphy.gif";
    String gifUrl2 = "https://media3.giphy.com/media/5ELFnK86mtFDO/200.gif";
    String gifUrl3 = "https://media4.giphy.com/media/11ruYKqYRD6FrO/200_s.gif";
    String gifUrl4 = "https://media3.giphy.com/media/ErRhwTBpzGKEo/200.gif";
    int deff = 0;
    static float deltaKeyboardHeight;

    OneActivity frag1;
    TwoActivity frag2;
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag1 = new OneActivity();
        frag2 = new TwoActivity();

        background = (FrameLayout) findViewById(R.id.backround);
        imagview = (ImageView) findViewById(R.id.imageView);
        /*options = (FrameLayout) findViewById(R.id.optionsTab);*/
        /*options.setVisibility(View.GONE);*/
        /*options.setY(0);*/

        Glide.with(this)
                .load(gifUrl4)
                .asGif()
                .into(imagview);
        findViewById(R.id.button).setOnClickListener(dropClip);
        findViewById(R.id.button2).setOnClickListener(dropText);
        findViewById(R.id.button3).setOnClickListener(checkParams);
       /* findViewById(R.id.btnTab1).setOnClickListener(keyboardTab1);
        findViewById(R.id.btnTab2).setOnClickListener(keyboardTab2);
        findViewById(R.id.btnTab3).setOnClickListener(keyboardTab3);
        findViewById(R.id.btnTab4).setOnClickListener(keyboardTab4);*/

       /* final Window mRootWindow = getWindow();
        final View mRootView = mRootWindow.getDecorView().findViewById(android.R.id.content);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        View view = mRootWindow.getDecorView();
                        view.getWindowVisibleDisplayFrame(r);
                        deltaKeyboardHeight = r.bottom - 200;
                        Log.d("xsxsxsx", " 1 "+ String.valueOf(deltaKeyboardHeight));
                    }
                });*/
    }


    View.OnClickListener keyboardTab1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener keyboardTab2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            allowForFix = false;
            clipView.clearKeyboard();
        }
    };

    View.OnClickListener keyboardTab3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            allowForFix = false;
            clipView.clearKeyboard();
        }
    };

    View.OnClickListener keyboardTab4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            allowForFix = false;
            clipView.clearKeyboard();
        }
    };


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
                    Log.d("clipParams", "TEXT " + "getX = " + clips.get(i).clip.getX() + " getY =" + clips.get(i).clip.getY() + " RotationAngel =" + clips.get(i).clip.getRotation() + " textSize= " + clips.get(i).clip.getTextsize() + " TEXT = " + clips.get(i).clip.getText() + " TextColor= " + clips.get(i).clip.getTxtColor());
                }
            }
        }
    };

    public void showTab() {
        Log.d("TAG6748", "Showing ");
        options.setY(deltaKeyboardHeight);
        options.setVisibility(View.VISIBLE);
        /*hiden = false;*/
    }

    public void hideTab() {
        Log.d("TAG6748", "hiding");
        options.setVisibility(View.GONE);
        hiden = true;
    }



}
