package se.iDroid.phonar.feedback;

import android.content.Context;
import android.os.Vibrator;

public class PhonarVibrator {
	
	private Vibrator v;
	private Context c;
	
	public PhonarVibrator(Context context) {
		this.c = context;
		v = (Vibrator)c.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	public void vib() {
		v.vibrate(100);
	}

}
