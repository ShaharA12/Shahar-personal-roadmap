package test.test.com.myapplication.utilities;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Timer;
import java.util.TimerTask;

//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;

public class LocationUtils implements LocationListener {

//    private static LocationUtils instance;
    private GoogleApiClient mGoogleApiClient;
    private boolean isConnected = false;
    private boolean currentlyAskingForLocation = false;
    private ILocationCallBack currentCallBack = null;

//    public static LocationUtils getInstance() {
//        if (instance == null) {
//            instance = new LocationUtils();
//        }
//        return instance;
//    }

    public void initialize(Context context, final LocationConnectionCallBack connectionCallBack) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            isConnected = true;
                            if (connectionCallBack != null) {
                                connectionCallBack.onConnectSuccess();
                            }
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            isConnected = false;
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            isConnected = false;
                            if (connectionCallBack != null) {
                                connectionCallBack.onConnectFailed();
                            }
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public boolean connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            return true;
        }
        return false;
    }

    public boolean disconnect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
            return true;
        }
        return false;
    }

    private Timer getLocationTimer;
    private TimerTask getLocationTimerTask;

    public void getLastKnownLocation(ILocationCallBack locationCallBack) throws SecurityException {
        if (mGoogleApiClient != null && isConnected) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                long diff = System.currentTimeMillis() - location.getTime();
                if (diff < 1000 * 30 && location.hasAccuracy() && location.getAccuracy() < 100) {
                    if (locationCallBack != null) {
                        locationCallBack.onLocation(location);
                    }
                    this.disconnect();
                    return;
                }
            }
            currentCallBack = locationCallBack;
            scheduleTimer();
            startLocationUpdates();
        }else {
            if (locationCallBack != null) {
                locationCallBack.onLocation(null);
            }
        }
    }

    private Object locker = new Object();

    private void scheduleTimer() {
        cancelTimer();
        getLocationTimer = new Timer();
        getLocationTimerTask = new TimerTask() {
            @Override
            public void run() {
                synchronized (locker) {
                    if (currentCallBack != null) {
                        currentCallBack.onLocation(null);
                    }
                    currentCallBack = null;
                }
                stopLocationUpdates();
            }
        };
        getLocationTimer.schedule(getLocationTimerTask,5000);
    }
    private void cancelTimer() {
        if (getLocationTimerTask != null ) {
            getLocationTimerTask.cancel();
        }
        if (getLocationTimer != null) {
            getLocationTimer.purge();
            getLocationTimer.cancel();
        }
        getLocationTimer = null;
        getLocationTimerTask = null;
    }


    @Override
    public void onLocationChanged(Location location) {
        synchronized (locker) {
            if (currentCallBack != null) {
                if (location != null && location.hasAccuracy() && location.getAccuracy() < 100) {
                    currentCallBack.onLocation(location);
                    currentCallBack = null;
                    cancelTimer();
                    stopLocationUpdates();
                }
            }
        }

    }

    private void startLocationUpdates() throws SecurityException {
        if (!currentlyAskingForLocation) {
            currentlyAskingForLocation = true;
            LocationRequest mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(0);
            mLocationRequest.setFastestInterval(0);

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    private void stopLocationUpdates() {
        currentlyAskingForLocation = false;
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }catch (IllegalStateException e) {
//            L.w("Illegal state exception : " + e.toString());
        }
        disconnect();
    }


    public static void getLocation(Context context, final ILocationCallBack locationCallBack) {
//        L.i("LOCATION: getLocation");
        final LocationUtils locationUtils = new LocationUtils();
        locationUtils.initialize(context, new LocationConnectionCallBack() {
            @Override
            public void onConnectSuccess() {
//                L.i("LOCATION: onConnectSuccess");
                locationUtils.getLastKnownLocation(locationCallBack);
            }

            @Override
            public void onConnectFailed() {
//                L.i("LOCATION: onConnectFailed");
                if (locationCallBack != null) {
                    locationCallBack.onLocation(null);
                }
            }
        });
        locationUtils.connect();
    }

    private interface LocationConnectionCallBack {
        void onConnectSuccess();
        void onConnectFailed();
    }
}
