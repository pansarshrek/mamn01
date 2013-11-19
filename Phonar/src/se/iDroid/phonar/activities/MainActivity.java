package se.iDroid.phonar.activities;

import se.iDroid.phonar.R;
import se.iDroid.phonar.bootstrap.Bootstrap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("Phonar", "starting");
		Bootstrap b = Bootstrap.getInstance();
		b.getCommunicationMonitor().createUser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void goToChart(View view)  {
		Intent intent = new Intent(this, ChartViewActivity.class);
		startActivity(intent);
	}
	
	public void goToGroup(View view){
		Intent intent = new Intent(this, CreateGroupActivity.class);
		startActivity(intent);
	}

}
