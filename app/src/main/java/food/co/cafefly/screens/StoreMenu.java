package food.co.cafefly.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wagonfly.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import kartify.adapter.RecyclerTouchListener;
import kartify.adapter.SimpleDividerItemDecoration;
import kartify_base.BaseActivity;
import past_orders.PastOrderAdapter;
import past_orders.PastOrderScreen;
import past_orders.PastOrder_invoice;

/**
 * Created by Kapil Katiyar on 8/26/2018.
 */

public class StoreMenu extends BaseActivity
{
    @BindView(R.id.recycler_view)
    RecyclerView myList;
//    @BindView(R.id.toolbar) Toolbar mToolbar;
//    @BindView(R.id.title)
//    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_store_layout);
        ButterKnife.bind(this);


      /*  title.setText("Store Menu");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //  mToolbar.setBackgroundColor(getResources().getColor(R.color.prime_primary_color));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });*/

        initializeRecycle();
    }


    StoreMenuAdapter mAdapter;
    void initializeRecycle() {
        mAdapter = new StoreMenuAdapter(this);
        myList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        myList.setLayoutManager(mLayoutManager);
        myList.addItemDecoration(new SimpleDividerItemDecoration(this));
        myList.setItemAnimator(new DefaultItemAnimator());
        myList.setAdapter(mAdapter);
        myList.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //  showDiloge(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
