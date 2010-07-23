package net.androgames.blog.sample.sensorlistener;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorListener extends Activity implements SensorEventListener {
    
	SensorManager sensorManager = null;
    private TextView x, y, z, ax, ay, az;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(R.layout.main);
        // initialize display
        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.y);
        z = (TextView) findViewById(R.id.z);
        ax = (TextView) findViewById(R.id.ax);
        ay = (TextView) findViewById(R.id.ay);
        az = (TextView) findViewById(R.id.az);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, 
        		sensorManager.getDefaultSensor(SensorManager.SENSOR_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, 
        		sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, 
        		sensorManager.getDefaultSensor(SensorManager.SENSOR_ORIENTATION));
        sensorManager.unregisterListener(this, 
        		sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER));
    }
    
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
        	switch (event.sensor.getType()) {
	        	case SensorManager.SENSOR_ORIENTATION :
	        		x.setText(String.valueOf(event.values[SensorManager.DATA_X]));
	        		y.setText(String.valueOf(event.values[SensorManager.DATA_Y]));
	        		z.setText(String.valueOf(event.values[SensorManager.DATA_Z]));
	        		break;
	        	case SensorManager.SENSOR_ACCELEROMETER :
	        		ax.setText(String.valueOf(event.values[SensorManager.DATA_X]));
	        		ay.setText(String.valueOf(event.values[SensorManager.DATA_Y]));
	        		az.setText(String.valueOf(event.values[SensorManager.DATA_Z]));
	        		break;
        	}           
        }
	} 
	
}