package kartify_base;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PostData implements ConstantValues
{


	PostCommentResponseListener  mPostCommentResponse ;






	public interface PostCommentResponseListener {
        public void requestStarted();
		public void requestCompleted(String message);
		public void requestEndedWithError(VolleyError error);
	}


	public interface PostCommentJsonResponseListener {
		public void requestStarted();
		public void requestCompleted(JSONObject message);
		public void requestEndedWithError(VolleyError error);
	}

	public static void       call(final Context ctx,JSONObject jsonobject_one, String url, final PostCommentJsonResponseListener mPostCommentResponse) {

		Log.d("SSSD", "VALUESS" + url + "--" + jsonobject_one.toString());



		mPostCommentResponse.requestStarted();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest
				(
						Request.Method.POST,url,
						jsonobject_one,


						new Response.Listener<JSONObject>()
						{
							@Override
							public void onResponse(JSONObject response)
							{
								if(mPostCommentResponse != null)
								{
									mPostCommentResponse.requestCompleted(response);
								}

							}


						}
						, new Response.ErrorListener()
						{

							@Override
							public void onErrorResponse(VolleyError error)
							{
								if(mPostCommentResponse != null)
								{
									mPostCommentResponse.requestEndedWithError(error);
								}

							}

						}) 

		{

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				headers.put("Accept-Encoding", "Accept-Encoding: gzip, deflate");
				headers.put("Authorization", CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, ctx));
				headers.put("Cookie",  CommonClass.returnGenericData(COOKIE_ID,COOKIE_ID_PREF,ctx));
		  Log.d("VA","VALUES -->"
		  + CommonClass.returnGenericData(COOKIE_ID,COOKIE_ID_PREF,ctx));
				Log.d("VA","VALUES -->"
						+ CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, ctx));
						return headers;
			}
		};


		int socketTimeout = 30000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);
		AppController.getInstance().addToRequestQueue(jsonObjReq);

	}

	public static void     callwithoutCookie(final Context ctx,JSONObject jsonobject_one, String url, final PostCommentJsonResponseListener mPostCommentResponse) {

		Log.d("SSSD", "VALUESS" + url + "--" + jsonobject_one.toString());



		mPostCommentResponse.requestStarted();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest
				(
						Request.Method.POST,url,
						jsonobject_one,


						new Response.Listener<JSONObject>()
						{
							@Override
							public void onResponse(JSONObject response)
							{
								mPostCommentResponse.requestCompleted(response);
							}


						}
						, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error)
					{

						{
							mPostCommentResponse.requestEndedWithError(error);
						}
					}

				})

		{

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				//headers.put("cookie",  CommonClass.returnGenericData(COOKIE_ID,COOKIE_ID_PREF,ctx));
//				headers.put("Authorization", "e9ad2f733b0b23b45e50ab1adfac5c430eb74ac4c087a97bda40c3f67168071f");
				return headers;
			}
		};


		int socketTimeout = 30000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);
		AppController.getInstance().addToRequestQueue(jsonObjReq);

	}
	public static void callWIthHeader(JSONObject jsonobject_one, String url, final PostCommentJsonResponseListener mPostCommentResponse,final String heade) {

		Log.d("SSSD", "SSSS URL" + url + "--" + jsonobject_one.toString());



		mPostCommentResponse.requestStarted();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest
				(
						Request.Method.POST,url,
						jsonobject_one,


						new Response.Listener<JSONObject>()
						{
							@Override
							public void onResponse(JSONObject response)
							{
								mPostCommentResponse.requestCompleted(response);
							}


						}
						, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error)
					{

						{
							mPostCommentResponse.requestEndedWithError(error);

						}
					}

				})

		{

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");


						headers.put("Authorization", ""+heade);


			//	Log.d("AUTHOS","HEADER"+heade);


				return headers;
			}
		};


		int socketTimeout = 30000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);
		AppController.getInstance().addToRequestQueue(jsonObjReq);

	}
	
	


	public static void calla(JSONObject jsonobject_one, String url, final PostCommentResponseListener mPostCommentResponse)
	{


		mPostCommentResponse.requestStarted();
		//	    RequestQueue queue = Volley.newRequestQueue(context);

		StringRequest sr = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				mPostCommentResponse.requestCompleted(response);
				//	            queue.
				Log.d("RESPOSNE","RESPONSE"+response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				mPostCommentResponse.requestEndedWithError(error);
			}
		}){



			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}


		};


		sr.setRetryPolicy(new DefaultRetryPolicy
				(
				2000, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		AppController.getInstance().addToRequestQueue(sr);
	}

	public static void getDataa(Context context, final String url, final PostCommentResponseListener	mPostCommentResponse){


		mPostCommentResponse.requestStarted();
		//	    RequestQueue queue = Volley.newRequestQueue(context);

		StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				mPostCommentResponse.requestCompleted(response);
				//	            queue.
				Log.d("RESPOSNE","RESPONSE"+response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				mPostCommentResponse.requestEndedWithError(error);
			}
		}){};

		sr.setRetryPolicy(new DefaultRetryPolicy(
				2000, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		AppController.getInstance().addToRequestQueue(sr);
	}

	public static void getDataWIthAuthentication(Context context, final String url, final PostCommentResponseListener	mPostCommentResponse){


		mPostCommentResponse.requestStarted();
		//	    RequestQueue queue = Volley.newRequestQueue(context);

		StringRequest sr = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				mPostCommentResponse.requestCompleted(response);
				//	            queue.
				Log.d("RESPOSNE","RESPONSE"+response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				mPostCommentResponse.requestEndedWithError(error);
			}
		}){};

		sr.setRetryPolicy(new DefaultRetryPolicy(
				2000, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		AppController.getInstance().addToRequestQueue(sr);
	}





	public static void callWithGetResponseAsHeader(final Context ctx,String jsonobject_one, String url,
												   final PostCommentResponseListener mPostCommentResponse)
	{

		Log.d("SSSD", "VALUESS" + url + "--" + jsonobject_one.toString());



		mPostCommentResponse.requestStarted();

		  Request jsonObjReq = new JsonRequest<String>
				(
						Request.Method.POST,url,
						    jsonobject_one,


											new Response.Listener<String>()
											{
												@Override
												public void onResponse(String response)
												{
													Log.d("RESPONSE:","RESPONSE::::"+response);
													mPostCommentResponse.requestCompleted(response);
												}


											}

						          , new Response.ErrorListener()
				                    {

											@Override
											public void onErrorResponse(VolleyError error)
											{

												Log.d("RESPONSE:","RESPONSE ERROR::::"+error.getMessage());
												{
													mPostCommentResponse.requestEndedWithError(error);
												}
											}
				                     }



				)

		  {

										@Override
										public Map<String, String> getHeaders() throws AuthFailureError
										{
											HashMap<String, String> headers = new HashMap<String, String>();
											headers.put("Content-Type", "application/json; charset=utf-8");
							//				headers.put("Authorization",      "e9ad2f733b0b23b45e50ab1adfac5c430eb74ac4c087a97bda40c3f67168071f");
											return headers;
										}

			  @Override
			  protected Response<String> parseNetworkResponse(NetworkResponse response)
			  {
				  if (response == null)
				  {
					  return null;
				  }
				  String parsed;
				  try {


					  Map<String, String> responseHeaders = response.headers;
					 /* Set keys = responseHeaders.keySet();
					  System.out.println("All keys are: " + keys);
// To get all key: value
					  for(Object key: keys){
						  System.out.println(key + ": " + responseHeaders.get(key));
					  }*/
					  String rawCookies = responseHeaders.get("set-cookie");
					  parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//					  Log.d("RESPONSE:","RESPONSE::HEADER::"+response.headers.get("Authorization"));
//					  Log.d("RESPONSE:","RESPONSE::COOKIe::"+rawCookies);
//					//  checkSessionCookie(response.data);
					  CommonClass.saveGenericData(response.headers.get("Authorization"),TOKEN_NAME,TOKEN_PREF_NAME,ctx);
					  CommonClass.saveGenericData(rawCookies,COOKIE_ID,COOKIE_ID_PREF,ctx);
				  } catch (Exception e) {
					  parsed = new String(response.data);
					  Log.d("RESPONSE:","RESPONSE::EXCEP::"+parsed);
				  }
				  return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
			  }




										/*  @Override
			  protected Response<Object> parseNetworkResponse(NetworkResponse response) {
				  try {
					  String type = response.headers.get(HTTP.CONTENT_TYPE);
					  if (type == null) {
						  type = TYPE_UTF8_CHARSET;
						  response.headers.put(HTTP.CONTENT_TYPE, type);
					  } else if (!type.contains("UTF-8")) {
						  type += ";" + TYPE_UTF8_CHARSET;
						  response.headers.put(HTTP.CONTENT_TYPE, type);
					  }
				  } catch (Exception e) {
					  //print stacktrace e.g.
				  }
				  return super.parseNetworkResponse(response);

			  }*/



		  };


		int socketTimeout = 30000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);
		AppController.getInstance().addToRequestQueue(jsonObjReq);

	}
	private static final String SET_COOKIE_KEY = "Set-Cookie";
	private static final String COOKIE_KEY = "Cookie";
	private static final String SESSION_COOKIE = "sessionid";


	public final void checkSessionCookie(Map<String, String> headers) {
		if (headers.containsKey(SET_COOKIE_KEY)
				&& headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
			String cookie = headers.get(SET_COOKIE_KEY);
			if (cookie.length() > 0) {
				String[] splitCookie = cookie.split(";");
				String[] splitSessionId = splitCookie[0].split("=");
				cookie = splitSessionId[1];

				/*Editor prefEditor = _preferences.edit();
				prefEditor.putString(SESSION_COOKIE, cookie);
				prefEditor.commit();*/
			}
		}
	}

	public static void callWIthHeader(final Context context,JSONObject jsonobject_one, String url, final PostCommentJsonResponseListener mPostCommentResponse,final String heade)
	{

		Log.d("SSSD", "SSSS URL" + url + "--" + jsonobject_one.toString());



		mPostCommentResponse.requestStarted();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest
				(
						Request.Method.POST,url,
						jsonobject_one,


						new Response.Listener<JSONObject>()
						{
							@Override
							public void onResponse(JSONObject response)
							{
								mPostCommentResponse.requestCompleted(response);
							}


						}
						, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error)
					{


						mPostCommentResponse.requestEndedWithError(error);


					}

				})


		{




			@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				Log.d("TOKENNN","TOKEN MSG  "+CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, context));
				headers.put("Authorization", ""+CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, context));
				headers.put("cookie",  CommonClass.returnGenericData(COOKIE_ID,COOKIE_ID_PREF,context));
				return headers;
			}
		};


		int socketTimeout = 30000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);
		AppController.getInstance().addToRequestQueue(jsonObjReq);

	}



	public static void getData(final Context context, final String url, final PostCommentResponseListener	mPostCommentResponse){



		Log.d("RESPONSE","RESPO"+url);
		mPostCommentResponse.requestStarted();
		//	    RequestQueue queue = Volley.newRequestQueue(context);

		StringRequest sr = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				mPostCommentResponse.requestCompleted(response);
				//	            queue.
				Log.d("RESPOSNE","RESPONSE"+response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error)
			{
				Log.d("Error Message","ERROR"+error.getMessage());
				mPostCommentResponse.requestEndedWithError(error);
			}
		}){



			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String>  params = new HashMap<String, String>();
				Log.d("RESPONSE","RESPO"+CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, context)+"--"+CommonClass.returnGenericData(COOKIE_ID,COOKIE_ID_PREF,context));
				params.put("Authorization", ""+CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, context));
				params.put("cookie",  CommonClass.returnGenericData(COOKIE_ID,COOKIE_ID_PREF,context));
				return params;
			}

		};

		sr.setRetryPolicy(new DefaultRetryPolicy(
				2000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		AppController.getInstance().addToRequestQueue(sr);
	}

	public static void  postData(final Context context,JSONObject jsonobject_one, String url, final PostCommentJsonResponseListener mPostCommentResponse) {

		Log.d("SSSD", "VALUESS" + url + "--" + jsonobject_one.toString());



		mPostCommentResponse.requestStarted();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest
				(
						Request.Method.POST,url,
						jsonobject_one,


						new Response.Listener<JSONObject>()
						{
							@Override
							public void onResponse(JSONObject response)
							{
								mPostCommentResponse.requestCompleted(response);
							}


						}
						, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error)
					{

						{
							mPostCommentResponse.requestEndedWithError(error);
						}
					}

				})

		{

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				headers.put("Accept-Encoding","Accept-Encoding: gzip, deflate");
				headers.put("Authorization", ""+CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, context));
				headers.put("cookie",  CommonClass.returnGenericData(COOKIE_ID,COOKIE_ID_PREF,context));
