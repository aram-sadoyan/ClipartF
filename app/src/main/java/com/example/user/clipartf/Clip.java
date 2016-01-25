package com.example.user.clipartf;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;


public class Clip extends FrameLayout {

    ImageView btnRemove, btnMove, border, image;
    /*EditText textV;*/
    EditText textV;

    float count;
    FrameLayout.LayoutParams moveBtnp, borderP, imageP, clipParams;

    FrameLayout.LayoutParams removBtnP;
    private float seTY, seTX, setRemovBtnX;
    private int rLayoutWidth, rLayoutHeight;
    double forTan = 0;
    float alfa = 0;

    public Clip(Context context, ImageView imageView, EditText /*TextView*/ txtView) {
        super(context);
        init(imageView, txtView);
    }


    public void init(ImageView imView, EditText/*TextView */txtView) {
        int margin = (int) getContext().getResources().getDimension(R.dimen.MARGIN);
        rLayoutWidth = (int) getContext().getResources().getDimension(R.dimen.CLIP_WIDTH);
        rLayoutHeight = (int) getContext().getResources().getDimension(R.dimen.CLIP_HEIGHT);

        if (imView != null) {
            clipParams = new FrameLayout.LayoutParams(rLayoutWidth, rLayoutHeight);
        } else {
            rLayoutWidth = (int) getContext().getResources().getDimension(R.dimen.TEXT_CLIP_WIDTH);
            rLayoutHeight = (int) getContext().getResources().getDimension(R.dimen.TEXT_CLIP_HEIGHT);
            clipParams = new FrameLayout.LayoutParams(rLayoutWidth,rLayoutHeight);
        }
        count = (float) rLayoutWidth / rLayoutHeight;

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setLayoutParams(clipParams);

        if (imView != null) {
            imageP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageP.setMargins(margin, margin, margin, margin);//////?
            image = new ImageView(getContext());
            image = imView;
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setLayoutParams(imageP);
            this.addView(image);
        } else {

            FrameLayout.LayoutParams paramsExample = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textV = txtView;
            /*textV.setSingleLine(true);*/
            textV.setTextColor(Color.BLACK);
            textV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);//was30
            textV.setBackgroundColor(Color.GRAY);
            textV.setLayoutParams(paramsExample);
            textV.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            int maxLength = 25;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            textV.setFilters(fArray);
            this.addView(txtView);
            this.invalidate();
        }

        borderP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        borderP.setMargins(margin, margin, margin, margin);
        border = new ImageView(getContext());
        border.setBackgroundResource(R.drawable.stroke);
        border.setScaleType(ImageView.ScaleType.FIT_XY);
        border.setLayoutParams(borderP);
        this.addView(border);
        int buttonsize = (int) getContext().getResources().getDimension(R.dimen.BUTTON_SIZE);

        removBtnP = new FrameLayout.LayoutParams(buttonsize, buttonsize);
        btnRemove = new ImageView(getContext());
        btnRemove.setBackgroundResource(R.drawable.handle_rotate_picsart_light);
        seTX = clipParams.width - removBtnP.width;
        setRemovBtnX = this.getX();
        btnRemove.setX(setRemovBtnX);
        btnRemove.setScaleType(ImageView.ScaleType.FIT_XY);
        btnRemove.setLayoutParams(removBtnP);
        this.addView(btnRemove);

        moveBtnp = new FrameLayout.LayoutParams(buttonsize, buttonsize);
        btnMove = new ImageView(getContext());
        btnMove.setBackgroundResource(R.drawable.handle_rotate_picsart_light);
        btnMove.setX(seTX);
        seTY = clipParams.height - removBtnP.height;
        btnMove.setY(seTY);
        btnMove.setLayoutParams(moveBtnp);
        this.addView(btnMove);
    }

    public ImageView getBtnMove() {
        return btnMove;
    }

    public ImageView getBtnRemove() {
        return btnRemove;
    }

    public void setY(float y) {
        this.seTY = y;
    }

    public int getMovebtnsize() {
        return moveBtnp.width;
    }

    public int getRlayoutWidth() {
        return this.getWidth();
    }

    public int getRLayoutHeight() {
        return this.getHeight();
    }

    public float getCount() {
        return count;
    }

    public void refreshTextSize(int sizeH) {
        if (textV != null) {
            textV.setTextSize(sizeH / 6);//was5
            /*textV.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);*/
        }
    }

    public void refreshBtn(int width, int height) {
        setRemovBtnX = 0;
        seTX = width - removBtnP.width;
        seTY = height - removBtnP.height;
        btnRemove.setX(setRemovBtnX);
        btnMove.setX(seTX);
        btnMove.setY(seTY);
    }

    public float getAlfa() {
        forTan = (double) clipParams.height / clipParams.width;
        alfa = (int) Math.toDegrees(Math.atan(forTan));
        return alfa;
    }


    public void hideShowBorder(int visibility) {
        border.setVisibility(visibility);
    }

    public View getClipImage() {
        return image;
    }

    public EditText getTextView() {
        return textV;
    }

    public String getText() {
        if (textV != null) {
            return textV.getText().toString();
        } else {
            return null;
        }
    }

    public float getTextsize() {
        if (textV != null) {
            return textV.getTextSize();
        } else {
            return 0;
        }
    }


}

