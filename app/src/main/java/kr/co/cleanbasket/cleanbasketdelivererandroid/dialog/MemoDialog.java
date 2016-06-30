package kr.co.cleanbasket.cleanbasketdelivererandroid.dialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.activity.MainActivity;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;

/**
 * Created by gingeraebi on 2016. 6. 30..
 */
public class MemoDialog extends DialogFragment implements View.OnClickListener{

    private OnTransferListener onTransferListener;
    private Order order;

    private EditText memo;

    public interface OnTransferListener {
        void onTransfer(Order order);
    }

    public static MemoDialog newInstance(OnTransferListener onTransferListener, Order order) {
        MemoDialog memoDialog = new MemoDialog();
        memoDialog.init(onTransferListener, order);
        return memoDialog;
    }


    public void init(OnTransferListener onTransferListener, Order order) {
        this.onTransferListener = onTransferListener;
        this.order = order;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_memo, container, false);
        Button complete = (Button) rootView.findViewById(R.id.complete);
        memo = (EditText) rootView.findViewById(R.id.note);

        complete.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        order.note = memo.getText().toString();
        onTransferListener.onTransfer(order);
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dismiss();
            }
        }, 100);
    }
}
