package com.com.kartify_fonts;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


import kartify_base.AppController;


/**
 * Created by Dmytro Denysenko on 5/6/15.
 */
public class BoldTextView extends AppCompatTextView {
    public BoldTextView(Context context) {
        this(context, null);
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        {
            setTypeface(AppController.boldText);
        }
    }

}
