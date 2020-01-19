package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EditActivity extends Activity {
	TextView accountType;
	Intent oldIntent;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_edit);
//	menangkap tipe akun dari halaman sebelumnya
	accountType = (TextView) findViewById(R.id.textView_accountType);
	oldIntent = getIntent();
	accountType.setText(oldIntent.getStringExtra("account_type"));
}	
	public void callToPrevious(View v) {
		finish();
	}
}
