package wag_checkout;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.apply_coupon.CouponApply;
import com.facebook.shimmer.ShimmerFrameLayout;
/*import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;*/
import com.wagonfly.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify.adapter.ReceiptListAdapter;
import kartify.model.Beneficiary;
import kartify.model.MallName;
import kartify.sql.DatabaseHelper;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;
import razor_pay_upi.UPI_Activity;
import wagonfly_screens.QrCodeScreen;

/**
 * Created by Kapil Katiyar on 11/26/2017.
 */

public class KartifyCheckoutScreen extends BaseActivity /*implements PaymentResultListener */{

    TextView date;
    TextView time;
    ListView list_rec;
    LinearLayout list_layout;
    TextView total_price;
    TextView final_prize;
    TextView gst;
    TextView packaging_charge,offer_discount;
    Button pay_now;
    int total_quantity = 0;
    RelativeLayout offerLay;
    RelativeLayout gst_lay;
    MallName mallName;
    int gst_for_bill = 0;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmer_view_container;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.karti_checkout_screen);

        ButterKnife.bind(this);
       // Checkout.preload(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView) findViewById(R.id.title);

        final_prize = (TextView) findViewById(R.id.final_prize);
        offerLay = (RelativeLayout) findViewById(R.id.offerLay);
        offer_discount = (TextView) findViewById(R.id.offer_discount);
        gst_lay = (RelativeLayout) findViewById(R.id.gst_lay);
        gst = (TextView) findViewById(R.id.gst);


        packaging_charge = (TextView) findViewById(R.id.packaging_charge);
        packaging_charge.setText(this.getResources().getString(R.string.rs) + "5");
        title.setText("Checkout");

        RelativeLayout apply = (RelativeLayout) findViewById(R.id.apply);
        apply.setVisibility(View.GONE);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent apply = new Intent(KartifyCheckoutScreen.this, UPI_Activity.class);
                startActivity(apply);
            }
        });


        Intent myData = getIntent();
        if (myData != null) {
            mallName = (MallName) myData.getSerializableExtra("mall_value");
            setDataForMall(mallName);
//            gst_for_bill = Integer.parseInt(mallName.getGst());
//            gst.setText(gst_for_bill + "%");
            if (mallName.getOffers() != null) {
                // mallName.getOffers().get(0).getOfferValues().get(0).
            }

        }
        list_layout = (LinearLayout) findViewById(R.id.list_layout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });
        list_rec = (ListView) findViewById(R.id.list_rec);
        total_price = (TextView) findViewById(R.id.total_price);
        pay_now = (Button) findViewById(R.id.pay_now);
        customFontsBold(pay_now);

        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderPojoArrayList.size()>0)
                {
//                    startPayment((int)Double.parseDouble(orderPojoArrayList.get(0).getTotalAmt()));
                    Intent apply = new Intent(KartifyCheckoutScreen.this, UPI_Activity.class);
                    startActivity(apply);
                }

