package kartify.ezetap;

import android.app.Dialog;
import android.content.Context;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import com.mindorks.placeholderview.PlaceHolderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kartify.adapter.BeneficiaryRecyclerAdapterRfid;
import kartify.adapter.RecyclerTouchListener;
import kartify.adapter.SimpleDividerItemDecoration;
import kartify.model.Beneficiary;
import kartify.scanner.Scanner;
import kartify.scanner.UpdateTable;
import kartify.sql.DatabaseHelper;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;
import wagonfly_screens.DrawerHeader;
import wagonfly_screens.DrawerMenuItem;

public class MainActivityForRfid extends BaseActivity implements UpdateTable {

    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;
    Button start_shopping;
    FloatingActionButton menu_icon;
    FloatingActionButton plus;
    FloatingActionButton checkout;
    TextView start_shopping_frame;
    Button pay_now;
    String mall_id;
    Handler mHandlera = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startImage();


        //  mainList =(ListView)findViewById(R.id.mainList);
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView) findViewById(R.id.drawerView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        start_shopping = (Button) findViewById(R.id.start_shopping);
        menu_icon = (FloatingActionButton) findViewById(R.id.menu_icon);


        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(Gravity.START);
            }
        });
        checkout = (FloatingActionButton) findViewById(R.id.checkout);

        TextView scan_text = (TextView) findViewById(R.id.scan_text);
        scan_text.setVisibility(View.INVISIBLE);
        plus = (FloatingActionButton) findViewById(R.id.plus);
        plus.setVisibility(View.INVISIBLE);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sacnner = new Intent(MainActivityForRfid.this, Scanner.class);
                startActivity(sacnner);
            }
        });

        Intent myData = getIntent();
        if (myData != null) {
            mall_id = myData.getStringExtra("id");
            Log.d("VALUE", "VALUESS--0--" + mall_id);
            String name = myData.getStringExtra("name");
            String image_path = myData.getStringExtra("image_path");
            String add = myData.getStringExtra("address");


            setDataForMall(name, mall_id, image_path, add);

            CommonClass.saveGenericData("" + mall_id, "MALL_ID_KEY", "MALL_ID_KEY_PREF", this);

//            sendNotification(name);
           /* if (CommonClass.returnGenericData("MALL_ID_KEY", "MALL_ID_KEY_PREF", this).equalsIgnoreCase(id)) {
                clearData();
                CommonClass.saveGenericData(id, "MALL_ID_KEY", "MALL_ID_KEY_PREF", this);
            } else {
                CommonClass.saveGenericData(id, "MALL_ID_KEY", "MALL_ID_KEY_PREF", this);
            }*/
        }
        start_shopping_frame = (TextView) findViewById(R.id.start_shopping_frame);
        start_shopping_frame.setVisibility(View.GONE);
        start_shopping_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sacnner = new Intent(MainActivityForRfid.this, Scanner.class);
                startActivity(sacnner);
            }
        });
        start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* */

            }
        });
        //  mGalleryView = (PlaceHolderView) findViewById(R.id.galleryView);
        setupDrawer();
        initializeRecycle();
        // updateList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // updateList();

        getCodeDetails();
        mHandlera.post(runnable);
    }



    Runnable runnable = new Runnable() {
        @Override
        public void run()
        {
            // do your stuff - don't create a new runnable here!
            getCodeDetails();
                mHandlera.postDelayed(this, 2000);

        }
    };

