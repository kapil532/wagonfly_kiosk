package smart_shopping_choose_option;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.wagonfly.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify.model.MallName;
import kartify_base.BaseActivity;
import wagonfly_screens.MainActivity;

/**
 * Created by Kapil Katiyar on 12/18/2017.
 */

public class SelectionScreen extends BaseActivity
{
    String name;
    String image_path;
    String add,mall_id;


    /*
    *
    *  Full code is powered by Wagonfly
    * */

    @BindView(R.id.title)TextView title;
    @BindView(R.id.des)TextView des;
    @BindView(R.id.scanner)RelativeLayout scanner;
    @BindView(R.id.kart)RelativeLayout kart;
    @BindView(R.id.bluetooth_scanne)RelativeLayout mblue;
    @BindView(R.id.toolbar)Toolbar mToolbar;

  MallName mallName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_screen);
        ButterKnife.bind(this);

        Intent myData = getIntent();
        if (myData != null)
        {
             mallName = (MallName) myData.getSerializableExtra("mall_value");
            setDataForMall(mallName);
        }

        title.setText("Shopping Options");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });


        scanner =(RelativeLayout)findViewById(R.id.scanner);
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity();
            }
        });
        kart =(RelativeLayout)findViewById(R.id.kart);
        kart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /* Intent mainActivity = new Intent(SelectionScreen.this, MainActivityForRfid.class);
                mainActivity.putExtra("name",""+name);
                mainActivity.putExtra("id",""+mall_id);
                mainActivity.putExtra("image_path",""+image_path);
                mainActivity.putExtra("address",""+add);
                startActivity(mainActivity);
                finish();*/

                Toast.makeText(SelectionScreen.this,"Coming soon...",Toast.LENGTH_LONG).show();



            }
        });

        mblue =(RelativeLayout)findViewById(R.id.bluetooth_scanne);
        mblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity();

            }
        });




    }


    void startActivity()
    {
        Intent mainActivity = new Intent(SelectionScreen.this, MainActivity.class);
        mainActivity.putExtra("mall_value", mallName);
        startActivity(mainActivity);
        finish();
    }



}
