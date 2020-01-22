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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class HistoriTopupActivity extends Activity {
	Intent inew, oldIntent;
	String user_id;
	private String TAG = AdminActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    // URL to get topups JSON
    private static String url = "http://apilearningpayment.totopeto.com/transactions/top_up_history?member_id=";
 
    ArrayList<HashMap<String, String>> topupsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		oldIntent = getIntent();
		user_id = oldIntent.getStringExtra("user_id");
		setContentView(R.layout.activity_historitopup);
        lv = (ListView) findViewById(R.id.listView_historiTopup);
	}
	
	private class Gettopups extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(HistoriTopupActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to URL and getting response
            String jsonStr = sh.makeServiceCall(url + user_id);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            // Read JSON
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
 
                    // Getting JSON Array node
                    JSONArray topups = jsonObj.getJSONArray("topups");
 
                    // looping through All topups
                    for (int i = 0; i < topups.length(); i++) {
                        JSONObject c = topups.getJSONObject(i);
                        
                        String amount = c.getString("amount");
                        
                        // tmp hash map for single topups
                        HashMap<String, String> topup = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        topup.put("amount", amount);
                        
                        // adding topup to topup list
                        topupsList.add(topup);
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
                    HistoriTopupActivity.this, topupsList,
                    R.layout.list_item, new String[]{"amount"}, 
                    new int[]{R.id.name});
 
            lv.setAdapter(adapter);
        }
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	topupsList = new ArrayList<HashMap<String, String>>();
    	new Gettopups().execute();
    }
	public void callToPrevious(View v) {
		finish();
	}
}