//                pay_now.setIndeterminateProgressMode(true);
               // createUserOrder();
            }
        });

        pay_now.setVisibility(View.GONE);
        updateList();
        //increaseHeight();
    }

    ReceiptListAdapter rec;
    int ii;
    int total_payment = 0;
    String fullData = "";
    JSONArray ja = new JSONArray();
    ArrayList<Beneficiary> list;

    void startShimmer(int id)
    {
        switch (id)
        {
            case 0:
                shimmer_view_container.setVisibility(View.VISIBLE);
                shimmer_view_container.startShimmerAnimation();
                list_layout.setVisibility(View.GONE);
                break;
            case 1:
                shimmer_view_container.stopShimmerAnimation();
                shimmer_view_container.setVisibility(View.GONE);
                list_layout.setVisibility(View.VISIBLE);
                break;
        }
    }
    void updateList() {

        startShimmer(0);
        DatabaseHelper db = new DatabaseHelper(this);
        list = (ArrayList<Beneficiary>) db.getAllBeneficiary();
        ii = 0;
        if (list.size() > 0)
        {
            CommonClass.saveGenericData("" + list.size(), "TOTAL_ITEMS", "TOTAL_ITEMS_PREF", this);
            for (int i = 0; i < list.size(); i++)
            {

                JSONObject jo = new JSONObject();
                try {
                    jo.put("product_id", list.get(i).getId());
                    jo.put("price", list.get(i).getS_price());
                    jo.put("quantity", list.get(i).getItem_quantity());
//
                } catch (JSONException e) {
                }
                ja.put(jo);
            }
            createOrder();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    void parseDataGetOrderIdList() {

    }
    //Razor pay


   /* public void startPayment(int totalPrice) {
        *//*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         *//*
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setImage(R.drawable.new_wagonfly_logo);
        Log.d("EEEEE", "EEEEE" +(totalPrice) + CommonClass.returnGenericData("email", "email", this));
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Wagonfly  Pvt Ltd");
            options.put("description", "order" + System.currentTimeMillis());
            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "" + (totalPrice) * 100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "" + CommonClass.returnGenericData(EMAIL, EMAIL_PREF, this));
            preFill.put("contact", "" + CommonClass.returnGenericData(MOBILE, MOBILE_PREF, this));

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        try {
            if(orderPojoArrayList.size()>0)
            {
                paymentAdd(s,orderPojoArrayList.get(0).getOrderListIdl(),orderPojoArrayList.get(0).getCode(),orderPojoArrayList.get(0).getTotalAmt());
            }

          //  Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    @Override
    public void onPaymentError(int i, String s) {

        try {

           // Log.d("", "Payment failed" + i + " " + s);
            Toast.makeText(this, "Payment cancelled" *//*+ i + " " + s*//*, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }
*/



    String paymentId = "";

    void paymentAdd(String paymentId,String orderId,String code,String totalAmount)
    {
        showProgressDialog();
        JSONObject table = new JSONObject();
        try {
            table.put("guestId", "" + network.getKartUid());
            table.put("transactionId", "" + paymentId);
            table.put("code", ""+code);
            table.put("orderListId", "" +orderId);
            table.put("amount",(int)Double.parseDouble(totalAmount)*100);


        } catch (JSONException e) {

        }
        PostData.postData(this, table, PAYMENT_CHARGE, new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted() {

            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                hideProgressDialog();
                Log.e("error","error"+error.getMessage());
            }

            @Override
            public void requestCompleted(JSONObject message) {
                hideProgressDialog();
                Log.e("error","error"+message.toString());
                try {
                    JSONObject jsonObject = message.getJSONObject("payment");
                    String orderID = jsonObject.getString("orderListId");
                    CommonClass.saveGenericData(orderID, ORDER_LIST_ID, ORDER_LIST_ID_PREF, KartifyCheckoutScreen.this);

                    if (orderID.length() > 4) {


                        CommonClass.saveGenericData("" + ii, "TOTAL_PRICE", "TOTAL_PRICE_PREF", KartifyCheckoutScreen.this);
                        CommonClass.saveGenericData("" + total_quantity, "TOTAL_ITEMS", "TOTAL_ITEMS_PREF", KartifyCheckoutScreen.this);

                        Intent order = new Intent(KartifyCheckoutScreen.this, QrCodeScreen.class);
                        startActivity(order);
                        finish();
                    }

                } catch (Exception e) {

                }


            }
        });


    }
    void createOrder() {

        JSONObject table = new JSONObject();

        try {
            table.put("storeId", "" + CommonClass.returnGenericData("MALL_ID_KEY", "MALL_ID_KEY_PREF", this));
            table.put("guestId", "" + network.getKartUid());
            table.put("productList", ja);


        } catch (JSONException e) {
        }
        PostData.call(this, table, CREATE_ORDER, new PostData.PostCommentJsonResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(JSONObject message) {
                Log.e("MESSAGE","MESSAGE"+message.toString());
                startShimmer(1);
                if(message.toString().length()>0)
                {
                    parseJsonForOrderList(message);
                }
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                startShimmer(1);
            }
        });
    }









