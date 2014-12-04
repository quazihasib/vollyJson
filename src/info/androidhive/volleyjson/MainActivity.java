package info.androidhive.volleyjson;

import java.util.Iterator;

import info.androidhive.volleyjson.R;
import info.androidhive.volleyjson.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class MainActivity extends Activity {

	// json object response url
	private String urlJsonObj = "http://site.bongobd.com/api/category.php?catID=1";
	
	// json array response url
	private String urlJsonArry = "http://api.androidhive.info/volley/person_array.json";

	private static String TAG = MainActivity.class.getSimpleName();
	private Button btnMakeObjectRequest, btnMakeArrayRequest;
	public static String DEBUG_TAG = MainActivity.class.getSimpleName();
	
	// Progress dialog
	private ProgressDialog pDialog;

	private TextView txtResponse;

	// temporary string to show the parsed response
	private String jsonResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnMakeObjectRequest = (Button) findViewById(R.id.btnObjRequest);
		btnMakeArrayRequest = (Button) findViewById(R.id.btnArrayRequest);
		txtResponse = (TextView) findViewById(R.id.txtResponse);

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);

		btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// making json object request
				makeJsonObjectRequest();
			}
		});

		btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// making json array request
				makeJsonArrayRequest();
			}
		});

	}

	/**
	 * Method to make json object request where json response starts wtih {
	 * */
	private void makeJsonObjectRequest() {

		showpDialog();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());

						try {
							
							JSONObject js = response.getJSONObject("data");
							
							// Parsing json object response
							// response will be a json object
//							String name = response.getString("content_title");
//							String email = response.getString("total_view");
//							JSONObject phone = response.getJSONObject("content_thumb");
//							String home = phone.getString("content_length");
//							String mobile = phone.getString("category_name");
//
//							jsonResponse = "";
//							jsonResponse += "Name: " + name + "\n\n";
//							jsonResponse += "Email: " + email + "\n\n";
//							jsonResponse += "Home: " + home + "\n\n";
//							jsonResponse += "Mobile: " + mobile + "\n\n";

							txtResponse.setText(""+js);
							
							Log.d(DEBUG_TAG, "js: "+js); 
							
						    Iterator<String> iter = js.keys();
						    while(iter.hasNext()) 
						    {
						        String key = iter.next();
						        try 
						        {
						            Object value = js.get(key);
						            Log.d(DEBUG_TAG, "value:"+value ); 
						            
						            JSONObject eachObject = js.getJSONObject(""+ key);
						            
						            
						            String avg_rating = eachObject.getString("avg_rating");
									Log.d(DEBUG_TAG, "avg_rating: "+avg_rating); 
									
									String content_title = eachObject.getString("content_title");
									Log.d(DEBUG_TAG, "content_title: "+content_title);
									
									String entry_time = eachObject.getString("entry_time");
								    Log.d(DEBUG_TAG, "entry_time: "+entry_time); 
										
									String content_thumb = eachObject.getString("content_thumb");
									content_thumb= "http://site.bongobd.com/wp-content/themes/bongobd/" +
											"images/posterimage/thumb/"+content_thumb;
									Log.d(DEBUG_TAG, "content_thumb: "+content_thumb);
									
									String by = eachObject.getString("by");
									Log.d(DEBUG_TAG, "by: "+by); 
									
									String total_view = eachObject.getString("total_view");
									Log.d(DEBUG_TAG, "total_view: "+total_view); 
									
									String content_length = eachObject.getString("content_length");
									Log.d(DEBUG_TAG, "content_length: "+content_length); 
									
									String content_short_summary = eachObject.getString("content_short_summary");
									Log.d(DEBUG_TAG, "content_short_summary: "+content_short_summary); 
									
//									String category_name = eachObject.getString("category_name");
//									Log.d(DEBUG_TAG, "category_name: "+category_name); 
									
//									String content_summary = eachObject.getString("content_summary");
//									Log.d(DEBUG_TAG, "content_summary: "+content_summary); 
									
									
									
//									HashMap<String, String> map = new HashMap<String, String>();
//									map.put("movieName", content_title);
//									map.put("movieViews", total_view);
//									map.put("movieDirector", by);
//									map.put("movieImage", content_thumb);
//									map.put("contentLength", content_length);
//									map.put("movieShortSummary", content_short_summary);
////									map.put("movieCategory", category_name);
//									
//									
//									arraylist.add(map);
						        } 
						        catch (JSONException e)
						        {
						            // Something went wrong!
						        }
						    }
						    
						

						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						hidepDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						hidepDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}

	/**
	 * Method to make json array request where response starts with [
	 * */
	private void makeJsonArrayRequest() {

		showpDialog();

		JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());

						try {
							// Parsing json array response
							// loop through each json object
							jsonResponse = "";
							for (int i = 0; i < response.length(); i++) {

								JSONObject person = (JSONObject) response
										.get(i);

								String name = person.getString("name");
								String email = person.getString("email");
								JSONObject phone = person
										.getJSONObject("phone");
								String home = phone.getString("home");
								String mobile = phone.getString("mobile");

								jsonResponse += "Name: " + name + "\n\n";
								jsonResponse += "Email: " + email + "\n\n";
								jsonResponse += "Home: " + home + "\n\n";
								jsonResponse += "Mobile: " + mobile + "\n\n\n";

							}

							txtResponse.setText(jsonResponse);

						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}

						hidepDialog();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						hidepDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req);
	}

	private void showpDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hidepDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}
}
