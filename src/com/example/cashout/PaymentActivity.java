package com.example.cashout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class PaymentActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
	}
	public void callToPrevious(View v) {
		finish();
	}
}
