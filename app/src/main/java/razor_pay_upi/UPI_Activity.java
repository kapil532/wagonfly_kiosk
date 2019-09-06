package razor_pay_upi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.razorpay.Razorpay;
import com.wagonfly.R;

import org.json.JSONObject;

import kartify_base.BaseActivity;

public class UPI_Activity extends BaseActivity {
    PaymentMethods paymentMethods;
    static PostPaymentListner paymentListner_;
    Razorpay razorpay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upi_activity_screen);
        intialize(this);
    }



    private void intialize(final Activity activity)
    {
        razorpay = new Razorpay(activity) {

            public void paymentMethodsCallback(String result) {
                Log.d("RESULSTSUPPORT","SUPPRTTTT"+result.toString());
                paymentMethods = new PaymentMethods(result);
                //addView();
            }


            public void onSuccess(String razorpay_payment_id) {
//                Toast.makeText(activity, "Payment Successful: " + razorpay_payment_id, Toast.LENGTH_SHORT).show();

                resetPayment();
                paymentListner_.paymentCompleted(razorpay_payment_id);
                finish();
            }


            public void onError(int code, String response) {
                resetPayment();
                JSONObject json;
                String desc = "";
                try {
                    json = new JSONObject(response);
                    JSONObject jsov = json.getJSONObject("error");
                    desc = jsov.getString("description");
                } catch (Exception e) {

                }

                paymentListner_.paymentFailed(desc);
               // showAlertMessage(desc);
            }
        };
        razorpay.getPaymentMethods();
    }
    public void resetPayment() {
      /*  inPayment = false;
        webview.setVisibility(View.GONE);
        methodsLayout.setVisibility(View.VISIBLE);*/
    }



    public interface PostPaymentListner
    {
        public void paymentStarted();

        public void paymentCompleted(String message);

        public void paymentFailed(String message);
    }


}
