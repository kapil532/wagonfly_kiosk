package wagonfly_screens;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kartify.adapter.BeneficiaryRecyclerAdapter;
import kartify.adapter.RecyclerTouchListener;
import kartify.adapter.SimpleDividerItemDecoration;
import kartify.model.Beneficiary;
import kartify.scanner.Scanner;
import kartify.scanner.UpdateTable;
import kartify.sql.DatabaseHelper;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;
import wag_checkout.KartifyCheckoutScreen;

public class MainActivityBluetooth extends BaseActivity implements UpdateTable {

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
    ImageView back_button;
    String deviceNamePrefix;
    EditText edit_box_code;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_checkout_data);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        startImage();
        edit_box_code =(EditText)findViewById(R.id.getScanData);
        edit_box_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==13)
                {
                    Toast.makeText(MainActivityBluetooth.this,s.toString(),Toast.LENGTH_LONG).show();

                    getCodeDetails(s.toString());
                }

            }
        });
        edit_box_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            edit_box_code.post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edit_box_code, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            });
        }
        });
        edit_box_code.requestFocus();



        //  mainList =(ListView)findViewById(R.id.mainList);
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView) findViewById(R.id.drawerView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        start_shopping = (Button) findViewById(R.id.start_shopping);
        menu_icon = (FloatingActionButton) findViewById(R.id.menu_icon);
        back_button=(ImageView)findViewById(R.id.back_button);
        back_button.setVisibility(View.VISIBLE);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
             /*   Intent mallList =new Intent(MainActivityBluetooth.this, MallList.class);
                startActivity(mallList);
                finish();*/


            }
        });
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(Gravity.START);
            }
        });
        checkout = (FloatingActionButton) findViewById(R.id.checkout);

        plus = (FloatingActionButton) findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sacnner = new Intent(MainActivityBluetooth.this, Scanner.class);
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
                Intent sacnner = new Intent(MainActivityBluetooth.this, Scanner.class);
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
        updateList();


    }

    int doRead(int bufferSize, byte[] buffer)
    {
       return bufferSize;
    }

    void updateList()
    {
        DatabaseHelper dataBase = new DatabaseHelper(this);
        dataList = (ArrayList<Beneficiary>) dataBase.getAllBeneficiary();
        int ii = 0;

        if (dataList.size() > 0) {

            for (int i = 0; i < dataList.size(); i++) {
                ii += Integer.parseInt(dataList.get(i).getItem_quantity()) * Integer.parseInt(dataList.get(i).getS_price());
            }
            price.setText("" + getResources().getString(R.string.rs) + "" + ii);
            price.setVisibility(View.VISIBLE);
            //ti.setVisibility(View.VISIBLE);
            start_shopping_frame.setVisibility(View.GONE);
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent sacnner = new Intent(MainActivityBluetooth.this, KartifyCheckoutScreen.class);
                    startActivity(sacnner);
                }
            });
        } else {
            // CommonClass.clearData("MALL_ID_KEY", "MALL_ID_KEY_PREF", this);
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CommonClass.showDiloge(MainActivityBluetooth.this, "Your Kart is Empty!");
                }
            });
            price.setVisibility(View.GONE);
            start_shopping_frame.setVisibility(View.VISIBLE);
            //ti.setVisibility(View.GONE);
        }
        Log.d("AAAAAAAAAAA", "AFTER" + dataList.size());
        mAdapter = new BeneficiaryRecyclerAdapter(dataList, this, this,this);
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
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT))
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

    /*TextView price;
    ImageView ti;

    void setDataForMall(String name, String id, String imagePath, String add) {

        TextView title = (TextView) findViewById(R.id.textView1); // title
        price = (TextView) findViewById(R.id.price); // title
        TextView artist = (TextView) findViewById(R.id.textView2); // artist name
        ImageView thumb_image = (ImageView) findViewById(R.id.imageView1); // thumb image
        ti = (ImageView) findViewById(R.id.ti); // thumb image


        // Setting all values in listview
        title.setText(name);
        artist.setText(add);
        ImageLoader.getInstance().displayImage(imagePath, thumb_image, options, null);
    }*/

    BeneficiaryRecyclerAdapter mAdapter;
    RecyclerView recyclerView;


    ArrayList<Beneficiary> dataList = new ArrayList<>();

    void initializeRecycle() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new BeneficiaryRecyclerAdapter(dataList, this, this,this);
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
            public void onClick(View view, int position)
            {
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
    public void updateData(String data)
    {
        updateList();
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




    void getCodeDetails(String code) {
        JSONObject table = new JSONObject();
        try {
            table.put("code", "" + code);
            table.put("store_id", "" + CommonClass.returnGenericData("MALL_ID_KEY", "MALL_ID_KEY_PREF", this));
            table.put("skey", "" + network.getKartKey());


        } catch (JSONException e) {

        }
        PostData.call(this,table, CommonClass.URL_PRODUCT_DETAILS, new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted() {
                showProgressDialog();
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
//                progressbar_id.setVisibility(View.GONE);
                hideProgressDialog();
            }

            @Override
            public void requestCompleted(JSONObject message) {
                hideProgressDialog();

                try {
                    Log.d("LOGS VALUESS", "VALUESS-->" + message.toString());
                    String s = message.getString("status");
                    if (s.equalsIgnoreCase("true")) {
                        jsonParser(message.toString());

                    } else {
                        showDiloge(2);
//                        Toast.makeText(Scanner.this, "Please try again!", Toast.LENGTH_LONG).show();
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
            JSONObject jsonObject = jsonObjecta.getJSONObject("data");
            Beneficiary ben = new Beneficiary();
            ben.setProduct_image("");
            ben.setWeight(jsonObject.getString("weight"));
            ben.setName(jsonObject.getString("name"));
            ben.setS_price(jsonObject.getString("s_price"));
            ben.setManufacturer(jsonObject.getString("manufacturer"));
            ben.setCode(jsonObject.getString("code"));
            ben.setProduct_image(jsonObject.getString("img"));
            ben.setId(jsonObject.getString("id"));
            ben.setCategory_name(jsonObject.getString("category_name"));
            ben.setBrand(jsonObject.getString("brand"));
            ben.setItem_quantity("1");
            listBen.add(ben);

        } catch (Exception e) {

        }
        Log.d("SIZZE", "SIZEEE" + listBen.size());
        if (listBen.size() > 0)
        {
            showDilogeWithA(0);

        }

    }


    private void showDiloge(final int is) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_screen);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * .7;
        Double height = metrics.heightPixels * .2;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());


        TextView text = (TextView) dialog.findViewById(R.id.text);
        switch (is) {
            case 1:
                text.setText("Your product has added");
                break;
            case 2:
                text.setText("Please try again!!");
                break;
            default:
                break;
        }

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                switch (is) {
                    case 1:

                        break;
                    case 2:

                        break;
                    default:
                        break;
                }

//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }



    void updateTable(Beneficiary ben) {
        DatabaseHelper db = new DatabaseHelper(this);
        //  db.open();
        // String code=  db.getValue(ben.getCode());
        Log.d("SIZZE", "SIZEEE Update" + db.getAllBeneficiary().size());
        String quantity = "";
        ArrayList<Beneficiary> list = (ArrayList<Beneficiary>) db.getAllBeneficiary();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCode().equalsIgnoreCase(ben.getCode())) {

                quantity = list.get(i).getItem_quantity();
                Log.d("SIZZE", "SIZEEE Update188" + quantity);
                break;
            }
        }
        Log.d("SIZZE", "SIZEEE Update18899--" + quantity);
        if (quantity.length() > 0) {
            Log.d("SIZZE", "SIZEEE Update11");
//           db.deleteSingleContact(ben.getCode());
            int value = Integer.parseInt(quantity) + 1;
            Log.d("SIZZE", "SIZEEE Update-->" + value);
            db.updateParticularData("" + value, ben.getCode());
        } else {
            Log.d("SIZZE", "SIZEEE InSERT");
            db.addBeneficiary(ben);
        }
        //db.close();

        updateList();
    }



    private void showDilogeWithA(int position)
    {

        Beneficiary movie = listBen.get(position);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_screen_for_product_add);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
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

        item_price.setText(this.getResources().getString(R.string.rs) + " " + movie.getS_price() + " per piece");
        item_title.setText(movie.getName());
        item_des.setText(movie.getManufacturer());


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                edit_box_code.setText("");
//                finish();
//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
                updateTable();
                edit_box_code.setText("");
              //  finish();

//                finish();
//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void updateTable()
    {
       /* new Thread(new Runnable() {
            @Override
            public void run() {*/
                updateTable(listBen.get(0));
           /* }
        }).start();*/
    }

}
