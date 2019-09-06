package wagonfly_screens;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify.adapter.BeneficiaryRecyclerAdapter;
import kartify.adapter.RecyclerTouchListener;
import kartify.adapter.SimpleDividerItemDecoration;
import kartify.model.Beneficiary;
import kartify.model.MallName;
import kartify.scanner.Scanner;
import kartify.scanner.UpdateTable;
import kartify.sql.DatabaseHelper;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.ResizeAnimation;
import wag_checkout.KartifyCheckoutScreen;

public class MainActivity extends BaseActivity implements UpdateTable {


    String name;
    String image_path;
    String deviceNamePrefix;
    String mall_id;
    String add;

    @BindView(R.id.start_shopping_frame)TextView start_shopping_frame;
    @BindView(R.id.title)TextView title;
    @BindView(R.id.drawerView)PlaceHolderView mDrawerView;
    @BindView(R.id.drawerLayout)DrawerLayout mDrawer;
    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.start_shopping)Button start_shopping;
    @BindView(R.id.checkout)LinearLayout checkout;
    @BindView(R.id.scan_layout)LinearLayout scan_layout;
    @BindView(R.id.back_button)ImageView back_button;
    @BindView(R.id.store)ImageView store;
    MallName mallName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startImage();
        title.setText("Your Cart");


        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent i = new Intent(MainActivity.this, MallList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);*/
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });
        checkout.setAlpha(.5f);
        scan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sacnner = new Intent(MainActivity.this, Scanner.class);


                startActivity(sacnner);
            }
        });

        Intent myData = getIntent();
        if (myData != null) {
             mallName = (MallName) myData.getSerializableExtra("mall_value");
            mall_id=mallName.getMallId();
            setDataForMall(mallName);
            price.setVisibility(View.VISIBLE);
        }
        start_shopping_frame.setVisibility(View.GONE);
        start_shopping_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sacnner = new Intent(MainActivity.this, Scanner.class);
                startActivity(sacnner);
            }
        });


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* */

            }
        });
        setupDrawer();
        initializeRecycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();


    }

    int doRead(int bufferSize, byte[] buffer) {
        return bufferSize;
    }

    void updateList() {
        DatabaseHelper dataBase = new DatabaseHelper(this);
        dataList = (ArrayList<Beneficiary>) dataBase.getAllBeneficiary();
        int ii = 0;

        if (dataList.size() > 0) {

            CommonClass.saveGenericData("" + mall_id, "MALL_ID_KEY", "MALL_ID_KEY_PREF", this);
            for (int i = 0; i < dataList.size(); i++) {
                ii += (int) (Integer.parseInt(dataList.get(i).getItem_quantity()) * Double.parseDouble(dataList.get(i).getS_price()));
            }
            price.setText("" + getResources().getString(R.string.rs) + "" + ii);
            price.setVisibility(View.VISIBLE);
//            ti.setVisibility(View.VISIBLE);
            start_shopping_frame.setVisibility(View.GONE);
            checkout.setAlpha(1f);
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mainActivity = new Intent(MainActivity.this, KartifyCheckoutScreen.class);
                    mainActivity.putExtra("mall_value", mallName);
                    startActivity(mainActivity);
                }
            });
        } else {
            CommonClass.clearData("MALL_ID_KEY", "MALL_ID_KEY_PREF", this);
            checkout.setAlpha(.5f);
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            price.setVisibility(View.GONE);
            start_shopping_frame.setVisibility(View.VISIBLE);
        }
        mAdapter = new BeneficiaryRecyclerAdapter(dataList, this, this, this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }


    private void setupDrawer()
    {
        mDrawerView
                .addView(new DrawerHeader(this))
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))//my account
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE))//my order
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_TODO)
                )//todolist
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS))//contactus
                //.addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_GROUPS))//faq
                .addView(new DrawerMenuItem(this, DrawerMenuItem.DRAWER_MENU_ITEM_REQUESTS))//term and condition
        ;

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);

        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.syncState();

        /**This is the comment***/
    }

    DisplayImageOptions options;

    void startImage() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.place_holder)
                .showImageForEmptyUri(R.drawable.place_holder)
                .showImageOnFail(R.drawable.place_holder)
                .cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
    }


    BeneficiaryRecyclerAdapter mAdapter;
    RecyclerView recyclerView;


    ArrayList<Beneficiary> dataList = new ArrayList<>();

    void initializeRecycle() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new BeneficiaryRecyclerAdapter(dataList, this, this, this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //  showDiloge(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    void clearData() {
        DatabaseHelper dataBase = new DatabaseHelper(this);
//        dataBase.open();
        dataBase.deleteCart();
    }

    @Override
    public void updateData(String data) {
        updateList();
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.LEFT);
        } else {
            finish();
        }
    }
}
