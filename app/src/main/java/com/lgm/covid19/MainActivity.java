package com.lgm.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView myListView = findViewById(R.id.Lv);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://run.mocky.io/v3/7c0e5ea7-b36c-46e3-bfb2-1a50ccb37afe", null, response -> {

                    try {
                        ArrayList<String> arl = new ArrayList<>();
                        for (int i=0; i<response.length();i++){
                            JSONObject obj = response.getJSONObject(i);
                            arl.add("STATE NAME : "+obj.getString("loc")+"\n"+"ACTIVE CASES : "+obj.getInt("totalConfirmed")+"\n"+"DEATHS : "+obj.getInt("deaths")+"\n"+"RECOVERED : "+obj.getInt("discharged"));
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arl);
                        myListView.setAdapter(arrayAdapter);

                    } catch ( JSONException e) {
                        e.printStackTrace();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}
