package com.example.cashout;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilActivity extends Activity {
	private static String url_topup = "http://apilearningpayment.totopeto.com/transactions/top_up";
	private String TAG = ProfilActivity.class.getSimpleName();
    private ProgressDialog pDialog, pDialog2;
	TextView accountType, namaProfil, phoneProfil, emailProfil, 
	saldoTextView, saldoProfil, idEdit, accountTextView, phoneTextView;
	EditText topupEdit;
	Intent oldIntent, iEdit;
	Button buttonBack, topupButton, historiTopupButton;
	String url, user_id, user_name, user_email, user_phone_number, user_amount, account_type;
    ArrayList<HashMap<String, String>> accountList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		oldIntent = getIntent();
		url = oldIntent.getStringExtra("url");
		account_type = oldIntent.getStringExtra("account_type");
		setContentView(R.layout.activity_profil);
		user_email = oldIntent.getStringExtra("user_email");
		//find R.ID profile data
		accountTextView = (TextView) findViewById(R.id.textView2);
		accountType = (TextView) findViewById(R.id.textView_accountType);
		namaProfil = (TextView) findViewById(R.id.profil_nama);
		phoneTextView = (TextView) findViewById(R.id.textView_phone);
		phoneProfil = (TextView) findViewById(R.id.profil_phone);
		emailProfil = (TextView) findViewById(R.id.profil_email);
		saldoTextView = (TextView) findViewById(R.id.textView_saldo);
		saldoProfil = (TextView) findViewById(R.id.profil_saldo);
		//find R.ID topup data all account
		topupButton = (Button) findViewById(R.id.button_new_topup);
		topupEdit = (EditText) findViewById(R.id.edit_topup);
		historiTopupButton = (Button) findViewById(R.id.button_historitopup);
		//find R.ID back button all account
		buttonBack = (Button) findViewById(R.id.button2);	
		topupButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TopUp().execute();
				topupEdit.setText(0);
			}
		});
		
		accountTextView.setVisibility(View.INVISIBLE);
		accountType.setVisibility(View.INVISIBLE);
		//setText profile data khusus account non-admin
		if(account_type.equals("members")) 
		{
		phoneProfil.setText(oldIntent.getStringExtra("user_phone_number"));
		}

		//***TEMPLATING***
		//template khusus account admin req by admin AA
		if(account_type.equals("administrators")) 
		{
			//topup features
			historiTopupButton.setVisibility(View.INVISIBLE);
			topupButton.setVisibility(View.INVISIBLE);
			topupEdit.setVisibility(View.INVISIBLE);
			//phone data
			phoneTextView.setVisibility(View.INVISIBLE);
			phoneProfil.setVisibility(View.INVISIBLE);
			//saldo data
			saldoTextView.setVisibility(View.INVISIBLE);
			saldoProfil.setVisibility(View.INVISIBLE);
		}
		//template khusus account tenants req by admin TA
		else if(account_type.equals("tenants")) 
		{
			//topup features
			topupButton.setVisibility(View.INVISIBLE);
			topupEdit.setVisibility(View.INVISIBLE);
			historiTopupButton.setVisibility(View.INVISIBLE);
			//phone data
			phoneTextView.setVisibility(View.INVISIBLE);
			phoneProfil.setVisibility(View.INVISIBLE);
			//saldo data
			saldoTextView.setVisibility(View.INVISIBLE);
			saldoProfil.setVisibility(View.INVISIBLE);
			//back button
			if(oldIntent.getStringExtra("request_from").equals("members")) {
			buttonBack.setVisibility(View.INVISIBLE);
			}
		}

		//template khusus account members req by member MM
		else if(oldIntent.getStringExtra("request_from").equals("members") 
		&& account_type.equals("members")) 
		{
			buttonBack.setVisibility(View.INVISIBLE);
			topupButton.setVisibility(View.INVISIBLE);
			topupEdit.setVisibility(View.INVISIBLE);
		}
		//template khusus account members req by admin MA
		else{
			//topup features

		}
	}
	
	private class TopUp extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog2 = new ProgressDialog(ProfilActivity.this);
            pDialog2.setMessage("Processing TopUp request...");
            pDialog2.setCancelable(false);
            pDialog2.show();
        }
        
        @Override
        protected Void doInBackground(Void... arg0) {
            String post_params = null;
            JSONObject params = new JSONObject();
 
            try {
            	params.put("member_id", user_id);
            	params.put("amount", topupEdit.getText().toString());
            	post_params = params.toString();
            	
            } catch (JSONException e) {
            	e.printStackTrace();
            }
            
            HttpHandler data = new HttpHandler();
            String jsonStr = data.makePostRequest(url_topup, post_params);
            Log.e(TAG, "Response from url: " + jsonStr);
            
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog2.isShowing())
                pDialog2.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
        }
	}
	
	private class Getaccount extends AsyncTask<Void, Void, Void>
	{
        protected void onPreExecute() 
        {
        	super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ProfilActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        protected Void doInBackground(Void... arg0) 
        {
            HttpHandler sh = new HttpHandler();
            
            // Making a request to URL and getting response
            String jsonStr = sh.makeServiceCall(url);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            // Read JSON
            if (jsonStr != null) 
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
 
                    // Getting JSON Array node
                    JSONArray account = jsonObj.getJSONArray(account_type);
 
                    // looping through All account
                    for (int i = 0; i < account.length(); i++) 
                    {
                        JSONObject m = account.getJSONObject(i);
                        if(m.getString("email").contentEquals(user_email))
                        {
                        		user_id = m.getString("id");
                        		user_name = m.getString("name");
                        		if(account_type.equals("members")) 
                        		{
                        			user_phone_number = m.getString("phone_number");
                        			user_amount = m.getString("current_balance");
                        		}      		
                        }
                    } }
                    catch (final JSONException e) 
                    {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread
                    (
                    		new Runnable() 
	                    {
	                        public void run() 
	                        {
	                            Toast.makeText(getApplicationContext(),
	                                    "Json parsing error: " + e.getMessage(),
	                                    Toast.LENGTH_LONG)
	                                    .show();
	                        }
	                    }
                    );
                }
            }
        else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread
                (
                new Runnable() 
	                {
	                    public void run() 
	                    {
	                        Toast.makeText(getApplicationContext(),
	                                "Couldn't get json from server. Check LogCat for possible errors!",
	                                Toast.LENGTH_LONG)
	                                .show();
	                    }
	                }
                );
            }
 
            return null;
        }
        protected void onPostExecute(Void result) 
        {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
    		accountType.setText(account_type);
    		namaProfil.setText(user_name);
    		emailProfil.setText(user_email);
    		phoneProfil.setText(user_phone_number);
    		saldoProfil.setText("  "+user_amount);
        }
	}
    @Override
    public void onResume() 
    {
    	super.onResume();
    	accountList = new ArrayList<HashMap<String, String>>();
    	new Getaccount().execute();
		//setText profile data
    }
	public void callEdit(View v) 
	{
		iEdit = new Intent(this, EditActivity.class);
		iEdit.putExtra("account_type",oldIntent.getStringExtra("account_type"));
		iEdit.putExtra("request_from",oldIntent.getStringExtra("request_from"));
		iEdit.putExtra("user_id",user_id);
		iEdit.putExtra("user_name",user_name);
		iEdit.putExtra("user_email",user_email);
		iEdit.putExtra("user_phone_number",user_phone_number);
		startActivity(iEdit);
	}
	public void callToPrevious(View v) 
	{
		finish();
	}
	public void callTopupHistory(View v) 
	{
		iEdit = new Intent(this, HistoriTopupActivity.class);
		iEdit.putExtra("user_id",user_id);
		startActivity(iEdit);
	}

}
