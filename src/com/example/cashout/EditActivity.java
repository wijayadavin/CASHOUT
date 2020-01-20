package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends Activity {
	TextView accountType;
	Intent oldIntent;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_edit);
	
	EditText PhoneEditProfil = (EditText) findViewById(R.id.edit_profil_phone);
	TextView PhoneTextView = (TextView) findViewById(R.id.textView_phone);
	//menangkap tipe akun dari halaman sebelumnya
	accountType = (TextView) findViewById(R.id.textView_accountType);
	oldIntent = getIntent();
	accountType.setText(oldIntent.getStringExtra("account_type"));
	
	//khusus account admin
	if(accountType.getText().toString().equals("administrators")) {

	}
	//khusus account members
	if(accountType.getText().toString().equals("members")) {
		PhoneEditProfil.setVisibility(View.GONE);
		PhoneTextView.setVisibility(View.GONE);
	}
	//khusus account tenants
	if(accountType.getText().toString().equals("tenants")) {

	}
}	
	public void callToPrevious(View v) {
		finish();
	}
}
