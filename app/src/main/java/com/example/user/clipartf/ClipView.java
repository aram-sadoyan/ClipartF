package com.example.user.clipartf;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Color;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by user on 12/28/15.
 */
public class ClipView extends FrameLayout {

    Clip clip;
    View borderBG;
    FrameLayout background;
    ImageView moveBt;
    View rect;
    RectF rectFordetection, rectForRemoving;

    FrameLayout.LayoutParams btnParams, clipMoveAndRotateRelParams, rectParams;
    private float _xDelta;
    private float _xDeltaforMove;
    private float _yDeltaforMove;
    private float _yDelta;
    boolean moved = false;
    boolean movedFromMultitouch = false;
    boolean allowToZoomFromSizeBlock = false;
    boolean keyboardIsOpened = false;
    /*boolean allowKeybOpen = true;*/

    private float degress;
    private float degres = 0, degres2 = 0, degres3 = 0, degresFinal, alfa1 = 0, degresAfterMultyTouch = 0;

    int countt = 0;
    float count = 0;

    ImageView imageClip, removeBtn;
    EditText txtView;
    /*TextView txtView;*/
    Context context;
    boolean istText = true;

    private ArrayList<ClipView> clipsw = new ArrayList<>();

    public ClipView(Context context, FrameLayout background, ImageView imageView, /*TextView */EditText textView) {
        super(context);
        this.imageClip = imageView;
        this.txtView = textView;
        this.context = context;
        this.background = background;
        if (textView == null) {
            istText = false;
        }
        init();
    }

