package se.iDroid.phonar.sensors;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;

public class ConnectionCallbacks implements
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener {
	
	private Context context;
	
	public ConnectionCallbacks(Context context) {
		this.context = context;
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Toast.makeText(context, "ConnectionFailed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnected(Bundle arg0) {
        Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(context, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
	}

}
