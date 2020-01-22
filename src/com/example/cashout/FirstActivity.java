package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FirstActivity extends Activity {
Intent intent;
EditText emailUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		emailUser = (EditText) findViewById(R.id.edit_user_email);
		}

		public void callLoginAsAdmin(View v) {
			intent = new Intent(this, AdminActivity.class);
			intent.putExtra("user_email", emailUser.getText().toString());
			intent.putExtra("url" , "http://apilearningpayment.totopeto.com/administrations/");
			startActivity(intent);
		}

		public void callLoginAsMember(View v) {
			intent = new Intent(this, MembersActivity.class);
			intent.putExtra("user_email", emailUser.getText().toString());
			intent.putExtra("url" , "http://apilearningpayment.totopeto.com/members/");
			startActivity(intent);
		}
		public void callLoginAsTenant(View v) {
			intent = new Intent(this, TenantsActivity.class);
			intent.putExtra("user_email", emailUser.getText().toString());
			intent.putExtra("url" , "http://apilearningpayment.totopeto.com/tenants/");
			startActivity(intent);
		}


}