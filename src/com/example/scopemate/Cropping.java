package com.example.scopemate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class Cropping extends Activity {
	private static final String TAG = "Scope.java";
	public Bitmap myimage;
	protected static final String PHOTO_TAKEN = "photo_taken";
	final int PIC_CROP = 2;
	public Uri image_uri;
	public String filepath;

	// Context a;
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
	ArrayList<String> rankList = new ArrayList<String>();
	// private Uri outputFileUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "enter...");
		
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
		rankList = getIntent().getStringArrayListExtra("rankList");
		Log.v(TAG, "Brightness: "+ brightnessValue);
		if (rankList == null)
			Log.v(TAG, "rank is null!");
		Log.v(TAG, "rank list 0 = "+ rankList.get(0).toString());
		
		Log.v(TAG, "cropping...");
		filepath = getIntent().getStringExtra("file_path");
		String b = getIntent().getStringExtra("image_uri");
		image_uri = Uri.parse(b);
		Log.v(TAG, image_uri.toString());

		crop_here(image_uri);
	}

	public void crop_here(Uri outputFileUri) {
		try {
			// call the standard crop action intent (the user device may not
			// support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(outputFileUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			cropIntent.putExtra("scale", true);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
		} catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this.getApplicationContext(),
					errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Uri image_uri=data.getData();
		Log.i(TAG, "resultCode: " + resultCode);
		Log.i(TAG, "requestCode: " + requestCode);
		if (resultCode == -1) {
			if (requestCode == PIC_CROP) {
				Bundle extras = data.getExtras();
				// Uri image_uri=data.getData();
				Log.v(TAG, "all good");
				Log.i(TAG, "resultCode: " + resultCode);
				Log.i(TAG, "requestCode: " + requestCode);
				Log.v(TAG, image_uri.toString());

				Bitmap thePic = extras.getParcelable("data");

				// Storing cropped file in temporary location
				File file = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"temp.bmp");

				try {
					FileOutputStream out = new FileOutputStream(file);
					thePic.compress(Bitmap.CompressFormat.PNG, 90, out);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Uri uri = Uri.fromFile(file);
				Log.v(TAG, uri.toString());

				Log.v(TAG, thePic.toString());
				filepath = file.getAbsolutePath();
				Intent intent = new Intent(this, CropScreen.class);
				intent.putExtra("file_path", filepath);
				intent.putExtra("image_uri", uri.toString());
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
				intent.putStringArrayListExtra("rankList", rankList);
				startActivity(intent);
			}

		} else if (resultCode == RESULT_CANCELED) {
			Log.v(TAG, "cancelled");
			Intent intent = new Intent(this, CropScreen.class);
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
			intent.putStringArrayListExtra("rankList", rankList);
			startActivity(intent);

		}
	}

}