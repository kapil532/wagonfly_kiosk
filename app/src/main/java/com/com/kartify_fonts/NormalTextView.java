package com.com.kartify_fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;


import kartify_base.AppController;

@SuppressLint("AppCompatCustomView")
public class NormalTextView extends TextView
{
    public NormalTextView(Context context) {
        this(context, null);
    }

    public NormalTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {
            setTypeface(AppController.normalText);
        }

    }

}