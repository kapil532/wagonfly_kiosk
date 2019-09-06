package kartify.scanner;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.xml.transform.Result;

import kartify.model.Beneficiary;
import kartify.sql.DatabaseHelper;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.KeyboardUtils;
import kartify_base.PostData;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Scanner extends BaseActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    Activity activity;

    FrameLayout camer_view;
    EditText edit_box_code;
    LinearLayout edit_layout;
    Button scanner_button;
    ImageView cancel_button;
    ProgressBar progressbar_id;
    ImageView flash;
    boolean flash_light_ = false;
    boolean standBy = false;
    RelativeLayout content;
    TextView standby;
    DisplayImageOptions options;
    Button todo_list_button;
    private DrawerLayout mDrawerLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        setContentView(R.layout.scanner_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        startImage();
        content = (RelativeLayout) findViewById(R.id.content);
        camer_view = (FrameLayout) findViewById(R.id.camer_view);
        todo_list_button = (Button) findViewById(R.id.todo_list_button);
        todo_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });


        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        camer_view.addView(mScannerView);
        camer_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (standBy) {
                    setTheText(1);
                    startCameraPreview();
                    makeCameraStandBy();
                    standBy = false;

                } else {

                }
            }
        });
        scanner_button = (Button) findViewById(R.id.scanner_button);
        standby = (TextView) findViewById(R.id.standby);
        edit_layout = (LinearLayout) findViewById(R.id.edit_layout);
        cancel_button = (ImageView) findViewById(R.id.cancel_button);
        edit_layout.setVisibility(View.GONE);
        progressbar_id = (ProgressBar) findViewById(R.id.progressbar_id);

        customFontsChecks(scanner_button);
        edit_box_code = (EditText) findViewById(R.id.edit_box_code);
        flash = (ImageView) findViewById(R.id.flash);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flash_light_) {

                    mScannerView.setFlash(true);
                    flash.setImageResource(R.drawable.ic_flash_on_black_24dp);

                    flash_light_ = true;

                } else {
                    mScannerView.setFlash(false);
                    flash_light_ = false;
                    flash.setImageResource(R.drawable.ic_flash_off_black_24dp);
                }
            }
        });


        scanner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_layout.setVisibility(View.VISIBLE);
                showKey(edit_box_code);
                scanner_button.setVisibility(View.GONE);
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKey();
                edit_layout.setVisibility(View.GONE);
                scanner_button.setVisibility(View.VISIBLE);
            }
        });
        edit_box_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {
                    cancel_button.setImageResource(R.drawable.exit);
                    cancel_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hideKey();
                            edit_layout.setVisibility(View.GONE);
                            scanner_button.setVisibility(View.VISIBLE);
                        }
                    });
                } else {

                    cancel_button.setImageResource(R.drawable.tick_green);
                    // progressbar_id.setVisibility(View.GONE);
                    cancel_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hideKey();
                            //  progressbar_id.setVisibility(View.VISIBLE);
                            getCodeDetails(edit_box_code.getText().toString());
                        }
                    });

                }

            }
        });

        //setContentView(mScannerView);
        checkCameraPermission();

    }

    void startImage() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.place_holder)
                .showImageForEmptyUri(R.drawable.place_holder)
                .showImageOnFail(R.drawable.place_holder)
                .cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
    }

    Handler cameraHandler = new Handler();


    void makeCameraStandBy() {

        cameraHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cameraHandler.removeCallbacks(this);
                standBy = true;
                setTheText(0);
                mScannerView.stopCameraPreview();
            }
        }, 60000);
    }


    void setTheText(int i) {
        switch (i) {
            case 0:
                standby.setVisibility(View.VISIBLE);
                break;
            case 1:
                standby.setVisibility(View.GONE);
                break;

        }

    }


    private void hideKey() {
        //if(keypad_show)
        {
            Log.d("KEYPAD", "KEYPAD-inside the view");
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit_box_code.getWindowToken(), 0);
        }

    }

    private void showKey(EditText view)
    {
        view.requestFocus();
        view.selectAll();
        KeyboardUtils.showKeyboard(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();

        makeCameraStandBy();
        //  mScannerView.setFlash(true);
        // Start camera on resume

        Log.d("OnRESMUNE","RESUME METHOD");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkCameraPermission() {
        int permissionCheck_location_coarse = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck_write_coarse = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck_camera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (permissionCheck_location_coarse != PackageManager.PERMISSION_GRANTED ||
                permissionCheck_write_coarse != PackageManager.PERMISSION_GRANTED
                || permissionCheck_camera != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CHECK_CAMERA);
        }


    }

    protected static final int REQUEST_CHECK_CAMERA = 112;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        {
            switch (requestCode) {
                case REQUEST_CHECK_CAMERA:
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                        selectImage();
                    } else {
                        checkCameraPermission();
                    }
                    return;

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(com.google.zxing.Result rawResult) {

        getCodeDetails(rawResult.getText().toString());

        mScannerView.stopCameraPreview();
    }


    void getCodeDetails(String code) {
        JSONObject table = new JSONObject();
        try {
            table.put("code", "" + code);
            table.put("store_id", "" + CommonClass.returnGenericData("MALL_ID_KEY", "MALL_ID_KEY_PREF", this));
            table.put("skey", "" + network.getKartKey());


        } catch (JSONException e) {

        }
        String url = "" + PRODUCT + "" + code + "/" + CommonClass.returnGenericData(STORE_ID, STORE_ID_PREF, this);

        Log.d("DDDD","URKL"+url);

        PostData.getData(this, url, new PostData.PostCommentResponseListener() {

            @Override
            public void requestStarted() {
                showProgressDialog();
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
//                progressbar_id.setVisibility(View.GONE);
                hideProgressDialog();
                showDiloge(2);
            }

            @Override
            public void requestCompleted(String message) {
                hideProgressDialog();

                // Toast.makeText(Scanner.this,message,Toast.LENGTH_LONG).show();
                Log.d("LOGS VALUESS", "VALUESS-->" + message.toString());
                try {
                    JSONObject jsonObjecta = new JSONObject(message);
                    if (jsonObjecta.isNull("products")) {

                        showDiloge(2);

                    } else {
                        jsonParser(message);
                        mScannerView.stopCameraPreview();
                        standBy = true;
                        setTheText(0);

                    }
                } catch (Exception e)
                {
                    Log.d("LOGS VALUESS", "VALUESS-->" + e.getMessage());
                    showDiloge(2);
                }

               /* try {
                    Log.d("LOGS VALUESS", "VALUESS-->" + message.toString());
                    String s = message.getString("status");
                    if (s.equalsIgnoreCase("true")) {
                        jsonParser(message.toString());
                        mScannerView.stopCameraPreview();
                    } else {
                        showDiloge(2);
//                        Toast.makeText(Scanner.this, "Please try again!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
*/

            }
        });


    }

    ArrayList<Beneficiary> listBen;

    void jsonParser(String jsonData) {
        listBen = new ArrayList<>();
        try {

         /*   {"product":{"idx":25,"variant_id":8901030664076,"product_name":"Decathlon Kelenji White","product_description":"Decathlon Kelenji White","brand":"Kelenji","supplier":"Decathlon","weight_value":0,"weight_unit":0,"buy_price":1,"retail_price":699,"productImage":"https://images-na.ssl-images-amazon.com/images/I/31pcjUjbLuL._SY450_.jpg","offer":{"offerPrice":0,"productOfferId":"1234","idx":2,"offerExpiry":"2018-08-31T00:00:00.000Z"}}}*/
            JSONObject jsonObjecta = new JSONObject(jsonData);
            JSONArray jsonObjectA = jsonObjecta.getJSONArray("products");
            Beneficiary ben = new Beneficiary();
            for(int i=0;i<jsonObjectA.length();i++) {
                JSONObject jsonObject = jsonObjectA.getJSONObject(i);

                ben.setVariant_name(jsonObject.getString("product_name"));
                ben.setName(jsonObject.getString("product_name"));
                ben.setWeight("");
                ben.setManufacturer(jsonObject.getString("product_description"));
                ben.setCode(jsonObject.getString("product_id"));
                ben.setProduct_image(jsonObject.getString("productImage"));
                ben.setId(jsonObject.getString("product_id"));
                ben.setCategory_name("");
                ben.setBrand(jsonObject.getString("brand"));
                ben.setItem_quantity("1");
                ben.setGst("0");

           /* try {
                JSONObject jsonObjectOffer = jsonObject.getJSONObject("offer");
                ben.setOffer(jsonObjectOffer.getString("offerPrice"));
            } catch (Exception ee) {
                ben.setOffer(jsonObject.getString("offer"));
            }*/
                ben.setOffer("0");

                ben.setRetail_price(jsonObject.getString("retail_price"));

                ben.setS_price("" + priceCalculation(ben.getOffer(), ben.getRetail_price(), "" + 0));
            }
            listBen.add(ben);

        } catch (Exception e) {
            Log.d("SIZZE", "SIZEEE  exce" + e.getMessage());
        }
        Log.d("SIZZE", "SIZEEE" + listBen.size());
        if (listBen.size() > 0) {
            showDilogeWithA(0);

        }

    }


    private void showDiloge(final int is) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_screen);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * .7;
        Double height = metrics.heightPixels * .2;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());


        TextView text = (TextView) dialog.findViewById(R.id.text);
        switch (is) {
            case 1:
                text.setText("Your product has added");
                break;
            case 2:
                text.setText("Product not available");
                break;
            default:
                break;
        }

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                switch (is) {
                    case 1:
                        finish();
                        break;
                    case 2:
                        startCameraPreview();
                        break;
                    default:
                        break;
                }

