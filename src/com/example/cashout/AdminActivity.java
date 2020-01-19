package com.example.cashout;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
import android.view.View;

@SuppressWarnings("deprecation")
public class AdminActivity extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
         
        intent = new Intent().setClass(this, Admin1Tab.class);//content pada tab 
        spec = tabhost.newTabSpec("tab1").setIndicator("Akun Toko",null).setContent(intent);//mengeset nama tab dan mengisi content pada menu tab
        tabhost.addTab(spec);//membuat tab baru 
         
        intent = new Intent().setClass(this, Admin2Tab.class);
        spec = tabhost.newTabSpec("tab2").setIndicator("Akun Pembeli",null).setContent(intent);
        tabhost.addTab(spec);
         
        intent = new Intent().setClass(this, Admin3Tab.class);
        spec = tabhost.newTabSpec("tab3").setIndicator("Akun Admin",null).setContent(intent);
        tabhost.addTab(spec);
 
    }
	public void callLogout(View v) {
		finish();
	} 
//	public void callAkunBaru(View v) {
//		Intent MainIntent = new Intent(this, NewActivity.class);
//		startActivity(MainIntent);
//	}
}