// start it with:



    @Override
    protected void onStop() {
        super.onStop();
        mHandlera.removeCallbacks(runnable);
    }

    void updateList(ArrayList<Beneficiary> daaa) {


        dataList = daaa;
        int ii = 0;

        if (dataList.size() > 0) {

            for (int i = 0; i < dataList.size(); i++) {
                ii += Integer.parseInt(dataList.get(i).getItem_quantity()) * Integer.parseInt(dataList.get(i).getS_price());
            }
            price.setText("" + getResources().getString(R.string.rs) + "" + ii);
            price.setVisibility(View.VISIBLE);
            ti.setVisibility(View.VISIBLE);
            start_shopping_frame.setVisibility(View.GONE);
            checkout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {

                  /*  KartifyCheckoutScreenRfid.lisst=dataList;
                    Intent sacnner = new Intent(MainActivityForRfid.this, KartifyCheckoutScreenRfid.class);
                    startActivity(sacnner);*/
                }
            });
        } else {
            // CommonClass.clearData("MALL_ID_KEY", "MALL_ID_KEY_PREF", this);
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CommonClass.showDiloge(MainActivityForRfid.this, "Your Kart is Empty!");
                }
            });
            price.setVisibility(View.GONE);
            start_shopping_frame.setVisibility(View.VISIBLE);
            ti.setVisibility(View.GONE);
        }
        Log.d("AAAAAAAAAAA", "AFTER" + dataList.size());
       // mAdapter = new BeneficiaryRecyclerAdapterRfid(dataList, this, this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //  mainList.setAdapter(new BeneficiaryRecyclerAdapter(dataList,this));

    }


    private void setupDrawer() {
        mDrawerView
                .addView(new DrawerHeader(this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_REQUESTS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_GROUPS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS));

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

    TextView price;
    ImageView ti;

    void setDataForMalla(String name, String id, String imagePath, String add) {

        TextView title = (TextView) findViewById(R.id.textView1); // title
        price = (TextView) findViewById(R.id.price); // title
        TextView artist = (TextView) findViewById(R.id.textView2); // artist name
        ImageView thumb_image = (ImageView) findViewById(R.id.imageView1); // thumb image
        ti = (ImageView) findViewById(R.id.ti); // thumb image


        // Setting all values in listview
        title.setText(name);
        artist.setText(add);
        ImageLoader.getInstance().displayImage(imagePath, thumb_image, options, null);
    }

    BeneficiaryRecyclerAdapterRfid mAdapter;
    RecyclerView recyclerView;


    ArrayList<Beneficiary> dataList = new ArrayList<>();

    void initializeRecycle() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
      // mAdapter = new BeneficiaryRecyclerAdapterRfid(dataList, this, this);
        recyclerView.setHasFixedSize(true);
        // vertical RecyclerView
//        adaddd
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        // adding inbuilt divider line
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                showDiloge(position);


//                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void showDiloge(int position) {

        Beneficiary movie = dataList.get(position);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_screen_mall_list);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * .8;
        Double height = metrics.heightPixels * .6;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());


        TextView item_price = (TextView) dialog.findViewById(R.id.item_price);
        ImageView item_icon = (ImageView) dialog.findViewById(R.id.item_icon);
        TextView item_title = (TextView) dialog.findViewById(R.id.item_title);
        TextView item_des = (TextView) dialog.findViewById(R.id.item_des);

        ImageLoader.getInstance().displayImage(movie.getProduct_image(), item_icon, options, null);

        item_price.setText(getResources().getString(R.string.rs) + " " + movie.getS_price() + " per piece");
        item_title.setText(movie.getName());
        item_des.setText(movie.getManufacturer());


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
//                finish();
//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    void clearData() {
        DatabaseHelper dataBase = new DatabaseHelper(this);
//        dataBase.open();
        dataBase.deleteCart();
    }

    @Override
    public void updateData(String data) {

//        updateList();
    }


    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.LEFT);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(mRunnable, 2000);
        }
    }



    void getCodeDetails() {
        JSONObject table = new JSONObject();
        try {
            table.put("uid", "" + network.getKartUid());
           // table.put("skey", "" + network.getKartKey());


        } catch (JSONException e) {

        }
        PostData.call(this,table, CommonClass.URL_RFID_BUCKET, new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted()
            {
//                showProgressDialog();
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
//                progressbar_id.setVisibility(View.GONE);
               // hideProgressDialog();
            }

            @Override
            public void requestCompleted(JSONObject message) {
               // hideProgressDialog();

                try {
                    Log.d("LOGS VALUESS", "VALUESS-->" + message.toString());
                    String s = message.getString("status");
                    if (s.equalsIgnoreCase("true")) {
                        jsonParser(message.toString());
                    } else {
                    }
                } catch (Exception e) {

                }


            }
        });


    }

    ArrayList<Beneficiary> listBen;

    void jsonParser(String jsonData) {
        listBen = new ArrayList<>();
        try {
            JSONObject jsonObjecta = new JSONObject(jsonData);
            JSONArray jsonObjectA = jsonObjecta.getJSONArray("data");


            for (int i = 0; i < jsonObjectA.length(); i++) {

                JSONObject jsonObject = jsonObjectA.getJSONObject(i);
                Beneficiary ben = new Beneficiary();
                ben.setProduct_image("");
                ben.setWeight(jsonObject.getString("weight"));
                ben.setName(jsonObject.getString("name"));
                ben.setS_price(jsonObject.getString("s_price"));
                ben.setManufacturer(jsonObject.getString("manufacturer"));
                ben.setCode(jsonObject.getString("code"));
                ben.setId(jsonObject.getString("code"));
                ben.setCategory_name(jsonObject.getString("category_name"));
                ben.setBrand(jsonObject.getString("brand"));
                ben.setItem_quantity("1");
                listBen.add(ben);
            }


        } catch (Exception e) {

        }
        Log.d("SIZZE", "SIZEEE" + listBen.size());
//        if (listBen.size() > 0)
        {

            updateList(listBen);
        }

    }


}
