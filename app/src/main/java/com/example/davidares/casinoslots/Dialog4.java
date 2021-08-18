package com.example.davidares.casinoslots;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class Dialog4 extends DialogFragment {
    ClickInterface chooseDialogInterface;
    Context context;
    public Dialog4(Context context, ClickInterface chooseDialogInterface){
        this.context = context;
        this.chooseDialogInterface = chooseDialogInterface;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
ImageView close;
    SwitchMaterial music,sound;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_answers, null);
        final Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(130);
        getDialog().getWindow().setBackgroundDrawable(d);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        music = v.findViewById(R.id.sw_music);
        sound = v.findViewById(R.id.sw_sounds);
        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chooseDialogInterface.playMusic();
                }
            }
        });
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chooseDialogInterface.playSound();
                }
            }
        });
close = v.findViewById(R.id.iv_close);
close.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dismiss();
    }
});
        return v;
    }



    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);


    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

}
