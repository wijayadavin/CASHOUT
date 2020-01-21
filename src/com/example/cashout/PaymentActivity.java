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

public class PaymentActivity extends Activity {

	private String TAG = PaymentActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String url = "http://apilearningpayment.totopeto.com/transactions/";
	TextView accountType;
	Button bsimpan;
	EditText eamount;
	Intent oldIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		oldIntent = getIntent();
		eamount = (EditText) findViewById(R.id.edit_amount);
		accountType = (TextView) findViewById(R.id.tv_tenant_id);
		accountType.setText(oldIntent.getStringExtra("tenant_id"));
		bsimpan = (Button) findViewById(R.id.button_save_edit);
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
            	params.put("member_id", "1");
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
