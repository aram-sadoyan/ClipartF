package com.example.user.clipartf;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;


public class Clip extends FrameLayout {

    ImageView btnRemove, btnMove, border, image;
    EditText textV;
    float count;
    FrameLayout.LayoutParams moveBtnp, borderP, imageP, clipParams;

    FrameLayout.LayoutParams removBtnP;
    private float seTY, seTX, setRemovBtnX;
    private int rLayoutWidth, rLayoutHeight;
    double forTan = 0;
    float alfa = 0;

    public Clip(Context context, ImageView imageView, EditText txtView) {
        super(context);
        init(imageView, txtView);
    }


    public void init(ImageView imView, EditText txtView) {
        rLayoutWidth = Constants.CLIP_WIDTH;
        rLayoutHeight = Constants.CLIP_HEIGHT;

        if (imView != null) {
            clipParams = new FrameLayout.LayoutParams(rLayoutWidth, rLayoutHeight);
        } else {
            rLayoutHeight = Constants.TEXT_CLIP_HEIGHT;
            clipParams = new FrameLayout.LayoutParams(rLayoutWidth/2, rLayoutHeight);
        }
        count = (float) rLayoutWidth / rLayoutHeight;

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setLayoutParams(clipParams);

        if (imView != null) {
            imageP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageP.setMargins(25, 25, 25, 25);
            image = new ImageView(getContext());
            image = imView;
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setLayoutParams(imageP);

            this.addView(image);
        } else {
            FrameLayout.LayoutParams paramsExample = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textV = txtView;
            textV.setSingleLine(true);
            textV.setTextColor(Color.BLACK);
            textV.setTextSize(this.getLayoutParams().height / 6);
            textV.setBackgroundColor(Color.TRANSPARENT);
            textV.setLayoutParams(paramsExample);
            textV.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            this.addView(txtView);
        }


            borderP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            borderP.setMargins(25, 25, 25, 25);
            border = new ImageView(getContext());
            border.setBackgroundResource(R.drawable.stroke);
            border.setScaleType(ImageView.ScaleType.FIT_XY);
            border.setLayoutParams(borderP);
            this.addView(border);


        removBtnP = new FrameLayout.LayoutParams(Constants.REMOVE_BUTTON_SIZE, Constants.REMOVE_BUTTON_SIZE);
        btnRemove = new ImageView(getContext());
        btnRemove.setBackgroundResource(R.drawable.handle_rotate_picsart_light);
        seTX = clipParams.width - removBtnP.width;
        setRemovBtnX = this.getX();
        btnRemove.setX(setRemovBtnX);
        btnRemove.setScaleType(ImageView.ScaleType.FIT_XY);
        btnRemove.setLayoutParams(removBtnP);
        this.addView(btnRemove);

        moveBtnp = new FrameLayout.LayoutParams(Constants.MOVE_BUTTON_SIZE, Constants.MOVE_BUTTON_SIZE);
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

            textV.setTextSize(sizeH / 8);
            textV.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
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
       /* if (image != null) {*/
            border.setVisibility(visibility);
       /* }*/
    }

    public View getClipImage() {
        return image;
    }

    public View getTextView() {
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