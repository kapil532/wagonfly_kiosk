package kartify.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import java.util.ArrayList;

import kartify.model.Beneficiary;
import kartify.scanner.UpdateTable;
import kartify.sql.DatabaseHelper;

/**
 * Created by delaroy on 5/10/17.
 */
public class BeneficiaryRecyclerAdapter extends RecyclerView.Adapter<BeneficiaryRecyclerAdapter.BeneficiaryViewHolder> {

    private ArrayList<Beneficiary> listBeneficiary;
    public ImageView overflow;
    private Context mContext;
    private ArrayList<Beneficiary> mFilteredList;
    UpdateTable handler;
    Activity act;
  DisplayImageOptions options;
    public BeneficiaryRecyclerAdapter(ArrayList<Beneficiary> listBeneficiary, Context mContext, UpdateTable handler,Activity act) {

        Log.d("VALUESSS", "VALUESSS" + listBeneficiary.size());
        startImage();
        this.act=act;
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;
        this.mFilteredList = listBeneficiary;
        this.handler = handler;


    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewEmail;
        public TextView textViewAddress;
        public TextView price;
        public TextView quantity;
        public ImageView delete_image;
        public ImageView overflow;
        public  LinearLayout layout_qty;

        public BeneficiaryViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textView1);
//            textViewEmail = (TextView) view.findViewById(R.id.textView2);
          //  textViewAddress = (TextView) view.findViewById(R.id.textView3);
            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    openDilouge();
                    int is = (int) view.getTag();
                    showDiloge(is);
                }
            });
            /*textViewAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    openDilouge();
                    int is = (int) view.getTag();
                    showDiloge(is);
                }
            });*/

            price = (TextView) view.findViewById(R.id.price);
            price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    openDilouge();
                    int is = (int) view.getTag();
                    showDiloge(is);
                }
            });

            layout_qty=(LinearLayout)view.findViewById(R.id.layout_qty);
            layout_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int is = (int) view.getTag();
                    openDilouge(is);
                }
            });
            quantity = (TextView) view.findViewById(R.id.quantity);

          //  delete_image = (ImageView) view.findViewById(R.id.delete_image);
            delete_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    openDilouge();
                    int is = (int) view.getTag();
                    deleteColoumn(is);
//                    Toast.makeText(mContext, "DELETE" + listBeneficiary.get(is).getItem_quantity(), Toast.LENGTH_LONG).show();
                }
            });
            overflow = (ImageView) view.findViewById(R.id.imageView1);
            overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    openDilouge();
                    int is = (int) view.getTag();
                    showDiloge(is);
                }
            });
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

        Log.d("VALUESSS", "VALUESSS" + listBeneficiary.get(position).getName());
        Log.d("VALUESSS", "VALUESSS" + listBeneficiary.get(position).getBrand());
        Log.d("VALUESSS", "VALUESSS" + listBeneficiary.get(position).getWeight());
        Log.d("VALUESSS", "VALUESSS" + listBeneficiary.get(position).getProduct_image());

        holder.textViewName.setText(listBeneficiary.get(position).getName());
//        holder.textViewEmail.setText(listBeneficiary.get(position).getBrand());
        holder.textViewAddress.setText(listBeneficiary.get(position).getWeight());
        holder.quantity.setText(listBeneficiary.get(position).getItem_quantity());

        holder.delete_image.setTag(position);

        holder.layout_qty.setTag(position);
        holder.overflow.setTag(position);
        holder.textViewName.setTag(position);
        holder.textViewAddress.setTag(position);
        holder.price.setTag(position);
        holder.quantity.setText("Qty | " + listBeneficiary.get(position).getItem_quantity());

        int ii = (int)(Integer.parseInt(listBeneficiary.get(position).getItem_quantity()) * Double.parseDouble(listBeneficiary.get(position).getS_price()));
        holder.price.setText(mContext.getResources().getString(R.string.rs) + " " + ii);
        ImageLoader.getInstance().displayImage(listBeneficiary.get(position).getProduct_image(), holder.overflow, options, null);

        // holder.textViewCountry.setText(listBeneficiary.get(position).getManufacturer());


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

    void deleteColoumn(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure? you want to delete " + listBeneficiary.get(position).getName());

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                updateTable(listBeneficiary.get(position).getCode());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    void updateTable(String id)
    {
        DatabaseHelper db = new DatabaseHelper(mContext);
        db.deleteSingleContact(id);
       // db.close();
        handler.updateData("");
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    private void openDilouge(final int id)
    {
       final String names[] ={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
      final  AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = act.getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom_dilouge, null);
        alertDialog.setView(convertView);
        final AlertDialog closedialog= alertDialog.create();
        ImageView close =(ImageView)convertView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closedialog.dismiss();
            }
        });

        ListView lv = (ListView) convertView.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.single_row_for_quantity,names);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                updateTableWithQty(""+names[i],id);
                closedialog.dismiss();
            }
        });
        closedialog.show();
    }


    void updateTableWithQty(String quantity,int id)
    {


        DatabaseHelper db = new DatabaseHelper(mContext);
        db.updateParticularData(quantity,listBeneficiary.get(id).getCode());
        //db.close();
        handler.updateData("");
    }


    private void showDiloge(int position) {

        Beneficiary movie = listBeneficiary.get(position);
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.popup_screen_mall_list);

        WindowManager wm = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * .8;
        Double height = metrics.heightPixels * .6;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());


        TextView item_price = (TextView) dialog.findViewById(R.id.item_price);
        ImageView item_icon = (ImageView) dialog.findViewById(R.id.item_icon);
        TextView item_title = (TextView) dialog.findViewById(R.id.item_title);
        TextView item_des = (TextView) dialog.findViewById(R.id.item_des);

        ImageLoader.getInstance().displayImage(movie.getProduct_image(), item_icon, options, null);

        item_price.setText(act.getResources().getString(R.string.rs) + " " + movie.getS_price() + " per piece");
        item_title.setText(movie.getName());
        item_des.setText(movie.getManufacturer());


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
//                finish();
//                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
