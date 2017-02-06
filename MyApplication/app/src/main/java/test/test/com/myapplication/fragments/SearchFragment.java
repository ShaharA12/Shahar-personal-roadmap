package test.test.com.myapplication.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.test.com.myapplication.MainActivity;
import test.test.com.myapplication.R;
import test.test.com.myapplication.interfaces.OnRouteReady;
import test.test.com.myapplication.utilities.ILocationCallBack;
import test.test.com.myapplication.utilities.LocationUtils;
import test.test.com.myapplication.utilities.PermissionUtils;

public class SearchFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    public static String NAME = "SearchFragment";

    @Bind(R.id.searchFromLL)
    LinearLayout searchFromLL;
    @Bind(R.id.sendBT)
    Button send;
    private LatLng latLongFrom = null;
    private LatLng latLongTo = null;
    private GoogleMap googleMap = null;
    MapView gMapView;
    GoogleMap gMap = null;
    private String distance = "0";
    private MainActivity mainActivity;
    private PlaceAutocompleteFragment autocompleteFragmentFrom;
    private OnRouteReady listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        MapsInitializer.initialize(getActivity().getApplicationContext());
        gMapView = (MapView) view.findViewById(R.id.map);
        gMapView.onCreate(savedInstanceState);
        gMapView.getMapAsync(this);
        PlaceAutocompleteFragment autocompleteFragmentTo = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.searchTo);
        autocompleteFragmentTo.setHint("Choose destination");
        autocompleteFragmentTo.setOnPlaceSelectedListener(onPlaceSelectedListenerTo);
        autocompleteFragmentFrom = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.searchFrom);
        autocompleteFragmentFrom.setText("Your location");
        autocompleteFragmentFrom.setOnPlaceSelectedListener(onPlaceSelectedListenerFrom);
        searchFromLL.setVisibility(View.GONE);
        getLocation();
        return view;
    }

    private void getLocation() {
        if (PermissionUtils.isLocationPermissionsApproved(mainActivity)) {
            LocationUtils.getLocation(mainActivity, new ILocationCallBack() {
                @Override
                public void onLocation(Location location) {
                    if (location != null) {
                        latLongFrom = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            });
        } else {
            PermissionUtils.requestForLocationPermissions(mainActivity);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (PermissionUtils.isLocationPermissionsApproved(getActivity())) {
            getLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        gMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null) {
            try {
                android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                android.app.Fragment fragment = fragmentManager.findFragmentById(R.id.searchFrom);
                android.app.Fragment fragment1 = fragmentManager.findFragmentById(R.id.searchTo);
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragment);
                fragmentTransaction.remove(fragment1);
                fragmentTransaction.commit();
            }catch (Exception e){}
        }
    }

    @OnClick(R.id.settingBT)
    public void settingBTClicked() {
        mainActivity.changeFragment(new SettingFragment(), SettingFragment.NAME);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        gMapView.onResume();
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

    private PlaceSelectionListener onPlaceSelectedListenerTo = new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(Place place) {
            // TODO: Get info about the selected place.
            Log.i(NAME, "Place: " + place);
            latLongTo = place.getLatLng();
            searchFromLL.setVisibility(View.VISIBLE);
            routeCalculation();
        }

        @Override
        public void onError(Status status) {
            // TODO: Handle the error.
            Log.i(NAME, "An error occurred: " + status);
        }
    };
    private PlaceSelectionListener onPlaceSelectedListenerFrom = new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(Place place) {
            // TODO: Get info about the selected place.
            Log.i(NAME, "Place: " + place);
            latLongFrom = place.getLatLng();
            routeCalculation();
        }

        @Override
        public void onError(Status status) {
            // TODO: Handle the error.
            Log.i(NAME, "An error occurred: " + status);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        gMapView.onDestroy();
    }

    private void routeCalculation() {
        if (latLongFrom != null && latLongTo != null && googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLongFrom, 14));
            final GoogleMap googleMap1 = googleMap;
            googleMap1.clear();
            GoogleDirection.withServerKey("AIzaSyCR_aY-ewaZBbJzZm8rh2-GMxvAr4mLSXg")
                    .from(latLongFrom)
                    .to(latLongTo)
                    .avoid(AvoidType.FERRIES)
                    .avoid(AvoidType.HIGHWAYS)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            if (direction.isOK()) {
                                try {
                                    JSONObject obj = new JSONObject(rawBody);
                                    JSONObject tmp = (JSONObject) obj.getJSONArray("routes").get(0);
                                    JSONObject tmp2 = (JSONObject) tmp.getJSONArray("legs").get(0);
                                    JSONObject tmp3 = tmp2.getJSONObject("distance");
                                    showDistance(tmp3.getString("text"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<LatLng> sectionPositionList = direction.getRouteList().get(0).getLegList().get(0).getSectionPoint();
                                for (LatLng position : sectionPositionList) {
//                                googleMap1.addMarker(new MarkerOptions().position(position));
                                }

                                List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(getActivity().getApplicationContext(), stepList, 5, Color.RED, 3, Color.BLUE);
                                for (PolylineOptions polylineOption : polylineOptionList) {
                                    googleMap1.addPolyline(polylineOption);
                                }

                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            // Do something
                        }
                    });
        } else if (latLongFrom == null) {
            autocompleteFragmentFrom.setHint("From...");
        }
    }

    private void showDistance(String text) {
        String[] parts = text.split(" ");
        distance = parts[0];
        listener.OnRouteReady(distance);
        send.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.sendBT)
    public void sendRoute() {
        listener.OnRouteReady(distance);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
//        googleMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude())));
        if (location != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setOnRouteReady(OnRouteReady listener) {
        this.listener = listener;
    }
}