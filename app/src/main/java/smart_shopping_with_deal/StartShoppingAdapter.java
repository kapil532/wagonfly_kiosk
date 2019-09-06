package smart_shopping_with_deal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wagonfly.R;

import java.util.ArrayList;

/**
 * Created by Kapil Katiyar on 6/26/2018.
 */

public class StartShoppingAdapter extends BaseAdapter {

    ArrayList<StartShoppingPojo> list;
    Activity act;
    LayoutInflater inflater;


    public StartShoppingAdapter(ArrayList<StartShoppingPojo> list, Activity act) {
        inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.act = act;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.single_row_for_deal_start_shopping, null);

        TextView deal_id = (TextView)vi.findViewById(R.id.deal_id); // title
        TextView save_rs = (TextView)vi.findViewById(R.id.save_rs); // artist name
        TextView des = (TextView)vi.findViewById(R.id.des); // duration
         deal_id.setText(list.get(i).getDeal_type());
        save_rs.setText(list.get(i).getDeal_save_price());
        des.setText(list.get(i).getDeal_des());

        return vi;
    }
}

