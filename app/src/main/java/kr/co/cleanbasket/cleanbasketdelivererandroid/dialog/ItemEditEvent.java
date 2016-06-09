package kr.co.cleanbasket.cleanbasketdelivererandroid.dialog;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;

/**
 * Created by gingeraebi on 2016. 6. 9..
 */
public class ItemEditEvent {
    private ArrayList<Item> items;

    public ItemEditEvent(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
