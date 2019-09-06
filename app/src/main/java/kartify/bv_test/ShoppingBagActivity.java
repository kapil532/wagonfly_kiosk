package kartify.bv_test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wagonfly.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kartify_base.BaseActivity;
import kartify_base.PostData;

/**
 * Created by Kapil Katiyar on 7/20/2018.
 */

public class ShoppingBagActivity extends BaseActivity implements CallBackToDeleteItem
{
    ListView list_;
    TextView total_price_;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);
        list_ = (ListView)findViewById(R.id.list);
        total_price_ = (TextView) findViewById(R.id.total_price);

        getData();


    }

    void getData() {
showProgressDialog();
         String url="https://api.bottleview.com/om/v1/cnsmr/data/cart?consumer_id=cnsmr-22242";
        PostData.getData(this, url, new PostData.PostCommentResponseListener()
        {
            @Override
            public void requestStarted()
            {

            }

            @Override
            public void requestCompleted(String message)
            {
                Log.d("VALUESSS","VALUESS--->"+message.toString());
hideProgressDialog();
                parseJson(message.toString());
            }

            @Override
            public void requestEndedWithError(VolleyError error)
            {
            }
        });
    }


ArrayList<ShoppingBagPojo> list;
    String total_price="";
    void parseJson(String message)
    {
        list =  new ArrayList<>();
        try {
            JSONObject jsonObjectA=new JSONObject(message);
            JSONObject result = jsonObjectA.getJSONObject("result");
            total_price=result.getString("price_with_tax");
            JSONArray cartArray=result.getJSONArray("cart_item");
            for(int i=0;i<cartArray.length();i++)
            {
                ShoppingBagPojo cartPojo= new ShoppingBagPojo();
                JSONObject jsonObject= cartArray.getJSONObject(i);
                cartPojo.setBrand(jsonObject.getString("brand"));
                cartPojo.setTitle(jsonObject.getString("title"));
                cartPojo.setProduct_id(jsonObject.getString("product_id"));
                JSONObject jsonImage = jsonObject.getJSONObject("image_urls");
                cartPojo.setImage_urls(""+jsonImage.getJSONArray("default").get(0));
                cartPojo.setQuantity(jsonObject.getString("quantity"));
                cartPojo.setPrice(jsonObject.getString("price"));

                list.add(cartPojo);

            }
        }
        catch (Exception e)
        {
            Log.d("SIZE","SIZEE-->"+e.getMessage());
        }

        if(list.size()>0)
        {
            ShoppingBagAdapter adapter = new ShoppingBagAdapter(this,list,this);
            list_.setAdapter(adapter);
            total_price_.setText("Total Price         "+getResources().getString(R.string.rs)+""+total_price);
        }
        Log.d("SIZE","SIZEE"+list.size());

    }

    @Override
    public void deletedItem(String productId)
    {
        getData();
    }
}
