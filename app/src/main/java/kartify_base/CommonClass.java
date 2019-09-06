package kartify_base;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wagonfly.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonClass implements NetworkUrls,ConstantValues
{


//    public static String paytm_client_id="merchant-divrt-staging";
//    public static String paytm_url="https://accounts-uat.paytm.com/signin/otp";
//    public static String paytm_url_check_balance="https://pguat.paytm.com/oltp/HANDLER_INTERNAL/checkBalance";
//    public static String paytm_url_validate_otp="https://accounts-uat.paytm.com/signin/validate/otp";


    public static String paytm_client_id="merchant-divrt";
    public static String paytm_url="https://accounts.paytm.com/signin/otp";
    public static String paytm_url_check_balance="https://secure.paytm.in/oltp/HANDLER_INTERNAL/checkBalance";
    public static String paytm_url_validate_otp="https://accounts.paytm.com/signin/validate/otp";
    public static String  secret_key="358819af-1993-494d-8211-548c1547bdc4";

         public static String razorPayLive="rzp_live_lp2k2FJSOHx2RD";
         public static String razorPayTest="rzp_test_vq89vGVMWVCH7W";
         public static String razorPayName="DIVRT";


        public static String FONT_WEWORK="WEWORK_FONT";
        public static String FONT_NORMAL="NORMAL_FONT";
        public static String FONT_PRIME="PRIME_FONT";



    private static String acname;
    private static String mobile_no;
    private static String email;

    public static boolean value_selected_by_slider = false;
    public static String selected_time = "";
    public static String selected_date = "";
    public static String selected_time_show = "";
    public static String selected_date_show = "";
    public static String duration_time = "";
    public static double time_difference = 3;

    public static double time_difference_garage = 3;

    public static String[] payments_methods = {"Razor Pay", "Pay pal"};
    public static String date_time_selected = "";
    public static String date_selected = "";
    public static String date_time_to_show = "";

    public static int progressSet2 = 26;
    public static int time_difference_min = 180;


    private boolean live = false;





  /*  public static String MAIN_FINAL_URL="http://api.kartify.co/";
    public static String URL_PAST_HISTORY=MAIN_FINAL_URL+"api/public/index.php/api/v1/order/orderHistory";
    public static String FINAL_URL = MAIN_FINAL_URL+"api/public/index.php/api/v3/user/";
    public static String FINAL_URL_PRODUCT = MAIN_FINAL_URL+"api/public/index.php/api/v1/product/";
    public static String URL_SHOW_ALL_MALL= MAIN_FINAL_URL+"api/public/index.php/api/v1/store/showall";
    public static String URL_PRODUCT_DETAILS= MAIN_FINAL_URL+"api/public/index.php/api/v1/product/productDetails";
    public static String URL_PAYMENT_ADD= MAIN_FINAL_URL+"api/public/index.php/api/v1/payment/add";
    public static String URL_CREATE_ORDER= MAIN_FINAL_URL+"api/public/index.php/api/v1/order/createOrder";
    public static String URL_RFID_BUCKET= MAIN_FINAL_URL+"api/public/index.php/api/v1/order/getrfidOrder";*/

//    http://dev-api.wagonfly.com/sms/requestOtp

//    public static String MAIN_FINAL_URL="http://testapi.wagonfly.com/";
    public static String MAIN_FINAL_URL="http://dev-api.wagonfly.com/";

    public static String URL_GET_OTP=MAIN_FINAL_URL+"sms/requestOtp";
//    public static String URL_VALIDATE_OTP=MAIN_FINAL_URL+"users/validateOTP";
    public static String URL_VALIDATE_OTP=MAIN_FINAL_URL+"guest/authenticate";


    public static String URL_SHOW_ALL_MALL=MAIN_FINAL_URL+"stores";
    public static String URL_PRODUCT_DETAILS=MAIN_FINAL_URL+"products/productDetails";
    public static String URL_CREATE_ORDER_1=MAIN_FINAL_URL+"orders/createOrder1";
    public static String URL_CREATE_ORDER=MAIN_FINAL_URL+"order/createOrder";
    public static String URL_PAYMENT_ADD=MAIN_FINAL_URL+"payment/charge";
    public static String URL_PAST_ORDER_INVOICE=MAIN_FINAL_URL+"orders/getOrderIdDetails";


    public static String URL_PAST_HISTORY=MAIN_FINAL_URL+"orders/getOrderHistory";

    public static String FINAL_URL = MAIN_FINAL_URL+"api/public/index.php/api/v3/user/";

    public static String FINAL_URL_PRODUCT = MAIN_FINAL_URL+"api/public/index.php/api/v1/product/";
//    public static String URL_SHOW_ALL_MALL= MAIN_FINAL_URL+"api/public/index.php/api/v1/store/showall";
//    public static String URL_PRODUCT_DETAILS= MAIN_FINAL_URL+"api/public/index.php/api/v1/product/productDetails";
//    public static String URL_PAYMENT_ADD= MAIN_FINAL_URL+"api/public/index.php/api/v1/payment/add";
//    public static String URL_CREATE_ORDER= MAIN_FINAL_URL+"api/public/index.php/api/v1/order/createOrder";
public static String MAIN_FINAL_URLa="http://testapi.wagonfly.com/";
    public static String URL_RFID_BUCKET= MAIN_FINAL_URLa+"orders/getrfidOrder";


//    http://18.221.161.225/kkart/api/public/index.php/api/v1/product/productDetails

    public boolean isLive()
    {
        return live;
    }





    public static String getDate(int i) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int finaDay = day + i;
        return year + "-" + (month + 1) + "-" + finaDay;
    }

    public static Date getUTCDateFromStringAndTimezone(String inputDate) {
        Date date = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");


        try {
            date = format.parse(inputDate);
        } catch (Exception e) {
            Log.d("TIME ISSUE", "ISSUE" + e.getMessage());
        }
        long msFromEpochGmt = date.getTime();

        Log.d("MILISECOND", "SECONF" + msFromEpochGmt);

        Date datea = new Date(msFromEpochGmt);
        return datea;
    }

    public static String capitalize(String capString)
    {

        try {
            StringBuffer capBuffer = new StringBuffer();
            Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
            while (capMatcher.find()) {
                capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
            }

            return capMatcher.appendTail(capBuffer).toString();
        }
        catch (Exception e)
        {
            return  capString;
        }
    }
    public static Double findExchangeRateAndConvert(String from, String to, int amount) {
        try {
            //Yahoo Finance API
            URL url = new URL("http://finance.yahoo.com/d/quotes.csv?f=l1&s="+ from + to + "=X");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = reader.readLine();
            if (line.length() > 0) {
                return Double.parseDouble(line) * amount;
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static Date getUTCDateFromStringBook(String inputDate, String timeZoneId) {
        Date date = null;

        Log.d("GETTIME ZONEID", "TIME ZONE ID" + inputDate + timeZoneId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        try {
            date = format.parse(inputDate);
        } catch (Exception e) {
            Log.d("TIME ISSUE", "ISSUE" + e.getMessage());
        }
        //Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
        long msFromEpochGmt = date.getTime();

        Log.d("MILISECOND", "SECONF" + msFromEpochGmt);
        Date datea = new Date(msFromEpochGmt);
        return datea;
    }


 /*   public static Date getUTCDateFromStringAndTimezone(String inputDate){
        Date date = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = format.parse(inputDate);
        } catch (Exception e) {
            Log.d("TIME ISSUE", "ISSUE" + e.getMessage());
        }
        //Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
        long msFromEpochGmt = date.getTime();

        Log.d("MILISECOND","SECONF"+msFromEpochGmt);
        //gives you the current offset in ms from GMT at the current date
        //  int offsetFromUTC = TimeZone.getDefault().getOffset(msFromEpochGmt) * (-1);
        //this (-1) forces addition or subtraction whatever is reqd to make UTC
        //create a new calendar in GMT timezone, set to this date and add the offset
        //  Calendar gmtCal = Calendar.getInstance(TimeZone.getDefault());
        // gmtCal.setTime(date);
        // gmtCal.add(Calendar.MILLISECOND, offsetFromUTC);
        Date datea = new Date(msFromEpochGmt);
        return datea;
    }*/

    public static void showRateDialogForRate(final Context context) {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Rate application")
                .setMessage("Please, rate the app at PlayMarket")
                .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context != null) {
                            ////////////////////////////////
                            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            // To count with Play market backstack, After pressing back button,
                            // to taken back to our application, we need to add following flags to intent.
                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            try {
                                context.startActivity(goToMarket);
                            } catch (ActivityNotFoundException e) {
                                context.startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                            }


                        }
                    }
                })
                .setNegativeButton("CANCEL", null);
        builder.show();*/

        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }


    }


    public static void initShareIntent(String type, String subject, String description, Activity ctx) {
        boolean found = false;
        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/jpeg");
        share.setType("text/plain");
        String[] TO = {"support@divrt.co"};
        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = ctx.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_EMAIL, TO);
                    share.putExtra(Intent.EXTRA_SUBJECT, subject);
                    share.putExtra(Intent.EXTRA_TEXT, description);