ArrayList<OrderPojo> orderPojoArrayList;
ArrayList<OrderPojo.appliedOffer> orderPojoappliedOfferArrayList;
ArrayList<OrderPojo.items> orderPojoitemsArrayList;
ArrayList<OrderPojo.Tax> orderPojoTaxArrayList;
    void parseJsonForOrderList(JSONObject message) {
        orderPojoArrayList = new ArrayList<>();
        orderPojoappliedOfferArrayList = new ArrayList<>();
        orderPojoitemsArrayList = new ArrayList<>();
        orderPojoTaxArrayList = new ArrayList<>();

        try {
            JSONObject orderLisrt = message.getJSONObject("orderList");
            OrderPojo orderListPojo = new OrderPojo();
            orderListPojo.setOrderListIdl(orderLisrt.getString("orderListId"));
            orderListPojo.setCode(orderLisrt.getString("code"));
            orderListPojo.setQty(orderLisrt.getString("qty"));
            orderListPojo.setTotalAmt(orderLisrt.getString("totalAmt"));
            JSONObject productList = orderLisrt.getJSONObject("productList");
            try {
                JSONObject appliedOffer = productList.getJSONObject("appliedOffer");


                OrderPojo.appliedOffer appliedOffer1Pojo = orderListPojo.new appliedOffer();
                appliedOffer1Pojo.setAmount(appliedOffer.getString("amount"));
                appliedOffer1Pojo.setValue(appliedOffer.getString("value"));
                appliedOffer1Pojo.setValueType(appliedOffer.getString("valueType"));
                appliedOffer1Pojo.setOfferText(appliedOffer.getString("offerText"));
                appliedOffer1Pojo.setStoreOffersId(appliedOffer.getString("storeOffersId"));
                appliedOffer1Pojo.setOfferApplied(appliedOffer.getString("offerApplied"));
                orderPojoappliedOfferArrayList.add(appliedOffer1Pojo);
                orderListPojo.setAppliedOfferA(orderPojoappliedOfferArrayList);

            } catch (Exception e) {
                Log.d("OFFER IDD", "OFFFEREES" + e.getMessage());
            }

            try {
                JSONObject taxeJsonObject = productList.getJSONObject("tax");
                OrderPojo.Tax taxPojo = orderListPojo.new Tax();
                taxPojo.setGst(taxeJsonObject.getString("gst"));
                taxPojo.setCgst(taxeJsonObject.getString("cgst"));
                taxPojo.setSgst(taxeJsonObject.getString("sgst"));
                taxPojo.setTaxable(taxeJsonObject.getString("taxable"));
                taxPojo.setNetAmt(taxeJsonObject.getString("netAmt"));
                orderPojoTaxArrayList.add(taxPojo);
            } catch (Exception ee) {

            }

            JSONArray itemsArray = productList.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                OrderPojo.items itemPojo = orderListPojo.new items();
                JSONObject itemJsonObject = itemsArray.getJSONObject(i);
                itemPojo.setBar_code(itemJsonObject.getString("bar_code"));
                itemPojo.setProduct_id(itemJsonObject.getString("product_id"));
                itemPojo.setProduct_name(itemJsonObject.getString("product_name"));
                itemPojo.setProduct_description(itemJsonObject.getString("product_description"));
                itemPojo.setProductImage(itemJsonObject.getString("productImage"));
                itemPojo.setBrand(itemJsonObject.getString("brand"));
                itemPojo.setExpiry(itemJsonObject.getString("expiry"));
                itemPojo.setRetail_price(itemJsonObject.getString("retail_price"));
                itemPojo.setQuantity(itemJsonObject.getString("quantity"));
                orderPojoitemsArrayList.add(itemPojo);

            }
            orderListPojo.setItems(orderPojoitemsArrayList);
            orderPojoArrayList.add(orderListPojo);


        } catch (JSONException e) {

            Log.d("Exception", "Exception" + e.getMessage());
        }

        if (orderPojoitemsArrayList.size() > 0) {
            Log.d("Exception", "Exception --->");
            increaseHeight();
            rec = new ReceiptListAdapter(this, orderPojoitemsArrayList);
            list_rec.setAdapter(rec);
            int finalPrice=0;
           if(orderPojoTaxArrayList.size()>0)
           {
               total_price.setText(this.getResources().getString(R.string.rs)+""+orderPojoTaxArrayList.get(0).getNetAmt());
           }

            final_prize.setText(this.getResources().getString(R.string.rs)+""+(int)Double.parseDouble(orderPojoArrayList.get(0).getTotalAmt()));
            pay_now.setVisibility(View.VISIBLE);
        }

        if (orderPojoappliedOfferArrayList.size() > 0) {

            String type = "" ;
            if (orderPojoappliedOfferArrayList.get(0).getValueType().startsWith("FL")) {
                type = "Rs";
                offerLay.setVisibility(View.VISIBLE);
                offer_discount.setText(this.getResources().getString(R.string.rs)+orderPojoappliedOfferArrayList.get(0).getValue() );

            } else if (orderPojoappliedOfferArrayList.get(0).getValueType().startsWith("PE")) {
                type = "%";
                offerLay.setVisibility(View.VISIBLE);
                offer_discount.setText(orderPojoappliedOfferArrayList.get(0).getValue()+""+type );
            }

        }

        if (orderPojoTaxArrayList.size() > 0) {

            gst_lay.setVisibility(View.VISIBLE);
            gst.setText("included");
//            gst_lay.setVisibility(View.VISIBLE);
//            gst.setText(""+(Integer.parseInt(orderPojoTaxArrayList.get(0).getCgst())+Integer.parseInt(orderPojoTaxArrayList.get(0).getSgst()))+"%");

//            gst_lay.setVisibility(View.VISIBLE);
//            gst.setText("included");
        }
        else {
            gst_lay.setVisibility(View.VISIBLE);
            gst.setText("included");
        }

    }



    String orderId = "";

    void getOrderID(String paymentId) {

        showProgressDialog();
        JSONObject table = new JSONObject();

        try {
            table.put("store_id", "" + CommonClass.returnGenericData("MALL_ID_KEY", "MALL_ID_KEY_PREF", this));
            table.put("price", "" + ii);
            table.put("uid", "" + network.getKartUid());
            table.put("payment_id", "" + paymentId);
//            table.put("order_details", "["+fullData+"]");
            table.put("order_details", ja);
            table.put("skey", "" + network.getKartKey());


        } catch (JSONException e) {

        }
        PostData.call(this, table, CREATE_ORDER, new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted() {

            }

            @Override
            public void requestEndedWithError(VolleyError error) {
//                progressbar_id.setVisibility(View.GONE);
                hideProgressDialog();
                Log.d("MESSAGE", "MESS" + error.getMessage());
            }

            @Override
            public void requestCompleted(JSONObject message) {
//                progressbar_id.setVisibility(View.GONE);
                hideProgressDialog();
                try {
                    Log.d("LOGS VALUESS", "VALUESS-->" + message.toString());
                    String s = message.getString("status");
                    if (s.equalsIgnoreCase("true")) {
//                        jsonParser(message.toString());
                        orderId = message.getString("order_id");

                        CommonClass.saveGenericData("" + orderId, "ORDER_ID", "ORDER_ID_PREF", KartifyCheckoutScreen.this);
                        CommonClass.saveGenericData("" + ii, "TOTAL_PRICE", "TOTAL_PRICE_PREF", KartifyCheckoutScreen.this);
                        Intent myIntent = new Intent(KartifyCheckoutScreen.this, QrCodeScreen.class);
                        startActivity(myIntent);

                        finish();

                    } else {
                        Toast.makeText(KartifyCheckoutScreen.this, "Please try again!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }


            }
        });


    }

    void increaseHeight() {
        ViewGroup.LayoutParams params = list_rec.getLayoutParams();
        params.height = list.size() * dpToPx(39);
        ;
        list_rec.setLayoutParams(params);
    }

    String[] offersApply(MallName mallName, int total_value) {
        String arry[]=new String[3];
        String valueType = mallName.getOffers().get(0).getOfferValues().get(0).getValue1Type();
        String valueType2 = mallName.getOffers().get(0).getOfferValues().get(0).getValue2Type();
        String valueType3 = mallName.getOffers().get(0).getOfferValues().get(0).getValue3Type();

        String value = mallName.getOffers().get(0).getOfferValues().get(0).getValue1();
        String value2 = mallName.getOffers().get(0).getOfferValues().get(0).getValue2();
        String value3 = mallName.getOffers().get(0).getOfferValues().get(0).getValue3();

        String option = mallName.getOffers().get(0).getOfferValues().get(0).getOption1();
        String option2 = mallName.getOffers().get(0).getOfferValues().get(0).getOption2();
        String option3 = mallName.getOffers().get(0).getOfferValues().get(0).getOption3();


        int flatValue1=0;
        int perValue1=0;
        int flatValue2=0;
        int perValue2=0;
        int flatValue3=0;
        int perValue3=0;

        if (valueType3.startsWith("FL"))
        {
            flatValue3 =  Integer.parseInt(value3);
            arry[0]="";
            if(total_value>flatValue3)
            {
              total_value=total_value-flatValue3;
                arry[1]="-"+getResources().getString(R.string.rs)+flatValue3;
                arry[2]=""+total_value;
                return arry;
            }
        }
        else if (valueType3.startsWith("PE"))
        {
            arry[0]="%";
            perValue3 =  Integer.parseInt(value3);
            int   optionValue3 =  Integer.parseInt(option3);
            if(total_value>=optionValue3)
            {
               int temV = ((total_value*perValue3)/100);
                 total_value=total_value-temV;
                arry[1]=""+perValue3;
                arry[2]=""+total_value;
                return arry;
            }
        }

        if (valueType2.startsWith("FL"))
        {
            arry[0]="";
             flatValue2 =  Integer.parseInt(value2);
            if(total_value>flatValue2)
            {
                  total_value=total_value-flatValue2;
                arry[1]="-"+getResources().getString(R.string.rs)+flatValue2; arry[2]=""+total_value;
                return arry;
            }

        }
        else if (valueType2.startsWith("PE"))
        {
            arry[0]="%";
             perValue2 =  Integer.parseInt(value2);
           int   optionValue2 =  Integer.parseInt(option2);
            if(total_value>=optionValue2 )
            {
                int temV = ((total_value*perValue2)/100);
                  total_value=total_value-temV;
                arry[1]=""+perValue2;
                arry[2]=""+total_value;
                return arry;
            }
        }


        if (valueType.startsWith("FL"))
        {
            arry[0]="";
            flatValue1 = Integer.parseInt(value);
            if(total_value>flatValue1)
            {
                 total_value=total_value-flatValue1;
                arry[1]="-"+getResources().getString(R.string.rs)+flatValue1; arry[2]=""+total_value;
                return arry;
            }

        } else if (valueType.startsWith("PE"))
        {
            arry[0]="%";
            perValue1 =  Integer.parseInt(value);
            int   optionValue =  Integer.parseInt(option);
            if(total_value>=optionValue )
            {
                int temV = ((total_value*perValue1)/100);
                  total_value=total_value-temV;
                arry[1]=""+perValue1;
                arry[2]=""+total_value;
                return arry;
            }
        }




        return arry;



    }




    int findMax(int x, int y, int z)
    {
        int max = Math.max(x,y);
        if(max>y){ //suppose x is max then compare x with z to find max number
           return max = Math.max(x,z);
        }
        else{ //if y is max then compare y with z to find max number
            return  max = Math.max(y,z);
        }
    }

}
