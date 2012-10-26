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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
	
	CheckBox thresholding;
	CheckBox otsu;
	SeekBar thresholdingValue;
	SeekBar thresholdingMaxValue;
	TextView thresholdingValueText;
	TextView thresholdingMaxValueText;
	Spinner threshSpinner;

	OnClickListener brightnessCheckBoxListener, 
		contrastCheckBoxListener,
		morphCheckBoxListener,
		homogeneousCheckBoxListener,
		gaussianCheckBoxListener,
		medianCheckBoxListener,
		bilateralCheckBoxListener,
		greyscaleCheckBoxListener,
		pyramidDownCheckBoxListener,
		pyramidUpCheckBoxListener,
		histogramCheckBoxListener,
		thresholdingCheckBoxListener,
		otsuCheckBoxListener;

	CheckBox homogeneousFilter;
	CheckBox gaussianFilter;
	CheckBox medianFilter;
	CheckBox bilateralFilter;
	CheckBox greyscale;
	CheckBox pyramidDown;
	CheckBox pyramidUp;
	CheckBox histogram;
	
	
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
		greyscale = (CheckBox) findViewById(R.id.chkGreyscale);
		pyramidDown = (CheckBox) findViewById(R.id.chkPyramidDown);
		pyramidUp = (CheckBox) findViewById(R.id.chkPyramidUp);
		histogram = (CheckBox) findViewById(R.id.chkHistogram);

		brightnessBool = (CheckBox) findViewById(R.id.chkBrightness);
		brightnessValue = (SeekBar) findViewById(R.id.skbBrightness);
		brightnessValText = (TextView) findViewById(R.id.txtBrightness);
		
		thresholding = (CheckBox) findViewById(R.id.chkThresholding);
		otsu = (CheckBox) findViewById(R.id.chkOtsu);
		thresholdingValue = (SeekBar) findViewById(R.id.skbThreshValue);
		thresholdingMaxValue = (SeekBar) findViewById(R.id.skbThreshMax);
		thresholdingValueText = (TextView) findViewById(R.id.txtThreshValue);
		thresholdingMaxValueText = (TextView) findViewById(R.id.txtThreshMax);
		threshSpinner = (Spinner) findViewById(R.id.spnThreshType);

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
		
		thresholdingValue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				thresholdingValueText.setText("Threshold Value: " + progress);
			}

			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		thresholdingMaxValue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				thresholdingMaxValueText.setText("Threshold Maximum Value: " + progress);
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
		
		greyscaleCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (greyscale.isChecked()) {
					orderList.add("greyscale");
				} else if (!greyscale.isChecked()) {
					orderList.remove("greyscale");
				}
			}
		};
		
		pyramidUpCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (pyramidUp.isChecked()) {
					orderList.add("pyramidUp");
				} else if (!pyramidUp.isChecked()) {
					orderList.remove("pyramidUp");
				}
			}
		};
		
		pyramidDownCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (pyramidDown.isChecked()) {
					orderList.add("pyramidDown");
				} else if (!pyramidDown.isChecked()) {
					orderList.remove("pyramidDown");
				}
			}
		};
		
		histogramCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (histogram.isChecked()) {
					orderList.add("histogram");
				} else if (!histogram.isChecked()) {
					orderList.remove("histogram");
				}
			}
		};
		
		thresholdingCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (thresholding.isChecked()) {
					orderList.add("thresholding");
				} else if (!thresholding.isChecked()) {
					orderList.remove("thresholding");
				}
			}
		};
		
		otsuCheckBoxListener = new OnClickListener() {
			public void onClick(View v) {
				if (otsu.isChecked()) {
					thresholdingValue.setEnabled(false);
					Toast.makeText(MainActivity.this.getApplicationContext(), 
					"Otsu Algorithm will choose an appropriate value for the Value!",
					Toast.LENGTH_SHORT).show();
				} else if (!otsu.isChecked()) {
					thresholdingValue.setEnabled(true);
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
		greyscale.setOnClickListener(bilateralCheckBoxListener);
		pyramidDown.setOnClickListener(pyramidDownCheckBoxListener);
		pyramidUp.setOnClickListener(pyramidUpCheckBoxListener);
		histogram.setOnClickListener(histogramCheckBoxListener);
		thresholding.setOnClickListener(thresholdingCheckBoxListener);
		otsu.setOnClickListener(otsuCheckBoxListener);
		
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
	
	public class CustomOnItemSelectedListener implements OnItemSelectedListener {
		 
		  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			  if (parent.getItemAtPosition(pos).toString().equals("Otsu Algorithm"))
			  {
				  
			  }
			  else
			  {
				  
			  }
		  }
		 
		  @Override
		  public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
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
			intent.putExtra("greyscaleChecked", greyscale.isChecked());
			intent.putExtra("pyramidDownChecked", pyramidDown.isChecked());
			intent.putExtra("pyramidUpChecked", pyramidUp.isChecked());
			intent.putExtra("histogramChecked", histogram.isChecked());
			intent.putExtra("thresholdingChecked", thresholding.isChecked());
			intent.putExtra("otsuChecked", otsu.isChecked());
			intent.putExtra("thresholdingType", threshSpinner.getSelectedItem().toString());
			intent.putExtra("thresholdingValue", thresholdingValue.getProgress());
			intent.putExtra("thresholdingMaxValue", thresholdingMaxValue.getProgress());
			
			
			//Bundle list_bundle=new Bundle();
	        //list_bundle.putStringArrayListExtra("lists",orderList);
	          
			intent.putExtra("rankList", orderList);
			Log.v(TAG, "ranklist 0 = "+ orderList.get(0));
			Log.v(TAG, "10.. Pass");
			startActivity(intent);

		}
	}
}


	 
