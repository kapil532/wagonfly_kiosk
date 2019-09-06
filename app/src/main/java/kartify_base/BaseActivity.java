package kartify_base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import org.json.JSONObject;

import kartify.ezetap.printer.ShellExecuter;
import kartify.model.MallName;


/**
 * Created by Kapil Katiyar on 9/13/2017.
 */

public class BaseActivity extends AppCompatActivity implements NetworkUrls,ConstantValues
{

    protected NetworkClass network;
  protected DisplayImageOptions options;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        network = new NetworkClass(getApplicationContext());

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.place_holder)
                .showImageForEmptyUri(R.drawable.place_holder)
                .showImageOnFail(R.drawable.place_holder)
                .cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();

//        ShellExecuter shell = new ShellExecuter();
//        shell.hideBar();
    }


    private ProgressDialog mProgressDialog;

    public void showProgressDialog()
    {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setMessage(message);
        }

        mProgressDialog.show();
    }



    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void customFontsChecks(TextView view) {

        view.setTypeface(AppController.lightText);
    }

    public void customFontsBold(TextView view) {

        view.setTypeface(AppController.normalText);
    }

    public String getJsonString(JSONObject jso, String field) {
        if(jso.isNull(field))
            return "";
        else
            try {
                return jso.getString(field);
            }
            catch(Exception ex) {

                return null;
            }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
        {
            return true;
        } else
            {
          return false;

        }
    }
    protected void setDataForMall(final MallName mallName)
    {
        setDataForMall(mallName.getMallName(), mallName.getMallId(), mallName.getMallImage(), mallName.getMallAddress());
        if(mallName.getOffers() != null)
        {
            TextView offer = (TextView) findViewById(R.id.offer);
            offer.setVisibility(View.VISIBLE);
            offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlert(mallName.getOffers().get(0).getOfferText());
                }
            });
        }
    }

   protected TextView price;
   protected void setDataForMall(String name, String id, String imagePath, String add) {

        TextView title = (TextView) findViewById(R.id.textView1); // title
         price = (TextView) findViewById(R.id.price); // title
        TextView artist = (TextView) findViewById(R.id.textView2); // artist name
        ImageView thumb_image = (ImageView) findViewById(R.id.imageView1); // thumb image
        ImageView ti = (ImageView) findViewById(R.id.ti); // thumb image


        // Setting all values in listview
        title.setText(name);
        artist.setText(add);
        ImageLoader.getInstance().displayImage(imagePath, thumb_image, options, null);
    }

    void showAlert(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
       /* if (hasFocus) {
            hideSystemUI();
        }*/
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
