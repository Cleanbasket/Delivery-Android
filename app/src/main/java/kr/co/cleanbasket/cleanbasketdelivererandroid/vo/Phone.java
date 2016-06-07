package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

/**
 * Created by gingeraebi on 2016. 6. 3..
 */
public class Phone {
    public String phone;

    public Phone(String phone) {
        this.phone = phone;
    }

    public Phone(int phone) {
        this.phone = "0" + phone;
    }
}
