package se.iDroid.phonar.activities;

import se.iDroid.phonar.R;
import se.iDroid.phonar.bootstrap.Bootstrap;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChartViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart_view);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart_view, menu);
		return true;
	}

}
