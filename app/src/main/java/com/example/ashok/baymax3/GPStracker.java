package com.example.ashok.baymax3;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;

public class GPStracker extends Service implements LocationListener {

    public Context context;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double latitude,longitude;
    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_BW_UPDATES= 1000*60;

    LocationManager locationManager;

    public GPStracker(Context context)
    {
        this.context=context;
        getLocation();
    }

    public Location getLocation()
    {
        try{
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled =locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){

            }
            else
            {
                this.canGetLocation = true;
                if(isNetworkEnabled)
                {
                    try
                    {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_FOR_UPDATE, this);
                    } catch (SecurityException e)
                    {
                        e.printStackTrace();
                    }


                    if (locationManager != null)
                    {
                        try {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if(isGPSEnabled)
                {
                    if(location == null)
                    {
                        try{locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_FOR_UPDATE,this);}catch (SecurityException e){e.printStackTrace();}

                        if(locationManager != null)
                        {
                            try{location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);}catch (SecurityException e){e.printStackTrace();}
                            if(location != null)
                            {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

        public void stopUsingGPS()
        {
            if(locationManager != null)
            {
                try{locationManager.removeUpdates(GPStracker.this);}catch (SecurityException e){e.printStackTrace();}
            }
        }

    public double getLatitude()
    {
        if(location != null)
        {
            latitude = location.getLatitude();
        }
    return latitude;
    }


    public double getLongitude()
    {
        if(location != null)
        {
            longitude= location.getLongitude();
        }
        return longitude;
    }

    public boolean CanGetLocation()
    {
        return this.canGetLocation;
    }

    public void showSettingsAlert()
    {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
        alertdialog.setTitle("GPS Setting");
        alertdialog.setMessage("Dou you want to turn it on?");
        alertdialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
alertdialog.show();
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
