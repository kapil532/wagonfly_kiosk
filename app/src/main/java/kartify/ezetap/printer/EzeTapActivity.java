package kartify.ezetap.printer;

import com.ezetap.sdk.EzeConstants;
import com.ezetap.sdk.EzetapApiConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eze.api.EzeAPI;
import com.wagonfly.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import kartify.ezetap.Product_Pojo;
import kartify_base.BaseActivity;


public class EzeTapActivity extends BaseActivity {
    /**
     * The response is sent back to your activity with a result code and request
     * code based
     */
    private final int REQUEST_CODE_INITIALIZE = 10001;
    private final int REQUEST_CODE_PREPARE = 10002;
    private final int REQUEST_CODE_SALE_TXN = 10006;
    private final int REQUEST_CODE_CLOSE = 10014;


    /**
     * The Base64 Image bitmap string for attach e-signature
     */
    private ImageView img;
    /**
     * unique ID for a transaction in EzeTap EMI Id associated with the
     * transaction
     */
    private String strTxnId = "", rrNumber = "", emiID = "";
    /**
     * Error message
     */
    private String mandatoryErrMsg = "Please fill up mandatory params.";
    Button pleaseTryAgain, cancel;

    String orderId, total_amount, tax, email, contact, customer_id, RFIDData;
    ArrayList<Product_Pojo> listBen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eze_tap_activity_xml);

        Intent myData = getIntent();
        if (myData != null) {
            orderId = myData.getStringExtra("orderId");
            listBen = (ArrayList<Product_Pojo>) myData.getSerializableExtra("array");
            total_amount = myData.getStringExtra("total_amount");
            tax = myData.getStringExtra("tax");
            email = myData.getStringExtra("email");
            contact = myData.getStringExtra("contact");
            RFIDData = myData.getStringExtra("RFIDData");
            customer_id = myData.getStringExtra("customer_id");
        }

        pleaseTryAgain = (Button) findViewById(R.id.pleaseTryAgain);
        pleaseTryAgain.setVisibility(View.GONE);
        pleaseTryAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doInitializeEzeTap();
                pleaseTryAgain.setVisibility(View.GONE);
            }
        });
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doCloseEzetap();
                finish();
            }
        });
        customFontsChecks(pleaseTryAgain);
        customFontsChecks(cancel);
        doInitializeEzeTap();
    }


