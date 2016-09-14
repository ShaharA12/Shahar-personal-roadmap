package test.test.com.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import test.test.com.myapplication.fragments.MainFragment;

public class MainActivity extends AppCompatActivity// implements OnMapReadyCallback
{
    public static int FRAGMENT_NO_ANIMATION = 0;
    public static int FRAGMENT_ANIMATION = 1;
    public static int FRAGMENT_BACK_ANIMATION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new MainFragment(), MainFragment.NAME, FRAGMENT_ANIMATION);
    }


    public void changeFragment(Fragment fragment, String className, int animationState) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        int i=R.animator.slide_in_right;
        if (animationState == FRAGMENT_ANIMATION) {
       //    ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fragment_scale_out);
        } else if (animationState == FRAGMENT_BACK_ANIMATION) {
       //     ft.setCustomAnimations(R.animator.fragment_scale_in, R.animator.slide_out_right);
        }
        ft.replace(R.id.fragment_container, fragment, className).commit();
//        FragmentTransaction ft  = getSupportFragmentManager().beginTransaction();
////        fragmentManager.beginTransaction()
////                .replace(R.id.fragment_container, fragment).commit();
//
//        if (animationState == FRAGMENT_ANIMATION) {
//            ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fragment_scale_out);
//        } else if (animationState == FRAGMENT_BACK_ANIMATION) {
//            ft.setCustomAnimations(R.animator.fragment_scale_in, R.animator.slide_out_right);
//        }
//        ft.replace(R.id.fragment_container, fragment, className).commit();
    }
}
