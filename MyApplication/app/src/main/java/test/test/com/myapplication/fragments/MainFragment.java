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
import butterknife.OnClick;
import test.test.com.myapplication.MainActivity;
import test.test.com.myapplication.R;
import test.test.com.myapplication.utilities.SharedPreferencesUtil;

import static test.test.com.myapplication.MainActivity.FRAGMENT_ANIMATION;


public class MainFragment extends Fragment {

    @Bind(R.id.distanceET)
    TextView distanceET;
    @Bind(R.id.resultsLL)
    LinearLayout resultsLL;
    @Bind(R.id.sum_all)
    TextView sumAll;
    public static String NAME = "MainFragment";
    private MainActivity mainActivity;
    private String distance = "0";
    private float gasPrice = (float) 6.16;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        try {
            distance = getArguments().getString("distance");
            distanceET.setText(distance + " km");
            sumAll.setText(new DecimalFormat("##.##").format((calculate(distance))));
            resultsLL.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            resultsLL.setVisibility(View.INVISIBLE);
        }
        return view;
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


    @OnClick(R.id.searchBT)
    public void search() {
        mainActivity.changeFragment(new SearchFragment(), SearchFragment.NAME, FRAGMENT_ANIMATION);
    }

    @OnClick(R.id.settingBT)
    public void settingBTClicked() {
        mainActivity.changeFragment(new SettingFragment(), SettingFragment.NAME, FRAGMENT_ANIMATION);
    }
}
