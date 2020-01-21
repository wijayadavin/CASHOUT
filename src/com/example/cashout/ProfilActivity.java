package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfilActivity extends Activity {
	TextView accountType, namaProfil, phoneProfil, emailProfil, SaldoTextView, SaldoProfil;
	EditText topupEdit;
	Intent oldIntent, iEdit;
	Button buttonBack, topupButton, historiTopupButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		oldIntent = getIntent();
		setContentView(R.layout.activity_profil);
		//find R.ID profile data
		accountType = (TextView) findViewById(R.id.textView_accountType);
		namaProfil = (TextView) findViewById(R.id.profil_nama);
		phoneProfil = (TextView) findViewById(R.id.profil_phone);
		emailProfil = (TextView) findViewById(R.id.profil_email);
		SaldoTextView = (TextView) findViewById(R.id.textView_saldo);
		SaldoProfil = (TextView) findViewById(R.id.profil_saldo);
		//setText profile data
		accountType.setText(oldIntent.getStringExtra("account_type"));
		namaProfil.setText(oldIntent.getStringExtra("name"));
		emailProfil.setText(oldIntent.getStringExtra("email"));
		phoneProfil.setText(oldIntent.getStringExtra("phone"));
		SaldoProfil.setText(oldIntent.getStringExtra("saldo"));
		//setText profile data khusus account non-admin
		if(!accountType.getText().toString().equals("administrators")) {
		phoneProfil.setText(oldIntent.getStringExtra("account_type"));
		}
		//find R.ID topup data all account
		topupButton = (Button) findViewById(R.id.button_new_topup);
		topupEdit = (EditText) findViewById(R.id.edit_new_name);
		historiTopupButton = (Button) findViewById(R.id.button_historitopup);
		//find R.ID back button all account
		buttonBack = (Button) findViewById(R.id.button2);	
		//***TEMPLATING***
		//template khusus account admin req by admin AA
		if(accountType.getText().toString().equals("administrators")) {
			//topup features
			topupButton.setVisibility(View.GONE);
			topupEdit.setVisibility(View.GONE);
			//phone data
			phoneProfil.setVisibility(View.GONE);
			//saldo data
			SaldoTextView.setVisibility(View.GONE);
			SaldoProfil.setVisibility(View.GONE);
		}
		//template khusus account tenants req by admin TA
		else if(accountType.getText().toString().equals("tenants")) {
			//topup features
			topupButton.setVisibility(View.GONE);
			topupEdit.setVisibility(View.GONE);
			historiTopupButton.setVisibility(View.GONE);
			//saldo data
			SaldoTextView.setVisibility(View.GONE);
			SaldoProfil.setVisibility(View.GONE);
		}

		//template khusus account members req by admin MA
		else if(oldIntent.getStringExtra("request_from") == "admin" 
		&& accountType.getText().toString().equals("members")) {
			buttonBack.setVisibility(View.GONE);	
		}
		//template khusus account members req by member MM
		else{
			//topup features
			topupButton.setVisibility(View.GONE);
			topupEdit.setVisibility(View.GONE);
		}
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
