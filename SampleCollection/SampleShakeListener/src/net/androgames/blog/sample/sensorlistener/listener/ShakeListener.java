package net.androgames.blog.sample.sensorlistener.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener implements SensorEventListener {
	
	private static final int FORCE_THRESHOLD 	= 350;
	private static final int TIME_THRESHOLD 	= 100;
	private static final int SHAKE_TIMEOUT 		= 500;
	private static final int SHAKE_DURATION 	= 1000;
	private static final int SHAKE_COUNT 		= 3;

	private SensorManager sensorManager;
	private float lastX = -1.0f, lastY = -1.0f, lastZ = -1.0f;
	private OnShakeListener shakeListener;
	private Context context;
	private int shakeCount = 0;
	private long lastTime;
	private long lastShake;
	private long lastForce;

	public interface OnShakeListener {
		public void onShake();
	}

	public ShakeListener(Context context) {
		this.context = context;
		resume();
	}

	public void setOnShakeListener(OnShakeListener listener) {
		shakeListener = listener;
	}

	public void resume() {
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager == null) {
			throw new UnsupportedOperationException();
		}
		boolean supported = sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
		if (!supported) {
			sensorManager.unregisterListener(this);
			throw new UnsupportedOperationException();
		}
	}

	public void pause() {
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
			sensorManager = null;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int arg1) {}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != SensorManager.SENSOR_ACCELEROMETER) {
			return;
		}
		long now = event.timestamp;

		if ((now - lastForce) > SHAKE_TIMEOUT) {
			shakeCount = 0;
		}

		if ((now - lastTime) > TIME_THRESHOLD) {
			long diff = now - lastTime;
			float speed = Math.abs(event.values[SensorManager.DATA_X]
					+ event.values[SensorManager.DATA_Y]
					+ event.values[SensorManager.DATA_Z] - lastX - lastY - lastZ)
					/ diff * 10000;
			if (speed > FORCE_THRESHOLD) {
				if (++shakeCount >= SHAKE_COUNT
						&& now - lastShake > SHAKE_DURATION) {
					lastShake = now;
					shakeCount = 0;
					if (shakeListener != null) {
						shakeListener.onShake();
					}
				}
				lastForce = now;
			}
			lastTime = now;
			lastX = event.values[SensorManager.DATA_X];
			lastY = event.values[SensorManager.DATA_Y];
			lastZ = event.values[SensorManager.DATA_Z];
		}
	}

}