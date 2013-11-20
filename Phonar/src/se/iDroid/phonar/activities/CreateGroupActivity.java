package se.iDroid.phonar.activities;

import se.iDroid.phonar.R;
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

public class CreateGroupActivity extends SensorFusion implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	
	private LocationClient mLocationClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_view);
		Log.d("phonar", "starting group activity");
		mLocationClient = new LocationClient(this, this, this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart_view, menu);
		return true;
	}
	
	public void buttonClick(View v) {
		Location loc = mLocationClient.getLastLocation();
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
        mLocationClient.connect();
    }
	
	@Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
	
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Toast.makeText(this, "ConnectionFailed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnected(Bundle arg0) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void updateCallback() {
		String text = "Relative to North: " + fusedOrientation[0] + ", Rotation X-axis: " + fusedOrientation[1] + ", Rotation Y-axis: " + fusedOrientation[2];
//		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		TextView orient0 = (TextView) findViewById(R.id.orient0);
		TextView orient1 = (TextView) findViewById(R.id.orient1);
		TextView orient2 = (TextView) findViewById(R.id.orient2);
		orient0.setText("North: " + fusedOrientation[0]);
		orient1.setText("X-axis: " + fusedOrientation[1]);
		orient2.setText("Y-axis: " + fusedOrientation[2]);
	}
}
