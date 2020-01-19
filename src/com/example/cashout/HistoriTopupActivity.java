package com.example.cashout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class HistoriTopupActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historitopup);
	}
	public void callToPrevious(View v) {
		finish();
	}
}
