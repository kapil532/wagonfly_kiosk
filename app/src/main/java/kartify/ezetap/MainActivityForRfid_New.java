package kartify.ezetap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.LinearLayout;
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

public class MainActivityForRfid_New extends BaseActivity implements UpdateTable {

    private PlaceHolderView mDrawerView;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;
    Button start_shopping;
    Button gotocheckout;
    TextView total_price, tax, grant_total, cart_shopping;
    Handler mHandlera = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        startImage();

        gotocheckout = (Button) findViewById(R.id.gotocheckout);
        total_price = (TextView) findViewById(R.id.total_price);
        cart_shopping = (TextView) findViewById(R.id.cart_shopping);
        cart_shopping.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Setting(getApplicationContext());
                return false;
            }
        });
        tax = (TextView) findViewById(R.id.tax);
        grant_total = (TextView) findViewById(R.id.grant_total);
        customFontsChecks(gotocheckout);
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
        public void run() {
            // do your stuff - don't create a new runnable here!
            getCodeDetails();
            mHandlera.postDelayed(this, 2000);

        }
    };

// start it with:

    public void Setting(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandlera.removeCallbacks(runnable);
    }

    void updateList(ArrayList<Product_Pojo> daaa) {


        dataList = daaa;
        float ii = 0;

        if (dataList.size() > 0) {

            for (int i = 0; i < dataList.size(); i++) {
                ii += Integer.parseInt(dataList.get(i).getUnit()) * Float.parseFloat(dataList.get(i).getManipulate_price());
            }

            total_price.setText(getResources().getString(R.string.rs) + " " + (ii - (Float.parseFloat(tax_basket))));
            tax.setText(getResources().getString(R.string.rs) + " " + tax_basket);
            grant_total.setText(getResources().getString(R.string.rs) + " " + ii);
            total_price.setVisibility(View.VISIBLE);
            final float finalIi = ii;
            gotocheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    createOrder("" + finalIi);
                }
            });
        } else {
            gotocheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CommonClass.showDiloge(MainActivityForRfid_New.this, "Your Kart is Empty!");
                }
            });
            total_price.setVisibility(View.GONE);
        }
        mAdapter = new BeneficiaryRecyclerAdapterRfid(dataList, this, this);
        // recyclerView.setAdapter(mAdapter);
        Parcelable state = recyclerView.getLayoutManager().onSaveInstanceState();
        recyclerView.setAdapter(mAdapter);
        recyclerView.getLayoutManager().onRestoreInstanceState(state);
        mAdapter.notifyDataSetChanged();
        //  mainList.setAdapter(new BeneficiaryRecyclerAdapter(dataList,this));

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


    BeneficiaryRecyclerAdapterRfid mAdapter;
    RecyclerView recyclerView;


    ArrayList<Product_Pojo> dataList = new ArrayList<>();

    void initializeRecycle() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new BeneficiaryRecyclerAdapterRfid(dataList, this, this);
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


        //recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


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


    void getCodeDetails() {
        JSONObject table = new JSONObject();

        Log.d("URL", "MAINACTIVITY_RFID" + GET_RFID_ORDERS + CommonClass.returnGenericData(STORE_ID, STORE_ID_PREF, getApplicationContext()));
        PostData.calla(table, GET_RFID_ORDERS + CommonClass.returnGenericData(STORE_ID, STORE_ID_PREF, getApplicationContext()), new PostData.PostCommentResponseListener() {

            @Override
            public void requestStarted() {
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
            }

            @Override
            public void requestCompleted(String message) {

                try {
                    Log.d("LOGS VALUESS", "MAINACTIVITY_RFID--> Response-->" + message.toString());

                    JSONObject jsonObject = new JSONObject(message);
                    String s = jsonObject.getString("status");
                    if (s.equalsIgnoreCase("false"))
                    {

                    }
                    else
                    {
                        jsonParser(message.toString());
                    }
                } catch (Exception e) {
                    Intent myIn = new Intent(getApplicationContext(), SplashScreenForRfid.class);
                    myIn.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(myIn);
                    finish();
                }


            }
        });


    }

    ArrayList<Product_Pojo> listBen;
    String tax_basket = "";
    String RFIDData = "";

    void jsonParser(String jsonData) {
        listBen = new ArrayList<>();
        try {
            JSONObject jsonObjecta = new JSONObject(jsonData);
            tax_basket = jsonObjecta.getString("tax");
            RFIDData = jsonObjecta.getString("RFIDData");
            Log.e("TAX", "TAX" + tax_basket);
            JSONArray jsonObjectA = jsonObjecta.getJSONArray("order");


            for (int i = 0; i < jsonObjectA.length(); i++) {

                JSONObject jsonObject = jsonObjectA.getJSONObject(i);
                Product_Pojo ben = new Product_Pojo();
                ben.setId(jsonObject.getString("id"));

               // ben.setRFIDData(jsonObject.getString("RFIDData"));

                ben.setItem_description(jsonObject.getString("item_description"));
                ben.setFit(jsonObject.getString("fit"));

                ben.setItem_name(jsonObject.getString("item_name"));
                ben.setItem_theme(jsonObject.getString("item_theme"));
                ben.setItem_code(jsonObject.getString("item_code"));
                ben.setColor(jsonObject.getString("color"));
                ben.setFabric(jsonObject.getString("fabric"));
                ben.setSleeves(jsonObject.getString("sleeves"));
                ben.setPattern(jsonObject.getString("pattern"));
                ben.setSize(jsonObject.getString("size"));
                ben.setMrp(jsonObject.getString("mrp"));
                ben.setUnit(jsonObject.getString("unit"));
                ben.setPrice(jsonObject.getString("price"));
                ben.setImage_url(jsonObject.getString("image_url"));
                ben.setManipulate_price(jsonObject.getString("manipulate_price"));
                ben.setDiscount(jsonObject.getString("discount"));
                listBen.add(ben);
            }


        } catch (Exception e) {

        }
        Log.d("SIZZE", "SIZEEE" + listBen.size());
        if (listBen.size() > 0) {

            updateList(listBen);
        } else {
            Intent myIn = new Intent(getApplicationContext(), SplashScreenForRfid.class);
            myIn.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(myIn);
            finish();
        }

    }


    void createOrder(final String total_amount) {
        JSONObject table = new JSONObject();
        try {
            table.put("total_amount", "" + total_amount);
        } catch (JSONException e) {

        }
        Log.d("URL", "MAINACTIVITY_RFID" + RFID_CREATE_ORDER + CommonClass.returnGenericData(STORE_ID, STORE_ID_PREF, getApplicationContext()));
        PostData.call(this, table, RFID_CREATE_ORDER + CommonClass.returnGenericData(STORE_ID, STORE_ID_PREF, getApplicationContext()), new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted() {
                showProgressDialog();
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                hideProgressDialog();
            }

            @Override
            public void requestCompleted(JSONObject message) {
                hideProgressDialog();
                try {
                    Log.d("LOGS VALUESS", "MAINACTIVITY_RFID--> Response-->" + message.toString());
                    String s = message.getString("status");
                    String message_ = message.getString("message");
                    String orderId = message.getString("id");
                   // String orderId = message.getString("orderId");
                    if (s.equalsIgnoreCase("true")) {
                        // jsonParser(message.toString());
                        openNextScreen(total_amount, orderId, tax_basket);
                        Toast.makeText(getApplicationContext(), "" + message_, Toast.LENGTH_LONG).show();


                    }
                } catch (Exception e) {

                }


            }
        });

    }

    void openNextScreen(String total_amount, String orderId, String tax_basket)
    {
        Intent myInt = new Intent(this, UserDetailsEmailsActivity.class);
        myInt.putExtra("total_amount", total_amount);
        myInt.putExtra("orderId", orderId);
        myInt.putExtra("RFIDData", RFIDData);
        myInt.putExtra("tax", tax_basket);
        Bundle bun = new Bundle();
        bun.putSerializable("array", listBen);
        myInt.putExtras(bun);
        startActivity(myInt);
    }


}
