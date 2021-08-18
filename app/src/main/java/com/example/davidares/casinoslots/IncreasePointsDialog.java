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
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;

public class IncreasePointsDialog extends DialogFragment {
    Context context;
    ClickInterface dialogInterface;
    public IncreasePointsDialog(Context context, ClickInterface dialogInterface){
        this.context = context;
        this.dialogInterface =dialogInterface;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_increase_points, null);
        final Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(130);
        getDialog().getWindow().setBackgroundDrawable(d);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        ImageView plus = v.findViewById(R.id.iv_plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInterface.increace();
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
