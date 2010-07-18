package com.nvforge.directorscut;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Location extends MapActivity {
	LinearLayout linearLayout;
	MapView mapView;
	MapController mapController;
	List<Overlay> mapOverlays;
	Drawable drawable;
	LocationItems itemizedOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locations);
		mapView = (MapView) findViewById(R.id.mapview); 

		mapView.setBuiltInZoomControls(true); //zoom controls

		this.mapController = this.mapView.getController();
		this.mapController.setZoom(6); //set the zoom 
		this.gotoMyHome(); //center map

		mapOverlays = mapView.getOverlays(); 

		drawable = this.getResources().getDrawable(R.drawable.marker); //marker image
		itemizedOverlay = new LocationItems(drawable);

		
		
		GeoPoint point = new GeoPoint(19240000,-99120000);
		OverlayItem overlayitem = new OverlayItem(point, "", "");
		itemizedOverlay.addOverlay(overlayitem);
		
	
		
		mapOverlays.add(itemizedOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private void gotoMyHome() {		
		this.mapController.setCenter(new GeoPoint(19240000,-99120000));

	}

}
