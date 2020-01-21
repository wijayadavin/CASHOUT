package com.example.cashout;

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

public class NewActivity extends Activity {

	private String TAG = NewActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    String url = "";
	TextView accountType;
	Button bsimpan;
	EditText ename, eemail, ephone;
	Intent oldIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		oldIntent = getIntent();
		url = oldIntent.getStringExtra("session_url");
		ename = (EditText) findViewById(R.id.edit_new_name);
		eemail = (EditText) findViewById(R.id.edit_new_email);
		ephone = (EditText) findViewById(R.id.edit_phone);
		accountType = (TextView) findViewById(R.id.textView_accountType);
		accountType.setText(oldIntent.getStringExtra("account_type"));
		bsimpan = (Button) findViewById(R.id.button_buat);
		bsimpan.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AddNew().execute();
				finish();
			}
		});
	}
	
	private class AddNew extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(NewActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
        @Override
        protected Void doInBackground(Void... arg0) {
            String post_params = null;
            JSONObject params = new JSONObject();
 
            try {
            	params.put("name", ename.getText().toString());
            	params.put("email", eemail.getText().toString());
            	params.put("phone_number", ephone.getText().toString());
            	params.put("current_balance", "0");
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
