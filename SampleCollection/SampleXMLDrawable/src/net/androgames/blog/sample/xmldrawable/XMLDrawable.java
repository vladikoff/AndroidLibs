package net.androgames.blog.sample.xmldrawable;

import android.app.Activity;
import android.os.Bundle;

public class XMLDrawable extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}

/**
 * 
 * 1 - shapes
 * 		a. simple forms
 * 		b. complex forms
 * 		c. 
 * 2 - bitmaps
 * 
 * 3 - layers
 * 
 */

/*
<rotate> 
android:drawable 		Reference to a drawable resource to use for the frame. 
android:fromDegrees 		 
android:pivotX 		 
android:pivotY 		 
android:toDegrees 		 
android:visible 		Provides initial visibility state

<scale>
android:drawable 		Reference to a drawable resource to draw with the specified scale. 
android:scaleGravity 		Specifies where the drawable is positioned after scaling. 
android:scaleHeight 		Scale height, expressed as a percentage of the drawable's bound. 
android:scaleWidth 		Scale width, expressed as a percentage of the drawable's bound. 

<shape>
android:bottom 		Bottom padding. 
android:color 		Defines the color of the shape. 
android:height 		Defines the height of the shape. 
android:left 		Left padding. 
android:right 		Right padding. 
android:top 		Top padding. 
android:width 		Defines the width of the shape. 

<color>
android:color 		The color to use. 

<clip>
android:clipOrientation 		The orientation for the clip. 
android:drawable 		Reference to a drawable resource to draw with the specified scale. 
android:gravity 		Specifies where to clip within the drawable. 

<bitmap>
android:antialias 		Enables or disables antialiasing. 
android:dither 		Enables or disables dithering of the bitmap if the bitmap does not have the same pixel configuration as the screen (for instance: a ARGB 8888 bitmap with an RGB 565 screen). 
android:filter 		Enables or disables bitmap filtering. 
android:gravity 		Defines the gravity for the bitmap. 
android:src 		Identifier of the bitmap file. 
android:tileMode 		Defines the tile mode. 

<shape> 
android:angle 		 
android:bottom 		 
android:centerColor 		Optional center color. 
android:centerX 		 
android:centerY 		 
android:color 		 
android:color 		 
android:dashGap 		 
android:dashWidth 		 
android:endColor 		 
android:gradientRadius 		 
android:height 		Makes the TextView be exactly this many pixels tall. 
android:innerRadius 		Inner radius of the ring. 
android:innerRadiusRatio 		Inner radius of the ring expressed as a ratio of the ring's width. 
android:left 		 
android:right 		 
android:shape 		 
android:startColor 		 
android:thickness 		Thickness of the ring. 
android:thicknessRatio 		Thickness of the ring expressed as a ratio of the ring's width. 
android:top 		 
android:type 		 
android:useLevel 		 
android:useLevel 		 
android:visible 		Provides initial visibility state of the drawable; the default value is false. 
android:width 		Makes the TextView be exactly this many pixels wide.

<inset>
android:drawable 		Reference to a drawable resource to use for the frame. 
android:insetBottom 		 
android:insetLeft 		 
android:insetRight 		 
android:insetTop 		 
android:visible 		Provides initial visibility state of the drawable; the default value is false.

<layer-list>
android:bottom 		 
android:drawable 		Reference to a drawable resource to use for the frame. 
android:id 		Supply an identifier name for this view, to later retrieve it with View.findViewById() or Activity.findViewById(). 
android:left 		 
android:right 		 
android:top 

<gradient>
<corner>
<padding>
<size>
<solid>
<stroke>
<item>
<bitmap>
*/