//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }


    void startCameraPreview() {
        mScannerView.startCamera();
        mScannerView.setResultHandler(this);
    }

    void updateTable(Beneficiary ben) {
        DatabaseHelper db = new DatabaseHelper(this);
        //  db.open();
        // String code=  db.getValue(ben.getCode());
        Log.d("SIZZE", "SIZEEE Update" + db.getAllBeneficiary().size());
        String quantity = "";
        ArrayList<Beneficiary> list = (ArrayList<Beneficiary>) db.getAllBeneficiary();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCode().equalsIgnoreCase(ben.getCode())) {

                quantity = list.get(i).getItem_quantity();
                Log.d("SIZZE", "SIZEEE Update188" + quantity);
                break;
            }
        }
        Log.d("SIZZE", "SIZEEE Update18899--" + quantity);
        if (quantity.length() > 0) {
            Log.d("SIZZE", "SIZEEE Update11");
//           db.deleteSingleContact(ben.getCode());
            int value = Integer.parseInt(quantity) + 1;
            Log.d("SIZZE", "SIZEEE Update-->" + value);
            db.updateParticularData("" + value, ben.getCode());
        } else {
            Log.d("SIZZE", "SIZEEE InSERT");
            db.addBeneficiary(ben);
        }
        //db.close();


    }


    private void showDilogeWithA(int position) {

        Beneficiary movie = listBen.get(position);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_screen_for_product_add);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * 1.0;
        Double height = metrics.heightPixels * .6;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());

