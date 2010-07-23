package net.androgames.blog.sample.orientation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Android orientation sensor tutorial
 * @author antoine vianey
 * under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 */
public class Orientation extends Activity implements OrientationListener {
	
	private static Context CONTEXT;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CONTEXT = this;
    }

    protected void onResume() {
    	super.onResume();
    	if (OrientationManager.isSupported()) {
    		OrientationManager.startListening(this);
    	}
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	if (OrientationManager.isListening()) {
    		OrientationManager.stopListening();
    	}
    	
    }

    public static Context getContext() {
		return CONTEXT;
	}

	@Override
	public void onOrientationChanged(float azimuth, 
			float pitch, float roll) {
		((TextView) findViewById(R.id.azimuth)).setText(
				String.valueOf(azimuth));
		((TextView) findViewById(R.id.pitch)).setText(
				String.valueOf(pitch));
		((TextView) findViewById(R.id.roll)).setText(
				String.valueOf(roll));
	}

	@Override
	public void onBottomUp() {
		Toast.makeText(this, "Bottom UP", 1000).show();
	}

	@Override
	public void onLeftUp() {
		Toast.makeText(this, "Left UP", 1000).show();
	}

	@Override
	public void onRightUp() {
		Toast.makeText(this, "Right UP", 1000).show();
	}

	@Override
	public void onTopUp() {
		Toast.makeText(this, "Top UP", 1000).show();
	}
    
}