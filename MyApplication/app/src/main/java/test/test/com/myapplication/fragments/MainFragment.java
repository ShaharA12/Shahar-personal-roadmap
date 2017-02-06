package test.test.com.myapplication.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.test.com.myapplication.MainActivity;
import test.test.com.myapplication.R;
import test.test.com.myapplication.interfaces.OnRouteReady1;
import test.test.com.myapplication.utilities.SharedPreferencesUtil;


public class MainFragment extends Fragment implements OnRouteReady1 {

    @Bind(R.id.distanceET)
    TextView distanceET;
    @Bind(R.id.resultsLL)
    LinearLayout resultsLL;
    @Bind(R.id.sum_all)
    TextView sumAll;
    @Bind(R.id.gasTV)
    TextView gasPrice;
    @Bind(R.id.kTomTV)
    TextView kToM;

    public static String NAME = "MainFragment";
    private MainActivity mainActivity;
    private String distance = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mainActivity.setOnRouteReady(this);
        setData();
        return view;
    }

    private void setData() {
        float priceDefault = (float) 6.16;
        gasPrice.setText(String.valueOf(SharedPreferencesUtil.loadFloatWithDefault(SharedPreferencesUtil.GAS_PRICE, priceDefault)));
        kToM.setText(String.valueOf(SharedPreferencesUtil.loadFloatWithDefault(SharedPreferencesUtil.KILOMETER, 14)) + " " + getActivity().getResources().getString(R.string.ktom_tv));
    }

    private float calculate(String distance) {
        float dis = Float.parseFloat(distance);
        float priceDefault = (float) 6.16;
        return (dis / SharedPreferencesUtil.loadFloatWithDefault(SharedPreferencesUtil.KILOMETER, 14)) * SharedPreferencesUtil.loadFloatWithDefault(SharedPreferencesUtil.GAS_PRICE, priceDefault);
    }

    @Override
    public void onResume() {
        super.onResume();
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


    @Override
    public void OnRouteReady1(String distance) {
        try {
            this.distance = distance;
            distanceET.setText(distance + " km");
            sumAll.setText(new DecimalFormat("##.##").format((calculate(distance))));
            resultsLL.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            resultsLL.setVisibility(View.INVISIBLE);
        }
    }
}
