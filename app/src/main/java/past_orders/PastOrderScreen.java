package past_orders;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.wagonfly.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify.adapter.BeneficiaryRecyclerAdapter;
import kartify.adapter.RecyclerTouchListener;
import kartify.adapter.SimpleDividerItemDecoration;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;

/**
 * Created by Kapil Katiyar on 11/29/2017.
 */

public class PastOrderScreen extends BaseActivity
{

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.listView)  RecyclerView myList;
    @BindView(R.id.name_fi)  TextView name_fi;
    @BindView(R.id.title)  TextView title;
    @BindView(R.id.message)  TextView message_;
    @BindView(R.id.rounde_image)  ImageView rounde_image;
    @BindView(R.id.progress_bar) RelativeLayout progress_bar;
    @BindView(R.id.shimmer_view_container)ShimmerFrameLayout shimmer_view_container;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_order);

        ButterKnife.bind(this);
        message_.setVisibility(View.GONE);

        rounde_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  invoice = new Intent(PastOrderScreen.this,PastOrder_invoice.class);
                PastOrder_invoice.orider_id="1234";
                startActivity(invoice);
            }
        });


        title.setText("My Orders");
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
        name_fi.setText(CommonClass.returnGenericData("name", "name", this));
        initializeRecycle();
        getData();
    }


    void getData() {
        shimmer_view_container.setVisibility(View.VISIBLE);
        shimmer_view_container.startShimmerAnimation();
        message_.setVisibility(View.GONE);
        JSONObject table = new JSONObject();

        try {
            table.put("uid", "" + network.getKartUid());
        } catch (JSONException e) {

        }

        String url=ORDER+""+network.getKartUid();

        PostData.getData(this, url, new PostData.PostCommentResponseListener() {
            @Override
            public void requestStarted()
            {

            }

            @Override
            public void requestCompleted(String message)
            {
              Log.d("VALUESSS","VALUESS--->"+message);
                shimmer_view_container.stopShimmerAnimation();
                shimmer_view_container.setVisibility(View.GONE);
                parseJason(message);
            }

            @Override
            public void requestEndedWithError(VolleyError error)
            {
                message_.setVisibility(View.VISIBLE);
                message_.setText("No Orders");
                shimmer_view_container.stopShimmerAnimation();
                shimmer_view_container.setVisibility(View.GONE);
                Log.d("VALUESSS","VALUESS--->"+error.getMessage());
                hideProgressDialog();
            }
        });
    }

    ArrayList<PastOrderPojoNew> orderPojoArrayList= new ArrayList<>();


    ArrayList<PastOrderPojoNew> productList;
    ArrayList<PastOrderPojoNew.StorePojo> storeList;
    ArrayList<PastOrderPojoNew.appliedOfferPojo> orderPojoappliedOfferArrayList;
    ArrayList<PastOrderPojoNew.TaxPojo> orderPojoTaxArrayList;
    ArrayList<PastOrderPojoNew.itemsPojo> orderPojoitemsArrayList;


    private void parseJason(String json) {
        orderPojoArrayList = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("orderList");
            for(int i=0;i<jsonArray.length();i++)
            {
                productList = new ArrayList<>();
                storeList= new ArrayList<>();
                orderPojoappliedOfferArrayList= new ArrayList<>();
                orderPojoTaxArrayList= new ArrayList<>();
                orderPojoitemsArrayList= new ArrayList<>();


                PastOrderPojoNew myPojo = new PastOrderPojoNew();

                JSONObject jsonObjectForPojo= jsonArray.getJSONObject(i);
                myPojo.setTotalAmt(jsonObjectForPojo.getString("totalAmt"));
                myPojo.setQty(jsonObjectForPojo.getString("qty"));
                myPojo.setCode(jsonObjectForPojo.getString("code"));


                //Store Data
                JSONObject storeJsonObject = jsonObjectForPojo.getJSONObject("store");
                PastOrderPojoNew.StorePojo storePojo= myPojo.new StorePojo();
                storePojo.setIdx(storeJsonObject.getString("idx"));
                storePojo.setName(storeJsonObject.getString("name"));
                storePojo.setImage(storeJsonObject.getString("image"));
                storeList.add(storePojo);
                myPojo.setStorePojo(storeList);

                //product list
                JSONObject productListObject = jsonObjectForPojo.getJSONObject("productList");
                   //appliedOffer
                try {
                    JSONObject appliedOfferObj = productListObject.getJSONObject("appliedOffer");


                  PastOrderPojoNew.appliedOfferPojo appliedOffer1Pojo = myPojo.new appliedOfferPojo();
                    appliedOffer1Pojo.setAmount(appliedOfferObj.getString("amount"));
                    appliedOffer1Pojo.setValue(appliedOfferObj.getString("value"));
                    appliedOffer1Pojo.setValueType(appliedOfferObj.getString("valueType"));
                    appliedOffer1Pojo.setOfferText(appliedOfferObj.getString("offerText"));
                    appliedOffer1Pojo.setStoreOffersId(appliedOfferObj.getString("storeOffersId"));
                    appliedOffer1Pojo.setOfferApplied(appliedOfferObj.getString("offerApplied"));
                    orderPojoappliedOfferArrayList.add(appliedOffer1Pojo);
                    myPojo.setAppliedOffer(orderPojoappliedOfferArrayList);
                } catch (Exception e) {
                    Log.d("OFFER IDD", "OFFFEREES" + e.getMessage());
                }
                //tax

                try {
                    JSONObject taxeJsonObject = productListObject.getJSONObject("tax");
                    PastOrderPojoNew.TaxPojo taxPojo = myPojo.new TaxPojo();
                    taxPojo.setGst(taxeJsonObject.getString("gst"));
                    taxPojo.setCgst(taxeJsonObject.getString("cgst"));
                    taxPojo.setSgst(taxeJsonObject.getString("sgst"));
                    taxPojo.setTaxable(taxeJsonObject.getString("taxable"));
                    taxPojo.setNetAmt(taxeJsonObject.getString("netAmt"));
                    orderPojoTaxArrayList.add(taxPojo);
                    myPojo.setTax(orderPojoTaxArrayList);
                } catch (Exception ee) {
                    Log.d("OFFER IDD", "OFFFEREES11-->" + ee.getMessage());
                }
               //items

                try {
                    JSONArray itemsArray = productListObject.getJSONArray("items");

                    for (int j = 0; j < itemsArray.length(); j++) {
                        PastOrderPojoNew.itemsPojo itemPojo = myPojo.new itemsPojo();
                        JSONObject itemJsonObject = itemsArray.getJSONObject(j);
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

                    myPojo.setItems(orderPojoitemsArrayList);
                }
                catch (Exception e)
                {
                    Log.d("OFFER IDD", "OFFFEREES-->" + e.getMessage());
                }

                orderPojoArrayList.add(myPojo);
            }


        }
        catch (Exception e)
        {
 Log.d("Exception","EXCEPTION--->"+e.getMessage());
        }

        if (orderPojoArrayList.size() > 0)
        {
            mAdapter= new PastOrderAdapter(this,orderPojoArrayList);
            myList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }
        else
        {
            message_.setVisibility(View.VISIBLE);
            message_.setText("No Orders");
        }

    }


    PastOrderAdapter mAdapter;
    void initializeRecycle() {
        mAdapter = new PastOrderAdapter(this,orderPojoArrayList);
        myList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myList.setLayoutManager(mLayoutManager);
        myList.addItemDecoration(new SimpleDividerItemDecoration(this));
        myList.setItemAnimator(new DefaultItemAnimator());
        myList.setAdapter(mAdapter);
        myList.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //  showDiloge(position);
//                Intent  invoice = new Intent(PastOrderScreen.this,PastOrder_invoice.class);
////
//                PastOrder_invoice.productList=orderPojoArrayList.get(position);
//                startActivity(invoice);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
