package kartify.bv_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.util.ArrayList;

import kartify_base.PostData;

/**
 * Created by Kapil Katiyar on 7/20/2018.
 */

public class ShoppingBagAdapter extends BaseAdapter
{

    ArrayList<ShoppingBagPojo> list;
    Context ctx;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private DisplayImageOptions options;
    CallBackToDeleteItem callback;
    public  ShoppingBagAdapter(Context ctx,ArrayList<ShoppingBagPojo> list,CallBackToDeleteItem callback)
    {
        this.ctx=ctx;
        this.list=list;
    this.callback =callback;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        return list.size();
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
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        View vi=view;
        if(view==null)
            vi = inflater.inflate(R.layout.single_row_for_shopping_bag, null);

        TextView product_desc = (TextView) vi.findViewById(R.id.product_desc); // title
        TextView quantity = (TextView)vi.findViewById(R.id.quantity); // artist name
        TextView price_per_unit = (TextView)vi.findViewById(R.id.price_per_unit); // artist name
        ImageView thumbs = (ImageView) vi.findViewById(R.id.thumbnail); // artist name
        final ImageView cancel_button = (ImageView) vi.findViewById(R.id.cancel_button); // artist name




        cancel_button.setTag(i);
        cancel_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int pos = (int)cancel_button.getTag();
                deleteItem(list.get(pos).getProduct_id());
             //   Toast.makeText(ctx,list.get(pos).getProduct_id(),Toast.LENGTH_LONG).show();
            }
        });
        product_desc.setText(list.get(i).getTitle()+""+ "("+list.get(i).getBrand()+")");
        quantity.setText("Quantity "+list.get(i).getQuantity());
        price_per_unit.setText("Unit Price "+ctx.getResources().getString(R.string.rs)+""+list.get(i).getPrice());
        ImageLoader.getInstance().displayImage(list.get(i).getImage_urls(), thumbs, options, null);

        return vi;
    }


    void deleteItem(String productId)
    {
        String URL="https://api.bottleview.com/om/v1/cnsmr/data/cart?consumer_id=cnsmr-22242&product_id="+productId+"&delete_line_item=True";
        PostData.deleteItem(ctx, URL, new PostData.PostCommentResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(String message) {
                callback.deletedItem(message);
            }

            @Override
            public void requestEndedWithError(VolleyError error) {

            }
        });
    }
}
