package kr.co.cleanbasket.cleanbasketdelivererandroid.unuse;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gingeraebi on 2016. 6. 5..
 */
public class ViewPagerDialog extends DialogFragment {
    private static final String TAG = "DEV_viewPagerFragment";
    private OnDialogPopListener onDialogPopListener;


    public interface OnDialogPopListener{
        void popUpDialog(ViewPagerDialog dialog);
    }

    public static ViewPagerDialog newInstance(OnDialogPopListener onDialogPopListener) {
        ViewPagerDialog viewPagerDialog = new ViewPagerDialog();
        viewPagerDialog.init(onDialogPopListener);
        return viewPagerDialog;
    }

    private void init(OnDialogPopListener onDialogPopListener) {
        this.onDialogPopListener = onDialogPopListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
