package se.iDroid.phonar.activities;

import geo.GeoObj;
import gl.GL1Renderer;
import gl.GLFactory;

import java.util.ArrayList;
import java.util.HashMap;

import se.iDroid.phonar.R;
import se.iDroid.phonar.bootstrap.Bootstrap;
import se.iDroid.phonar.data.Data;
import se.iDroid.phonar.model.User;
import se.iDroid.phonar.sensors.SensorFusion;
import system.ArActivity;
import system.DefaultARSetup;
import util.Vec;
import worldData.World;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends SensorFusion implements
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	
	private LocationClient locationClient;
	private LatLng myPos;
	private GoogleMap map;
	private Bootstrap bootstrap;
	private LocationRequest locationRequest;
	private HashMap<String, Marker> mapMarkers;
	private int nbrDummyLocations = 4;
	private ViewFlipper viewFlipper;
	private boolean connected = false;
	private Marker mPositionMarker;
	//private Bitmap bm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("phonar", "starting group activity");
		mapMarkers = new HashMap<String, Marker>();
		viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		final SharedPreferences sp = getSharedPreferences(Data.DATAFILE, 0);
		myPos = new LatLng(0, 0);
		if (!sp.contains(Data.USERNAME)) {
			// SHOW DIALOG
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Username");
			alert.setMessage("Please Enter Your Username:");

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value = input.getText().toString();
							sp.edit().putString(Data.USERNAME, value).commit();
						}
					});

			alert.show();
		}
				
		Log.d("Phonar", "starting");
		
		bootstrap = new Bootstrap(this);
		bootstrap.getCommunicationMonitor().createUser();
		locationClient = new LocationClient(this, this, this);
		
		locationRequest = LocationRequest.create();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(5000);
		locationRequest.setFastestInterval(1000);
		
		// Get a handle to the Map Fragment
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        
        map.moveCamera(CameraUpdateFactory.zoomTo(13));
        
        
        mPositionMarker = map.addMarker(new MarkerOptions().flat(true)
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.position_indicator)).anchor(0.5f, 0.5f)
        		.position(myPos));
	}
	
	private void placeDummyLocations(int nbrDummyLocations) {
		
		ArrayList<User> users = new ArrayList<User>();
		if(!mapMarkers.containsKey("person 1")){
			for(int i = 0; i <nbrDummyLocations;i++){
				double randomLong =  1+(Math.random() -0.5)*0.002;
				double randomLat = 1+(Math.random() -0.5)*0.001;//latitude has half the range of longitude
				users.add(new User("person "+ (i+1),myPos.latitude*randomLat,myPos.longitude*randomLong, 0));
			}
		}
		else{
			for(int i = 0; i <nbrDummyLocations;i++){
				double randomLong =  1+(Math.random() -0.5)*0.0002;
				double randomLat = 1+(Math.random() -0.5)*0.0001;//latitude has half the range of longitude
				LatLng startLocation = mapMarkers.get("person " +(i+1)).getPosition();
				users.add(new User("person "+ (i+1), startLocation.latitude*randomLat,startLocation.longitude*randomLong, 0));
			}
		}
		
		bootstrap.getModel().updateUserCoords(users);
	}

	public void setMarker(String name, LatLng pos) {
		if (mapMarkers.containsKey(name)) {
			Marker m = mapMarkers.get(name);
			m.setPosition(pos);
		} else {
			Marker marker = map.addMarker(new MarkerOptions()
                .title(name)
                .position(pos)); 
			Bitmap bm = drawTextToBitmap(this, R.drawable.marker_small, name);
			marker.setIcon(BitmapDescriptorFactory.fromBitmap(bm));
			mapMarkers.put(name, marker);
		}
	}
	
	public void updateBearing(float bearing) {
		map.moveCamera(CameraUpdateFactory.newCameraPosition(
				new CameraPosition.Builder(map.getCameraPosition())
					.bearing(bearing).build()));
	}
	
	public void updatePos(LatLng pos) {
        CameraPosition currentPlace = new CameraPosition.Builder(map.getCameraPosition())
                .target(pos).build();
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
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
        locationClient.disconnect();
        super.onStop();
    }
	
	@Override
	protected void updateCallback() {
		if (locationClient != null) {
			float bearing = (float) (fusedOrientation[0] * 180 / Math.PI);
			updatePos(myPos);
			updateBearing(bearing);
			mPositionMarker.setPosition(myPos);
			mPositionMarker.setRotation(bearing);
			checkIfPointingAtSomeone(myPos, bearing);
			
			if(viewFlipper.getDisplayedChild() == 0 && fusedOrientation[1] <-1.3){
				viewFlipper.setInAnimation(this, R.anim.in_from_top);
		        viewFlipper.setOutAnimation(this, R.anim.out_to_top);
		        viewFlipper.showNext();
		        buildARView();
			}
			else if(viewFlipper.getDisplayedChild() == 1 && fusedOrientation[1] >-0.7){
				viewFlipper.setInAnimation(this, R.anim.in_from_bottom);
		        viewFlipper.setOutAnimation(this, R.anim.out_to_bottom);
		        viewFlipper.showPrevious();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Toast.makeText(this, "ConnectionFailed", Toast.LENGTH_SHORT).show();
	}

	
	
	@Override
	public void onConnected(Bundle arg0) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        locationClient.requestLocationUpdates(locationRequest, this);
        connected = true;
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLocationChanged(Location loc) {
		Toast.makeText(this, "location updated", Toast.LENGTH_SHORT).show();
		myPos = new LatLng(loc.getLatitude(), loc.getLongitude());
		updatePos(myPos);
		
		bootstrap.getModel().setLatitude(loc.getLatitude());
		bootstrap.getModel().setLongitude(loc.getLongitude());
//		placeDummyLocations(nbrDummyLocations);
	}
	
	public void checkIfPointingAtSomeone(LatLng pos, float bearing) {
		if (connected) {
			Location locA, locB; 
			locA = locationClient.getLastLocation();
			locB = locationClient.getLastLocation();
			
			if (locA != null && locB != null) {
				locA.setLatitude(pos.latitude);
				locA.setLongitude(pos.longitude);
				
				bootstrap.vibrateIfPointedAt(locA, locB, bearing);
			}
		}
	}
	
	public Bitmap drawTextToBitmap(Context gContext, int gResId, String gText) {
		Resources resources = gContext.getResources();
		float scale = resources.getDisplayMetrics().density;
		Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

		android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
		// set default bitmap config if none
		if (bitmapConfig == null) {
			bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
		}
		// resource bitmaps are imutable,
		// so we need to convert it to mutable one
		bitmap = bitmap.copy(bitmapConfig, true);

		Canvas canvas = new Canvas(bitmap);
		// new antialised Paint
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// text color - #3D3D3D
		paint.setColor(Color.rgb(61, 61, 61));
		// text size in pixels
		paint.setTextSize((int) (14 * scale));
		// text shadow
		paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

		// draw text to the Canvas center
		Rect bounds = new Rect();
		paint.getTextBounds(gText, 0, gText.length(), bounds);
		int x = (bitmap.getWidth() - bounds.width()) / 2;
		int y = (bitmap.getHeight() + bounds.height()) / 2;

		canvas.drawText(gText, x, y-15, paint);

		return bitmap;
	}
	

	public void buildARView() {
		ArActivity.startWithSetup(this, new DefaultARSetup() {

			@Override
			public void addObjectsTo(GL1Renderer renderer, World world, GLFactory objectFactory) {
				//GeoObj o = new GeoObj();
				//o.setComp()
				//o.setComp(objectFactory.newTextObject());
				//for(int i = 0; i < mapMarkers.size(); i++) {
				world.add(objectFactory.newPositionMarker(camera));
				//}
			}
			
		});
	}
	

}
