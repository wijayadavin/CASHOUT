package com.example.cashout;

import java.util.ArrayList;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;
@SuppressWarnings("deprecation")
public class MembersActivity extends TabActivity
{
	private String TAG = MembersActivity.class.getSimpleName();
	Intent oldIntent;
	private ProgressDialog pDialog;
	String user_id, user_name, user_email, user_phone_number, user_amount;
	ArrayList<HashMap<String, String>> membersList;
	private static String url = "http://apilearningpayment.totopeto.com/members/";
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
    	oldIntent = getIntent();
        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent member_intent;
         
        member_intent = new Intent().setClass(this, Member1Tab.class);//content pada tab 
        member_intent.putExtra("account_type","members");
        member_intent.putExtra("request_from","members");
        member_intent.putExtra("user_email", oldIntent.getStringExtra("user_email"));
        spec = tabhost.newTabSpec("home").setIndicator("Home",null).setContent(member_intent);//mengeset nama tab dan mengisi content pada menu tab
        tabhost.addTab(spec);//membuat tab baru 
         
        member_intent = new Intent().setClass(this, Member2Tab.class);
        member_intent.putExtra("account_type","members");
        member_intent.putExtra("request_from","members");
        member_intent.putExtra("user_email", oldIntent.getStringExtra("user_email"));
        spec = tabhost.newTabSpec("histori     transaksi").setIndicator("Histori     Transaksi",null).setContent(member_intent);
        tabhost.addTab(spec);
         
        member_intent = new Intent().setClass(this, ProfilActivity.class);
        member_intent.putExtra("url",url);
        member_intent.putExtra("account_type","members");
        member_intent.putExtra("request_from","members");
        member_intent.putExtra("user_email", oldIntent.getStringExtra("user_email"));
        spec = tabhost.newTabSpec("akun").setIndicator("Akun",null).setContent(member_intent);
        tabhost.addTab(spec);
	}
	
	public void callLogout(View v) {
	finish();
	} 

}





