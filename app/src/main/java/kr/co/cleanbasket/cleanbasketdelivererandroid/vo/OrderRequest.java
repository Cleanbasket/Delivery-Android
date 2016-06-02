package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public class OrderRequest {
    public String oid;
    public String note;
    public String uid;

    public OrderRequest(String oid) {
        this.oid = oid;
    }

    public OrderRequest(String oid, int uid) {
        this.oid = oid;
        this.uid = "" + uid;
    }

    public OrderRequest(String oid, String note) {
        this.oid = oid;
        this.note = note;
    }

}