//				headers.put("Authorization", "e9ad2f733b0b23b45e50ab1adfac5c430eb74ac4c087a97bda40c3f67168071f");
				return headers;
			}
		};


		int socketTimeout = 30000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);
		AppController.getInstance().addToRequestQueue(jsonObjReq);

	}


	public static void deleteItem(final Context context, final String url, final PostCommentResponseListener	mPostCommentResponse){



		Log.d("RESPONSE","RESPO"+url);
		mPostCommentResponse.requestStarted();
		//	    RequestQueue queue = Volley.newRequestQueue(context);

		StringRequest sr = new StringRequest(Request.Method.DELETE,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				mPostCommentResponse.requestCompleted(response);
				//	            queue.
				Log.d("RESPOSNE","RESPONSE"+response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error)
			{
				Log.d("Error Message","ERROR"+error.getMessage());
				mPostCommentResponse.requestEndedWithError(error);
			}
		}){



			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				/*Log.d("RESPONSE","RESPO"+CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, context));
				params.put("Authorization", ""+CommonClass.returnGenericData(TOKEN_NAME, TOKEN_PREF_NAME, context));

				params.put("cookie", "sessionId=s%3ACTTTlBCK_iqCxY2UyebZVXU0eYmpCq3M.QR9jO%2BgCDbMJwF1Jm63Ibl2qGooKkGnktSxjL%2FUytdo; Path=\\/; Expires=Tue, 17 Jul 2018 14:28:20 GMT; HttpOnly");
*/
				return params;
			}



		};

		sr.setRetryPolicy(new DefaultRetryPolicy(
				2000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		AppController.getInstance().addToRequestQueue(sr);
	}


}

