package fil.eservices.campusincident;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {


    private static String CAMPUS_CITE = "Campus Cité Scientifque";
    private static String CAMPUS_PBOIS = "Campus Pont De Bois";
    private static String CAMPUS_MOULINS = "Campus Moulins";
    private MapView mapView;
    private Spinner spinnerCampus;
    private Toolbar toolbar;
    private CameraUpdate cameraUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String MAP_TOKEN = getString(R.string.mapbox_access_token);

        /**
         * Mettre en place la MapBox avec son token MAP_TOKEN
         */
        Mapbox.getInstance(getApplicationContext(), MAP_TOKEN);
        setContentView(R.layout.activity_map);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        /**
         * Chargement de la carte et choix de son style
         */
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments

                    }
                });

            }
        });

        final LatLng citeScientifique = new LatLng(50.609621, 3.136460);
        final LatLng pontDeBois = new LatLng(50.628211, 3.126170);
        final LatLng moulins = new LatLng(50.619456, 3.068495);

        toolbar = findViewById(R.id.toolbar);
        spinnerCampus = toolbar.findViewById(R.id.campus_spinner);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(CAMPUS_CITE);
        arrayList.add(CAMPUS_PBOIS);
        arrayList.add(CAMPUS_MOULINS);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCampus.setAdapter(arrayAdapter);
        spinnerCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String campusName = parent.getItemAtPosition(position).toString();

                if (campusName.equals(CAMPUS_CITE)){

                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull MapboxMap mapboxMap) {
                            // We'll maintain zoom level and tilt, just want to change position
                            CameraPosition old = mapboxMap.getCameraPosition();
                            CameraPosition pos = new CameraPosition.Builder()
                                    .target(citeScientifique)
                                    .zoom(old.zoom)
                                    .tilt(old.tilt)
                                    .build();
                            mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
                        }
                    });

                }else if(campusName.equals(CAMPUS_PBOIS)){

                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull MapboxMap mapboxMap) {
                            // We'll maintain zoom level and tilt, just want to change position
                            CameraPosition old = mapboxMap.getCameraPosition();
                            CameraPosition pos = new CameraPosition.Builder()
                                    .target(pontDeBois)
                                    .zoom(old.zoom)
                                    .tilt(old.tilt)
                                    .build();
                            mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
                        }
                    });

                }else {

                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull MapboxMap mapboxMap) {
                            // We'll maintain zoom level and tilt, just want to change position
                            CameraPosition old = mapboxMap.getCameraPosition();
                            CameraPosition pos = new CameraPosition.Builder()
                                    .target(moulins)
                                    .zoom(old.zoom)
                                    .tilt(old.tilt)
                                    .build();
                            mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
                        }
                    });

                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void onButtonClick(View view) {
        Intent myIntent = new Intent(getBaseContext(),   DetailsActivity.class);
        startActivity(myIntent);
    }

    public void onButtonClickNotifications(View view) {
        Intent myIntent = new Intent(getBaseContext(),   NotificationActivity.class);
        startActivity(myIntent);
    }
}
