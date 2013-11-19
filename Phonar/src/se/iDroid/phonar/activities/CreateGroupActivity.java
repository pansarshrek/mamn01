package se.iDroid.phonar.activities;

import se.iDroid.phonar.R;
import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;

public class CreateGroupActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart_view, menu);
		return true;
	}
}
