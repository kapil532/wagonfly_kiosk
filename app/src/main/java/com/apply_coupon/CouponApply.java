package com.apply_coupon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wagonfly.R;

import kartify_base.BaseActivity;

/**
 * Created by Kapil Katiyar on 7/3/2018.
 */

 public class CouponApply extends BaseActivity
{

    EditText edit_box_code;
    TextView apply_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_apply);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title =(TextView)findViewById(R.id.title);
        title.setText("Apply Coupons");
        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                finish();
            }
        });
        edit_box_code =(EditText)findViewById(R.id.edit_box_code);
        apply_text =(TextView) findViewById(R.id.apply_text);
        apply_text.setAlpha(.5f);
        edit_box_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.toString().length()>0)
                {
                    apply_text.setAlpha(1f);
                    apply_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(CouponApply.this,""+edit_box_code.getText().toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    apply_text.setAlpha(.5f);
                    apply_text.setOnClickListener(null);

                }
            }
        });

    }
}
