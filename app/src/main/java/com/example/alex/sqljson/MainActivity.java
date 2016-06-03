package com.example.alex.sqljson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button insert,show;
    EditText age,firstname,lastname;
    RequestQueue requestQueue;
    TextView result;
    String insertUrl="http://192.168.56.1/android/insertStudent.php";
    String ShowUrl="http://192.168.56.1/android/showStudents.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        age=(EditText)findViewById(R.id.editage);
        lastname=(EditText)findViewById(R.id.editlast);
        firstname=(EditText)findViewById(R.id.editfirstname);
        show=(Button)findViewById(R.id.buttonShow);
        insert=(Button)findViewById(R.id.buttonInsert);
        result=(TextView) findViewById(R.id.textShow);

        requestQueue= Volley.newRequestQueue(getApplicationContext());

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, ShowUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray students=response.getJSONArray("students");

                            for (int i=0; i<students.length();i++){
                                JSONObject student=students.getJSONObject(i);


                                String firstName=student.getString("firstname");
                                String lastname=student.getString("lastname");
                                String age=student.getString("age");

                                result.append(firstName+" "+lastname+" "+age+ " \n ");

                            }
                            result.append("===\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){



                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("firstname", firstname.getText().toString());
                            parameters.put("lastname", lastname.getText().toString());
                            parameters.put("age", age.getText().toString());
                            return parameters;

                        }


                    };
                requestQueue.add(stringRequest);
            }
        });
    }
}
