package kartify_base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kapil on 4/20/17.
 */

public class NetworkClass implements NetworkUrls,ConstantValues
{


    final String getPointDetails = "api/v3/user/getpointsDetails";
    final String getRefpoints = "api/v3/user/getRefpoints";
    public static String checkBookingExist = "api/v3/booking/checkBookingExist";
    public static String byGeolocation = "api/v3/booking/byGeolocation";
    public static String getBookingsHistoryByUserId = "api/v3/booking/getBookingsHistoryByUserId";
    public static String showSummonButton = "api/v3/prime/showsummonbuttonforUser";
    public static String myCarBring = "api/v3/prime/bringMyCar";
    public static String generateSubscriptionId = "api/v3/prime/generateSubscriptionId";
    public static String primeAdd = "api/v3/prime/add";
    public static String showsummonetaforUser = "api/v3/prime/showsummonetaforUser";
    public static String showprimepasses = "api/v3/prime/showprimepasses";


    String kart_key = "";
    String isPrimeValues = "";
    String isDailyValues = "";
    String passType = "";

    Activity a;
    Context ctx;
    String uid = "";
    String token = "";
    String userName = "";
    String email = "";
    String mobile = "";
    String guest_id = "";

    public NetworkClass(Context ctx)
    {

      /*  this.ctx = ctx;
        uid = CommonClass.returnGenericData("udid", "UDID", ctx);
        kart_key = CommonClass.returnGenericData("divrt_user_authtoken", "USER", ctx);
        userName = CommonClass.returnGenericData("name", "name", ctx);
        email = CommonClass.returnGenericData("email", "email", ctx);
        mobile = CommonClass.returnGenericData("mobile_number", "mobile_number", ctx);
        isPrimeValues= CommonClass.returnGenericData("DIVRT_ZONEID","DIVRT_ZONEID_PREF",ctx);
        isDailyValues= CommonClass.returnGenericData("DIVRT_THEME_DAILY","DIVRT_THEME_DAILY_PREF",ctx);
        passType=  CommonClass.returnGenericData("DIVRT_THEME","DIVRT_THEME_PREF",ctx);
*/
        this.ctx = ctx;
        token = CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, ctx);
        userName = CommonClass.returnGenericData(NAME, NAME_PREF, ctx);
        email = CommonClass.returnGenericData(EMAIL, EMAIL_PREF, ctx);
        mobile = CommonClass.returnGenericData(MOBILE, MOBILE_PREF, ctx);
        guest_id = CommonClass.returnGenericData(GUEST_ID, GUEST_ID_PREF, ctx);
    }


    public String getKartKey() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getKartUid() {
        return guest_id;
    }

    public String getUserName() {
        return userName;
    }


    public boolean getPrime() {
        if (isPrimeValues.length() > 0) {
            return true;
        } else
        {
            return false;
        }
    }
    public String getPaasType() {
        return passType;
    }


    public boolean getDaily() {
        if (isDailyValues.length() > 0) {
            return true;
        } else
        {
            return false;
        }
    }
    public void grtPoints(final TextView tv) {

        // TODO: Implement this method to send token to your app server.
        JSONObject table = new JSONObject();

        try {
            table.put("uid", uid);
            table.put("divrt", kart_key);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        PostData.call(ctx,table, CommonClass.FINAL_URL + getRefpoints, new PostData.PostCommentJsonResponseListener() {
            @Override
            public void requestStarted() {
                tv.setText("Loading..");
            }

            @Override
            public void requestCompleted(JSONObject message) {
                try {
                    String messStri = message.getString("status");
                    if (messStri.equalsIgnoreCase("true")) {
                        String messStria = message.getString("message");
                        JSONObject jso = new JSONObject(messStria);
                        tv.setText("" + jso.getString("points"));

                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void requestEndedWithError(VolleyError error) {

            }
        });
    }



    public void getPrimeDetails(final TextView user_mail_prime)
    { 
        // TODO: Implement this method to send token to your app server.
        JSONObject table = new JSONObject();

        try {
            table.put("uid", uid);
            table.put("divrt", kart_key);


        } catch (JSONException e) {

        }
        PostData.call(ctx,table, CommonClass.FINAL_URL + getPointDetails, new PostData.PostCommentJsonResponseListener() {

            @Override
            public void requestStarted()
            {

            }

            @Override
            public void requestEndedWithError(VolleyError error) {

            }

            @Override
            public void requestCompleted(JSONObject message) {

                try
                {
                    Log.d("LOGS VALUESS","VALUESS-->"+message.toString());
                    String s = message.getString("status");
                    if (s.equalsIgnoreCase("true")) {

                        CommonClass.clearData("FIRSTTIMEPRIME", "FIRSTTIMEPRIME_REF", ctx);
                        JSONObject json = message.getJSONObject("message");
                        String isPrime = json.getString("isprime");

                        if (isPrime.equalsIgnoreCase("1"))
                        {

                            user_mail_prime.setVisibility(View.VISIBLE);

                            JSONObject jsonPrime = json.getJSONObject("prime");

                            String zoneId=jsonPrime.getString("zid");

                            CommonClass.saveGenericData(zoneId,"DIVRT_ZONEID","DIVRT_ZONEID_PREF",ctx);
                            CommonClass.saveGenericData("monthy", "DIVRT_THEME", "DIVRT_THEME_PREF", ctx);
                            CommonClass.saveGenericData(jsonPrime.toString(), "DIVRT_PRIME_DETAILS", "DIVRT_PRIME_DETAILSPREF", ctx);

                        }else

                            if(json.has("dailyPassDetails"))
                            {
                                CommonClass.saveGenericData("daily","DIVRT_THEME","DIVRT_THEME_PREF",ctx);
                                CommonClass.saveGenericData("12","DIVRT_THEME_DAILY","DIVRT_THEME_DAILY_PREF",ctx);
                                JSONArray jsonArray = json.getJSONArray("dailyPassDetails");
                                JSONObject jsonObj= jsonArray.getJSONObject(0);
                                CommonClass.saveGenericData(jsonObj.getString("zid"),"DIVRT_ZONEID","DIVRT_ZONEID_PREF",ctx);
                                CommonClass.saveGenericData(jsonObj.toString(),"DIVRT_PRIME_DETAILS","DIVRT_PRIME_DETAILSPREF",ctx);
                            }


                       else {
                            CommonClass.clearData("DIVRT_THEME","DIVRT_THEME_PREF",ctx);
                            CommonClass.clearData("DIVRT_ZONEID","DIVRT_ZONEID_PREF",ctx);
                            CommonClass.clearData("DIVRT_THEME_DAILY","DIVRT_THEME_DAILY_PREF",ctx);
                        }

                    } else {

                    }
                } catch (Exception e) {

                }


            }
        });
    }









    static String llaS = "";
    static String lloS = "";


    public static String messageToShow = "";



    private JSONObject contacts;
    String zoneid = "";

    public void showAlertOne(String message)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick (DialogInterface dialog,int which)
            {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private long startTime = 0L;
    private android.os.Handler myHandler = new android.os.Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;



    private Runnable updateTimerMethod = new Runnable()
    {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
                  seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);

            myHandler.postDelayed(this, 0);
        }

    };




}
