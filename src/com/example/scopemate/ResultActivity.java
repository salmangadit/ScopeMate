package com.example.scopemate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ResultActivity extends Activity {
	String ocrText;
	TextView result;
	Button home;
	final Context a=this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        ocrText = getIntent().getStringExtra("ocrText");
        
        result = (TextView) findViewById(R.id.resultLabel);
        home = (Button) findViewById(R.id.button1);
        result.setText(ocrText);
        
        home.setOnClickListener(new ButtonClickHandler());
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_result, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
 // Upon button click
 	public class ButtonClickHandler implements View.OnClickListener {
 		@Override
 		public void onClick(View view) {
 			// Activity to open Main
 			Intent intent = new Intent(a, MainActivity.class);
 			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 			startActivity(intent);
 		}
 	}

}
