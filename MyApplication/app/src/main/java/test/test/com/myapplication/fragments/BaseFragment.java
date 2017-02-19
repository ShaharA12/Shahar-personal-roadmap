package test.test.com.myapplication.fragments;


import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import test.test.com.myapplication.MainActivity;

/**
 * Created by ShaharAlush on 09/02/2017.
 */

public class BaseFragment extends Fragment {
    protected void setToolBar(MainActivity activity, Toolbar toolbar, String text) {
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (text != null)
            toolbar.setTitle(text);
    }

    protected void setToolbar(MainActivity activity, Toolbar toolbar, String text) {
        setToolBar(activity, toolbar, text);
    }

    public String getFragmentTag() {
        return getClass().getSimpleName();
    }
}