
package com.helloandroid.android.newsdroid;


import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class URLEditor extends Activity  {

	EditText mText;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.url_editor);

        // Set up click handlers for the text field and button
        mText = (EditText) this.findViewById(R.id.url);
        
        if (icicle != null)
        	mText.setText(icicle.getString("url"));
        
        Button ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	okClicked();
            }          
        });
        
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	finish();
            }          
        });
        
    }

    protected void okClicked() {
    	try {
    		RSSHandler rh = new RSSHandler();
    		rh.createFeed(this, new URL(mText.getText().toString()));
    		finish();
    	} catch (MalformedURLException e) {
    		showAlert("Invalid URL", "The URL you have entered is invalid.", "Ok", false);
    	}
    }

	@Override
	protected void onFreeze(Bundle outState) {
		super.onFreeze(outState);
		outState.putString("url", mText.getText().toString());		
	}
    
    

}
