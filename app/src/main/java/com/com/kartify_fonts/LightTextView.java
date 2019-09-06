package com.com.kartify_fonts;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


import kartify_base.AppController;

/**
 * Created by Kapil Katiyar on 5/6/15.
 */
public class LightTextView extends AppCompatTextView {
    public LightTextView(Context context) {
        this(context, null);
    }

    public LightTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LightTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        {
            setTypeface(AppController.lightText);
        }
    }

}
