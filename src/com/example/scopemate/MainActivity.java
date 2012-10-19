package com.example.scopemate;

import java.util.ArrayList;

import com.example.scopemate.CropScreen;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	CheckBox brightnessBool;
	SeekBar brightnessValue;
	TextView brightnessValText;
	boolean brightness = false;

	CheckBox contrastBool;
	SeekBar contrastValue;
	TextView contrastValText;
	boolean contrast = false;

	CheckBox morphBool;
	SeekBar morphValue;
	TextView morphValText;
	int morphValueActual;
	boolean morph = false;

	OnClickListener brightnessCheckBoxListener, 
		contrastCheckBoxListener,
		morphCheckBoxListener,
		homogeneousCheckBoxListener,
		gaussianCheckBoxListener,
		medianCheckBoxListener,
		bilateralCheckBoxListener;

	CheckBox homogeneousFilter;
	CheckBox gaussianFilter;
	CheckBox medianFilter;
	CheckBox bilateralFilter;
	
	ArrayList<String> orderList = new ArrayList<String>();

	private static int RESULT_LOAD_IMAGE = 1;
	private static final String TAG = "ScopeMate.java";
	protected static Button _button;
	public final int RESULT_OK = -1;
	public static Bitmap myimage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		homogeneousFilter = (CheckBox) findViewById(R.id.chkHomogeneousFilter);
		gaussianFilter = (CheckBox) findViewById(R.id.chkGaussianFilter);
		medianFilter = (CheckBox) findViewById(R.id.chkMedianFilter);
		bilateralFilter = (CheckBox) findViewById(R.id.chkBilateralFilter);

		brightnessBool = (CheckBox) findViewById(R.id.chkBrightness);
		brightnessValue = (SeekBar) findViewById(R.id.skbBrightness);
		brightnessValText = (TextView) findViewById(R.id.txtBrightness);

		brightnessValue
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						brightnessValText.setText("Brightness %: " + progress);
					}

					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}
				});

		contrastBool = (CheckBox) findViewById(R.id.chkContrast);
		contrastValue = (SeekBar) findViewById(R.id.skbContrast);
		contrastValText = (TextView) findViewById(R.id.txtContrast);

		contrastValue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				contrastValText.setText("Contrast %: " + progress);
			}

			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}
		});

		morphBool = (CheckBox) findViewById(R.id.chkMorph);
		morphValue = (SeekBar) findViewById(R.id.skbMorph);
		morphValText = (TextView) findViewById(R.id.txtMorph);

		morphValue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				morphValueActual = (progress - 50) * 2;
				morphValText.setText("Morph %: " + morphValueActual);
			}

			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}
		});

		brightnessCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (brightnessBool.isChecked()) {
					orderList.add("brightness");
				} else if (!brightnessBool.isChecked()) {
					orderList.remove("brightness");
				}
			}
		};
		
		contrastCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (contrastBool.isChecked()) {
					orderList.add("contrast");
				} else if (!contrastBool.isChecked()) {
					orderList.remove("contrast");
				}
			}
		};
		morphCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (morphBool.isChecked()) {
					orderList.add("morph");
				} else if (!morphBool.isChecked()) {
					orderList.remove("morph");
				}
			}
		};
		homogeneousCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (homogeneousFilter.isChecked()) {
					orderList.add("homogeneous");
				} else if (!homogeneousFilter.isChecked()) {
					orderList.remove("homogeneous");
				}
			}
		};
		gaussianCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (gaussianFilter.isChecked()) {
					orderList.add("gaussian");
				} else if (!gaussianFilter.isChecked()) {
					orderList.remove("gaussian");
				}
			}
		};
		medianCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (medianFilter.isChecked()) {
					orderList.add("median");
				} else if (!medianFilter.isChecked()) {
					orderList.remove("median");
				}
			}
		};
		bilateralCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (bilateralFilter.isChecked()) {
					orderList.add("bilateral");
				} else if (!bilateralFilter.isChecked()) {
					orderList.remove("bilateral");
				}
			}
		};

		brightnessBool.setOnClickListener(brightnessCheckBoxListener);
		contrastBool.setOnClickListener(contrastCheckBoxListener);
		morphBool.setOnClickListener(morphCheckBoxListener);
		homogeneousFilter.setOnClickListener(homogeneousCheckBoxListener);
		gaussianFilter.setOnClickListener(gaussianCheckBoxListener);
		medianFilter.setOnClickListener(medianCheckBoxListener);
		bilateralFilter.setOnClickListener(bilateralCheckBoxListener);

		_button = (Button) findViewById(R.id.btnUpload);
		_button.setOnClickListener(new ButtonClickHandler());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// Upon button click
	public class ButtonClickHandler implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			Log.v(TAG, "Gallery button pressed");
			Log.v(TAG, "1.. Pass");
			// Activity to open Gallery
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			Log.v(TAG, "2.5.. Pass");
			startActivityForResult(i, RESULT_LOAD_IMAGE);
			Log.v(TAG, "3.. Pass");

		}
	}

	// To save the chosen image
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.v(TAG, "4.. Pass");
		super.onActivityResult(requestCode, resultCode, data);
		Log.v(TAG, "5.. Pass");
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Log.v(TAG, "6.. Pass");
			Log.v(TAG, selectedImage.toString());

			// IMP : Getting global activity status to use getContentresolver()
			// under a fragment
			Context a = this.getApplicationContext();
			Log.i(TAG, "Activity: " + a);
			Log.v(TAG, "not screwed");

			Cursor cursor = a.getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			Log.v(TAG, "7.5.. Pass");
			cursor.moveToFirst();
			Log.v(TAG, "8.. Pass");

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String filePath = cursor.getString(columnIndex);
			cursor.close();
			Log.v(TAG, "9.. Pass");
			Log.v(TAG, filePath);

			Intent intent = new Intent(a, CropScreen.class);
			intent.putExtra("file_path", filePath);
			intent.putExtra("image_uri", selectedImage.toString());
			intent.putExtra("brightnessChecked", brightnessBool.isChecked());
			intent.putExtra("brightnessValue", brightnessValue.getProgress());
			intent.putExtra("contrastChecked", contrastBool.isChecked());
			intent.putExtra("contrastValue", contrastValue.getProgress());
			intent.putExtra("morphChecked", morphBool.isChecked());
			intent.putExtra("morphValue", morphValueActual);
			intent.putExtra("homogeneousChecked", homogeneousFilter.isChecked());
			intent.putExtra("gaussianChecked", gaussianFilter.isChecked());
			intent.putExtra("medianChecked", medianFilter.isChecked());
			intent.putExtra("bilateralChecked", bilateralFilter.isChecked());
			
			//Bundle list_bundle=new Bundle();
	        //list_bundle.putStringArrayListExtra("lists",orderList);
	          
			intent.putExtra("rankList", orderList);
			Log.v(TAG, "ranklist 0 = "+ orderList.get(0));
			Log.v(TAG, "10.. Pass");
			startActivity(intent);

		}
	}
}
