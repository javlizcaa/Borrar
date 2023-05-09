package com.example.borrar;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.util.HashMap;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import android.location.Location;
import android.view.View;
import android.widget.TextView;

public class nearByGyms extends AppCompatActivity implements OnMapReadyCallback {

    boolean isPermissionGranted;
    GoogleMap mGoogleMap;

    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_gyms);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkMyPermission();

        if (isPermissionGranted) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
            supportMapFragment.getMapAsync(this);
        }
    }

    private void checkMyPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(nearByGyms.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(locationSuccessListener);
        }

        HashMap<Marker, Integer> markerImageMap = new HashMap<>();


        Marker marker1 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.44019094005283, -3.682605931274256))
                .title("Reto 48")
                .snippet("https://reto48.es/")
        );
        markerImageMap.put(marker1, R.mipmap.reto48);

        Marker marker2 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.4275641776547, -3.6871741749813896))
                .title("Fitup Gym")
                .snippet("https://fitupweb.es/fitup-serrano/")
        );
        markerImageMap.put(marker2, R.mipmap.fitup_gym);

        Marker marker3 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.4039580408379, -3.678239861490761))
                .title("Retiro Sport Fitness")
                .snippet("https://www.retirosportfitness.com/")
        );
        markerImageMap.put(marker3, R.mipmap.retiro_sport_fitness);

        Marker marker4 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.438179553127064, -3.6938771056693134))
                .title("Metropolitan Abascal")
                .snippet("https://clubmetropolitan.com/gimnasio/madrid/abascal")
        );
        markerImageMap.put(marker4, R.mipmap.metropolitan_abascal);

        Marker marker5 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.45345254052197, -3.692944082741032))
                .title("Forus Capitan Haya")
                .snippet("https://forus.es/centros-forus/madrid/capitan-haya/")
        );
        markerImageMap.put(marker5, R.mipmap.forus_capitan);

        Marker marker6 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.42365285503983, -3.6007224614897884))
                .title("Basic Fit")
                .snippet("https://www.basic-fit.com/es-es/home")
        );
        markerImageMap.put(marker6, R.mipmap.basic_fit);

        Marker marker7 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.458995353132075, -3.6959460749797914))
                .title("Viva Gym")
                .snippet("https://www.vivagym.es/")
        );
        markerImageMap.put(marker7, R.mipmap.viva_gym);

        Marker marker8 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.40294603357114, -3.7019531461466))
                .title("Urban Box CrossFit")
                .snippet("https://urbanboxcrossfit.com/")
        );
        markerImageMap.put(marker8, R.mipmap.urban_box);

        Marker marker9 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.42160728098425, -3.804277044615947))
                .title("Reebok Sports Club")
                .snippet("https://www.davidlloyd.es/es-es/clubs/serrano/")
        );
        markerImageMap.put(marker9, R.mipmap.reebook);

        Marker marker10 = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.42164593782702, -3.7048209038177826))
                .title("Gymage Lounge Resort")
                .snippet("https://gymage.es/gymage-lounge-resort/")
        );
        markerImageMap.put(marker10, R.mipmap.gymage_noche_neon);

        mGoogleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                TextView title = v.findViewById(R.id.title);
                TextView snippet = v.findViewById(R.id.snippet);
                ImageView image = v.findViewById(R.id.image);
                title.setText(marker.getTitle());
                snippet.setText(marker.getSnippet());
                image.setImageResource(markerImageMap.get(marker)); // Obtener la imagen correspondiente al marcador

                return v;
            }
        });


        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String url = marker.getSnippet();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private OnSuccessListener<Location> locationSuccessListener = new OnSuccessListener<Location>() {
        @Override
        public void onSuccess(Location location) {
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
            }
        }
    };


}



