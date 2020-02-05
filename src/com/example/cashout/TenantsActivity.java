package com.example.cashout;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
import android.view.View;

@SuppressWarnings("deprecation")
public class TenantsActivity extends TabActivity {
	Intent oldIntent;
	private static String url = "http://apilearningpayment.totopeto.com/tenants/";
	public void onCreate(Bundle savedInstanceState) {
		oldIntent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants);
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent tenants_intent;
         
        tenants_intent = new Intent().setClass(this, TenantTab.class);
        spec = tabhost.newTabSpec("histori transaksi").setIndicator("Histori Transaksi",null).setContent(tenants_intent);
        tenants_intent.putExtra("user_email", oldIntent.getStringExtra("user_email"));
        tabhost.addTab(spec);
         
        tenants_intent = new Intent().setClass(this, ProfilActivity.class);
        tenants_intent.putExtra("url",url);
        tenants_intent.putExtra("account_type","tenants");
        tenants_intent.putExtra("request_from","tenants");
        tenants_intent.putExtra("user_email", oldIntent.getStringExtra("user_email"));
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



