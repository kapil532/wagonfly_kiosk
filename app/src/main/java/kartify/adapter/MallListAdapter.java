package kartify.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.util.ArrayList;

import kartify.model.MallName;
import past_orders.OrderPojo;
import past_orders.PastOrderAdapter;

/**
 * Created by Kapil Katiyar on 11/24/2017.
 */

public class MallListAdapter extends RecyclerView.Adapter<MallListAdapter.MallListViewHolder> {
    ArrayList<MallName> arraylist;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private DisplayImageOptions options;


    public MallListAdapter(Activity activity,  ArrayList<MallName> arraylist) {
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

    public class MallListViewHolder extends RecyclerView.ViewHolder {


        public  ImageView thumb_image;
        public  TextView title;
        public  TextView artist;
        public  TextView view_offer;
        public TextView offers_text;

        public MallListViewHolder(View vi) {
            super(vi);
             title = (TextView) vi.findViewById(R.id.textView1); // title
             artist = (TextView)vi.findViewById(R.id.textView2); // artist name
             view_offer = (TextView)vi.findViewById(R.id.view_offer); // artist name
             offers_text = (TextView)vi.findViewById(R.id.offers_text); // artist name
             thumb_image=(ImageView)vi.findViewById(R.id.imageView1); // thumb image

        }


    }
    @Override
    public MallListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_mall_list, parent, false);
        return new MallListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MallListViewHolder holder, int position) {


        MallName malData =arraylist.get(position);

        if(malData.getOffers() != null)
        {
            holder. view_offer.setAlpha(1);
            holder.offers_text.setText(malData.getOffers().get(0).getOfferText());

        }
        else
        {
            holder. view_offer.setAlpha(.5f);
            holder. offers_text.setText("");
        }
        // Setting all values in listview
        holder.title.setText(arraylist.get(position).getMallName());
        holder.artist.setText(arraylist.get(position).getMallAddress());
        ImageLoader.getInstance().displayImage(arraylist.get(position).getMallImage(),    holder.thumb_image, options, null);
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }


}
