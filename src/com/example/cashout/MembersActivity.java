package com.example.cashout;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
import android.view.View;

@SuppressWarnings("deprecation")
public class MembersActivity extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent member_intent;
         
        member_intent = new Intent().setClass(this, Member1Tab.class);//content pada tab 
        spec = tabhost.newTabSpec("home").setIndicator("Home",null).setContent(member_intent);//mengeset nama tab dan mengisi content pada menu tab
        tabhost.addTab(spec);//membuat tab baru 
         
        member_intent = new Intent().setClass(this, Member2Tab.class);
        spec = tabhost.newTabSpec("histori     transaksi").setIndicator("Histori     Transaksi",null).setContent(member_intent);
        tabhost.addTab(spec);
         
        member_intent = new Intent().setClass(this, ProfilActivity.class);
        member_intent.putExtra("account_type", "members");
        spec = tabhost.newTabSpec("akun").setIndicator("Akun",null).setContent(member_intent);
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



