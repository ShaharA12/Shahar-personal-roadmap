package test.test.com.myapplication.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.test.com.myapplication.MainActivity;
import test.test.com.myapplication.R;
import test.test.com.myapplication.managers.FragmentTransactionManager;
import test.test.com.myapplication.utilities.SharedPreferencesUtil;

/**
 * Created by ShaharAlush on 29/01/2017.
 */
public class SettingFragment extends BaseFragment {

    public static String NAME = "SettingFragment";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gasTV)
    TextView gasPrice;
    @Bind(R.id.kTomTV)
    TextView kToM;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setToolBar();
        setData();
        return view;
    }

    private void setData() {
        gasPrice.setText(SharedPreferencesUtil.getGasPrice());
        kToM.setText(SharedPreferencesUtil.getKToM());
    }



    @OnClick(R.id.editTV)
    public void edit(){
        FragmentTransactionManager.makeTransactionWithFragment(((AppCompatActivity)getActivity()), R.id.fragment_container, new EditSettingFragment(), null);
    }
    private void setToolBar() {
        super.setToolbar((MainActivity) getActivity(),toolbar,getString(R.string.app_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
}
