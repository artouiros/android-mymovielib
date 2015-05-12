package com.mymovielib;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by arthur on 17.12.2015.
 */
public class CInemateAPI {
    final static RequestQueue queue = Volley.newRequestQueue(MainActivity.ctx);
    public static void GetMovie(){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, "http://api.cinemate.cc/movie.search?apikey=<API>>&term=%D0%9F%D0%B8%D1%80%D0%B0%D1%82%D1%8B%20%D0%BA%D0%B0%D1%80%D0%B8%D0%B1&format=json", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    /******/
                    JsonObjectRequest jsObjR = new JsonObjectRequest(Request.Method.GET, "http://api.cinemate.cc/movie?apikey=<API>>&id="+response.getJSONArray("movie").getJSONObject(0).get("id").toString()+"&format=json", null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // TODO Auto-generated method stub
                            try {
                                OutputStreamWriter or = new OutputStreamWriter(new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath()+"/jj.json")));
                                try {
                                    or.write(response.toString());
                                    or.flush();
                                    or.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Log.d("Hy", response.toString());
                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("Hey", error.toString());
                        }

                    });
                    /*****/
                    queue.add(jsObjR);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Log.d("Hey", response.getJSONArray("movie").getJSONObject(0).get("id").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Hey", error.toString());
            }

        });

        queue.add(jsObjRequest);
    }
}
