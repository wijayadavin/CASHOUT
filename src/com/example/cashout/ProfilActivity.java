package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfilActivity extends Activity {
	TextView accountType;
	EditText topupEdit;
	Intent oldIntent, iEdit;
	Button buttonBack, topupButton, historiTopupButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		accountType = (TextView) findViewById(R.id.textView_accountType);
		oldIntent = getIntent();
		accountType.setText(oldIntent.getStringExtra("account_type"));
		//find ID topup
		topupButton = (Button) findViewById(R.id.button_new);
		historiTopupButton = (Button) findViewById(R.id.button_historitopup);
		topupEdit = (EditText) findViewById(R.id.edit_topup);
		//khusus account admin
		if(accountType.getText().toString().equals("administrators")) {
			topupButton.setVisibility(View.GONE);
			historiTopupButton.setVisibility(View.GONE);
			topupEdit.setVisibility(View.GONE);
		}
		//khusus account members
		buttonBack = (Button) findViewById(R.id.button2);
		if(accountType.getText().toString().equals("members")) {
			buttonBack.setVisibility(View.GONE);
		}
		//khusus account tenants
		if(accountType.getText().toString().equals("tenants")) {
		topupButton.setVisibility(View.GONE);
		historiTopupButton.setVisibility(View.GONE);
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
