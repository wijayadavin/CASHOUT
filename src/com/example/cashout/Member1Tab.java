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


public class Member1Tab extends Activity {
	Intent inew, oldIntent;
	private String TAG = AdminActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    
    // URL to get tenants JSON
    private static String url = "http://apilearningpayment.totopeto.com/tenants/";
 
    ArrayList<HashMap<String, String>> tenantsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_member1);
        lv = (ListView) findViewById(R.id.list_tab_member1);        
        lv.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
			HashMap<String, String> hm = tenantsList.get(position);
			Intent intent = new Intent(Member1Tab.this, PaymentActivity.class);
			intent.putExtra("tenant_id", hm.get("id"));
			startActivity(intent);
			}
		});
        
        

	}
	
	private class Gettenants extends AsyncTask<Void, Void, Void> {
   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Member1Tab.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
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
    	tenantsList = new ArrayList<HashMap<String, String>>();
    	new Gettenants().execute();
    }	
}