    public void init() {

        borderBG = new View(context);
        btnParams = new FrameLayout.LayoutParams(50, 50);
        rectParams = new FrameLayout.LayoutParams(10, 10);

      /*  txtView = new EditText(context);
        txtView.setText("555");*/

        clip = new Clip(context, imageClip, txtView);

        moveBt = new ImageButton(context);
        moveBt.setLayoutParams(btnParams);
        moveBt.setOnTouchListener(moveBtTouch);
        background.setOnClickListener(onClipTouchclick);
        rect = new ImageButton(context);
        count = clip.getCount();
        if (istText) {

            txtView.addTextChangedListener(new TextWatcher() {
                int initialTxtlength = 5;//was 2

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    txtView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                    ////fixmovebtn
                  /*  moveBt.setX(clip.getX() + clip.getRlayoutWidth() - clip.getMovebtnsize());
                    moveBt.setY(clip.getY() + clip.getRLayoutHeight() - clip.getMovebtnsize());
                    moveBt.setLayoutParams(moveBt.getLayoutParams());
                    moveBt.setPivotX(-clip.getWidth() / 2 + moveBt.getLayoutParams().width);
                    moveBt.setPivotY(-clip.getHeight() / 2 + moveBt.getLayoutParams().height);
                    moveBt.setRotation(degresFinal);
                    moveBt.setLayoutParams(moveBt.getLayoutParams());
                    background.invalidate();
                    moveBt.invalidate();*/
///scjsc
                    fixMoveButton(true);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    boolean adding = false;
                    if (count == 0) {
                        txtView.setCursorVisible(true);
                    }
                    int sizeCount = (int) clip.getTextsize() /2;
                    if (txtView.length() > initialTxtlength) {
                        txtView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                        clip.getLayoutParams().width += sizeCount;
                        FrameLayout.LayoutParams layoutParamsForMovebtn = (FrameLayout.LayoutParams) clip.getLayoutParams();
                        layoutParamsForMovebtn.leftMargin -= sizeCount / 2;                         ///was 2
                        clip.getBtnMove().setX(clip.getBtnMove().getX() + sizeCount);
                        ///fixmovebtn
                        fixRect();
                        fixMoveButton(true);
                        alfa1 = clip.getAlfa();
                        initialTxtlength = txtView.length();

                        /*moveBt.setX(clip.getX() + clip.getRlayoutWidth() + 35);/////////???
                        moveBt.setY(clip.getY() + clip.getRLayoutHeight() - clip.getMovebtnsize());
                        moveBt.setLayoutParams(moveBt.getLayoutParams());
                        moveBt.setPivotX(-clip.getWidth() / 2 + moveBt.getLayoutParams().width);
                        moveBt.setPivotY(-clip.getHeight() / 2 + moveBt.getLayoutParams().height);
                        moveBt.setRotation(degresFinal);
                        moveBt.setLayoutParams(moveBt.getLayoutParams());*/

                    } else if (txtView.length() < initialTxtlength && txtView.length() >= 5) {////was>=2
                        clip.getLayoutParams().width -= sizeCount;
                        FrameLayout.LayoutParams layoutParamsForMovebtn = (FrameLayout.LayoutParams) clip.getLayoutParams();
                        layoutParamsForMovebtn.leftMargin += sizeCount / 2;//////was 2
                        clip.getBtnMove().setX(clip.getBtnMove().getX() - sizeCount);
                        //fix movebtn
                        ////here
                        //
                        fixRect();
                        fixMoveButton(true);

                        background.invalidate();
                        moveBt.invalidate();

                        alfa1 = clip.getAlfa();
                        initialTxtlength = txtView.length();

                     /*   moveBt.setX(clip.getX() + clip.getRlayoutWidth() - clip.getMovebtnsize());
                        moveBt.setY(clip.getY() + clip.getRLayoutHeight() - clip.getMovebtnsize());
                        moveBt.setLayoutParams(moveBt.getLayoutParams());
                        moveBt.setPivotX(-clip.getWidth() / 2 + moveBt.getLayoutParams().width);
                        moveBt.setPivotY(-clip.getHeight() / 2 + moveBt.getLayoutParams().height);
                        moveBt.setRotation(degresFinal);
                        moveBt.setLayoutParams(moveBt.getLayoutParams());*/
                    }
                    if (txtView.length() == 0) {
                        txtView.setCursorVisible(true);
                    } else {
                        txtView.setCursorVisible(false);
                    }
                }
            });
        }
    }

    View.OnClickListener onClipTouchclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearKeyboard();
            hideShowBorders2(View.INVISIBLE, false);
            listenerSwitcher(clip, VISIBLE, false);
        }
    };

    View.OnTouchListener rectTouch = new View.OnTouchListener() {
        boolean allowMove = true;
        boolean firstCheckForRemove = false;
        double initialDistance = 0;
        boolean allowToCloseKeyboard = true;
        int deltaPointerD = 0;////was double
        double dimens = 0;
        double actualDistanceForPointer = 0;
        double deltaSize = 1;
        FrameLayout.LayoutParams layoutParams5 = null, laPrmsForRect = null;
        int zoomHeight;
        int chekSize;
        int initialClipWidth = 0;
        int initialClipHeight = 0;
        int actualClipWidth = 0;
        int actualClipHeight = 0;
        int deltaClipwidth = 0;
        int deltaClipHeight = 0;
        float X = 0;
        float Y = 0;

        Point checker = new Point();

        PointF deltaPoint = new PointF();
        PointF startPointer0 = new PointF();
        PointF startPointer1 = new PointF();
        Point deltaP0 = new Point();
        Point deltaP1 = new Point();
        Point point0 = new Point();
        PointF initialP0 = new PointF();
        PointF initialP1 = new PointF();

        /*int count = 0;*/
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            X = (int) event.getRawX();
            Y = (int) event.getRawY();

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    if (istText) {
                        deltaSize = 2;

                    }
                    listenerSwitcher(clip, GONE, true);
                    hideShowBorders(clip);
                    clip.getBtnRemove().setOnClickListener(null);
                    if (istText) {
                        laPrmsForRect = (FrameLayout.LayoutParams) rect.getLayoutParams();
                        laPrmsForRect.height = rect.getLayoutParams().width;
                        laPrmsForRect.width += rect.getLayoutParams().width / 4;
                        rect.setY(clip.getY() - rect.getLayoutParams().height / 4);
                        rect.setX(clip.getX() - rect.getLayoutParams().width / 6);
                        rect.setLayoutParams(laPrmsForRect);


/*
                        rect.getLayoutParams().height = rect.getLayoutParams().width;
                        rect.getLayoutParams().width += rect.getLayoutParams().width / 4;
                        rect.setY(clip.getY() - rect.getLayoutParams().height / 4);
                        rect.setX(clip.getX() - rect.getLayoutParams().width / 6);
                        rect.setLayoutParams(rect.getLayoutParams());*/


                        /*txtView.setEnabled(false);*/

                    }
                    rect.setRotation(0);
                    allowMove = true;
                    setrectForDetectionX_Y();
                    if (rectForRemoving.contains(event.getX() + clip.getX(), event.getY() + clip.getY())) {
                        ////
                        firstCheckForRemove = true;
                    }
                    fixMoveButton(false);
                    clip.invalidate();
                    /////one tuch
                    if (event.getPointerCount() < 2) {
                        dimens = clip.getLayoutParams().width;
                        FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) clip.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        //////remove/comment
                        _xDeltaforMove = _xDelta;
                        _yDeltaforMove = _yDelta;
                        checker.x = (int) event.getRawX();
                        checker.y = (int) event.getRawY();

                    }
                    deltaPoint.x = clip.getLayoutParams().width - moveBt.getLayoutParams().width;
                    deltaPoint.y = clip.getLayoutParams().height - moveBt.getLayoutParams().height;
                    break;
                case MotionEvent.ACTION_MOVE:
                    fixMoveButton(false);
                    if (allowMove) {
                        clip.invalidate();
                        Log.d("tuchh", String.valueOf(event.getPointerCount()));
                        if (event.getPointerCount() >= 2) {
                            if (istText) {
                                chekSize = 600;
                            } else {
                                chekSize = 1600;
                            }
                            zoomHeight = (int) (deltaPointerD / count);
                            actualDistanceForPointer = (int) Math.hypot(event.getX(1) - event.getX(0), event.getY(1) - event.getY(0));////
                            deltaPointerD = (int) (dimens + (actualDistanceForPointer - initialDistance));////??
                           /* degres3 = (int) Math.toDegrees(Math.atan2(event.getY(1) - event.getY(0), event.getX(1) - event.getX(0)) - Math.atan2(startPointer1.y - startPointer0.y, startPointer1.x - startPointer0.x))*/
                            degres2 = (int) Math.toDegrees(Math.atan2(event.getY(0) - event.getY(1), event.getX(0) - event.getX(1)) - Math.atan2(startPointer0.y - startPointer1.y, startPointer0.x - startPointer1.x));

                            if (!movedFromMultitouch) {
                                moved = false;
                                clip.setLayoutParams(clip.getLayoutParams());
                                clip.setRotation(degresFinal + degres2);
                            } else {
                                moved = false;
                                clip.setLayoutParams(clip.getLayoutParams());
                                clip.setRotation(degresAfterMultyTouch + degres2);
                            }

                            clip.invalidate();
                            background.invalidate();

                            deltaP0.x = (int) (event.getX(0) - initialP0.x);
                            deltaP0.y = (int) (event.getY(0) - initialP0.y);
                            deltaP1.x = (int) (event.getX(1) - initialP1.x);
                            deltaP1.y = (int) (event.getY(1) - initialP1.y);

                            point0.set((int) (event.getX(0) + (event.getX(1))) / 2, (int) (event.getY(0) + event.getY(1)) / 2);

                            if (startPointer0.y < startPointer1.y) {
                                zoomHeight = (int) (deltaPointerD / count);
                                if (zoomHeight < chekSize && zoomHeight > chekSize / 8) {
                                    /*int deltaPointerNow = (int) deltaPointerD;*/
                                    int beforeW = clip.getLayoutParams().width;
                                    int beforeH = clip.getLayoutParams().height;
                                    if (istText) {
                                        int w = (int) (deltaPointerD * Math.cos(Math.toRadians(alfa1)));
                                        int h = (int) (deltaPointerD * Math.sin(Math.toRadians(alfa1)));
                                        clip.getLayoutParams().width = w;
                                        clip.getLayoutParams().height = h;
                                    } else {
                                        clip.getLayoutParams().width = deltaPointerD;
                                        clip.getLayoutParams().height = (int) (deltaPointerD/count);
                                    }

                                    clip.refreshTextSize((clip.getLayoutParams().height + 20));
                                    int afterW = clip.getLayoutParams().width;
                                    int afterH = clip.getLayoutParams().height;
                                    int dw = afterW - beforeW;
                                    int dh = afterH - beforeH;

                                    layoutParams5 = (FrameLayout.LayoutParams) clip.getLayoutParams();
                                    layoutParams5.leftMargin = (int) (point0.x - _xDeltaforMove - dw / 2);
                                    layoutParams5.topMargin = (int) (point0.y - _yDeltaforMove - dh / 2);
                                    clip.setLayoutParams(layoutParams5);

                                } else {
                                    point0.set((int) (event.getX(0) + (event.getX(1))) / 2, (int) (event.getY(0) + event.getY(1)) / 2);
                                    layoutParams5 = (FrameLayout.LayoutParams) clip.getLayoutParams();
                                    deltaClipwidth = 0;
                                    deltaClipHeight = 0;
                                    layoutParams5.leftMargin = (int) (point0.x - _xDeltaforMove);
                                    layoutParams5.topMargin = (int) (point0.y - _yDeltaforMove);
                                    clip.setLayoutParams(layoutParams5);
                                }

                              /*  actualClipWidth = (int) deltaPointerD;                            //  remove from
                                actualClipHeight = (int) (deltaPointerD / count);
                                deltaClipwidth = initialClipWidth - actualClipWidth;
                                deltaClipHeight = initialClipHeight - actualClipHeight;*/

                                actualClipWidth = clip.getLayoutParams().width;                     // to
                                actualClipHeight = clip.getLayoutParams().height;
                                deltaClipwidth = actualClipWidth - initialClipWidth;
                                deltaClipHeight = actualClipHeight - initialClipHeight;

                            } else if (startPointer0.y >= startPointer1.y) {
                                zoomHeight = (int) (deltaPointerD / count);///////??????????
                                if (zoomHeight < chekSize && zoomHeight > chekSize / 8) {

                                    if (istText) {
                                        int w = (int) (deltaPointerD * Math.cos(Math.toRadians(alfa1)));
                                        int h = (int) (deltaPointerD * Math.sin(Math.toRadians(alfa1)));
                                        clip.getLayoutParams().width = w;
                                        clip.getLayoutParams().height = h;
                                    } else {
                                        clip.getLayoutParams().width = deltaPointerD;
                                        clip.getLayoutParams().height = (int) (deltaPointerD/count);/////repeat count
                                    }

                                    clip.refreshTextSize((clip.getLayoutParams().height + 20));
                                }

                                actualClipWidth = clip.getLayoutParams().width;
                                actualClipHeight = clip.getLayoutParams().height;
                                deltaClipwidth = actualClipWidth - initialClipWidth;
                                deltaClipHeight = actualClipHeight - initialClipHeight;

                                layoutParams5 = (FrameLayout.LayoutParams) clip.getLayoutParams();
                                layoutParams5.leftMargin = (int) (point0.x - _xDeltaforMove - deltaClipwidth / 2);
                                layoutParams5.topMargin = (int) (point0.y - _yDeltaforMove - deltaClipHeight / 2);
                                clip.setLayoutParams(layoutParams5);
                                clip.requestLayout();
                                clip.invalidate();
                                allowToZoomFromSizeBlock = false;
                            }
                            if ((deltaP0.x - 2 > 0 && deltaP1.x - 2 > 0) || (deltaP0.x + 2 < 0 && deltaP1.x + 2 < 0) || (deltaP0.y - 2 > 0 && deltaP1.y - 2 > 0) || (deltaP0.y + 2 < 0 && deltaP1.y + 2 < 0)) {////MOVE
                                clip.requestLayout();
                                clip.invalidate();
                                clip.requestLayout();
                                clip.invalidate();
                                background.invalidate();
                            } else {///zoom
                                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) clip.getLayoutParams();
                                _xDelta = X - lParams.leftMargin;
                                _yDelta = Y - lParams.topMargin;

                                _xDeltaforMove = point0.x - lParams.leftMargin;
                                _yDeltaforMove = point0.y - lParams.topMargin;
                            }

                            initialP0.x = event.getX(0);
                            initialP0.y = event.getY(0);
                            initialP1.x = event.getX(1);
                            initialP1.y = event.getY(1);

                            initialClipWidth = clip.getLayoutParams().width;
                            initialClipHeight = clip.getLayoutParams().height;

                            deltaClipwidth = actualClipWidth - initialClipWidth;  // /////////del
                            deltaClipHeight = actualClipHeight - initialClipHeight; // ///////del

                            FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) clip.getLayoutParams();
                            _xDelta = X - lParams.leftMargin;
                            _yDelta = Y - lParams.topMargin;

                            _xDeltaforMove = point0.x - lParams.leftMargin;
                            _yDeltaforMove = point0.y - lParams.topMargin;
                            /////repeat
                        } else/*(event.getPointerCount() <= 1) */ {

                            Log.d(".p,,", "1 point");
                            FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) clip.getLayoutParams();
                            layoutParams5.leftMargin = (int) (X - _xDelta);
                            layoutParams5.topMargin = (int) (Y - _yDelta);
                            clip.setLayoutParams(layoutParams5);
                            clip.requestLayout();
                            clip.invalidate();
                            background.invalidate();
                        }
                    }
                    clip.refreshBtn(clip.getLayoutParams().width, clip.getLayoutParams().height);

                    if (checker.x != X || checker.y != Y) {

                        clearKeyboard();
                        firstCheckForRemove = false;
                        allowToCloseKeyboard = false;
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.d(".p,,", "2pointDown");
                    clearKeyboard();
                    if (istText) {
                        txtView.setSingleLine(false);
                    }
                    firstCheckForRemove = false;
                    initialClipWidth = clip.getLayoutParams().width;
                    initialClipHeight = clip.getLayoutParams().height;

                    point0.set((int) (event.getX(0) + (event.getX(1))) / 2, (int) (event.getY(0) + event.getY(1)) / 2);
                    FrameLayout.LayoutParams lParams8 = (FrameLayout.LayoutParams) clip.getLayoutParams();
                    _xDeltaforMove = point0.x - lParams8.leftMargin;
                    _yDeltaforMove = point0.y - lParams8.topMargin;

                    startPointer0.set(event.getX(0), event.getY(0));
                    startPointer1.set(event.getX(1), event.getY(1));

                    initialP0.x = startPointer0.x;
                    initialP0.y = startPointer0.y;
                    initialP1.x = startPointer1.x;
                    initialP1.y = startPointer1.y;

                    if (!rectFordetection.contains(startPointer1.x + rect.getX(), startPointer1.y + rect.getY())) {
                        allowMove = false;
                        if (istText) {/////???
                            allowMove = false;
                        }
                    }
                    initialDistance = (int) Math.hypot(event.getX(1) - event.getX(0), event.getY(1) - event.getY(0));
                    allowToCloseKeyboard = false;
                    break;
                case MotionEvent.ACTION_POINTER_UP:

                    degresAfterMultyTouch = clip.getRotation();
                    movedFromMultitouch = true;
                    allowMove = false;
                    break;
                case MotionEvent.ACTION_UP:
                    if (firstCheckForRemove) {
                        //removeClip
                        clearKeyboard();
                        removeClip();
                        /////////////
                        firstCheckForRemove = false;
                        allowToCloseKeyboard = false;
                    }
                    listenerSwitcher(clip, VISIBLE, true);
                    allowMove = false;
                    degresAfterMultyTouch = clip.getRotation();
                    movedFromMultitouch = true;
                    if (event.getPointerCount() == 1) {
                        fixRect();
                        fixMoveButton(false);
                    }

                    if (istText && allowToCloseKeyboard) {
                        removeBtn = new ImageView(context);
                        removeBtn = clip.getBtnRemove();
                        removeBtn.setOnClickListener(removeTextClip);
                        txtView.callOnClick();
                    }
                    allowToCloseKeyboard = true;
                    break;
            }
            return false;
        }
    };


    OnTouchListener txtTouch = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            txtView.setCursorVisible(false);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_UP:
                    /*txtView.clearFocus();*/
                    break;
            }
            return false;
        }
    };

    OnClickListener txtClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            txtView.setSingleLine(true);
            txtView.setFocusable(true);
            txtView.setSelection(txtView.getText().length(), txtView.getText().length());
            txtView.setCursorVisible(true);
            txtView.setOnKeyListener(onKeyDown);
            txtView.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            /*imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);*/
            imm.showSoftInput(txtView, InputMethodManager.SHOW_IMPLICIT);

        }
    };

    OnKeyListener onKeyDown = new OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (KeyEvent.KEYCODE_ENTER == keyCode) { // match ENTER key
                clearKeyboard();
                return true; // indicate that we handled event, won't propagate it
            }
            return false;
        }
    };


    public void drop() {
        int MOVEBTN_RECT_SIZE = (int) getContext().getResources().getDimension(R.dimen.MOVEBTN_RECT_SIZE);
        clipMoveAndRotateRelParams = new FrameLayout.LayoutParams(/*70*/MOVEBTN_RECT_SIZE, MOVEBTN_RECT_SIZE);///button-size+margin
        moveBt.setBackgroundColor(Color.TRANSPARENT);
        /*moveBt.setX(clip.getX() + clip.getLayoutParams().width - moveBt.getLayoutParams().width/2);
        moveBt.setY(clip.getY() + clip.getLayoutParams().height - moveBt.getLayoutParams().height/2);*/
        moveBt.setX(clip.getX() + clip.getLayoutParams().width - MOVEBTN_RECT_SIZE/*-clipMoveAndRotateRelParams.width*/);
        moveBt.setY(clip.getY() + clip.getLayoutParams().height - MOVEBTN_RECT_SIZE /*- clipMoveAndRotateRelParams.height*/);
        moveBt.setLayoutParams(clipMoveAndRotateRelParams);
        alfa1 = clip.getAlfa();

        rectParams = new FrameLayout.LayoutParams(clip.getLayoutParams().width /*- 50*/, clip.getLayoutParams().height/* - 50*/);
        rect.setBackgroundColor(Color.TRANSPARENT);
        rect.setX(clip.getX() /*+ 50*/);////////
        rect.setY(clip.getY() /*+ 50*/);///////
        rect.setLayoutParams(rectParams);
        background.addView(rect);
        if (istText) {
            txtView.setOnTouchListener(txtTouch);
            txtView.setOnClickListener(txtClick);
        }

        background.addView(moveBt);
        rect.setOnTouchListener(rectTouch);
        /*fixMoveButton(1);*/
        /*clip.getBtnRemove().isFocusableInTouchMode();*///////?????
        hideShowBorders2(GONE, true);
        moveBt.setVisibility(VISIBLE);

      /*  clip.invalidate();*/                  //?!

    }


    View.OnTouchListener moveBtTouch = new View.OnTouchListener() {
        PointF now = new PointF();
        PointF start = new PointF();
        PointF deltaPoint = new PointF();
        double actualDistance = 0;
        double actualDistance2 = 0;
        int clipCenterX = 0;
        int clipCenterY = 0;
        double dimensR = 0;
        int w = 0, h = 0;
        boolean sized = false;
        float X = 0;
        float Y = 0;
        int count = 0;
        int chekSize = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    clearKeyboard();
                    if (istText) {
                        txtView.setSingleLine(false);
                    }
                    hideShowBorders(clip);
                    listenerSwitcher(clip, GONE, false);
                    clipCenterX = (int) (clip.getX() + clip.getLayoutParams().width / 2);
                    clipCenterY = (int) (clip.getY() + clip.getLayoutParams().height / 2);
                    FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) clip.getLayoutParams();
                    X = lParams.leftMargin + clip.getLayoutParams().width / 2;
                    Y = lParams.topMargin + clip.getLayoutParams().height / 2;
                    try {
                        count = clip.getLayoutParams().width / clip.getLayoutParams().height;
                    } catch (Exception e) {
                        count = 1;
                    }
                    deltaPoint.x = clip.getLayoutParams().width - moveBt.getLayoutParams().width;
                    deltaPoint.y = clip.getLayoutParams().height - moveBt.getLayoutParams().height;
                    start.set(event.getX() /*- 50*/, event.getY() /*- 50*/);///??????????????????????

                    dimensR = Math.sqrt((clip.getWidth() * clip.getWidth()) + (clip.getHeight() * clip.getHeight()));
                    actualDistance2 = Math.hypot(0 - event.getX(), 0 - event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (istText) {
                        txtView.setSingleLine(false);
                    }
                    now.set(event.getX(), event.getY());
                    if (!sized) {
                        actualDistance = Math.hypot(0 - event.getX(), 0 - event.getY());
                        Log.d("bvbvbvnv","c "+actualDistance +" "+actualDistance2+" "+dimensR*2/3+" "+dimensR );
                        actualDistance = (int) (actualDistance - actualDistance2 + dimensR * 2 / 3);
                        h = ((int) (actualDistance * Math.sin(Math.toRadians(alfa1))));
                        w = ((int) (actualDistance * Math.cos(Math.toRadians(alfa1))));
                        int zoomHeight = (h * 3 / 2);
                        if (istText) {
                            chekSize = 600;
                        } else {
                            chekSize = 1600;
                        }
                        if (zoomHeight < chekSize && zoomHeight > chekSize / 8) {
                            clip.getLayoutParams().width = (w * 3 / 2);
                            clip.getLayoutParams().height = (h * 3 / 2/*/count*/);//was count
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) clip.getLayoutParams();
                            layoutParams.leftMargin = (int) (X - w * 3 / 4);
                            layoutParams.topMargin = (int) (Y - h * 3 / 4);
                        }
                        clip.invalidate();
                        background.invalidate();
                        sized = true;
                    } else {

                        actualDistance = (int) Math.hypot((-deltaPoint.x) * 2 / 3 - event.getX(), (-deltaPoint.y) * 2 / 3 - event.getY());
                        Log.d("bvbvbvnv","c 1 "+actualDistance +" "+actualDistance2+" "+dimensR*2/3+" "+dimensR );
                        h = ((int) (actualDistance * Math.sin(Math.toRadians(alfa1))));
                        w = ((int) (actualDistance * Math.cos(Math.toRadians(alfa1))));
                        int wd = w * 3 / 2;
                        int hd = h * 3 / 2;
                        if (istText) {
                            chekSize = 600;
                        } else {
                            chekSize = 1600;
                        }
                        if (hd < chekSize && hd > chekSize / 8) {
                            clip.getLayoutParams().width = wd;
                            clip.getLayoutParams().height = hd/*/count*/;//was count

                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) clip.getLayoutParams();
                            layoutParams.leftMargin = (int) (X - w * 3 / 4);
                            layoutParams.topMargin = (int) (Y - h * 3 / 4);
                        }
                    }
                    clip.refreshBtn(clip.getLayoutParams().width, clip.getLayoutParams().height);
                    clip.refreshTextSize(clip.getLayoutParams().height + 20);////////////////////////////////////           repeat
                    clip.setLayoutParams(clip.getLayoutParams());
                    clip.invalidate();
                    int forText = 1;
                    if (istText) {
                        forText = 1;//was 4
                    }
                    int z = (int) (moveBt.getX() - clipCenterX / forText);
                    int z2 = (int) (moveBt.getY() - clipCenterY);
                    degres = (int) Math.toDegrees(Math.atan2(now.y + z2, now.x + z) - Math.atan2(start.y + z2, start.x + z));

                    if (!moved) {
                        if (movedFromMultitouch) {
                            movedFromMultitouch = false;
                        } else {
                            clip.setRotation(degresAfterMultyTouch + degres);
                        }
                    } else {
                        if (movedFromMultitouch) {
                            movedFromMultitouch = false;
                        } else {
                            clip.setRotation(degress + degres);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (istText) {
                        /*txtView.setSingleLine(true);*/////re
                    }
                    listenerSwitcher(clip, VISIBLE, false);
                    degresFinal = clip.getRotation();
                    degress = clip.getRotation();
                    moved = true;
                    fixRect();
                    fixMoveButton(false);
                    break;
            }
            return true;
        }
    };


    public void removeClip() {
        if (getClipPosition(clip) >= 0) {
            clipsw.remove(getClipPosition(clip));
        }////////////////////////////////////////////use it
            /*clipsw.remove(getClipPosition(clip));*/
        background.removeView(clip);
        background.removeView(moveBt);
        background.removeView(rect);
    }

    View.OnClickListener removeTextClip = new OnClickListener() {
        @Override
        public void onClick(View v) {
            removeClip();
        }
    };

    private int getClipPosition(Clip clip) {
        int pos = -1;
        for (int i = 0; i < clipsw.size(); i++) {
            if (clipsw.get(i).clip == clip) {
                pos = i;
            }
        }
        return pos;
    }

    public void listenerSwitcher(Clip clip, int visibility, boolean clipTouch) {
        for (int i = 0; i < clipsw.size(); i++) {
            if (clipsw.get(i).clip == clip) {
                ///curent clip
                if (clipTouch) {
                    //ret touch
                    clipsw.get(i).moveBt.setVisibility(visibility);
                } else {
                    //movebtn touch
                    clipsw.get(i).rect.setVisibility(visibility);
                }
            } else {
                //other clips
                clipsw.get(i).rect.setVisibility(visibility);
                clipsw.get(i).moveBt.setVisibility(GONE);
            }
        }
    }

    /* if (istText) {
               txtView.clearFocus();
               txtView.setCursorVisible(false);
               InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
               imm.hideSoftInputFromWindow(txtView.getWindowToken(), 0);
           }*/
    public void clearKeyboard() {////show in example
        if (istText) {
            txtView.clearFocus();
            txtView.requestFocus();
            txtView.setCursorVisible(false);
        }
        for (int i = 0; i < clipsw.size(); i++) {
            if (clipsw.get(i).istText) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(clipsw.get(i).clip.textV.getWindowToken(), 0);
            }
        }


       /* if (keyboardIsOpen && istText) {                              //////repeat
            Log.d("hyhyhyh", "Close");
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            keyboardIsOpen = false;

        }*/
    }

    public void fixMoveButton(boolean textChanges) {
        int size = clip.getMovebtnsize();
        if (textChanges) {

        }
        moveBt.setX(clip.getX() + clip.getRlayoutWidth() - size);
        moveBt.setY(clip.getY() + clip.getRLayoutHeight() - size);

        moveBt.setLayoutParams(moveBt.getLayoutParams());
        moveBt.setPivotX(-clip.getWidth() / 2 + moveBt.getLayoutParams().width - 10);
        moveBt.setPivotY(-clip.getHeight() / 2 + moveBt.getLayoutParams().height - 10);
        moveBt.setRotation(clip.getRotation());
        moveBt.setLayoutParams(moveBt.getLayoutParams());

    }

    public void fixRect() {
        rect.setRotation(clip.getRotation());
        rect.getLayoutParams().width = clip.getLayoutParams().width;
        rect.getLayoutParams().height = clip.getLayoutParams().height;
        rect.setX(clip.getX());////////
        rect.setY(clip.getY());//////////
        rect.setLayoutParams(rect.getLayoutParams());
        /*rect.invalidate();*/
    }


    public void hideShowBorders(Clip clip) {
        for (int i = 0; i < clipsw.size(); i++) {
            if (clipsw.get(i).clip == clip) {
                ///curent clip
                clipsw.get(i).clip.getBtnRemove().setVisibility(VISIBLE);
                clipsw.get(i).clip.getBtnMove().setVisibility(VISIBLE);
                clipsw.get(i).clip.hideShowBorder(VISIBLE);
            } else {
                //other clips
                clipsw.get(i).clip.getBtnRemove().setVisibility(GONE);
                clipsw.get(i).clip.getBtnMove().setVisibility(GONE);
                clipsw.get(i).clip.hideShowBorder(GONE);
                /*clipsw.get(i).moveBt.setVisibility(GONE);*/
            }
        }

    }

    public void hideShowBorders2(int visibility, boolean drop) {
        for (int i = 0; i < clipsw.size(); i++) {
            clipsw.get(i).clip.getBtnRemove().setVisibility(visibility);
            clipsw.get(i).clip.getBtnMove().setVisibility(visibility);
            clipsw.get(i).clip.hideShowBorder(visibility);
            clipsw.get(i).moveBt.setVisibility(visibility);
            if (istText && drop) {
                clipsw.get(getClipPosition(clip)).clip.hideShowBorder(VISIBLE);
            }
        }
    }


    public void setrectForDetectionX_Y() {
        rectFordetection = new RectF();
        rectForRemoving = new RectF();
        rectFordetection.set(rect.getX(), rect.getY(), rect.getX() + rect.getLayoutParams().width, rect.getY() + rect.getLayoutParams().height);////was clip
        rectForRemoving.set(clip.getX(), clip.getY(), clip.getX() + 75, clip.getY() + 75);

    }

    public void addClipToArraylist() {
        background.addView(clip);
        drop();

    }

    public void setArray(ArrayList<ClipView> clipslist) {
        this.clipsw = clipslist;
    }


}
