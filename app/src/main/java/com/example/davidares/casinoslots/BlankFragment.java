package com.example.davidares.casinoslots;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_blank, container, false);
        EditText code = v.findViewById(R.id.editText);
        MaterialButton verify = v.findViewById(R.id.mbtn_sendcode);

        SharedPreferences prefs = getActivity().getSharedPreferences("verify", MODE_PRIVATE);
        int idName = prefs.getInt("code", 0);
        Log.d("sujka", String.valueOf(idName));
        verify.setOnClickListener(v1 -> {
            if(!TextUtils.isEmpty(code.getText())&&Integer.parseInt(code.getText().toString())  == idName){
                SharedPreferences.Editor editor =getActivity().getSharedPreferences("allow", MODE_PRIVATE).edit();
                editor.putInt("code", 1);
                editor.apply();

                startActivity(new Intent(getContext(), emptyActivity.class));
            }
            else {
                Toast.makeText(getContext(),"Wrong code", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}