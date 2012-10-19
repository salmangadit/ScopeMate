package com.example.scopemate;

import java.util.ArrayList;
import java.util.Iterator;

import org.opencv.android.OpenCVLoader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PreProcess extends Activity {

	private static final String TAG = "Scope.java";
	static {
		if (!OpenCVLoader.initDebug()) {
			Log.e(TAG, "Some Error!");
		}
	}
	public double alpha = 3.0;
	public double beta = 0;
	public Bitmap myimage;
	public Bitmap ppimage;
	public Uri image_uri;
	public Uri output_uri;
	public String filepath;

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
	ArrayList<String> rankList;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "enter...");
		Log.v(TAG, "Preprocess...");

		setContentView(R.layout.cropping);

		/*
		 * ActionBar ab = getActionBar(); ab.setTitle("Title");
		 * ab.setDisplayShowTitleEnabled(false); ab.setSubtitle("Subtitle");
		 * ab.setDisplayShowTitleEnabled(false);
		 */

		// Intent input
		filepath = getIntent().getStringExtra("file_path");
		String b = getIntent().getStringExtra("image_uri");
		image_uri = Uri.parse(b);

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
		
		output_uri = null;

		for (int i = 0; i<rankList.size(); i++) {
			Log.v(TAG, "next: "+ rankList.get(i));
			if (rankList.get(i).equals("brightness")) {
				Log.v(TAG, "HEREH");
				Brightness brightener;
				if (output_uri == null) {
					brightener = new Brightness(this.getApplicationContext(),
							image_uri);
					Log.v(TAG, "HEREH2");
				} else {
					brightener = new Brightness(this.getApplicationContext(),
							output_uri);
				}
				output_uri = brightener.Brighten(brightnessValue);
				Log.v(TAG, "AAA" + output_uri.toString());
			} else if (rankList.get(i).equals("contrast")) {
				Contrast contraster;
				if (output_uri == null) {
					contraster = new Contrast(this.getApplicationContext(),
							image_uri);
				} else {
					contraster = new Contrast(this.getApplicationContext(),
							output_uri);
				}
				output_uri = contraster.contrast_change(contrastValue);
			} else if (rankList.get(i).equals("morph")) {
				Morphing morphing;
				if (output_uri == null) {
					morphing = new Morphing(this.getApplicationContext(),
							image_uri);
				} else {
					morphing = new Morphing(this.getApplicationContext(),
							output_uri);
				}
				output_uri = morphing.erode(morphValue);
			} else if (rankList.get(i).equals("homogeneous")) {
				Smoothing smoothing;
				if (output_uri == null) {
					smoothing = new Smoothing(this.getApplicationContext(),
							image_uri);
				} else {
					smoothing = new Smoothing(this.getApplicationContext(),
							output_uri);
				}
				output_uri = smoothing.HomogeneousFilter();
			} else if (rankList.get(i).equals("gaussian")) {
				Smoothing smoothing;
				if (output_uri == null) {
					smoothing = new Smoothing(this.getApplicationContext(),
							image_uri);
				} else {
					smoothing = new Smoothing(this.getApplicationContext(),
							output_uri);
				}
				output_uri = smoothing.GaussianFilter();
			} else if (rankList.get(i).equals("median")) {
				Smoothing smoothing;
				if (output_uri == null) {
					smoothing = new Smoothing(this.getApplicationContext(),
							image_uri);
				} else {
					smoothing = new Smoothing(this.getApplicationContext(),
							output_uri);
				}
				output_uri = smoothing.MedianFilter();
			} else if (rankList.get(i).equals("bilateral")) {
				Smoothing smoothing;
				if (output_uri == null) {
					smoothing = new Smoothing(this.getApplicationContext(),
							image_uri);
				} else {
					smoothing = new Smoothing(this.getApplicationContext(),
							output_uri);
				}
				output_uri = smoothing.BilateralFilter();
			}
		}
		Log.v(TAG, image_uri.toString());
		Log.v(TAG, output_uri.toString());
		ImageView imageView = (ImageView) findViewById(R.id.imgView);
		imageView.setImageURI((output_uri == null? image_uri : output_uri));
		Button button_done = (Button) findViewById(R.id.button1);

		final Context a = this;

		button_done.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v(TAG, "Done button clicked");
				Intent i = new Intent(a, Ocrmain.class);
				i.putExtra("file_path", filepath);
				i.putExtra("image_uri", (output_uri == null? image_uri.toString() : output_uri.toString()));
				startActivity(i);

			}
		});

	}
}