//                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(myPath)) ); // Optional, just if you wanna share an image.
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return;

            ctx.startActivity(Intent.createChooser(share, "Select"));
        }
    }

    public static void saveGenericData(String data, String key, String prefrenceName, Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(prefrenceName, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(key, data);
        editor.commit();
    }


    public static void showDiloge(Context ctx, String message)
    {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.popup_screen);

        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels*.7;
        Double height = metrics.heightPixels*.2;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());


        TextView text =(TextView)dialog.findViewById(R.id.text) ;
        text.setText(message);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public static String returnGenericData(String key, String prefrenceName, Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(prefrenceName, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }


    public static void clearData(String key, String prefrenceName, Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(prefrenceName, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.remove(key);
        editor.clear();
        editor.commit();

    }


	/*public static String getEmail(Context ctx)
    {
		String mail="";
		AccountManager am = AccountManager.get(ctx);
		Account[] accounts = am.getAccounts();

		for (Account ac : accounts)
		{
			String acname = ac.name;
			String actype = ac.type;
			System.out.println("Accounts : " + acname + ", " + actype);
			return acname;
		}

		return mail;

	}*/


    public static String getEmail(Context ctx) {

        String emai = "";
        AccountManager am = AccountManager.get(ctx);
        Account[] accounts = am.getAccounts();
        for (Account ac : accounts)
        {
            acname = ac.name;

            if (acname.startsWith("91"))
            {
                mobile_no = acname;
            } else if (acname.endsWith("@gmail.com") || acname.endsWith("@yahoo.com") || acname.endsWith("@hotmail.com")) {
                email = acname;
                return email;
            }

            // Take your time to look at all available accounts
            Log.i("Accounts : ", "Accounts : " + email + mobile_no);
        }
        Log.d("Accounts : ", "Accounts : " + email + mobile_no);
        return emai;
    }


    /*public static String getMyPhoneNumber(Context ctx) throws Exception {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String number = mTelephonyMgr.getLine1Number();
        if (number.length() > 10) {
            return number.substring(3, 13);
        } else {
            return mTelephonyMgr.getLine1Number();
        }

    }*/


    public static String jsonParseGetMessage(String key, Context ctx) {
        String message = "";
        String response = CommonClass.returnGenericData("LANGAUGE", "LANG_PRFE", ctx);
        try {
            JSONObject json = new JSONObject(response);
            return json.getString(key);
//		String
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return message;
    }


    public static String timeDiffrence(String first, String second) {

        SimpleDateFormat df = new SimpleDateFormat("h:mm a");

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(first);
            date2 = df.parse(second);
        } catch (ParseException e) {

        }
        long differenceInHours = (Math.abs(date1.getTime() - date2.getTime()) / 1000 / 60 / 60);
        String res = "";
        res = "" + differenceInHours;
        return res;
    }


    public static void open(String alert, String message, final Activity activity, final int id, boolean value) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setTitle(alert);
        alertDialogBuilder.setCancelable(value);
        alertDialogBuilder.setMessage(message);//"we've sent you an email with a link to reset your password");

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (id == 0) {
                    arg0.cancel();
                } else {
                    activity.finish();
                }

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void open(String alert, String message, final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);


//        alertDialogBuilder.setV

//        ImageView viee = new ImageView(activity);
//        viee.setImageResource(R.drawable.no_wifi);
//        alertDialogBuilder.setView(viee);
        alertDialogBuilder.setTitle(alert);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setMessage(message);//"we've sent you an email with a link to reset your password");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                {
                    activity.finish();
                }

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                {
                    arg0.cancel();
                }

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void setKeyboardVisibilityListener(Activity activity, final KeyboardVisibilityListener keyboardVisibilityListener) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mPreviousHeight;

            @Override
            public void onGlobalLayout() {
                int newHeight = contentView.getHeight();
                if (mPreviousHeight != 0) {
                    if (mPreviousHeight > newHeight) {
                        // Height decreased: keyboard was shown
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(true);
                    } else if (mPreviousHeight < newHeight) {
                        // Height increased: keyboard was hidden
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(false);
                    } else {
                        // No change

                    }
                }
                mPreviousHeight = newHeight;
            }
        });
    }

    static TelephonyManager tel;

 /*   public static void getValues(Context ctx) {
        tel = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String s = Build.MODEL;
        String sq = Build.MANUFACTURER;


        Log.d("VALUES PRINT ", "ime" + tel.getDeviceId() + "\n"
                + tel.getDeviceSoftwareVersion() + "\n"
                + s + "\n" + Build.VERSION.RELEASE + "\n" +
                sq + "\n" + Build.DEVICE + "\n" + Build.DISPLAY + "\n" + Build.HARDWARE);
    }
*/
    static PackageInfo pInfo = null;

    public static String appVersion(Context ctx) {
        try {
            pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return pInfo.versionName;
    }

    /*public static String imeiNumber(Context ctx) {
        TelephonyManager tel = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }*/

    public interface KeyboardVisibilityListener {
        void onKeyboardVisibilityChanged(boolean keyboardVisible);
    }


    public static void setPaymentMethod(String name, Context ctx) {
        saveGenericData(name, "payment", "PAYMENT_METHOD", ctx);
    }

    public static String getPaymentMethod(Context ctx) {
        return returnGenericData("payment", "PAYMENT_METHOD", ctx);
    }


  static   Context ctx;
    public static void  sendRegistrationToServer(String token ,Context ctxa)
    {
       ctx=ctxa;
        // TODO: Implement this method to send token to your app server.
        JSONObject table = new JSONObject();


        try {

//            table.put("manufacturer",""+ Build.MANUFACTURER);
//            table.put("model",""+ Build.MODEL);
//            table.put("appversion",""+CommonClass.appVersion(ctx));
//            table.put("osversion",""+Build.VERSION.RELEASE);
            table.put("pushNotificationId",""+token);
            table.put("guestId",""+ CommonClass.returnGenericData(GUEST_ID, GUEST_ID_PREF, ctx));
            table.put("device","ANDROID");
            table.put("uuid","1234tyt");


        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        PostData.postData(ctxa,table, PUSH_NOTIFICATION, mpostaFinalBooking);
    }

   static PostData.PostCommentJsonResponseListener mpostaFinalBooking = new PostData.PostCommentJsonResponseListener() {



        @Override
        public void requestStarted()
        {

        }

        @Override
        public void requestEndedWithError(VolleyError error) {
            // TODO Auto-generated method stub
            Log.d("ERROR","ERROR--->>"+error.getMessage());
        }

        @Override
        public void requestCompleted(JSONObject message)
        {
            Log.d("RESSS","RESSS"+message.toString());
            try {
              {
                 CommonClass.saveGenericData("1","FCM_REGI","FCM_REGI_PREF",ctx);
                }
            }
            catch (Exception e)
            {

            }
        }
    };


  /*  public static Double distanceBetween(LatLng point1, LatLng point2) {
        if (point1 == null || point2 == null) {
            return null;
        }

        return SphericalUtil.computeDistanceBetween(point1, point2);
    }*/

    public static  void localCachePaymentData(String amount,String trascationId,String orderid,Context ctx)
    {

        JSONObject json = new JSONObject();
        try {
            json.put("amount",amount);
            json.put("trascationId",trascationId);
            json.put("orderid",orderid);
        } catch (JSONException e) {
        }

            String jsonStri= ""+json.toString();
    Log.d("JSON DATA","JSONDATA"+jsonStri);

          saveGenericData(jsonStri,"PAYMENT_DATA","PAYMENT_DATA_PREF",ctx);
    }
    public static  void localClearCachePaymentData(Context ctx)
    {

        clearData("PAYMENT_DATA","PAYMENT_DATA_PREF",ctx);
    }


    public static  String localReturnCachePaymentData(Context ctx)
    {

       return returnGenericData("PAYMENT_DATA","PAYMENT_DATA_PREF",ctx);
    }

    public static <T> List<T> reverse(final List<T> list) {
        final List<T> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }

}