package net.androgames.blog.sample.orientation;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Android Orientation Sensor Manager Archetype
 * @author antoine vianey
 * under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 */
public class OrientationManager {
	
	private static Sensor sensor;
	private static SensorManager sensorManager;
	// you could use an OrientationListener array instead
	// if you plans to use more than one listener
	private static OrientationListener listener;
	
	/** indicates whether or not Orientation Sensor is supported */
	private static Boolean supported;
	/** indicates whether or not Orientation Sensor is running */
	private static boolean running = false;
	
	/** Sides of the phone */
	enum Side {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT;
	}
	
	/**
	 * Returns true if the manager is listening to orientation changes
	 */
	public static boolean isListening() {
		return running;
	}
	
	/**
	 * Unregisters listeners
	 */
	public static void stopListening() {
		running = false;
		try {
			if (sensorManager != null && sensorEventListener != null) {
				sensorManager.unregisterListener(sensorEventListener);
			}
		} catch (Exception e) {}
	}
	
	/**
	 * Returns true if at least one Orientation sensor is available
	 */
	public static boolean isSupported() {
		if (supported == null) {
			if (Orientation.getContext() != null) {
				sensorManager = (SensorManager) Orientation.getContext()
						.getSystemService(Context.SENSOR_SERVICE);
				List<Sensor> sensors = sensorManager.getSensorList(
						Sensor.TYPE_ORIENTATION);
				supported = new Boolean(sensors.size() > 0);
			} else {
				supported = Boolean.FALSE;
			}
		}
		return supported;
	}
	
	/**
	 * Registers a listener and start listening
	 */
	public static void startListening(
			OrientationListener orientationListener) {
		sensorManager = (SensorManager) Orientation.getContext()
				.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sensorManager.getSensorList(
				Sensor.TYPE_ORIENTATION);
		if (sensors.size() > 0) {
			sensor = sensors.get(0);
			running = sensorManager.registerListener(
					sensorEventListener, sensor, 
					SensorManager.SENSOR_DELAY_NORMAL);
			listener = orientationListener;
		}
	}

	/**
	 * The listener that listen to events from the orientation listener
	 */
	private static SensorEventListener sensorEventListener = 
		new SensorEventListener() {
		
		/** The side that is currently up */
		private Side currentSide = null;
		private Side oldSide = null;
		private float azimuth;
		private float pitch;
		private float roll;
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		
		public void onSensorChanged(SensorEvent event) {
			
			azimuth = event.values[0]; 	// azimuth
			pitch = event.values[1]; 	// pitch
			roll = event.values[2];		// roll
			
			if (pitch < -45 && pitch > -135) {
				// top side up
				currentSide = Side.TOP;
			} else if (pitch > 45 && pitch < 135) {
				// bottom side up
				currentSide = Side.BOTTOM;
			} else if (roll > 45) {
				// right side up
				currentSide = Side.RIGHT;
			} else if (roll < -45) {
				// left side up
				currentSide = Side.LEFT;
			}
			
			if (currentSide != null && !currentSide.equals(oldSide)) {
				switch (currentSide) {
					case TOP : 
						listener.onTopUp();
						break;
					case BOTTOM : 
						listener.onBottomUp();
						break;
					case LEFT: 
						listener.onLeftUp();
						break;
					case RIGHT: 
						listener.onRightUp();
						break;
				}
				oldSide = currentSide;
			}
			
			// forwards orientation to the OrientationListener
			listener.onOrientationChanged(azimuth, pitch, roll);
		}
		
	};

}