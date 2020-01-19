package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends Activity {
Intent iAdmin, iMember, iTenant;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		}

		public void callLoginAsAdmin(View v) {
		iAdmin = new Intent(this, AdminActivity.class);
		startActivity(iAdmin);
		}

		public void callLoginAsMember(View v) {
			iMember = new Intent(this, MembersActivity.class);
			startActivity(iMember);
		}
		public void callLoginAsTenant(View v) {
			iTenant = new Intent(this, TenantsActivity.class);
			startActivity(iTenant);
		}


}