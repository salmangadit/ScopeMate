package com.example.scopemate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

public class Ocrmain extends Activity {

	private static final String TAG = "Scope.java";
	public static final String lang = "eng";
	public Bitmap myimage;
	protected static final String PHOTO_TAKEN = "photo_taken";
	final int PIC_CROP = 2;
	public Uri image_uri;
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().toString() + "/ScopeMate/";
	public String filepath;
	public Bitmap my_image;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "enter...");
		Log.v(TAG, "OCR...");
		
		
		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };
		Log.v(TAG, "datapath: "+DATA_PATH);
		for (String path : paths) {
			File dir = new File(path);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
					return;
				} else {
					Log.v(TAG, "Created directory " + path + " on sdcard");
				}
			}

		}
		
		
		// lang.traineddata file with the app (in assets folder)
				// You can get them at:
				// http://code.google.com/p/tesseract-ocr/downloads/list
				// This area needs work and optimization
				if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
					try {

						AssetManager assetManager = getAssets();
						InputStream in = assetManager.open("tessdata/eng.traineddata");
						//GZIPInputStream gin = new GZIPInputStream(in);
						OutputStream out = new FileOutputStream(DATA_PATH
								+ "tessdata/eng.traineddata");

						// Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						//while ((lenf = gin.read(buff)) > 0) {
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						//gin.close();
						out.close();
						
						Log.v(TAG, "Copied " + lang + " traineddata");
					} catch (IOException e) {
						Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
					}
				}

				if (!(new File(DATA_PATH + "tessdata/osd.traineddata")).exists()) {
					try {

						AssetManager assetManager = getAssets();
						InputStream in = assetManager.open("tessdata/osd.traineddata");
						//GZIPInputStream gin = new GZIPInputStream(in);
						OutputStream out = new FileOutputStream(DATA_PATH
								+ "tessdata/osd.traineddata");

						// Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						//while ((lenf = gin.read(buff)) > 0) {
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						//gin.close();
						out.close();
						
						Log.v(TAG, "Copied " + "osd" + " traineddata");
					} catch (IOException e) {
						Log.e(TAG, "Was unable to copy " + "osd" + " traineddata " + e.toString());
					}
				}
				
				if (!(new File(DATA_PATH + "tessdata/eng.cube.bigrams")).exists()) {
                    try {

                        AssetManager assetManager = getAssets();
                        InputStream in = assetManager.open("tessdata/eng.cube.bigrams");
                        //GZIPInputStream gin = new GZIPInputStream(in);
                        OutputStream out = new FileOutputStream(DATA_PATH
                                + "tessdata/eng.cube.bigrams");

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        //while ((lenf = gin.read(buff)) > 0) {
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        //gin.close();
                        out.close();

                        Log.v(TAG, "Copied eng.cube.bigrams");
                    } catch (IOException e) {
                        Log.e(TAG, "Was unable to copy eng.cube.bigrams " + e.toString());
                    }
                }


                if (!(new File(DATA_PATH + "tessdata/eng.cube.fold")).exists()) {
                    try {

                        AssetManager assetManager = getAssets();
                        InputStream in = assetManager.open("tessdata/eng.cube.fold");
                        //GZIPInputStream gin = new GZIPInputStream(in);
                        OutputStream out = new FileOutputStream(DATA_PATH
                                + "tessdata/eng.cube.fold");

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        //while ((lenf = gin.read(buff)) > 0) {
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        //gin.close();
                        out.close();

                        Log.v(TAG, "Copied eng.cube.fold");
                    } catch (IOException e) {
                        Log.e(TAG, "Was unable to copy eng.cube.fold " + e.toString());
                    }
                }


                if (!(new File(DATA_PATH + "tessdata/eng.cube.lm")).exists()) {
                    try {

                        AssetManager assetManager = getAssets();
                        InputStream in = assetManager.open("tessdata/eng.cube.lm");
                        //GZIPInputStream gin = new GZIPInputStream(in);
                        OutputStream out = new FileOutputStream(DATA_PATH
                                + "tessdata/eng.cube.lm");

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        //while ((lenf = gin.read(buff)) > 0) {
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        //gin.close();
                        out.close();

                        Log.v(TAG, "Copied eng.cube.lm");
                    } catch (IOException e) {
                        Log.e(TAG, "Was unable to copy eng.cube.lm " + e.toString());
                    }
                }

                if (!(new File(DATA_PATH + "tessdata/eng.cube.nn")).exists()) {
                    try {

                        AssetManager assetManager = getAssets();
                        InputStream in = assetManager.open("tessdata/eng.cube.nn");
                        //GZIPInputStream gin = new GZIPInputStream(in);
                        OutputStream out = new FileOutputStream(DATA_PATH
                                + "tessdata/eng.cube.nn");

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        //while ((lenf = gin.read(buff)) > 0) {
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        //gin.close();
                        out.close();

                        Log.v(TAG, "Copied eng.cube.nn");
                    } catch (IOException e) {
                        Log.e(TAG, "Was unable to copy eng.cube.nn " + e.toString());
                    }
                }

                if (!(new File(DATA_PATH + "tessdata/eng.cube.params")).exists()) {
                    try {

                        AssetManager assetManager = getAssets();
                        InputStream in = assetManager.open("tessdata/eng.cube.params");
                        //GZIPInputStream gin = new GZIPInputStream(in);
                        OutputStream out = new FileOutputStream(DATA_PATH
                                + "tessdata/eng.cube.params");

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        //while ((lenf = gin.read(buff)) > 0) {
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        //gin.close();
                        out.close();

                        Log.v(TAG, "Copied eng.cube.params");
                    } catch (IOException e) {
                        Log.e(TAG, "Was unable to copy eng.cube.params " + e.toString());
                    }
                }

                if (!(new File(DATA_PATH + "tessdata/eng.cube.size")).exists()) {
                    try {

                        AssetManager assetManager = getAssets();
                        InputStream in = assetManager.open("tessdata/eng.cube.size");
                        //GZIPInputStream gin = new GZIPInputStream(in);
                        OutputStream out = new FileOutputStream(DATA_PATH
                                + "tessdata/eng.cube.size");

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        //while ((lenf = gin.read(buff)) > 0) {
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        //gin.close();
                        out.close();

                        Log.v(TAG, "Copied eng.cube.size");
                    } catch (IOException e) {
                        Log.e(TAG, "Was unable to copy eng.cube.size " + e.toString());
                    }
                }


                if (!(new File(DATA_PATH + "tessdata/eng.tesseract_cube.nn")).exists()) {
                    try {

                        AssetManager assetManager = getAssets();
                        InputStream in = assetManager.open("tessdata/eng.tesseract_cube.nn");
                        //GZIPInputStream gin = new GZIPInputStream(in);
                        OutputStream out = new FileOutputStream(DATA_PATH
                                + "tessdata/eng.tesseract_cube.nn");

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        //while ((lenf = gin.read(buff)) > 0) {
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        //gin.close();
                        out.close();

                        Log.v(TAG, "Copied eng.tesseract_cube.nn");
                    } catch (IOException e) {
                        Log.e(TAG, "Was unable to copy eng.tesseract_cube.nn " + e.toString());
                    }
                }		
				
                if (!(new File(DATA_PATH + "tessdata/eng.cube.word-freq")).exists()) {
                    try {

                        AssetManager assetManager = getAssets();
                        InputStream in = assetManager.open("tessdata/eng.cube.word-freq");
                        //GZIPInputStream gin = new GZIPInputStream(in);
                        OutputStream out = new FileOutputStream(DATA_PATH
                                + "tessdata/eng.cube.word-freq");

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        //while ((lenf = gin.read(buff)) > 0) {
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        //gin.close();
                        out.close();

                        Log.v(TAG, "Copied eng.cube.word-freq");
                    } catch (IOException e) {
                        Log.e(TAG, "Was unable to copy eng.cube.word-freq " + e.toString());
                    }
                }	
				
				
				
		// Intent input
		filepath = getIntent().getStringExtra("file_path");
		String b = getIntent().getStringExtra("image_uri");
		image_uri = Uri.parse(b);
		Log.v(TAG, image_uri.toString());

		ocr_main();
	}

	@TargetApi(12)
	public void ocr_main() {
		Log.v(TAG, "entering tess");

		// Getting uri of image/cropped image
		try {
			myimage = MediaStore.Images.Media.getBitmap(
					this.getContentResolver(), image_uri);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Log.v(TAG, "bitmap " + myimage.getByteCount());
//        Bitmap argb = myimage;
//        argb = argb.copy(Bitmap.Config.ARGB_8888, true);
//        Log.v(TAG, "bitmap after argb:" + argb.getByteCount());
//        
		InputStream input=null;
		try{
		input = this.getContentResolver().openInputStream(image_uri);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		Log.v(TAG, "bitmap before comp: " + myimage.getHeight()*myimage.getWidth());
		BitmapFactory.Options opt = new BitmapFactory.Options();
        //opt.inSampleSize = 3;
        Log.v(TAG, "Filepath:" + filepath);
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        myimage = BitmapFactory.decodeStream(input, null, opt);
        //myimage = myimage.copy(Bitmap.Config.ARGB_8888, true);
        //Log.v(TAG, "bitmap after comp:" + myimage.getByteCount());
        Log.v(TAG, "bitmap after comp: " + myimage.getHeight()*myimage.getWidth());
        //TessBase starts
        
		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.clear();
		baseApi.setDebug(true);
		baseApi.init(DATA_PATH, lang,TessBaseAPI.OEM_CUBE_ONLY);
		baseApi.setPageSegMode(TessBaseAPI.PSM_AUTO);
		//Log.v(TAG,"return: "+a);
		Log.v(TAG, "Before baseApi");
		Log.v(TAG,"Language: "+baseApi.getInitLanguagesAsString());
		//baseApi.clear();
		baseApi.setImage(myimage);
		Log.v(TAG, "Before baseApi2");
		String recognizedText = baseApi.getUTF8Text();
		Log.v(TAG, "Before baseApi3");
		baseApi.end();
		
		Log.v(TAG, "OCRED TEXT: " + recognizedText);

		if (lang.equalsIgnoreCase("eng")) {
			recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
		}

		//recognizedText is the final OCRed text
		recognizedText = recognizedText.trim();

//		String ocrtext = "And...BAM! OCRed: " + recognizedText;
//		Toast toast = Toast.makeText(this.getApplicationContext(), ocrtext,
//				Toast.LENGTH_LONG);
//		toast.show();
		
		//deleting temporary crop file created
		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"temp.bmp");
		boolean deleted = file.delete();
		Log.i(TAG, "File deleted: "+deleted);
		
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtra("ocrText", recognizedText);
		startActivity(intent);
	}
}