//total price
        TextView item_price = (TextView) dialog.findViewById(R.id.total_price);
        TextView supplier = (TextView) dialog.findViewById(R.id.supplier);
        //retail price
        TextView first_price = (TextView) dialog.findViewById(R.id.first_price);
        LinearLayout retail = (LinearLayout) dialog.findViewById(R.id.retail);


       //offer perceteage
        TextView offer_price_text = (TextView) dialog.findViewById(R.id.offer_price_text);
        LinearLayout offers = (LinearLayout) dialog.findViewById(R.id.offers);

        // price  gst
        TextView price_gst = (TextView) dialog.findViewById(R.id.price_gst);
        LinearLayout gst_lay = (LinearLayout) dialog.findViewById(R.id.gst_lay);



        ImageView item_icon = (ImageView) dialog.findViewById(R.id.item_icon);
        TextView item_title = (TextView) dialog.findViewById(R.id.item_title);
        TextView item_des = (TextView) dialog.findViewById(R.id.item_des);


        if(movie.getProduct_image().length()>4)
        {
            ImageLoader.getInstance().displayImage(movie.getProduct_image(), item_icon, options, null);
        }
        else
        {
            item_icon.setVisibility(View.GONE);
            RelativeLayout other_lay =(RelativeLayout)dialog.findViewById(R.id.other_lay);
            LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) other_lay.getLayoutParams();
            lParams.weight = 3f;
            other_lay.setLayoutParams(lParams);
        }


        if (movie.getOffer().equalsIgnoreCase("0")) {
            offers.setVisibility(View.GONE);
         //   retail.setVisibility(View.GONE);
          //
        }else
        {
            offer_price_text.setText(""+movie.getOffer()+"%");
        }
        //gst_lay.setVisibility(View.GONE);
      if(movie.getGst().equalsIgnoreCase("0")) {
            gst_lay.setVisibility(View.GONE);
        }
        else
        {
            price_gst.setText(""+movie.getGst()+"%");
        }


       if(movie.getGst().equalsIgnoreCase("0") && movie.getOffer().equalsIgnoreCase("0"))
        {
            retail.setVisibility(View.GONE);
        }
else
        {
            first_price.setText(this.getResources().getString(R.string.rs)+" "+movie.getRetail_price());
            first_price.setPaintFlags(first_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        item_price.setText(this.getResources().getString(R.string.rs) + " " + movie.getS_price());

        item_title.setText(movie.getName());
        item_des.setText(movie.getBrand());
        supplier.setText(movie.getCategory_name());


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startCameraPreview();
//                finish();
//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
                updateTable();
                finish();

//                finish();
//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void updateTable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateTable(listBen.get(0));
            }
        }).start();
    }

    int priceCalculation(String offers, String retailPrice, String gst) {
        int retPr = Integer.parseInt(retailPrice);
        int gstPer = 0;
        int offersPer = 0;
        int finalPrice = Integer.parseInt(retailPrice);
        int tempPrice = 0;

        if (!gst.equalsIgnoreCase("0"))
        {
            gstPer = Integer.parseInt(gst);
            tempPrice = (int) (gstPer * retPr) / 100;
            Log.d("TOTAL","PRICE GST"+tempPrice);
            finalPrice = retPr + tempPrice;
            Log.d("TOTAL","PRICE GST"+finalPrice);
        }

        if (!offers.equalsIgnoreCase("0"))
        {
            offersPer = Integer.parseInt(offers);
            if (finalPrice == 0) {
                tempPrice = (int) (offersPer * retPr) / 100;
                finalPrice = retPr - tempPrice;
            } else {
                tempPrice = (int) (offersPer * retPr) / 100;
                Log.d("TOTAL","PRICE OFFERS"+tempPrice);
                finalPrice = finalPrice - tempPrice;
                Log.d("TOTAL","PRICE OFFERS"+finalPrice);
            }

        }

        return finalPrice;
    }
}
