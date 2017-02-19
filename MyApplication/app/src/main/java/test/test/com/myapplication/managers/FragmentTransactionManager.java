package test.test.com.myapplication.managers;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import test.test.com.myapplication.fragments.BaseFragment;


public class FragmentTransactionManager {

    public static void makeTransactionWithFragment(FragmentActivity activityBase, int containerId, BaseFragment fragment, String backstackTag) {
        android.support.v4.app.FragmentManager fragmentManager = activityBase.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(backstackTag);
        ft.replace(containerId, fragment, fragment.getFragmentTag()).commit();

    }


//    public static Fragment getTopFragment(Activity activity) {
//
//        FragmentManager fragmentManager = activity.getFragmentManager();
//        if (fragmentManager.getBackStackEntryCount()==0)
//            return null;
//        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
//        return fragmentManager.findFragmentByTag(fragmentTag);
//
//    }
}
