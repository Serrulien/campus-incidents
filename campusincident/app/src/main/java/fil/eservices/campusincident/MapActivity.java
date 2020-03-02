package fil.eservices.campusincident;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.gson.JsonObject;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.*;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback, OnLocationClickListener, PermissionsListener, OnCameraTrackingChangedListener{

    private static final String ID_ICON = "incident_marker";
    private static String CAMPUS_CITE = "Campus Cité Scientifque";
    private static String CAMPUS_PBOIS = "Campus Pont De Bois";
    private static String CAMPUS_MOULINS = "Campus Moulins";
    private MapView mapView;
    private Spinner spinnerCampus;
    private Toolbar toolbar;
    private CameraUpdate cameraUpdate;
    private SymbolManager symbolManager;
    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    private boolean isInTrackingMode;
    private PermissionsManager permissionsManager;


    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private CarmenFeature home;
    private CarmenFeature work;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";

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
        mapView.getMapAsync((OnMapReadyCallback) this);
        setDefaultLocations();
    }

    /**
     * Chargement de la carte et choix de son style
     */
    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                addSymbolMarker(style);
                enableLocationComponent(style);
                initSearchFab();
                addUserLocations();
                // Add the symbol layer icon to map for future use
//                style.addImage(symbolIconId, BitmapFactory.decodeResource(
//                        MapActivity.this.getResources(), R.drawable.incident_marker));

                style.addImage(ID_ICON,
                        Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.incident_marker))),
                        true);

// Create an empty GeoJSON source using the empty feature collection
                setUpSource(style);

// Set up a new symbol layer for displaying the searched location's feature coordinates
                setupLayer(style);
            }
        });
    }

    private void initSearchFab() {
        findViewById(R.id.fab_location_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.mapbox_access_token))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .addInjectedFeature(home)
                                .addInjectedFeature(work)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(MapActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }

    private void addUserLocations() {
        home = CarmenFeature.builder().text("Mapbox SF Office")
                .geometry(Point.fromLngLat(-122.3964485, 37.7912561))
                .placeName("50 Beale St, San Francisco, CA")
                .id("mapbox-sf")
                .properties(new JsonObject())
                .build();

        work = CarmenFeature.builder().text("Mapbox DC Office")
                .placeName("740 15th Street NW, Washington DC")
                .geometry(Point.fromLngLat(-77.0338348, 38.899750))
                .id("mapbox-dc")
                .properties(new JsonObject())
                .build();
    }

    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(ID_ICON),
                iconOffset(new Float[] {0f, -8f})
        ));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

// Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

// Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
// Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[] {Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }

// Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);
                }
            }
        }
    }

    private void addSymbolMarker(Style style){
        // create symbol manager
        symbolManager = new SymbolManager(mapView, mapboxMap, style);
        // set non data driven properties
        symbolManager.setIconAllowOverlap(true);
        symbolManager.setTextAllowOverlap(true);

        symbolManager.addClickListener(symbol -> Toast.makeText(MapActivity.this,
                String.format("symbol clicked %s", symbol.getId()),
                Toast.LENGTH_SHORT).show());

        symbolManager.addLongClickListener(symbol -> Toast.makeText(MapActivity.this,
                String.format("symbol long clicked %s", symbol.getId()),
                Toast.LENGTH_SHORT).show());

        // symbolManager.addLongClickListener(symbol ->  symbolManager.delete(symbol));

        style.addImage(ID_ICON,
                Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.incident_marker))),
                true);

        // create a fixed circle
        SymbolOptions symbolOptions = new SymbolOptions()
                .withLatLng(new LatLng(50.609621, 3.136460))
                .withIconImage(ID_ICON)
                .withIconSize(1.5f)
                .withDraggable(true);
        symbolManager.create(symbolOptions);
    }


    private void setDefaultLocations(){
        final LatLng citeScientifique = new LatLng(50.609621, 3.136460);
        final LatLng pontDeBois = new LatLng(50.628211, 3.126170);
        final LatLng moulins = new LatLng(50.619456, 3.068495);

        toolbar = findViewById(R.id.toolbar);
        spinnerCampus = toolbar.findViewById(R.id.campus_spinner);
        toolbar.inflateMenu(R.menu.menu_in_map);

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
                    mapPositionCampus(citeScientifique);
                }else if(campusName.equals(CAMPUS_PBOIS)){
                    mapPositionCampus(pontDeBois);
                }else {
                    mapPositionCampus(moulins);
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }


    /**
     * Set camera map position when campus changes
     * @param nomCampus name campus
     */
    private void mapPositionCampus(final LatLng nomCampus) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                // We'll maintain zoom level and tilt, just want to change position
                CameraPosition old = mapboxMap.getCameraPosition();
                CameraPosition pos = new CameraPosition.Builder()
                        .target(nomCampus)
                        .zoom(old.zoom)
                        .tilt(old.tilt)
                        .build();
                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
            }
        });
    }


    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    .accuracyColor(Color.BLUE)
                    .backgroundDrawable(R.drawable.ic_my_location)
                    .build();

            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();

            // Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            // Add the location icon click listener
            locationComponent.addOnLocationClickListener((OnLocationClickListener) this);

            // Add the camera tracking listener. Fires if the map camera is manually moved.
            locationComponent.addOnCameraTrackingChangedListener((OnCameraTrackingChangedListener) this);

            findViewById(R.id.back_to_camera_tracking_mode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isInTrackingMode) {
                        isInTrackingMode = true;
                        locationComponent.setCameraMode(CameraMode.TRACKING);
                        locationComponent.zoomWhileTracking(16f);
                        Toast.makeText(MapActivity.this, "tracking_enabled",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MapActivity.this, "tracking_already_enabled",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            permissionsManager = new PermissionsManager((PermissionsListener) this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public void onLocationComponentClick() {
        if (locationComponent.getLastKnownLocation() != null) {
            Toast.makeText(this, String.format(getString(R.string.current_location),
                    locationComponent.getLastKnownLocation().getLatitude(),
                    locationComponent.getLastKnownLocation().getLongitude()), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCameraTrackingDismissed() {
        isInTrackingMode = false;
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {
        // Empty on purpose
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "on explanation needed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "permission not granted", Toast.LENGTH_LONG).show();
            finish();
        }
    }



    /**
     * Inflate the menu in map
     * @param menu menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MANUUUUUUUUUUUU", "onCreateOptionsMenu: inflate menu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_in_map, menu);
        return true;
    }

    /**
     * Action on selection of item in menu
     * @param item item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MANUUUUUUUUUUUU", "onOptionsItemSelected: item clicked");
        switch (item.getItemId()) {
            case R.id.logout:
                Log.d("MANUUUUUUUUUUUU", "onOptionsItemSelected: Logout");
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                return true;
            case R.id.support:
                Log.d("MANUUUUUUUUUUUU", "onOptionsItemSelected: Support");
                startActivity(new Intent(getBaseContext(), MapActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
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
        Intent myIntent = new Intent(getBaseContext(),   ReportActivity.class);
        startActivity(myIntent);
    }

    public void onButtonClickNotifications(View view) {
        Intent myIntent = new Intent(getBaseContext(),   NotificationActivity.class);
        startActivity(myIntent);
    }
}
