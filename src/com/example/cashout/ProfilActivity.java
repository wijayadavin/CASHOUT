package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfilActivity extends Activity {
	TextView accountType, namaProfil, phoneProfil, emailProfil;
	EditText topupEdit;
	Intent oldIntent, iEdit;
	Button buttonBack, topupButton, historiTopupButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		oldIntent = getIntent();
		setContentView(R.layout.activity_profil);
		//find R.ID profile
		accountType = (TextView) findViewById(R.id.textView_accountType);
		namaProfil = (TextView) findViewById(R.id.profil_nama);
		phoneProfil = (TextView) findViewById(R.id.profil_phone);
		emailProfil = (TextView) findViewById(R.id.profil_email);
		//setText profile
		accountType.setText(oldIntent.getStringExtra("account_type"));
		namaProfil.setText(oldIntent.getStringExtra("account_type"));
		phoneProfil.setText(oldIntent.getStringExtra("account_type"));
		emailProfil.setText(oldIntent.getStringExtra("account_type"));
		//find R.ID topup
		topupButton = (Button) findViewById(R.id.button_new);
		historiTopupButton = (Button) findViewById(R.id.button_historitopup);
		topupEdit = (EditText) findViewById(R.id.edit_new_name);
		//find R.ID back button
		buttonBack = (Button) findViewById(R.id.button2);
		//template khusus account admin req by admin
		if(accountType.getText().toString().equals("administrators")) {
			topupButton.setVisibility(View.GONE);
			historiTopupButton.setVisibility(View.GONE);
			topupEdit.setVisibility(View.GONE);
		}
		//template khusus account members req by members
		if(oldIntent.getStringExtra("request_from") == "admin" 
		&& accountType.getText().toString().equals("members")) {
			buttonBack.setVisibility(View.GONE);	
		}
		//template khusus account tenants req by admin
		if(accountType.getText().toString().equals("tenants")) {
			topupButton.setVisibility(View.GONE);
			historiTopupButton.setVisibility(View.GONE);
			topupEdit.setVisibility(View.GONE);
		}
		//template khusus account members req by admin is default (nothing to change)
	}
	public void callEdit(View v) {
		iEdit = new Intent(this, EditActivity.class);
		iEdit.putExtra("account_type",oldIntent.getStringExtra("account_type"));
		startActivity(iEdit);
	}
	
	
	public void callToPrevious(View v) {
		finish();
	}
}
