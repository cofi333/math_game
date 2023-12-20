package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DummyActivity extends AppCompatActivity {
    private TextView scoreInput;
    private TextView nameInput;
    private Button submitBtn;
    RequestQueue requestQueue;
    private final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        scoreInput = findViewById(R.id.scoreEdit);
        nameInput = findViewById(R.id.nameEdit);
        submitBtn = findViewById(R.id.submitBtn);

        //add click listener that will execute POST request
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String url = "https://mathgameapi.000webhostapp.com/api/scores/";
                    JSONObject jsonParams = new JSONObject();
                    try {
                        jsonParams.put("username", nameInput.getText().toString());
                        jsonParams.put("score", scoreInput.getText().toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            response -> Toast.makeText(DummyActivity.this, "Success", Toast.LENGTH_LONG).show(),
                            error -> Toast.makeText(DummyActivity.this, "Error", Toast.LENGTH_LONG).show()) {
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            // Convert the JSON object to byte array
                            return jsonParams.toString().getBytes();
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                    };

                    requestQueue = Volley.newRequestQueue(DummyActivity.this);
                    requestQueue.add(stringRequest);
                }

        });
    }
}
