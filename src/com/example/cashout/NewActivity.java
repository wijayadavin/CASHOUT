package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewActivity extends Activity {
	TextView accountType;
	Intent oldIntent;
	EditText phoneEdit;
	TextView phoneTextView;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		accountType = (TextView) findViewById(R.id.textView_accountType);
		oldIntent = getIntent();
		accountType.setText(oldIntent.getStringExtra("account_type"));
		phoneEdit = (EditText) findViewById(R.id.edit_phone);
		phoneTextView = (TextView) findViewById(R.id.textViewPhone);
		if(accountType.getText().toString().equals("administrators")) {
			phoneEdit.setVisibility(View.GONE);
			phoneTextView.setVisibility(View.GONE);
		}
	}
	public void callToPrevious(View v) {
		finish();
	}
}
