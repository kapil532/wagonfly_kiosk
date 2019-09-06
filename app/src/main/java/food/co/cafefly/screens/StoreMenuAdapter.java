package food.co.cafefly.screens;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.util.ArrayList;

import past_orders.OrderPojo;
import past_orders.PastOrderAdapter;

/**
 * Created by Kapil Katiyar on 8/26/2018.
 */

public class StoreMenuAdapter extends RecyclerView.Adapter<StoreMenuAdapter.StoreMenuAdapterViewHolder>
{
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private DisplayImageOptions options;

    public StoreMenuAdapter(Activity activity) {
        this.activity = activity;
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
int number_=1;
    public class StoreMenuAdapterViewHolder extends RecyclerView.ViewHolder {


        public ImageView dis_image;
        public ImageView veg_nonveg;
        public TextView item_name;
        public TextView add;
        public TextView item_des;
        public TextView item_price;

        public TextView bt_less;
        public TextView bt_more;
        public TextView number;

        public LinearLayout add_layout;
        public LinearLayout plus;

        public StoreMenuAdapterViewHolder(View view) {
            super(view);
            item_des = (TextView) view.findViewById(R.id.item_des); // title
            item_name = (TextView)view.findViewById(R.id.item_name); // artist name
            add = (TextView)view.findViewById(R.id.add); // artist name
            dis_image=(ImageView)view.findViewById(R.id.dis_image); // thumb image
            veg_nonveg=(ImageView)view.findViewById(R.id.veg_nonveg); // thumb image
            item_price=(TextView)view.findViewById(R.id.item_price); // thumb image

            number=(TextView)view.findViewById(R.id.number); // thumb image
            bt_less=(TextView)view.findViewById(R.id.bt_less); // thumb image
            bt_less .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   if( number_==1)
                   {
                       add.setVisibility(View.VISIBLE);
                       plus.setVisibility(View.GONE);
                   }
                   else
                   {
                       number_=number_-1;
                       number.setText(""+number_);
                   }

                }
            });
            bt_more=(TextView)view.findViewById(R.id.bt_more); // thumb image
            bt_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    number_=number_+1;
                    number.setText(""+number_);

                }
            });
            add_layout=(LinearLayout)view.findViewById(R.id.add_layout); // thumb image
            plus=(LinearLayout)view.findViewById(R.id.plus); // thumb image

            add_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    plus.setVisibility(View.VISIBLE);
                    add.setVisibility(View.GONE);

                }
            });
        }


    }



    @Override
    public StoreMenuAdapter.StoreMenuAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_menu_store, parent, false);
        return new StoreMenuAdapter.StoreMenuAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoreMenuAdapter.StoreMenuAdapterViewHolder holder, int position) {
        holder.add_layout.setTag(position);
     /*   try {
            getTotalPrice(position);
        }
        catch (Exception e)
        {

        }
        // Setting all values in listview
        holder. title.setText(arraylist.get(position).getStore().get(0).getName());
        holder. product_count.setText("Total Quantity : "+quantity);
        holder.artist.setText("Total Price : "+activity.getResources().getString(R.string.rs)+""+totalPrice+"");
        ImageLoader.getInstance().displayImage(arraylist.get(position).getStore().get(0).getImage(), holder.thumb_image, options, null);*/
    }


    @Override
    public int getItemCount() {
        return 10;
    }




}
