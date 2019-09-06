package smart_shopping_with_deal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wagonfly.R;

import org.w3c.dom.Text;

import kartify_base.BaseActivity;
import kartify_base.CommonClass;
import smart_shopping_choose_option.SelectionScreen;

/**
 * Created by Kapil Katiyar on 6/25/2018.
 */

public class StartShoppingScreen extends BaseActivity implements View.OnClickListener {
    LinearLayout start_shopping_lay;
    LinearLayout change_store_lay;
    ListView list;
    TextView place_name;
    TextView add;
    ImageView image_back;
    TextView at_position;
    String mall_id;
    String image_path;
    String name;
    String add_;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_shopping);

        Intent myData = getIntent();
        if (myData != null) {
            mall_id = myData.getStringExtra("id");
            Log.d("VALUE", "VALUESS--0--" + mall_id);
             name = myData.getStringExtra("name");
             image_path = myData.getStringExtra("image_path");
             add_ = myData.getStringExtra("address");

            CommonClass.saveGenericData("" + mall_id, "MALL_ID_KEY", "MALL_ID_KEY_PREF", this);
        }
        start_shopping_lay = (LinearLayout) findViewById(R.id.start_shopping_lay);
        change_store_lay = (LinearLayout) findViewById(R.id.change_store_lay);
        list = (ListView) findViewById(R.id.list);
        place_name = (TextView) findViewById(R.id.place_name);
        add = (TextView) findViewById(R.id.add);
        at_position = (TextView) findViewById(R.id.at_position);
        image_back = (ImageView) findViewById(R.id.image_back);


        start_shopping_lay.setOnClickListener(this);
        change_store_lay.setOnClickListener(this);

        place_name.setText(name);
        add.setText(add_);
        at_position.setText(add_);
        ImageLoader.getInstance().displayImage(image_path, image_back, options, null);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_shopping_lay:
                openNextActivity(0);
                break;


            case R.id.change_store_lay:
                finish();
                break;


        }


    }


    void openNextActivity(int position)
    {
        Intent mainActivity = new Intent(StartShoppingScreen.this, SelectionScreen.class);
        mainActivity.putExtra("name",""+name);
        mainActivity.putExtra("id",""+mall_id);
        mainActivity.putExtra("image_path",""+image_path);
        mainActivity.putExtra("address",""+add_);
        startActivity(mainActivity);
        // finish();




     /*   Intent mainActivity = new Intent(MallList.this, MainActivity.class);
        mainActivity.putExtra("name",""+mallList.get(position).getMallName());
        mainActivity.putExtra("id",""+mallList.get(position).getMallId());
        mainActivity.putExtra("image_path",""+mallList.get(position).getMallImage());
        mainActivity.putExtra("address",""+mallList.get(position).getMallAddress());
        startActivity(mainActivity);
        finish();*/
    }


    void startAdapter() {

    }
}
