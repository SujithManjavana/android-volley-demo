package com.sujith.hellovolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private TextView statusText;
    private EditText textInput;
    private Button button;
    private RequestQueue queue;
    public static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusText = findViewById(R.id.txt);
        textInput = findViewById(R.id.edttxt);
        button = findViewById(R.id.btn);
        queue = Volley.newRequestQueue(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushToServer(textInput.getText().toString()); // <= Your text here.
            }
        });
    }


    /*


    READ DOCUMENTATION => https://developer.android.com/training/volley/simple#java

    Add volley dependencies in build.gradle file (App level)
    path => ~/HelloVolley/app/build.gradle

     */

    private void pushToServer(String payload) {
        if(payload==null || payload.equals(""))return;
        statusText.setText("Pushing...");
        // Instantiate the RequestQueue.
        String url = "https://whoispanel.duckdns.org/test/api/api.php?string=" + payload;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        statusText.setText("Done!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statusText.setText("That didn't work!");
            }
        });

        // Set the tag on the request.
        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}