package com.example.scopemate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.example.scopemate.BitmapHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CropScreen extends Activity {
	private static final String TAG = "ScopeMate.java";
	public Bitmap myimage;

	boolean brightnessBool;
	int brightnessValue;
	boolean contrastBool;
	int contrastValue;
	boolean morphBool;
	int morphValue;
	boolean homogeneousBool;
	boolean gaussianBool;
	boolean medianBool;
	boolean bilateralBool;
	boolean greyscaleBool;
	boolean pyramidDownBool;
	boolean pyramidUpBool;
	boolean histogramBool;
	
	boolean thresholdingBool;
	boolean otsuBool;
	String thresholdingType;
	int thresholdingValue, thresholdingMaxValue;

	ArrayList<String> rankList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get pre-process shit
		brightnessBool = getIntent()
				.getBooleanExtra("brightnessChecked", false);
		brightnessValue = getIntent().getIntExtra("brightnessValue", 0);
		contrastBool = getIntent().getBooleanExtra("contrastChecked", false);
		contrastValue = getIntent().getIntExtra("contrastValue", 0);
		morphBool = getIntent().getBooleanExtra("morphChecked", false);
		morphValue = getIntent().getIntExtra("morphValue", 0);
		homogeneousBool = getIntent().getBooleanExtra("homogeneousChecked",
				false);
		gaussianBool = getIntent().getBooleanExtra("gaussianChecked", false);
		medianBool = getIntent().getBooleanExtra("medianChecked", false);
		bilateralBool = getIntent().getBooleanExtra("bilateralChecked", false);
		greyscaleBool = getIntent().getBooleanExtra("greyscaleChecked", false);
		pyramidDownBool = getIntent()
				.getBooleanExtra("pyramidDownChecked", false);
		pyramidUpBool = getIntent().getBooleanExtra("pyramidUpChecked", false);
		histogramBool = getIntent().getBooleanExtra("histogramChecked", false);
		thresholdingBool = getIntent().getBooleanExtra("thresholdingChecked", false);
		otsuBool = getIntent().getBooleanExtra("otsuChecked", false);
		thresholdingType = getIntent().getStringExtra("thresholdingType");
		thresholdingValue = getIntent().getIntExtra("thresholdingValue", 0);
		thresholdingMaxValue = getIntent().getIntExtra("thresholdingMaxValue", 0);
		
		rankList = getIntent().getStringArrayListExtra("rankList");

		Log.v(TAG, "enter...");
		setContentView(R.layout.cropscreen);
		Log.v(TAG, "cropscreen...");
		Log.v(TAG, "Brightness: " + brightnessValue);
		if (rankList == null)
			Log.v(TAG, "rank is null!");
		Log.v(TAG, "rank list 0 = " + rankList.get(0).toString());
		/*
		 * ActionBar ab = getActionBar(); ab.setTitle("Title");
		 * ab.setDisplayShowTitleEnabled(false); ab.setSubtitle("Subtitle");
		 * ab.setDisplayShowTitleEnabled(false);
		 */

		final String filepath = getIntent().getStringExtra("file_path");
		String b = getIntent().getStringExtra("image_uri");
		final Uri image_uri = Uri.parse(b);
		Log.v(TAG, image_uri.toString());

//		try {
//			myimage = MediaStore.Images.Media.getBitmap(
//					this.getContentResolver(), image_uri);
//			// myimage = readBitmap(image_uri);
//			// bmp =
//			// MediaStore.Images.Media.getBitmap(this.getContentResolver(),
//			// Uri.fromFile(file) );
//			// do whatever you want with the bitmap (Resize, Rename, Add To
//			// Gallery, etc)
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		BitmapHandler bitmaphandler = new BitmapHandler();
		myimage = bitmaphandler.decodeFileAsPath(filepath);

		// Log.v(TAG,"Myimageeeeee Size:"+myimage.getByteCount());

		// Bitmap yourSelectedImage = BitmapFactory.decodeFile(filepath);
		ImageView imageView = (ImageView) findViewById(R.id.imgView);
		imageView.setImageBitmap(myimage);

		Button button_crop = (Button) findViewById(R.id.crop);
		Button button_done = (Button) findViewById(R.id.done);
		final Context a = this;

		button_crop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v(TAG, "crop button clicked");
				Log.v(TAG, image_uri.toString());
				Intent intent = new Intent(a, Cropping.class);
				intent.putExtra("file_path", filepath);
				intent.putExtra("image_uri", image_uri.toString());
				intent.putExtra("brightnessChecked", brightnessBool);
				intent.putExtra("brightnessValue", brightnessValue);
				intent.putExtra("contrastChecked", contrastBool);
				intent.putExtra("contrastValue", contrastValue);
				intent.putExtra("morphChecked", morphBool);
				intent.putExtra("morphValue", morphValue);
				intent.putExtra("homogeneousChecked", homogeneousBool);
				intent.putExtra("gaussianChecked", gaussianBool);
				intent.putExtra("medianChecked", medianBool);
				intent.putExtra("bilateralChecked", bilateralBool);
				intent.putExtra("greyscaleChecked", greyscaleBool);
				intent.putExtra("pyramidDownChecked", pyramidDownBool);
				intent.putExtra("pyramidUpChecked", pyramidUpBool);
				intent.putExtra("histogramChecked", histogramBool);
				intent.putExtra("thresholdingChecked", thresholdingBool);
				intent.putExtra("otsuChecked", otsuBool);
				intent.putExtra("thresholdingType", thresholdingType);
				intent.putExtra("thresholdingValue", thresholdingValue);
				intent.putExtra("thresholdingMaxValue", thresholdingMaxValue);
				intent.putExtra("rankList", rankList);
				startActivity(intent);

			}
		});

		button_done.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v(TAG, "OCR button clicked");
				Intent intent = new Intent(a, PreProcess.class);
				intent.putExtra("file_path", filepath);
				intent.putExtra("image_uri", image_uri.toString());

				intent.putExtra("brightnessChecked", brightnessBool);
				intent.putExtra("brightnessValue", brightnessValue);
				intent.putExtra("contrastChecked", contrastBool);
				intent.putExtra("contrastValue", contrastValue);
				intent.putExtra("morphChecked", morphBool);
				intent.putExtra("morphValue", morphValue);
				intent.putExtra("homogeneousChecked", homogeneousBool);
				intent.putExtra("gaussianChecked", gaussianBool);
				intent.putExtra("medianChecked", medianBool);
				intent.putExtra("bilateralChecked", bilateralBool);
				intent.putExtra("greyscaleChecked", greyscaleBool);
				intent.putExtra("pyramidDownChecked", pyramidDownBool);
				intent.putExtra("pyramidUpChecked", pyramidUpBool);
				intent.putExtra("histogramChecked", histogramBool);
				intent.putExtra("thresholdingChecked", thresholdingBool);
				intent.putExtra("otsuChecked", otsuBool);
				intent.putExtra("thresholdingType", thresholdingType);
				intent.putExtra("thresholdingValue", thresholdingValue);
				intent.putExtra("thresholdingMaxValue", thresholdingMaxValue);
				intent.putExtra("rankList", rankList);
				startActivity(intent);
			}
		});

	}

	// Clear bitmap

	public static void clearBitmap(Bitmap bm) {

		bm.recycle();

		System.gc();

	}

	// Read bitmap
	public Bitmap readBitmap(Uri selectedImage) {
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 5;
		AssetFileDescriptor fileDescriptor = null;
		try {
			fileDescriptor = this.getContentResolver().openAssetFileDescriptor(
					selectedImage, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bm = BitmapFactory.decodeFileDescriptor(
						fileDescriptor.getFileDescriptor(), null, options);
				fileDescriptor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;
	}
}
