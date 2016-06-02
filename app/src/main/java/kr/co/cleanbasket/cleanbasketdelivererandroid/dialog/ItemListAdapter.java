package kr.co.cleanbasket.cleanbasketdelivererandroid.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.ItemCode;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class ItemListAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<ItemCode> itemCodes;
    private ArrayList<Item> items;
    private DecimalFormat mFormatKRW = new DecimalFormat("###,###,###");
    private HashMap<String, Integer> itemMap = new HashMap<>();


    public ItemListAdapter(Activity context, ArrayList<ItemCode> itemCodes, ArrayList<Item> items) {
        this.context = context;
        this.itemCodes = itemCodes;
        this.items = items;
        setItemMap(items);
    }

    private void setItemMap(ArrayList<Item> items) {
        for (Item item : items) {
            itemMap.put(item.name, item.count);
        }
    }

    @Override
    public int getCount() {
        return itemCodes.size();
    }

    @Override
    public Object getItem(int position) {
        return itemCodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View row = layoutInflater.inflate(R.layout.row_itmelist, parent, false);

        TextView itemName = (TextView) row.findViewById(R.id.itemName);
        TextView price = (TextView) row.findViewById(R.id.price);
        final TextView itemCount = (TextView) row.findViewById(R.id.count);
        RelativeLayout layout = (RelativeLayout) row.findViewById(R.id.row);
        ImageView increase = (ImageView) row.findViewById(R.id.increase);
        ImageView decrease = (ImageView) row.findViewById(R.id.decrease);

        final ItemCode curItem = itemCodes.get(position);

        itemName.setText(curItem.name);

        if(itemMap.containsKey(curItem.name)) {
            itemCount.setText("" + itemMap.get(curItem.name));
        }

        price.setText(mFormatKRW.format((double) curItem.price));

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(curItem);
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount(curItem);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseCount(curItem);
            }
        });

        return row;
    }

    private void decreaseCount(ItemCode itemCode) {

        String itemName = itemCode.name;

        if(itemMap.containsKey(itemName)) {
            int count = itemMap.get(itemName);
            if(count > 0) {
                itemMap.remove(itemName);
                itemMap.put(itemName, count - 1);
            }
        }
        notifyDataSetChanged();
    }

    private void increaseCount(ItemCode itemCode) {
        String itemName = itemCode.name;

        if(itemMap.containsKey(itemName)) {
            int count = itemMap.get(itemName);
            itemMap.remove(itemName);
            itemMap.put(itemName, count + 1);
        }else {
            itemMap.put(itemName, 1);
        }
        notifyDataSetChanged();
    }

}
