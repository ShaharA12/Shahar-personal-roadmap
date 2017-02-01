package test.test.com.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.test.com.myapplication.MainActivity;
import test.test.com.myapplication.R;
import test.test.com.myapplication.utilities.SharedPreferencesUtil;

/**
 * Created by ShaharAlush on 30/01/2017.
 */
public class EditSettingFragment extends Fragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gasET)
    EditText gasPrice;
    @Bind(R.id.kTomET)
    EditText kToM;

    public static String Name = "EditSettingFragment";
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setToolBar();
        setData();
        return view;
    }

    private void setData() {
        float priceDefault = (float) 6.16;
        gasPrice.setText(String.valueOf(SharedPreferencesUtil.loadFloatWithDefault(SharedPreferencesUtil.GAS_PRICE, priceDefault)));
        kToM.setText(String.valueOf(SharedPreferencesUtil.loadFloatWithDefault(SharedPreferencesUtil.KILOMETER, 14)) + " ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = ((MainActivity) context);
        } else {
            throw new RuntimeException(context.toString()
                    + " Must be of MainActivity class");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mainActivity = ((MainActivity) activity);
        } else {
            throw new RuntimeException(activity.toString()
                    + " Must be of MainActivity class");
        }
    }

    private void setToolBar() {
        mainActivity.setSupportActionBar(toolbar);
        toolbar.setTitle("");
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onBackPressed();
            }
        });
    }

    @OnClick(R.id.saveBT)
    public void saveData() {
        float gas = 0;
        float kilometer = 0;
        try {
            gas = Float.parseFloat(String.valueOf(gasPrice.getText()));
            kilometer = Float.parseFloat(String.valueOf(kToM.getText()));
            if (gas > 0 && kilometer > 0) {
                SharedPreferencesUtil.saveFloat(SharedPreferencesUtil.GAS_PRICE, gas);
                SharedPreferencesUtil.saveFloat(SharedPreferencesUtil.KILOMETER, kilometer);
                mainActivity.onBackPressed();
            }else {

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "oooops1", Toast.LENGTH_LONG).show();
        }
    }
}
