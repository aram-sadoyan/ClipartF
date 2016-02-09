package com.example.user.clipartf;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends Activity implements FragmentColors.onSomeEventListener, FragmentFonts.onSomeEventListener, FragmentEffects.onSomeEventListener {

    /* FragmentForKeyboartTab fragmentForKeyboartTab;*/
    FragmentFonts frag1;
    FragmentEffects frag2;
    FragmentColors frag3;
    Activity activity;
    ClipView clipView, fragmentView;
    FrameLayout background, fragmentForKeyboartTab;
    ImageView imageClip, imagview;
    EditText editText;

    boolean hiden = true;
    private ArrayList<ClipView> clips = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    String gifUrl1 = "https://media.giphy.com/media/KdxMHWRXlMDYI/giphy.gif";
    String gifUrl2 = "https://media3.giphy.com/media/5ELFnK86mtFDO/200.gif";
    String gifUrl3 = "https://media4.giphy.com/media/11ruYKqYRD6FrO/200_s.gif";
    String gifUrl4 = "https://media3.giphy.com/media/ErRhwTBpzGKEo/200.gif";
    int deff = 0;
    static float deltaKeyboardHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background = (FrameLayout) findViewById(R.id.backround);
        imagview = (ImageView) findViewById(R.id.imageView);

        activity = this;
        Glide.with(this)
                .load(gifUrl4)
                .asGif()
                .into(imagview);
        findViewById(R.id.button).setOnClickListener(dropClip);
        findViewById(R.id.button2).setOnClickListener(dropText);
        findViewById(R.id.button3).setOnClickListener(checkParams);

        /*fragmentCreater();*/

       /* final Window mRootWindow = getWindow();
        final View mRootView = mRootWindow.getDecorView().findViewById(android.R.id.content);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        View view = mRootWindow.getDecorView();
                        view.getWindowVisibleDisplayFrame(r);
                        deltaKeyboardHeight = r.bottom *//*- 200*//*;
                        Log.d("keyboardH", " 1 " + String.valueOf(deltaKeyboardHeight));
                    }
                });*/

        init();
    }

    private void init() {
        fragmentForKeyboartTab = new FrameLayout(this);
        FrameLayout.LayoutParams containerParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fragmentForKeyboartTab.setLayoutParams(containerParams);
        fragmentForKeyboartTab.setId(R.id.container);
        frag1 = new FragmentFonts();
        frag2 = new FragmentEffects();
        frag3 = new FragmentColors();
        fragmentList.add(frag1);
        fragmentList.add(frag2);
        fragmentList.add(frag3);
        background.addView(fragmentForKeyboartTab);
        fragmentForKeyboartTab.setVisibility(View.GONE);

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
            clipView = new ClipView(activity, getApplicationContext(), background, imageClip, null,fragmentForKeyboartTab,fragmentList);
            clips.add(clipView);
            clipView.setArray(clips);
            clipView.addClipToArraylist();
        }
    };

    View.OnClickListener dropText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editText = new EditText(getApplicationContext());
            clipView = new ClipView(activity, getApplicationContext(), background, null, editText, fragmentForKeyboartTab,fragmentList);
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
                if (clips.get(i).clip.getClipImage() != null) {
                    Log.d("clipParams", "getX = " + X + " getY =" + Y + " RotationAngel =" + clips.get(i).clip.getRotation() + " clipWidth= " + clips.get(i).clip.getLayoutParams().width + " clipHeight= " + clips.get(i).clip.getLayoutParams().height);
                } else if (clips.get(i).clip.textV != null) {
                    Log.d("clipParams", "TEXT " + "getX = " + clips.get(i).clip.getX() + " getY =" + clips.get(i).clip.getY() + " RotationAngel =" + clips.get(i).clip.getRotation() + " textSize= " + clips.get(i).clip.getTextsize() + " TEXT = " + clips.get(i).clip.getText() + " TextColor= " + clips.get(i).clip.getTxtColor());
                }
            }
        }
    };


    private int getClipPosition(ClipView clip) {
        int pos = -1;
        for (int i = 0; i < clips.size(); i++) {
            if (clips.get(i) == clip) {
                pos = i;
            }
        }
        return pos;
    }

    @Override
    public void eventForColors(int color) {
        Log.d("ccxcc", "Color " + " " + getClipPosition(clipView));
        clipView.setTextColor(Color.rgb(10 * color, 0, 0), clipView.getcurrentClip());
    }

    @Override
    public void eventForFonts(int font) {
        Log.d("ccxcc", "Font " + font);
    }

    @Override
    public void eventForEffects(int effect) {
        Log.d("ccxcc", "Effect " + effect);
    }
}
