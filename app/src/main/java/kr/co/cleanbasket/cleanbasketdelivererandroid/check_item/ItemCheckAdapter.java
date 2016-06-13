package kr.co.cleanbasket.cleanbasketdelivererandroid.check_item;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;

/**
 * Created by gingeraebi on 2016. 6. 13..
 */
public class ItemCheckAdapter extends ArrayAdapter<Item> {

    private Activity context;
    private int resource;
    private ArrayList<Item> items;

    public ItemCheckAdapter(Context context, int resource, ArrayList<Item> items) {
        super(context, resource, items);
        this.context = (Activity) context;
        this.items = items;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(resource, parent, false);
        TextView itemName = (TextView) view.findViewById(R.id.itemName);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView count = (TextView) view.findViewById(R.id.count);

        itemName.setText(items.get(position).name);
        DecimalFormat df = new DecimalFormat("#,##0");
        String price_str  = String.valueOf(df.format(items.get(position).price));
        price.setText(price_str + "원");
        count.setText(items.get(position).count + "");
        return view;
    }
}
