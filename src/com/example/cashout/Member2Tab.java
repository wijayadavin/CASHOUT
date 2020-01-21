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


public class Member2Tab extends Activity {
	Intent inew, oldIntent;
	private String TAG = AdminActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    
    // URL to get transactions JSON
    private static String url = "http://apilearningpayment.totopeto.com/transactions?member_id=1";
 
    ArrayList<HashMap<String, String>> transactionsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_member2);
        lv = (ListView) findViewById(R.id.list_tab_member2);          
	}
	
	private class Gettransactions extends AsyncTask<Void, Void, Void> {
   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Member2Tab.this);
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
                    JSONArray transactions = jsonObj.getJSONArray("transactions");
 
                    // looping through All transactions
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject c = transactions.getJSONObject(i);
                        
                        String member_id = c.getString("member_id");
                        String tenant_id = c.getString("tenant_id");
                        String amount = c.getString("amount");
                        
                        // tmp hash map for single transactions
                        HashMap<String, String> transaction = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        transaction.put("member_id", member_id);
                        transaction.put("tenant_id", tenant_id);
                        transaction.put("amount", amount);
                        
                        // adding transaction to transaction list
                        transactionsList.add(transaction);
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
            		Member2Tab.this, transactionsList,
                    R.layout.list_item, new String[]{"tenant_name", "amount"}, new int[]{R.id.name, R.id.email});
 
            lv.setAdapter(adapter);
        }
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	transactionsList = new ArrayList<HashMap<String, String>>();
    	new Gettransactions().execute();
    }	
}