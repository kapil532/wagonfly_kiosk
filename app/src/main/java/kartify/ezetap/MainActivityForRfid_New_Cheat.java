package kartify.ezetap;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mindorks.placeholderview.PlaceHolderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kartify.adapter.BeneficiaryRecyclerAdapterRfid;
import kartify.adapter.RecyclerTouchListener;
import kartify.adapter.SimpleDividerItemDecoration;
import kartify.scanner.UpdateTable;
import kartify.sql.DatabaseHelper;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;

public class MainActivityForRfid_New_Cheat extends BaseActivity {

    EditText barcode;
    TextView rfid, rfidss;
    Button clear;
    String rfid_, baarCode_;
    LinearLayout scan_layout,layout_rfid;
    ProgressBar prgress,progress_bar_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_cheat);
        barcode = (EditText) findViewById(R.id.barcode);
        rfid = (TextView) findViewById(R.id.rfid);
        rfidss = (TextView) findViewById(R.id.rfidss);
        clear = (Button) findViewById(R.id.clear);
        prgress=(ProgressBar)findViewById(R.id.prgress);
        progress_bar_1=(ProgressBar)findViewById(R.id.progress_bar_1);
        prgress.setVisibility(View.INVISIBLE);
        progress_bar_1.setVisibility(View.INVISIBLE);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcode.getText().clear();
            }
        });

        scan_layout = (LinearLayout) findViewById(R.id.scan_layout);
        layout_rfid = (LinearLayout) findViewById(R.id.layout_rfid);
        scan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (barcode.getText().length() > 2 && rfid_.length() > 2) {
                    rfidss.setText("" + barcode.getText().toString().trim() + "-" + rfid_);
                    submitRfid(barcode.getText().toString().trim(), rfid_);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter proper data", Toast.LENGTH_LONG).show();
                }
            }
        });

        layout_rfid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeDetails();
            }
        });

        // updateList();
    }


    void getCodeDetails() {
        JSONObject table = new JSONObject();

        PostData.calla(table, RFIDS+"?store=1", new PostData.PostCommentResponseListener() {
            @Override
            public void requestStarted() {
                prgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void requestCompleted(String message) {
                prgress.setVisibility(View.INVISIBLE);
                Log.d("MESSAGE", "MESSAGE" + message.toString());
                rfid_ = replaceLastFour(message.toString());
                rfid.setText(rfid_);


            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                prgress.setVisibility(View.INVISIBLE);
            }
        });


    }


    void submitRfid(String barCode, String Rfid) {

        JSONObject table = new JSONObject();
        String finalUrl = GENERATE_RFID + "rfid=" + Rfid + "&barcode=" + barCode;
        PostData.calla(table, finalUrl, new PostData.PostCommentResponseListener() {
            @Override
            public void requestStarted() {
                progress_bar_1.setVisibility(View.VISIBLE);
            }

            @Override
            public void requestCompleted(String message) {
                progress_bar_1.setVisibility(View.INVISIBLE);
//                {'status': true,'message': 'RFID Record Created'}
                try {
                    JSONObject jsonObj = new JSONObject(message);
                    String status = jsonObj.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        barcode.getText().clear();
                        rfid.setText("RFID");
                    } else {
                        Toast.makeText(getApplicationContext(), "Pls Try Again!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                progress_bar_1.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Pls Try Again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public static String replaceLastFour(String s) {
        int length = s.length();
        //Check whether or not the string contains at least four characters; if not, this method is useless
        if (length < 2)
            return "Error: The provided string is not greater than four characters long.";
        return s.substring(0, length - 2);
    }


}
