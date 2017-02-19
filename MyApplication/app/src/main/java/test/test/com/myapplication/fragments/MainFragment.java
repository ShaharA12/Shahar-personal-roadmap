package test.test.com.myapplication.fragments;


import android.os.Bundle;
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

import static test.test.com.myapplication.utilities.Dataconstants.KILOMETER_DEFAULT;
import static test.test.com.myapplication.utilities.Dataconstants.PRICE_DEFAULT;


public class MainFragment extends BaseFragment implements OnRouteReady1 {

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
    private String distance = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity)getActivity()).setOnRouteReady(this);
        setData();
        return view;
    }

    private void setData() {
        gasPrice.setText(SharedPreferencesUtil.getGasPrice());
        kToM.setText(SharedPreferencesUtil.getKToM());
    }

    private float calculate(String distance) {
        float dis = Float.parseFloat(distance);
        return (dis / SharedPreferencesUtil.loadFloatWithDefault(SharedPreferencesUtil.KILOMETER, KILOMETER_DEFAULT)) * SharedPreferencesUtil.loadFloatWithDefault(SharedPreferencesUtil.GAS_PRICE, PRICE_DEFAULT);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void OnRouteReady1(String distance) {
            this.distance = distance;
            distanceET.setText(distance + getActivity().getResources().getString(R.string.kilometer));
            sumAll.setText(new DecimalFormat("##.##").format((calculate(distance))));
            resultsLL.setVisibility(View.VISIBLE);
    }
}
