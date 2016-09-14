package test.test.com.myapplication.fragments;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.test.com.myapplication.MainActivity;
import test.test.com.myapplication.R;


public class MainFragment extends Fragment {

    @Bind(R.id.distanceET)
    EditText distanceET;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    public static String NAME = "MainFragment";
    private MainActivity mainActivity;
    private String distance = "0";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        radioGroup.setOnCheckedChangeListener(checkedChanged);
        try {
            distance=getArguments().getString("distance");
            distanceET.setText(distance);
        }
        catch (Exception e){

        }
        return view;
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


    private RadioGroup.OnCheckedChangeListener checkedChanged=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.radio_kl:

                    break;
                case R.id.radio_mi:

                    break;
            }
        }
    } ;
    @OnClick(R.id.searchBT)
    public void search(){
        mainActivity.changeFragment(new SearchFragment(),SearchFragment.NAME,mainActivity.FRAGMENT_ANIMATION);


    }
}
