package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawer;

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load/initialize the osmdroid configuration, this can be done
        Configuration.getInstance().load(   getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()) );
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_main);
        createMenu();
        createMap();

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_carte:
                setTitle("Cartes");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_mes_recommandations:
                setTitle("Mes recommandations");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MesRecommandationsFragment()).commit();
                break;
            case R.id.nav_mesAmis:
                setTitle("Mes amis");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MesAmisFragment()).commit();
                break;
            case R.id.nav_parametres:
                setTitle("Paramètres");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ParametresFragment()).commit();
                break;

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    public void createMenu(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setTitle("Mes recommandations");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MesRecommandationsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_mes_recommandations);
        }
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void createMap(){
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);    //render
        map.setBuiltInZoomControls(true);               // zoomable
        //map.setMultiTouchControls(true);                //  zoom with 2 fingers

        GeoPoint startPoint = new GeoPoint(43.65020, 7.00517);
        IMapController mapController = map.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(18.0);


        //create a new item to draw on the map
        //your items
        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem home = new OverlayItem("J.Decobert", "nos bureaux", new GeoPoint(43.65020,7.00517));
        Drawable m = home.getMarker(0);

        items.add(home); // Lat/Lon decimal degrees
        items.add(new OverlayItem("Producteur de lait", "chez ???", new GeoPoint(43.64950,7.00517))); // Lat/Lon decimal degrees

        //the Place icons on the map with a click listener
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        //do something
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return false;
                    }
                });


        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);
    }
}
