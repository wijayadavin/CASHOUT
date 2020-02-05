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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class TenantTab extends Activity 
{
	Intent inew, oldIntent;
	private String TAG = AdminActivity.class.getSimpleName();
    private ProgressDialog pDialog1, pDialog2 ;
    private ListView lv;
    // URL to get transactions JSON
    private static String url = "http://apilearningpayment.totopeto.com/tenants/", member_name = "";
    ArrayList<HashMap<String, String>> tenantList;
    ArrayList<HashMap<String, String>> transactionsList;
    String user_email, user_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		oldIntent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_tenant);
		user_email = oldIntent.getStringExtra("user_email");
        lv = (ListView) findViewById(R.id.list_tenant_tab);        
	}
	private class getID extends AsyncTask<Void, Void, Void>
	{
	    protected void onPreExecute() 
	    {
	    	super.onPreExecute();
	        // Showing progress dialog
	        pDialog1 = new ProgressDialog(TenantTab.this);
	        pDialog1.setMessage("Getting ID.. Please wait...");
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
	                JSONArray tenants = jsonObj.getJSONArray("tenants");

	                // looping through All tenants
	                for (int i = 0; i < tenants.length(); i++) 
	                {
	                    JSONObject m = tenants.getJSONObject(i);
	                    if(m.getString("email").contentEquals(user_email))
	                    {
	                    		user_id = m.getString("id");
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
        }
	}
	private class Gettransactions extends AsyncTask<Void, Void, Void> 
	{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog2 = new ProgressDialog(TenantTab.this);
            pDialog2.setMessage("Fetching transactions List.. Please wait...");
            pDialog2.setCancelable(false);
            pDialog2.show();
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to URL and getting response
            String jsonStr = sh.makeServiceCall(url+user_id+"/transaction");
            Log.e(TAG, "Response from url: " + jsonStr);
            // Read JSON
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
 
                    // Getting JSON Array node
                    JSONArray transactions = jsonObj.getJSONArray("transactions");
 
                    // looping through All transactions
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject c = transactions.getJSONObject(i);
                        
                        String member_id = c.getString("member_id");
                        String amount = c.getString("amount");
                        
                        // tmp hash map for single transactions
                        HashMap<String, String> tenant = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
//                        tenant.put("member_id", member_id);
                        // adding member name for each transactions
                        String jsonStr2 = sh.makeServiceCall("http://apilearningpayment.totopeto.com/members/");
                        JSONObject jsonObj2 = new JSONObject(jsonStr2);
                        JSONArray account = jsonObj2.getJSONArray("members");
                        for (int index = 0; index < account.length(); index++) 
                        {
                            JSONObject m = account.getJSONObject(index);
                            if(m.getString("id").contentEquals(member_id))
                            {
                            		member_name = m.getString("name");
                            }
                        }
                        tenant.put("member_name", "Purchased by:" + member_name);
                        tenant.put("amount","Amount:" + amount);
                        
                        // adding tenant to tenant list
                        transactionsList.add(tenant);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) 
        {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog2.isShowing())
                pDialog2.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
            		TenantTab.this, transactionsList,
                    R.layout.list_item, new String[]{"member_name", "amount"}, new int[]{R.id.name, R.id.email});
 
            lv.setAdapter(adapter);
        }
    }
    
    @Override
    public void onResume() {
    	super.onResume();
		tenantList = new ArrayList<HashMap<String, String>>();
		new getID().execute();
    	transactionsList = new ArrayList<HashMap<String, String>>();
    	new Gettransactions().execute();
    }	
}