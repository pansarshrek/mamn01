package se.iDroid.phonar.activities;

import se.iDroid.phonar.R;
import se.iDroid.phonar.bootstrap.Bootstrap;
import se.iDroid.phonar.sensors.ConnectionCallbacks;
import se.iDroid.phonar.sensors.SensorFusion;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

public class CreateGroupActivity extends SensorFusion {
	
	private LocationClient locationClient;
	private LatLng myPos;
	private GoogleMap map;
	private Bootstrap bootstrap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_view);
		Log.d("phonar", "starting group activity");
		ConnectionCallbacks cc = new ConnectionCallbacks(this);
		bootstrap = Bootstrap.getInstance(this);
		locationClient = bootstrap.getLocationClient();
		
		// Get a handle to the Map Fragment
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        LatLng nicklasApartment = new LatLng(55.7071961, 13.182156);

        
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(nicklasApartment, 13));

        Marker marker = map.addMarker(new MarkerOptions()
                .title("Nicklas' Apartment")
                .position(nicklasApartment));        
        

	}
	
	public void updateCamera(float bearing, LatLng pos) {
        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(pos)
                .bearing(bearing).zoom(13f).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart_view, menu);
		return true;
	}
	
	public void buttonClick(View v) {
		Location loc = locationClient.getLastLocation();
		if (loc != null) {
			String text = "Alt: " + loc.getAltitude() + ", Long: " + loc.getLongitude() + ", Lat: " + loc.getLatitude();
			Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		} else {
			Log.d("click", "Didn't work :(");
		}
	}

	@Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        locationClient.connect();
    }
	
	@Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        locationClient.disconnect();
        super.onStop();
    }

	@Override
	protected void updateCallback() {
		if (locationClient != null) {
			Location loc = locationClient.getLastLocation();
			myPos = new LatLng(loc.getLatitude(), loc.getLongitude());
//			String text = "Relative to North: " + fusedOrientation[0] + ", Rotation X-axis: " + fusedOrientation[1] + ", Rotation Y-axis: " + fusedOrientation[2];
//			Toast.makeText(this, text, Toast.LENGTH_LONG).show();
			TextView orient0 = (TextView) findViewById(R.id.orient0);
			TextView orient1 = (TextView) findViewById(R.id.orient1);
			TextView orient2 = (TextView) findViewById(R.id.orient2);
			orient0.setText("North: " + fusedOrientation[0]);
			orient1.setText("X-axis: " + fusedOrientation[1]);
			orient2.setText("Y-axis: " + fusedOrientation[2]);
			float bearing = (float) (fusedOrientation[0] * 180 / Math.PI);
			updateCamera(bearing, myPos);
		}
	}
}
