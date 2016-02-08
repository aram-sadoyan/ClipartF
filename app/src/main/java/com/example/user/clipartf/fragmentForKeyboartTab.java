package com.example.user.clipartf;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by user on 2/8/16.
 */
public class fragmentForKeyboartTab extends FrameLayout {

    FrameLayout.LayoutParams containerParams;

    public fragmentForKeyboartTab(Context context) {
        super(context);
        init();
    }

    private void init() {
        containerParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(containerParams);

    }


}
