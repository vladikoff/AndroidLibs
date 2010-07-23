package net.androgames.blog.sample.customdialog;

import net.androgames.blog.sample.customdialog.dialog.CustomDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * Create custom Dialog windows for your application
 * Custom dialogs rely on custom layouts wich allow you to 
 * create and use your own look & feel.
 * 
 * Sample from <a href="http://blog.androgames.net">Android tutorial blog</a>
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * 
 * @author antoine vianey
 *
 */
public class CustomDialogActivity extends Activity {
	
	private static final int CUSTOM_DIALOG 	= 1;
	private static final int DEFAULT_DIALOG	= 2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.customDialog).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CustomDialogActivity.this.showDialog(CUSTOM_DIALOG);
			}
		});
        findViewById(R.id.defaultDialog).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CustomDialogActivity.this.showDialog(DEFAULT_DIALOG);
			}
		});
    }
    
    /**
     * Build the desired Dialog
     * CUSTOM or DEFAULT
     */
    @Override
    public Dialog onCreateDialog(int dialogId) {
    	Dialog dialog = null;
    	switch (dialogId) {
	    	case CUSTOM_DIALOG :
				CustomDialog.Builder customBuilder = new
				CustomDialog.Builder(CustomDialogActivity.this);
				customBuilder.setTitle("Custom title")
					.setMessage("Custom body")
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							CustomDialogActivity.this
							.dismissDialog(CUSTOM_DIALOG);
						}
					})
					.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
	            dialog = customBuilder.create();
	    		break;
	    	case DEFAULT_DIALOG :
				AlertDialog.Builder alertBuilder = new
				AlertDialog.Builder(CustomDialogActivity.this);
				alertBuilder.setTitle("Default title")
					.setMessage("Default body")
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					})
					.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							CustomDialogActivity.this
							.dismissDialog(DEFAULT_DIALOG);
						}
					});
	            dialog = alertBuilder.create();
	    		break;
    	}
    	return dialog;
    }
    
    
}