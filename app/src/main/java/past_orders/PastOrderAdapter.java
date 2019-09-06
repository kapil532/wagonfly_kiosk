package past_orders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.util.ArrayList;

import kartify.adapter.BeneficiaryRecyclerAdapter;
import wagonfly_screens.Contents;
import wagonfly_screens.QRCodeEncoder;
import wagonfly_screens.QrCodeScreenForOrder;

/**
 * Created by Kapil Katiyar on 11/24/2017.
 */

public class PastOrderAdapter extends RecyclerView.Adapter<PastOrderAdapter.PastOrderViewHolder> {
    ArrayList<PastOrderPojoNew> arraylist;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private DisplayImageOptions options;

    public PastOrderAdapter(Activity activity, ArrayList<PastOrderPojoNew> arraylist) {
        this.activity = activity;
        this.arraylist = arraylist;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.place_holder)
                .showImageForEmptyUri(R.drawable.place_holder)
                .showImageOnFail(R.drawable.place_holder)
                .cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
    }

    public class PastOrderViewHolder extends RecyclerView.ViewHolder {


        public ImageView thumb_image;
        public ImageView qr_logo;
        public TextView artist;
        public TextView title;
        public LinearLayout view_reciept_lay;
        public TextView product_count;


        public PastOrderViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView1); // title
            artist = (TextView) view.findViewById(R.id.product_price); // artist name
            thumb_image = (ImageView) view.findViewById(R.id.logo); // thumb image
            view_reciept_lay = (LinearLayout) view.findViewById(R.id.view_reciept_lay); // thumb image

            view_reciept_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int is = (int) view.getTag();
                    Intent invoice = new Intent(activity, PastOrder_invoice.class);
                    PastOrder_invoice.productList = arraylist.get(is);
                    activity.startActivity(invoice);

                }
            });
//            Intent invoice = new Intent(activity,PastOrder_invoice.class);
////                // PastOrder_invoice.orider_id=orderPojoArrayList.get(i).getOrder_id();
//            PastOrder_invoice.productList=orderPojoArrayList.get(position);
//           activity.startActivity(invoice);
            qr_logo = (ImageView) view.findViewById(R.id.qr_logo); // thumb image
            product_count = (TextView) view.findViewById(R.id.product_count); // thumb image

            qr_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int is = (int) view.getTag();
                    Intent invoice = new Intent(activity, QrCodeScreenForOrder.class);
                    QrCodeScreenForOrder.ORDER_ID = arraylist.get(is).getCode();
                    activity.startActivity(invoice);

                }
            });

        }


    }


    @Override
    public PastOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.past_order_single_row, parent, false);
        return new PastOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PastOrderViewHolder holder, int position) {


        // Setting all values in listview
        holder.title.setText(arraylist.get(position).getStorePojo().get(0).getName());
        holder.product_count.setText("Total Quantity : " + arraylist.get(position).getQty());
        holder.artist.setText("Total Price : " + activity.getResources().getString(R.string.rs) + "" + arraylist.get(position).getTotalAmt() + "");
        holder.qr_logo.setTag(position);
        holder.view_reciept_lay.setTag(position);
        ImageLoader.getInstance().displayImage(arraylist.get(position).getStorePojo().get(0).getImage(), holder.thumb_image, options, null);
        generate(arraylist.get(position).getCode(), holder.qr_logo);
    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    private void generate(String macId, final ImageView qrcode) {


        WindowManager manager = (WindowManager) activity.getSystemService(activity.WINDOW_SERVICE);
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

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = qrCodeEncoder.encodeAsBitmap();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                qrcode.setImageBitmap(bitmap);
            }
        });
    }


    private void generate(String macId) {


        WindowManager manager = (WindowManager) activity.getSystemService(activity.WINDOW_SERVICE);
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

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = qrCodeEncoder.encodeAsBitmap();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                showpopup(bitmap);
                // qrcode.setImageBitmap(bitmap);
            }
        });
    }


    private void showpopup(Bitmap qrc) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View mView = inflater.inflate(R.layout.image_qr_code_layout, null);
        PopupWindow mPopupWindow = new PopupWindow(mView, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, false);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        ImageView TV = (ImageView) mView.findViewById(R.id.widget45);
        TV.setImageBitmap(qrc);
        //  TV.
        // TableLayout L1 = (TableLayout)findViewById(R.id.tblntarialview);

        mPopupWindow.showAtLocation(TV, Gravity.CENTER, 45, 0);

    }
}
