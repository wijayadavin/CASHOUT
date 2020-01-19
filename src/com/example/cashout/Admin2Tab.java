package com.example.cashout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin2Tab extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_admin2);
    }
	public void callAkunBaru(View v) {
	Intent MainIntent = new Intent(this, NewActivity.class);
	MainIntent.putExtra("account_type", "members");
	startActivity(MainIntent);
	}
}
