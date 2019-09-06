package wagonfly_screens;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.wagonfly.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify.sql.DatabaseHelper;
import kartify_base.BaseActivity;
import kartify_base.CommonClass;

/**
 * Created by Kapil Katiyar on 11/28/2017.
 */

public class QrCodeScreenForOrder extends BaseActivity
{
    Bitmap bm;
    private ImageView qrcode;
    TextView date;
    TextView time;
    TextView amount;
    TextView items;
public static String ORDER_ID="";
    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.date_layout)LinearLayout date_layout;
    @BindView(R.id.time_layout)LinearLayout time_layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_layout);
        ButterKnife.bind(this);
//        clearDataBase();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Exit Pass");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy ");
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("hh:mm");
        String format = simpleDateFormat.format(new Date());
        String formata = simpleDateFormats.format(new Date());
        qrcode = (ImageView) findViewById(R.id.qrcode);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });
date_layout.setVisibility(View.GONE);
time_layout.setVisibility(View.GONE);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        items = (TextView) findViewById(R.id.items);
        amount = (TextView) findViewById(R.id.amount);

        date.setText(format);
        time.setText(formata);

        items.setText(CommonClass.returnGenericData("TOTAL_ITEMS", "TOTAL_ITEMS_PREF", this));
        amount.setText(CommonClass.returnGenericData("TOTAL_PRICE", "TOTAL_PRICE_PREF", this));
        items.setVisibility(View.GONE);
        amount.setVisibility(View.GONE);
        time.setVisibility(View.GONE);
        date.setVisibility(View.GONE);
        generate(ORDER_ID);
    }

    private void generate(String macId) {


        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;


        //Encode with a QR Code image
        final QRCodeEncoder qrCodeEncoder = new QRCodeEncoder("" + macId,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bm = bitmap = qrCodeEncoder.encodeAsBitmap();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                qrcode.setImageBitmap(bitmap);
            }
        });
    }

   /* void clearDataBase() {
        DatabaseHelper dataBase = new DatabaseHelper(this);
//        dataBase.open();
        dataBase.deleteCart();
    }*/
}
