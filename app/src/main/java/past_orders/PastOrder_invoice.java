package past_orders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.wagonfly.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify.model.Beneficiary;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;

/**
 * Created by Kapil Katiyar on 3/7/2018.
 */


public class PastOrder_invoice extends BaseActivity {


    /*For notification*/
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)  TextView title;

    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;
    int counter = 0;
//    private NotificationReceiver nReceiver;
    ArrayList<AsyncTask<String, String, Void>> arr;


    TextView date;
    TextView time;
    ListView list_rec;
    LinearLayout list_layout;
    TextView total_price;
    Button pay_now;
    public static ArrayList<Beneficiary> lisst;
    public static String orider_id;
   public static PastOrderPojoNew productList;

    @BindView(R.id.final_prize)  TextView final_prize;
    @BindView(R.id.offer_discount)  TextView offer_discount;
    @BindView(R.id.gst)  TextView gst;
    @BindView(R.id.toPay)  TextView toPay;
    @BindView(R.id.store_title)  TextView store_title;
    @BindView(R.id.offerLay)  RelativeLayout offerLay;
    @BindView(R.id.gst_lay)  RelativeLayout gst_lay;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_order_invoice);
        ButterKnife.bind(this);




        list_layout = (LinearLayout) findViewById(R.id.list_layout);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy ");
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("hh:mm");
        String format = simpleDateFormat.format(new Date());
        String formata = simpleDateFormats.format(new Date());

        title.setText("Bill Receipt");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //  mToolbar.setBackgroundColor(getResources().getColor(R.color.prime_primary_color));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        date.setText(format);
        time.setText(formata);
        store_title.setVisibility(View.GONE);
        total_price = (TextView) findViewById(R.id.total_price);
        list_rec = (ListView) findViewById(R.id.list_rec);


        pay_now = (Button) findViewById(R.id.pay_now);
        customFontsBold(pay_now);
        updateList();
    }

    ReceiptListAdapter rec;
    double ii;
    String fullData = "";

    void updateList() {
        ArrayList<PastOrderPojoNew.itemsPojo> list = productList.getItems();
        ii = 0;
        if (list.size() > 0)
        {

            rec = new ReceiptListAdapter(this, list);
            list_rec.setAdapter(rec);
            }
            total_price.setText("" + getResources().getString(R.string.rs) + "" + productList.getTotalAmt());



        if (productList.getItems().size() > 0) {


            list_rec.setAdapter(rec);
            int finalPrice=0;
            if(productList.getTax().size()>0)
            {
                total_price.setText(this.getResources().getString(R.string.rs)+""+productList.getTax().get(0).getNetAmt());
            }
            toPay.setText("Paid Amount");
            final_prize.setText(this.getResources().getString(R.string.rs)+""+(int)Double.parseDouble(productList.getTotalAmt()));
            pay_now.setVisibility(View.GONE);
        }

        if (productList.getAppliedOffer()!= null) {

            String type = "" ;
            if (productList.getAppliedOffer().get(0).getValueType().startsWith("FL")) {
                type = "Rs";
                offerLay.setVisibility(View.VISIBLE);
                offer_discount.setText(this.getResources().getString(R.string.rs)+productList.getAppliedOffer().get(0).getValue() );

            } else if (productList.getAppliedOffer().get(0).getValueType().startsWith("PE")) {
                type = "%";
                offerLay.setVisibility(View.VISIBLE);
                offer_discount.setText(productList.getAppliedOffer().get(0).getValue()+""+type );
            }

        }

        if (productList.getTax().size() > 0) {
            gst_lay.setVisibility(View.VISIBLE);
            gst.setText(""+(Integer.parseInt(productList.getTax().get(0).getCgst())+Integer.parseInt(productList.getTax().get(0).getSgst()))+"%");
        }

        }

    }
