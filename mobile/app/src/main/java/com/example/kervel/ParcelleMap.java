package com.example.kervel;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParcelleMap extends AppCompatActivity implements LocationListener,OnMapReadyCallback
{

    private DrawerLayout drawerlayout;
    //LocationListener get informations de localisations
    private Button btnAddEvent;
    LocationManager lm;

    //id qui represente la permission qu'on veut activer on peut avoir pleusieur permission dans notre cas que celci
    public static final int ID_Permission=44;
    private MapFragment mapFragment;
    private GoogleMap gmps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcellemap);

        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        drawerlayout = findViewById(R.id.drawerlayout);


    }

    //a chaque fois que je recois un nouveau postionnement je recharge ma carte avec cette localisation
    @Override
    public void onLocationChanged(Location location) {
        //une localisation capturé
        double altitude = location.getAltitude();
        double lon = location.getLongitude();
        //  Toast.makeText(this,"altitude "+ altitude + "long" + lon, Toast.LENGTH_SHORT).show();
        //par peur que la carte soit pas chargé
        if(gmps != null){
            LatLng postion = new LatLng(altitude, lon);
            gmps.moveCamera(CameraUpdateFactory.newLatLng(postion) );


        }
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


    @Override
    protected void onResume() {
        super.onResume();
        //tester les permissions
        chechPermission();


    }

    private void chechPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //demander d'activer l'autorisation afficher le pop
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },ID_Permission);
            return;
        }

        //recuperer le service locationmanager d'android
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        //si un fournisseur particulier est autorisé je m'abonne
        //recevoir les differente localisation sur les proveder
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //LocationManager.GPS_PROVIDER fournisseur
            //la frequence a la quel je recçois les donné

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);}
        if(lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
            //LocationManager.GPS_PROVIDER fournisseur
            //la frequence a la quel je recçois les donné
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,10000,0,this);}
        if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            //LocationManager.GPS_PROVIDER fournisseur
            //la frequence a la quel je recçois les donné
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,this);}
        //onLoadMap();
        onMapReady(gmps);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //je reviens a ce bloc une fois les permission on été activé
        if(requestCode == ID_Permission){
            //je fais le chechpermission cest pas acctivé jactive je refais le check bien activé donc je m'abonne au provider
            chechPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (lm != null){
            lm.removeUpdates(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        //capturer la carte sur laquelle travailler
//
     gmps = googleMap;

      try{
         gmps.moveCamera(CameraUpdateFactory.zoomBy(15));
          gmps.setMyLocationEnabled(true);
         // gmps.addMarker(new MarkerOptions().position(new LatLng(2.002,23.02)));
     }
      catch (NullPointerException exception){
           Log.e("mapApp", exception.toString());
        }

    }




}
