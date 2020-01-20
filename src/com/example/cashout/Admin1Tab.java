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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Admin1Tab extends Activity {
	private String TAG = Admin1Tab.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private Button badd;
    
    // URL to get contacts JSON
    private static String url = "http://apilearningpayment.totopeto.com/tenants";
    
    ArrayList<HashMap<String, String>> tenantList;
	@Override
	
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_admin1);
        
        lv = (ListView) findViewById(R.id.tenant_list);
        badd = (Button) findViewById(R.id.button_new);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> hm = tenantList.get(position);
				Intent intent = new Intent(Admin1Tab.this, ProfilActivity.class);
				intent.putExtra("id", hm.get("id"));
				intent.putExtra("email", hm.get("email"));
				intent.putExtra("number", hm.get("number"));
				startActivity(intent);
			}
		});
        class GetContacts extends AsyncTask<Void, Void, Void> {
        	
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                pDialog = new ProgressDialog(Admin1Tab.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
     
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
                        JSONArray contacts = jsonObj.getJSONArray("contacts");
     
                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            
                            String id = c.getString("id");
                            String name = c.getString("name");
                            String email = c.getString("email");
                            String phone = c.getString("phone");
                            
                            // tmp hash map for single contact
                            HashMap<String, String> contact = new HashMap<String, String>();
     
                            // adding each child node to HashMap key => value
                            contact.put("id", id);
                            contact.put("name", name);
                            contact.put("email", email);
                            contact.put("phone", phone);
                            
                            // adding contact to contact list
                            tenantList.add(contact);
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
                        Admin1Tab.this, tenantList,
                        R.layout.list_item, new String[]{"name", "email",
                        "phone"}, new int[]{R.id.name,
                        R.id.email, R.id.phone});
     
                lv.setAdapter(adapter);
            }
        }
        

              
        
        
    }

	public void callAkunBaru(View v) {
	Intent MainIntent = new Intent(this, NewActivity.class);
	MainIntent.putExtra("account_type", "tenants");
	startActivity(MainIntent);
	}

}
