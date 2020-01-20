package com.example.cashout;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
import android.view.View;

@SuppressWarnings("deprecation")
public class TenantsActivity extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants);
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent tenants_intent;
         
        tenants_intent = new Intent().setClass(this, TenantTab.class);
        spec = tabhost.newTabSpec("histori transaksi").setIndicator("Histori Transaksi",null).setContent(tenants_intent);
        tabhost.addTab(spec);
         
        tenants_intent = new Intent().setClass(this, ProfilActivity.class);
        tenants_intent.putExtra("account_type", "tenants");
        spec = tabhost.newTabSpec("akun").setIndicator("Akun",null).setContent(tenants_intent);
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



