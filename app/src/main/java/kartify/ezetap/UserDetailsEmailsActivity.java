package kartify.ezetap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.wagonfly.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify.ezetap.printer.EzeTapActivity;
import kartify_base.BaseActivity;
import kartify_base.PostData;

public class UserDetailsEmailsActivity extends BaseActivity
{

    @BindView(R.id.input_email)EditText input_email;
    @BindView(R.id.input_layout_email)TextInputLayout input_layout_email;

    @BindView(R.id.input_name)EditText input_name;
    @BindView(R.id.input_layout_name)TextInputLayout input_layout_name;


    @BindView(R.id.btn_signup)Button btn_signup;

   String orderId,total_amount,tax,RFIDData;
    ArrayList<Product_Pojo> listBen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_email_mobile_xml);
        ButterKnife.bind(this);

        Intent myData = getIntent();
        if (myData != null) {
            orderId = myData.getStringExtra("orderId");
            listBen= ( ArrayList<Product_Pojo>)  myData.getSerializableExtra("array");
            total_amount = myData.getStringExtra("total_amount");
            RFIDData = myData.getStringExtra("RFIDData");
            tax = myData.getStringExtra("tax");
        }

        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input_layout_email.setError(null);
                input_layout_email.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        input_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input_layout_name.setError(null);
                input_layout_name.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        customFontsChecks(btn_signup);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    if (input_email.getText().length() > 0)
                    {
                        if (isValidEmail(input_email.getText()) ) {
                        }
                        else
                            {
                            input_layout_email.setError("Please enter a valid email id!");
                            return;
                        }
                    }
                    else
                        {
                        input_layout_email.setError("Please enter a valid !");
                        return;
                    }
                    if (input_name.getText().length() > 9) {

                    } else {
                        input_layout_name.setErrorEnabled(true);
                        input_layout_name.setError("Please enter a valid mobile number");
                        return;
                    }
                    customerCreate(input_email.getText().toString(),input_name.getText().toString());

                } catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid data!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void customerCreate(final String email, final String name)
    {
        // TODO: Implement this method to send
        // token to your app server.
        showProgressDialog();
        JSONObject table = new JSONObject();
        try {

            table.put("name", "WROGN");
            table.put("contact", ""+name);
            table.put("email", ""+email);
            table.put("id", ""+orderId);


        } catch (JSONException e) {

        }
        PostData.call( getApplicationContext(),table, CUSTOMER_CREATE, new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted()
            {

            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                hideProgressDialog();
            }

            @Override
            public void requestCompleted(JSONObject message) {
                hideProgressDialog();

                try {
                    Log.d("LOGS VALUESS", "VALUESS-->" + message.toString());
                    String status= message.getString("status");
                    String customer_id= message.getString("id");
                    if(status.equalsIgnoreCase("true"))
                    {
                        openNextScreen(email,name,customer_id);
                    }

                } catch (Exception e) {

                }

            }
        });
    }


    void openNextScreen(String email, String contact,String customer_id)
    {
        Intent myInt = new Intent(this, EzeTapActivity.class);
       myInt.putExtra("total_amount",total_amount);
        myInt.putExtra("tax",tax);
        //orderID= ID
        myInt.putExtra("orderId",orderId);
        myInt.putExtra("email",email);
        myInt.putExtra("contact",contact);
        myInt.putExtra("RFIDData",RFIDData);
        myInt.putExtra("customer_id",customer_id);
        Bundle bun= new Bundle();
        bun.putSerializable("array",listBen);
        myInt.putExtras(bun);
        startActivity(myInt);
    }






}
