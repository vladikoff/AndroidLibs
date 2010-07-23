package net.androgames.blog.sample.magnetic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Android magnetic field sensor tutorial
 * @author antoine vianey
 * under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 */
public class MagneticField extends Activity implements MagneticFieldListener {
	
	private static Context CONTEXT;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CONTEXT = this;
    }

    protected void onResume() {
    	super.onResume();
    	if (MagneticFieldManager.isSupported()) {
    		MagneticFieldManager.startListening(this);
    	}
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	if (MagneticFieldManager.isListening()) {
    		MagneticFieldManager.stopListening();
    	}
    	
    }
	
    public static Context getContext() {
		return CONTEXT;
	}

	@Override
	public void onCompassChanged(float x, float y, float z) {
		((TextView) findViewById(R.id.x)).setText(String.valueOf(x));
		((TextView) findViewById(R.id.y)).setText(String.valueOf(y));
		((TextView) findViewById(R.id.z)).setText(String.valueOf(z));
	}
    
}