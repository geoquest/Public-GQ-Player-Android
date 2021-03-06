package com.qeevee.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import edu.bonn.mobilegaming.geoquest.R;

public class FullScreenImage extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.full_screen_image);
	ImageView imageView = (ImageView) findViewById(R.id.full_screen_image);
	imageView.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		finish();
	    }
	});

	Intent intent = getIntent();
	if (intent.hasExtra("bitmapPath")) {
	    String bitmapPath = intent.getStringExtra("bitmapPath");
	    Bitmap bitmap = BitmapUtil.loadBitmap(bitmapPath,
						  true);
	    bitmap = BitmapUtil.scaleBitmapToScreenWidth(bitmap);
	    imageView.setImageBitmap(bitmap);
	} else {
	    if (intent.hasExtra("resId")) {
		imageView.setImageResource(intent
			.getIntExtra("resID",
				     R.drawable.missingbitmap));
	    }
	}
    }
}
