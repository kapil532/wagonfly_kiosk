package com.store_selected;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.wagonfly.R;

import kartify.model.MallName;

/**
 * Created by Kapil Katiyar on 8/30/2018.
 */

public class FragmentOfLocations  extends ListFragment implements AdapterView.OnItemClickListener {
    ImageView image_one;
    DisplayImageOptions options;
    public static  MallName mallName;
    TextView mall_addre,mall_name;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        /*((MallList) getActivity()).setFragmentRefreshListener(new MallList.FragmentRefreshListener() {
            @Override
            public void onRefresh() {

                // Refresh Your Fragment

                setTheView();

            }
        });*/

      image_one=(ImageView)view.findViewById(R.id.image_one);
        mall_addre=(TextView)view.findViewById(R.id.mall_addre);
        mall_name=(TextView)view.findViewById(R.id.mall_name);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.place_holder)
                .showImageForEmptyUri(R.drawable.place_holder)
                .showImageOnFail(R.drawable.place_holder)
                .cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Planets, android.R.layout.simple_list_item_1);


        try {
            Log.d("IMAGES","Image"+mallName.getMallImage());
            ImageLoader.getInstance().displayImage(mallName.getMallImage(),   image_one, options, null);
        }
        catch (Exception e)
        {

        }

        setListAdapter(adapter);



        getListView().setOnItemClickListener(this);*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    void setTheView()
    {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Planets, android.R.layout.simple_list_item_1);


        try {
            Log.d("IMAGES","Image"+mallName.getMallImage());
            mall_name.setText(mallName.getMallName());
            ImageLoader.getInstance().displayImage(mallName.getMallImage(),   image_one, options, null);
        }
        catch (Exception e)
        {

        }

        setListAdapter(adapter);



        getListView().setOnItemClickListener(this);
    }
}