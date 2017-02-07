package test.test.com.myapplication;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.test.com.myapplication.fragments.SearchFragment;
import test.test.com.myapplication.interfaces.ILocationCallBack;
import test.test.com.myapplication.interfaces.OnRouteReady;
import test.test.com.myapplication.interfaces.OnRouteReady1;
import test.test.com.myapplication.utilities.LocationUtils;
import test.test.com.myapplication.utilities.PermissionUtils;

public class MainActivity extends AppCompatActivity implements OnRouteReady {
    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout mSlider;
    @Bind(R.id.fragment_container)
    FrameLayout mapLayout;
    private android.app.Fragment mainFragment;
    public static final float SLIDING_PANEL_ANCHOR_POINT = 0.50f;
    private OnRouteReady1 listener;
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        searchFragment = new SearchFragment();
        searchFragment.setOnRouteReady(this);
        mainFragment = getFragmentManager().findFragmentById(R.id.static_fragment);
        getLocation();
        setUpSlider();
    }

    private void getLocation() {
        if (PermissionUtils.isLocationPermissionsApproved(this)) {
            LocationUtils.getLocation(this, new ILocationCallBack() {
                @Override
                public void onLocation(Location location) {
                    if (location != null) {
                        changeFragment(searchFragment, SearchFragment.NAME);
                    }
                }
            });
        } else {
            PermissionUtils.requestForLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (PermissionUtils.isLocationPermissionsApproved(this)) {
            getLocation();
        }
    }

    private void setUpSlider() {
        mSlider.setCoveredFadeColor(0);
        mSlider.setOverlayed(false);
        mSlider.setAnchorPoint(SLIDING_PANEL_ANCHOR_POINT);
        mSlider.setPanelHeight(0);
        mSlider.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, final float slideOffset) {
                updateMapMargins((int) (panel.getHeight() * slideOffset) / 2);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });
    }

    private void updateMapMargins(int topMargin) {

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mapLayout.getLayoutParams();
        params.topMargin = -topMargin;
        params.bottomMargin = topMargin;
        mapLayout.setLayoutParams(params);
    }

    public void changeFragment(Fragment fragment, String className) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, fragment, className).commit();
    }

    @Override
    public void OnRouteReady(String distance) {
        listener.OnRouteReady1(distance);
        mSlider.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        mSlider.setPanelHeight((int) SLIDING_PANEL_ANCHOR_POINT);
    }

    @Override
    public void onBackPressed() {
        if (mSlider.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED || mSlider.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            mSlider.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        } else if (searchFragment != null && searchFragment.isVisible()) {
            finish();
        } else
            super.onBackPressed();

    }

    public void setOnRouteReady(OnRouteReady1 listener) {
        this.listener = listener;
    }
}
