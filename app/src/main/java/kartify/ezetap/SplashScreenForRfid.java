package kartify.ezetap;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.volley.VolleyError;
import com.wagonfly.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import kartify_base.PostData;

public class SplashScreenForRfid extends BaseActivity
{
    Handler mHandlera = new Handler();
    Handler mHandlerb = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

      onPlayLocalVideo();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // updateList();

      getCodeDetails();
 onPlayLocalVideo();
        mHandlera.post(runnable);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // do your stuff - don't create a new runnable here!
           getCodeDetails();
            mHandlera.postDelayed(this, 2000);

        }
    };
   /* Runnable runnablea = new Runnable() {
        @Override
        public void run()
        {

            Intent myIn= new Intent(SplashScreenForRfid.this,MainActivityForRfid_New.class);
            startActivity(myIn);
            finish();


        }
    };*/

    void getCodeDetails() {
        JSONObject table = new JSONObject();

        Log.d("URL", "MAINACTIVITY_RFID" + GET_RFID_ORDERS + CommonClass.returnGenericData(STORE_ID, STORE_ID_PREF, getApplicationContext()));
        PostData.calla(table, GET_RFID_ORDERS + CommonClass.returnGenericData(STORE_ID, STORE_ID_PREF, getApplicationContext()), new PostData.PostCommentResponseListener() {

            @Override
            public void requestStarted() {
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
            }

            @Override
            public void requestCompleted(String message) {

                try {
                    Log.d("LOGS VALUESS", "MAINACTIVITY_RFID--> Response-->" + message.toString());
                    //  String s = message.getString("status");
                    //if (s.equalsIgnoreCase("true"))
                    {
                        jsonParser(message.toString());
                    }
                } catch (Exception e) {

                }


            }
        });


    }

    ArrayList<Product_Pojo> listBen;
    String tax_basket = "";

    void jsonParser(String jsonData) {
        listBen = new ArrayList<>();
        try {
            JSONObject jsonObjecta = new JSONObject(jsonData);
            tax_basket = jsonObjecta.getString("tax");
            JSONArray jsonObjectA = jsonObjecta.getJSONArray("order");
            if(jsonObjectA.length()>0)
            {
                Intent myIn= new Intent(SplashScreenForRfid.this,MainActivityForRfid_New.class);
                myIn.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(myIn);
                finish();
            }
        }
        catch (Exception e)
        {

        }
    }

  VideoView mVideoView;
   public void onPlayLocalVideo()
   {
         mVideoView = (VideoView) findViewById(R.id.video_view);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.wrogn_time_out));
        mVideoView.setMediaController(null);
        mVideoView.requestFocus();
        mVideoView.start();
       mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
               mp.setLooping(true);
           }
       });
    }
    @Override
    protected void onStop() {
        super.onStop();
        mHandlera.removeCallbacks(runnable);
    if(mVideoView != null)
     {
         mVideoView.stopPlayback();

     }
    }


}
