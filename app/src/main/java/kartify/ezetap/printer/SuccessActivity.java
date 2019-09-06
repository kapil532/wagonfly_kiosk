package kartify.ezetap.printer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.wagonfly.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kartify.ezetap.Product_Pojo;
import kartify.ezetap.SplashScreenForRfid;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;

public class SuccessActivity extends BaseActivity {
    //    CheckView  check;
    Handler my = new Handler();
    String orderId, total_amount, tax, email, contact, customer_id, txnId, rrNumber, RFIDData, responseTx;
    ArrayList<Product_Pojo> listBen;
    Button btn_feedback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_activity);


        btn_feedback = (Button) findViewById(R.id.btn_feedback);
        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextScreen();
            }
        });
        Intent myData = getIntent();
        if (myData != null) {
            orderId = myData.getStringExtra("orderId");
            listBen = (ArrayList<Product_Pojo>) myData.getSerializableExtra("array");
            total_amount = myData.getStringExtra("total_amount");
            tax = myData.getStringExtra("tax");
            RFIDData = myData.getStringExtra("RFIDData");
            responseTx = myData.getStringExtra("responseTx");
            email = myData.getStringExtra("email");
            txnId = myData.getStringExtra("txnId");
            rrNumber = myData.getStringExtra("rrNumber");
            contact = myData.getStringExtra("contact");
            customer_id = myData.getStringExtra("customer_id");
        }
//        check = (CheckView) findViewById(R.id.check);
//        check.check();
        createPayment();
        //  my.postDelayed(run,2000);

    }








   /* Runnable run = new Runnable()
    {
        @Override
        public void run() {
           openNextScreen();
        }
    };*/


    //Create Payment


    void openNextScreen() {
        Intent myInt = new Intent(this, SplashScreenForRfid.class);
      /*  myInt.putExtra("total_amount",total_amount);
        myInt.putExtra("tax",tax);
        myInt.putExtra("orderId",orderId);
        myInt.putExtra("email",email);
        myInt.putExtra("contact",contact);
        myInt.putExtra("customer_id",customer_id);
        Bundle bun= new Bundle();
        bun.putSerializable("array",listBen);
        myInt.putExtras(bun);*/
        startActivity(myInt);
        finish();
    }


    public void createPayment() {
        // TODO: Implement this method to send
        // token to your app server.
        JSONObject table = new JSONObject();
        try {


            table.put("id", "" + orderId);
            table.put("payment", new JSONObject(responseTx));
            table.put("email", "" + email);
            table.put("contact", "" + contact);


        } catch (JSONException e) {

        }
        PostData.call(getApplicationContext(), table, CREATE_PAYMENT + CommonClass.returnGenericData(STORE_ID, STORE_ID_PREF, getApplicationContext()), new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted() {

            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "PAYMENT FAIL", Toast.LENGTH_LONG).show();
                getDeactivateRfids();
            }

            @Override
            public void requestCompleted(JSONObject message) {

                try {
                    Log.d("LOGS VALUESS", "VALUESS-->" + message.toString());

                    Toast.makeText(getApplicationContext(), "PAYMENT DONE", Toast.LENGTH_LONG).show();
                    getDeactivateRfids();
                   /* String status= message.getString("status");
                    if(status.equalsIgnoreCase("true"))
                    {
                        getDeactivateRfids();
                    }
*/
                } catch (Exception e) {

                }

            }
        });
    }


    void getDeactivateRfids() {
        JSONObject table = new JSONObject();
        Log.e("LINK", "LINK-->" + DEACTIVATE_RFIDS + RFIDData);
        PostData.calla(table, DEACTIVATE_RFIDS + RFIDData, new PostData.PostCommentResponseListener() {

            @Override
            public void requestStarted() {
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "DEACTIVATE_RFIDS FAIL", Toast.LENGTH_LONG).show();
               // openNextScreen();
            }

            @Override
            public void requestCompleted(String message) {

                try {
                    Toast.makeText(getApplicationContext(), "DEACTIVATE_RFIDS SUCCESS", Toast.LENGTH_LONG).show();
                   // openNextScreen();
                } catch (Exception e) {

                }


            }
        });


    }
}