/*
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnInitialize:

			break;
		case R.id.btnPrepare:
			doPrepareDeviceEzeTap();
			break;

		case R.id.btnSale:
		    doSaleTxn();
			break;

		case R.id.btnClose:
			doCloseEzetap();
			break;
		default:
			break;
		}
	}*/

    /**
     * invoke to initialize the SDK with the merchant key and the device (card
     * reader) with bank keys
     */
    private void doInitializeEzeTap() {
        JSONObject jsonRequest = new JSONObject();
        {
            try {
                jsonRequest.put("demoAppKey", "1dec30f1-9585-419b-bb26-0a45ead32f86");
                jsonRequest.put("prodAppKey", "0967a9b2-0d96-4c8c-b049-cf4707124b34");
                jsonRequest.put("merchantName", "WAGON_FLY_443885");
                jsonRequest.put("userName", "8390400740");
                jsonRequest.put("currencyCode", "INR");
                jsonRequest.put("appMode", "PROD");
                jsonRequest.put("captureSignature", "true");
                jsonRequest.put("prepareDevice", "false");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        EzeAPI.initialize(this, REQUEST_CODE_INITIALIZE, jsonRequest);
    }

    /**
     * optional mechanism to prepare a device for card transactions
     */
    private void doPrepareDeviceEzeTap() {
        EzeAPI.prepareDevice(this, REQUEST_CODE_PREPARE);
    }


    /**
     * Take credit card transactions for Visa, Mastercard and Rupay. Debit card
     * transactions for Indian banks. Ability to perform EMI option.
     */
    private void doSaleTxn() {
        JSONObject jsonRequest = new JSONObject();
        JSONObject jsonCustomer = new JSONObject();
        JSONObject jsonOptionalParams = new JSONObject();
        JSONObject jsonReferences = new JSONObject();
        try {
            jsonRequest.put("amount", "1.00");
            jsonRequest.put("mode", "SALE");
            jsonReferences.put("reference1", "" + orderId);
            jsonCustomer.put("name", "");
            jsonCustomer.put("mobileNo", "" + contact);
            jsonCustomer.put("email", "" + email);
            jsonOptionalParams.put("references", jsonReferences);
            jsonOptionalParams.put("customer", jsonCustomer);
            jsonRequest.put("options", jsonOptionalParams);
        } catch (Exception e) {

        }

        EzeAPI.cardTransaction(this, REQUEST_CODE_SALE_TXN, jsonRequest);
    }


    /**
     * closes the connection with Ezetap server and shut down gracefully
     */
    private void doCloseEzetap() {
        EzeAPI.close(this, REQUEST_CODE_CLOSE);
    }

    /**
     * @param message message for Toast
     */
    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    String responseTx="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //	Log.d("SampleAppLogs", "requestCode = " + requestCode + "resultCode = " + resultCode);
        try {
            if (intent != null && intent.hasExtra("response")) {
                //Toast.makeText(this, intent.getStringExtra("response"), Toast.LENGTH_LONG).show();
                Log.d("SampleAppLogs", intent.getStringExtra("response"));
            }
            switch (requestCode) {

                case REQUEST_CODE_INITIALIZE:
                    if (resultCode == RESULT_OK) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("result");
                        Log.e("response", "response-->" + response);
                        doPrepareDeviceEzeTap();
                    } else if (resultCode == RESULT_CANCELED) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("error");
                        String errorCode = response.getString("code");
                        String errorMessage = response.getString("message");
                        Log.e("response", "response-e->" + errorMessage);
                        pleaseTryAgain.setVisibility(View.VISIBLE);
                    }
                    break;
                case REQUEST_CODE_SALE_TXN:

                    if (resultCode == RESULT_OK) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("result");
                        response = response.getJSONObject("txn");
                        strTxnId = response.getString("txnId");
                        emiID = response.getString("emiId");
                        rrNumber = response.getString("rrNumber");
                        Log.e("response", "response-PAse->" + response.toString());
                        responseTx= response.toString();
                        openNextScreen();
                    } else if (resultCode == RESULT_CANCELED) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("error");
                        String errorCode = response.getString("code");
                        String errorMessage = response.getString("message");
                        Log.e("response", "response-se->" + errorMessage);
                        pleaseTryAgain.setVisibility(View.VISIBLE);
                    }

                    break;
                case REQUEST_CODE_PREPARE:
                    if (resultCode == RESULT_OK) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("result");
                        Log.e("response", "response-REe->" + response);
                        doSaleTxn();
                    } else if (resultCode == RESULT_CANCELED) {
                        JSONObject response = new JSONObject(intent.getStringExtra("response"));
                        response = response.getJSONObject("error");
                        String errorCode = response.getString("code");
                        String errorMessage = response.getString("message");
                        pleaseTryAgain.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void pleaseTryAgain() {

    }

    /**
     * @return transaction id is valid
     */
    private boolean isTransactionIdValid() {
        if (strTxnId == null)
            return false;
        else
            return true;
    }


    //Create Payment
    //Deactivate RFID


    void openNextScreen() {
        Intent myInt = new Intent(this, SuccessActivity.class);
        myInt.putExtra("total_amount", total_amount);
        myInt.putExtra("tax", tax);
        myInt.putExtra("orderId", orderId);
        myInt.putExtra("txnId", strTxnId);
        myInt.putExtra("rrNumber", rrNumber);
        myInt.putExtra("email", email);
        myInt.putExtra("RFIDData", RFIDData);
        myInt.putExtra("responseTx", responseTx);
        myInt.putExtra("contact", contact);
        myInt.putExtra("customer_id", customer_id);
        Bundle bun = new Bundle();
        bun.putSerializable("array", listBen);
        myInt.putExtras(bun);
        startActivity(myInt);
        finish();
    }


    /**
     * Use this operation to print a bitmap image
     */


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}