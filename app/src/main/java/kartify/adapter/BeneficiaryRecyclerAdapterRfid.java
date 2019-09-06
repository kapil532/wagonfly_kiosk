package kartify.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.util.ArrayList;

import kartify.ezetap.Product_Pojo;
import kartify.model.Beneficiary;
import kartify.scanner.UpdateTable;
import kartify.sql.DatabaseHelper;

/**
 * Created by delaroy on 5/10/17.
 */
public class BeneficiaryRecyclerAdapterRfid extends RecyclerView.Adapter<BeneficiaryRecyclerAdapterRfid.BeneficiaryViewHolder> {

    private ArrayList<Product_Pojo> listBeneficiary;
    public ImageView overflow;
    private Context mContext;
    private ArrayList<Product_Pojo> mFilteredList;
    UpdateTable handler;
  DisplayImageOptions options;
    public  BeneficiaryRecyclerAdapterRfid(ArrayList<Product_Pojo> listBeneficiary, Context mContext, UpdateTable handler) {

        Log.d("VALUESSS", "VALUESSS" + listBeneficiary.size());
        startImage();
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;
        this.mFilteredList = listBeneficiary;
        this.handler = handler;


    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView desc;
        public TextView unit;
        public TextView price;
        public ImageView product_image;


        public BeneficiaryViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            desc = (TextView) view.findViewById(R.id.desc);
            unit = (TextView) view.findViewById(R.id.unit);
            price = (TextView) view.findViewById(R.id.price);
            product_image = (ImageView) view.findViewById(R.id.product_image);

        }


    }


    @Override
    public BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kart_list_single_row, parent, false);

        return new BeneficiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryViewHolder holder, int position) {

     Log.d("VALUESSS", "VALUESSS" + listBeneficiary.get(position).getImage_url());
Product_Pojo pojo= listBeneficiary.get(position);
        holder.name.setText(listBeneficiary.get(position).getItem_name());
        holder.desc.setText(pojo.getItem_description());
        holder.unit.setText("Size: "+listBeneficiary.get(position).getSize()+", color: "+pojo.getColor()+", fabric: "+listBeneficiary.get(position).getFabric()+", pattern: "+listBeneficiary.get(position).getPattern()+", sleeves: "+listBeneficiary.get(position).getSleeves()+", fit: "+pojo.getFit());
       if(pojo.getItem_theme()!= null || pojo.getItem_theme().length()>0)
       {
           holder.price.setText("Price: " + mContext.getResources().getString(R.string.rs) + listBeneficiary.get(position).getManipulate_price()+"  "+pojo.getItem_theme());
       }
       else {
           holder.price.setText("Price: " + mContext.getResources().getString(R.string.rs) + listBeneficiary.get(position).getManipulate_price());
       }
        ImageLoader.getInstance().displayImage(listBeneficiary.get(position).getImage_url(), holder.product_image, options, null);

//        holder.product_image


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


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


}
