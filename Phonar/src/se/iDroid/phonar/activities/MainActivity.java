package se.iDroid.phonar.activities;

import se.iDroid.phonar.R;
import se.iDroid.phonar.bootstrap.Bootstrap;
import se.iDroid.phonar.data.Data;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SharedPreferences sp = getSharedPreferences(Data.DATAFILE, 0);

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
		Bootstrap b = Bootstrap.getInstance(this);
		b.getCommunicationMonitor().createUser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void goToChart(View view) {
		Intent intent = new Intent(this, ChartViewActivity.class);
		startActivity(intent);
	}

	public void goToGroup(View view) {
		Intent intent = new Intent(this, CreateGroupActivity.class);
		startActivity(intent);
	}

}
