package kartify.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.util.ArrayList;

import kartify.model.Beneficiary;
import wag_checkout.OrderPojo;

/**
 * Created by Kapil Katiyar on 11/24/2017.
 */

public class ReceiptListAdapter extends BaseAdapter {
    ArrayList<OrderPojo.items> arraylist;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ReceiptListAdapter(Activity activity, ArrayList<OrderPojo.items>  arraylist) {
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

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        View vi=view;
        if(view==null)
            vi = inflater.inflate(R.layout.rec_price_single_row, null);

        TextView title = (TextView) vi.findViewById(R.id.name); // title
        TextView quantity = (TextView)vi.findViewById(R.id.quantity); // artist name
        TextView price = (TextView)vi.findViewById(R.id.price); // artist name



        // Setting all values in listview
        title.setText((position+1)+".   "+arraylist.get(position).getProduct_name());
       // artist.setText(arraylist.get(position).getItem_quantity());
        int ii = (int)(Integer.parseInt(arraylist.get(position).getQuantity()) * Double.parseDouble(arraylist.get(position).getRetail_price()));
        price.setText(activity.getResources().getString(R.string.rs)+""+ii);
        quantity.setText("Qty "+arraylist.get(position).getQuantity());
//        ImageLoader.getInstance().displayImage(arraylist.get(position).getMallImage(), thumb_image, options, null);
        return vi;
    }


}
