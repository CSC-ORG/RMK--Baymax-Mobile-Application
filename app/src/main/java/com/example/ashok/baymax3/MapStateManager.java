package com.example.ashok.baymax3;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

@SuppressWarnings("unused")
public class MapStateManager {

    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String ZOOM = "zoom";
    private static final String BEARING = "bearing";
    private static final String TILT = "tilt";
    //private static final String MAPTYPE = "maptype";
    private static final String PREFS_NAME = "mapCameraState";
    private SharedPreferences mapSatePref;

    public MapStateManager(Context context)
    {
        mapSatePref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

    public void saveMapState(GoogleMap map)
    {
        SharedPreferences.Editor editor = mapSatePref.edit();
        CameraPosition position = map.getCameraPosition();

        editor.putFloat(LATITUDE,(float) position.target.latitude);
        editor.putFloat(LONGITUDE,(float) position.target.longitude);
        editor.putFloat(ZOOM, position.zoom);
        editor.putFloat(BEARING, position.bearing);
        editor.putFloat(TILT, position.tilt);

        editor.apply();
    }

    public CameraPosition getSavedCameraPosition()
    {
        double latitude = mapSatePref.getFloat(LATITUDE,0);
        if (latitude==0)
            return null;

        double longitude = mapSatePref.getFloat(LONGITUDE,0);
        LatLng target = new LatLng(latitude,longitude);
        float zoom = mapSatePref.getFloat(ZOOM,0);
        float bearing = mapSatePref.getFloat(BEARING,0);
        float tilt = mapSatePref.getFloat(TILT,0);

        CameraPosition pos = new CameraPosition(target,zoom,tilt,bearing);
        return pos;

    }
}
