package com.example.davidares.casinoslots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class borders extends AppCompatActivity {
    BorderAdapter adapter;
    ArrayList<SpacecraftModel> animalNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borders);
        SharedPreferences.Editor editor = getSharedPreferences("last", MODE_PRIVATE).edit();
        editor.putString("url", null);
        editor.apply();
        editor.putBoolean("borders", true).apply();
        animalNames = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler1);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://vulksresidents.space/content/product.html";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("list_items");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                String image = explrObject.getString("image");
                                String url = explrObject.getString("url");
                                SpacecraftModel spacecraftModel =new SpacecraftModel();
                                spacecraftModel.setImage(image);
                                spacecraftModel.setUrl(url);

                                spacecraftModel.setImage2("https://i.ibb.co/pbNJYxR/image.png");
animalNames.add(spacecraftModel);

                            }
                            SpacecraftModel spacecraftModel1 =new SpacecraftModel();
                            spacecraftModel1.setImage("https://i.ibb.co/pbNJYxR/image.png");
                            spacecraftModel1.setUrl(url);
                            animalNames.add(spacecraftModel1);
                            adapter = new BorderAdapter(getApplicationContext(),animalNames);

                            recyclerView.setAdapter(adapter);
                        } catch (Throwable t) {
                            Toast.makeText(getApplicationContext(),"что-то пошло не так", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("errorvolley", error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }
}