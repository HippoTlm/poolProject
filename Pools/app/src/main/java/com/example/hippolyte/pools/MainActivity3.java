package com.example.hippolyte.pools;




import com.google.android.gms.maps.MapView;
/**
 * Created by Hippolyte on 20/11/2017.
 */

public class MainActivity3 extends MapActivity{

    private MapView mapView;
    private MapController mc;
    private GeoPoint location;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.mapView =  new MapView(this,this.getResources().getString(R.string.mapskey));
        this.mapView.setClickable(true);
        this.mc = this.mapView.getController();
        double latitude = 50.606;
        double longitude = 3.15;
        this.location = new GeoPoint((int) (latitude * 1000000.0),(int) (longitude * 1000000.0));
        this.mc.setCenter(this.location);
        this.mc.setZoom(17);
        this.mapView.setSatellite(true);
        this.mapView.invalidate();

        this.setContentView(this.mapView);
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
        this.mc.setCenter(this.location);
        this.mapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}