package com.example.cashout;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

public class PaymentActivity extends Activity {

	private String TAG = PaymentActivity.class.getSimpleName();
    private ProgressDialog pDialog, pDialog1;
    private static String url = "http://apilearningpayment.totopeto.com/transactions/";
	TextView TVTenantName;
	Button bsimpan;
	EditText eamount;
	Intent oldIntent;
	String member_id, tenant_id, tenant_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		oldIntent = getIntent();
		setContentView(R.layout.activity_payment);
		eamount = (EditText) findViewById(R.id.edit_amount);
		TVTenantName = (TextView) findViewById(R.id.tv_tenant_id);
		member_id = oldIntent.getStringExtra("member_id");
		tenant_id = oldIntent.getStringExtra("tenant_id");
		bsimpan = (Button) findViewById(R.id.button_save_edit);
    	new Getaccount().execute();
		bsimpan.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AddNew().execute();
				finish();
			}
		});
	}
	
	private class Getaccount extends AsyncTask<Void, Void, Void>
	{
        protected void onPreExecute() 
        {
        	super.onPreExecute();
            // Showing progress dialog
            pDialog1 = new ProgressDialog(PaymentActivity.this);
            pDialog1.setMessage("Getting tenant's data..");
            pDialog1.setCancelable(false);
            pDialog1.show();
        }
 
        protected Void doInBackground(Void... arg0) 
        {
            HttpHandler sh = new HttpHandler();
            
            // Making a request to URL and getting response
            String jsonStr = sh.makeServiceCall("http://apilearningpayment.totopeto.com/tenants/");
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            // Read JSON
            if (jsonStr != null) 
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
 
                    // Getting JSON Array node
                    JSONArray account = jsonObj.getJSONArray("tenants");
 
                    // looping through All account
                    for (int i = 0; i < account.length(); i++) 
                    {
                        JSONObject m = account.getJSONObject(i);
                        if(m.getString("id").contentEquals(tenant_id))
                        {
                        	tenant_name = m.getString("name");
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
            if (pDialog1.isShowing())
                pDialog1.dismiss();
    		TVTenantName.setText(tenant_name);
        }
	}
//    @Override
//    public void onResume() 
//    {
//    	super.onResume();
//
//		//setText profile data
//
//    }
	
	private class AddNew extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PaymentActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
        @Override
        protected Void doInBackground(Void... arg0) {
            String post_params = null;
            JSONObject params = new JSONObject();
 
            try {
            	params.put("member_id", member_id);
            	params.put("tenant_id", oldIntent.getStringExtra("tenant_id"));
            	params.put("amount", eamount.getText().toString());
            	post_params = params.toString();
            	
            } catch (JSONException e) {
            	e.printStackTrace();
            }
            
            HttpHandler data = new HttpHandler();
            String jsonStr = data.makePostRequest(url, post_params);
            Log.e(TAG, "Response from url: " + jsonStr);
            
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
        }
	}
}
