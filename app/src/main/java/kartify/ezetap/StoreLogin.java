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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.wagonfly.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;

public class StoreLogin extends BaseActivity {

    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_layout_email)
    TextInputLayout input_layout_email;

    @BindView(R.id.input_name)
    EditText input_name;
    @BindView(R.id.input_layout_name)
    TextInputLayout input_layout_name;


    @BindView(R.id.btn_signup)
    Button btn_signup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_login);
        ButterKnife.bind(this);


        CommonClass.saveGenericData("1", STORE_ID, STORE_ID_PREF, getApplicationContext());
        if(CommonClass.returnGenericData(STORE_ID,STORE_ID_PREF,getApplicationContext()).length()>0)
        {
            Intent myIntent = new Intent(StoreLogin.this,SplashScreenForRfid.class);
            startActivity(myIntent);
            finish();
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


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    if (input_email.getText().length() > 0 && input_name.length() > 0) {
                        storeLogin(input_email.getText().toString(), input_name.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter valid data!", Toast.LENGTH_LONG).show();
                        return;
                    }

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


    public void storeLogin(String name, String pass) {
        // TODO: Implement this method to send
        // token to your app server.
        showProgressDialog();
        JSONObject table = new JSONObject();
        try {

            table.put("name", name);
            table.put("password", pass);


        } catch (JSONException e) {

        }
        PostData.callwithoutCookie(this, table, STORES, new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted() {

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
                    String s = message.getString("status");
                    if (s.equalsIgnoreCase("true")) {
                        JSONObject jsonObject = message.getJSONObject("store");
                        String store_id = jsonObject.getString("id");
                        CommonClass.saveGenericData(store_id, STORE_ID, STORE_ID_PREF, getApplicationContext());

                        Intent myIntent = new Intent(StoreLogin.this,SplashScreenForRfid.class);
                        startActivity(myIntent);
                        finish();
                    }


                } catch (Exception e)
                {
Log.d("Exception   ","Exception E"+e.getMessage());
                }


            }
        });
    }


}
