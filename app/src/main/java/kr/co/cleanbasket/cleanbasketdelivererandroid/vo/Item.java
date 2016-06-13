package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

import java.io.Serializable;

/**
 * Item.java
 * CleanBasket Deliverer Android
 * <p>
 * Created by Yongbin Cha on 16. 4. 8..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class Item {
    public Integer itid = null;
    public Integer oid = 0;
    public Integer item_code = 0;
    public String name = null;
    public String descr = null;
    public Integer price = null;
    public Integer count = 0;
    public String img = null;
    public String rdate = null;

    public Item(int oid, int item_code,  int count) {
        this.oid = oid;
        this.item_code = item_code;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itid=" + itid +
                ", oid=" + oid +
                ", item_code=" + item_code +
                ", name='" + name + '\'' +
                ", descr='" + descr + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", img='" + img + '\'' +
                ", rdate='" + rdate + '\'' +
                '}';
    }
}
