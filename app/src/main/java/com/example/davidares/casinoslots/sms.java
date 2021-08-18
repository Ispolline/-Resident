package com.example.davidares.casinoslots;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.hbb20.CountryCodePicker;

import org.json.JSONObject;

import java.util.Objects;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;

public class sms extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = getResources().getConfiguration();


        if (config.smallestScreenWidthDp >= 400) {
            setContentView(R.layout.activity_sms);
        } else {
            setContentView(R.layout.activity_sms_small);
        }

        MaskedEditText phonenumbered = findViewById(R.id.phone_input);
        CountryCodePicker ccp;
        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        if(ccp.getSelectedCountryCode().length() == 3){
            phonenumbered.setMask("(##)###-##-##");

        }
        TextView hint = findViewById(R.id.tv_hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hint.setVisibility(View.GONE);
              phonenumbered.setVisibility(View.VISIBLE);
          phonenumbered.requestFocus();

            }
        });


ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
    @Override
    public void onCountrySelected() {
        if(ccp.getSelectedCountryCode().length() == 3){
            phonenumbered.setMask("(##)###-##-##");}
        else {

            phonenumbered.setMask("(###)###-##-##");
        }
    }
});
        RelativeLayout relativeLayout1 = findViewById(R.id.rl_2);

        EditText codeed = findViewById(R.id.editText);
        MaterialButton verify = findViewById(R.id.mbtn_sendcode1);
        Log.d("suka", String.valueOf(config.smallestScreenWidthDp));
        RelativeLayout relativeLayout = findViewById(R.id.rl_1);
        MaterialButton materialButton = findViewById(R.id.mbtn_sendcode);
        materialButton.setOnClickListener(v -> {

            if(!TextUtils.isEmpty(phonenumbered.getText())){
                final InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(phonenumbered, InputMethodManager.SHOW_IMPLICIT);

                String s = phonenumbered.getText().toString().replace("(","");
                    String s1 = s.replace(")", "");
                    String s2 = s1.replace("-","");
                    String s3 = ccp.getSelectedCountryCode()+s2;
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url = "https://smsbuilder.ru/api/create?phone="+s3+"&key=rrZ3JwNR7o";
// Request a string response from the provided URL.
                    relativeLayout.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = getSharedPreferences("last", MODE_PRIVATE).edit();




                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                        int code = obj.getInt("code");
                                        relativeLayout1.setVisibility(View.VISIBLE);
                                        verify.setOnClickListener(v1 -> {
                                            if(!TextUtils.isEmpty(codeed.getText())&&Integer.parseInt(codeed.getText().toString())  == code){
editor.putBoolean("borders", true).apply();
                                                startActivity(new Intent(getApplicationContext(), borders.class));
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),"Wrong code", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    } catch (Throwable t) {
                                        Toast.makeText(getApplicationContext(),"что-то пошло не так", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG ).show();
                                    Log.d("errorvolley", error.getLocalizedMessage());
                        }
                    });
                    queue.add(stringRequest);


            }
            else {
                Toast.makeText(getApplicationContext(),"Введите номер телефона", Toast.LENGTH_LONG).show();

            }

        });


    }
}