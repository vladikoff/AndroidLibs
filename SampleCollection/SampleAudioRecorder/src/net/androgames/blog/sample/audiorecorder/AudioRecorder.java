package net.androgames.blog.sample.audiorecorder;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

/**
 * Send order
 * AndroSpy
 * send by email or store simply
 * @author avianey
 *
 */
public class AudioRecorder extends Activity {
	
	private MediaRecorder recorder;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	File audiofile = null;

	protected void startRecording(File dest) throws IOException {
		// set up the recorder
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//		if (mSampleFile == null) {
//			File sampleDir = Environment.getDataDirectory();
//			try {
//				audiofile = File.createTempFile("ibm", ".3gp", sampleDir);
//			} catch (IOException e) {
//				Log.e(TAG, "sdcard access error");
//				return;
//			}
//		}
		recorder.setOutputFile(dest.getAbsolutePath());
		recorder.prepare();
		recorder.start();
	}

	protected void stopRecording() {
		recorder.stop();
		recorder.release();
		processaudiofile(audiofile.getAbsolutePath());
	}

	protected void processaudiofile(String uri) {
		ContentValues values = new ContentValues(3);
		long current = System.currentTimeMillis();
		values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
		values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
		values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
		values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
		ContentResolver contentResolver = getContentResolver();

		Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Uri newUri = contentResolver.insert(base, values);

		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
	}

}