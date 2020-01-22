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


public class Member1Tab extends Activity 
{
	Intent inew, oldIntent;
	private String TAG = AdminActivity.class.getSimpleName();
    private ProgressDialog pDialog1, pDialog2 ;
    private ListView lv;
    // URL to get tenants JSON
    private static String url = "http://apilearningpayment.totopeto.com/tenants/";
    ArrayList<HashMap<String, String>> membersList;
    ArrayList<HashMap<String, String>> tenantsList;
    String user_email, user_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		oldIntent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_member1);
		user_email = oldIntent.getStringExtra("user_email");
        lv = (ListView) findViewById(R.id.list_tab_member1);        
        lv.setOnItemClickListener
        (
	        new OnItemClickListener() 
	        {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
				{
				// TODO Auto-generated method stub
				HashMap<String, String> hm = tenantsList.get(position);
				Intent intent = new Intent(Member1Tab.this, PaymentActivity.class);
				intent.putExtra("tenant_id", hm.get("id"));
				intent.putExtra("member_id", user_id);
				startActivity(intent);
				}
			}
        );
	}
	private class Getmembers extends AsyncTask<Void, Void, Void>
	{
	    protected void onPreExecute() 
	    {
	    	super.onPreExecute();
	        // Showing progress dialog
	        pDialog1 = new ProgressDialog(Member1Tab.this);
	        pDialog1.setMessage("Getting ID.. Please wait...");
	        pDialog1.setCancelable(false);
	        pDialog1.show();
	    }

	    protected Void doInBackground(Void... arg0) 
	    {
	        HttpHandler sh = new HttpHandler();
	        
	        // Making a request to URL and getting response
	        String jsonStr = sh.makeServiceCall("http://apilearningpayment.totopeto.com/members/");

	        Log.e(TAG, "Response from url: " + jsonStr);

	        // Read JSON
	        if (jsonStr != null) 
	        {
	            try {
	                JSONObject jsonObj = new JSONObject(jsonStr);

	                // Getting JSON Array node
	                JSONArray members = jsonObj.getJSONArray("members");

	                // looping through All members
	                for (int i = 0; i < members.length(); i++) 
	                {
	                    JSONObject m = members.getJSONObject(i);
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
	private class Gettenants extends AsyncTask<Void, Void, Void> 
	{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog2 = new ProgressDialog(Member1Tab.this);
            pDialog2.setMessage("Fetching Tenants List.. Please wait...");
            pDialog2.setCancelable(false);
            pDialog2.show();
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to URL and getting response
            String jsonStr = sh.makeServiceCall(url);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            // Read JSON
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
 
                    // Getting JSON Array node
                    JSONArray tenants = jsonObj.getJSONArray("tenants");
 
                    // looping through All tenants
                    for (int i = 0; i < tenants.length(); i++) {
                        JSONObject c = tenants.getJSONObject(i);
                        
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        
                        // tmp hash map for single tenants
                        HashMap<String, String> tenant = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        tenant.put("id", id);
                        tenant.put("name", name);
                        tenant.put("email", email);
                        
                        // adding tenant to tenant list
                        tenantsList.add(tenant);
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
            		Member1Tab.this, tenantsList,
                    R.layout.list_item, new String[]{"name", "email"}, new int[]{R.id.name, R.id.email});
 
            lv.setAdapter(adapter);
        }
    }
    
    @Override
    public void onResume() {
    	super.onResume();
		membersList = new ArrayList<HashMap<String, String>>();
		new Getmembers().execute();
    	tenantsList = new ArrayList<HashMap<String, String>>();
    	new Gettenants().execute();
    }	
}