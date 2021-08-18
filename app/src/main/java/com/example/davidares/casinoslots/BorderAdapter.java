package com.example.davidares.casinoslots;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.RelativeLayout.OnClickListener;

public class BorderAdapter extends RecyclerView.Adapter<BorderAdapter.ViewHolder> {
    ArrayList<SpacecraftModel> videosList = new ArrayList<>();
    Context context;
    public BorderAdapter(Context context, ArrayList<SpacecraftModel> videosList) {
        this.context = context;
        this.videosList = videosList;
    }

    @Override
    public BorderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull BorderAdapter.ViewHolder holder, int position) {
        final String url = videosList.get(position).url;
        final String imageUrl = videosList.get(position).image;
        final String imageUrl2 = videosList.get(position).image2;
        Log.d("sukaimage", imageUrl);
        Picasso.get().load(imageUrl).fit().into(holder.image);
holder.play.setOnClickListener(v -> {
    SharedPreferences.Editor editor = context.getSharedPreferences("last", MODE_PRIVATE).edit();
    editor.putString("url", url);
    editor.apply();
    context.startActivity(new Intent(context, emptyActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
});
if(position ==videosList.size()-1){
    holder.relativeLayout2.setVisibility(View.GONE);
holder.relativeLayout.setVisibility(View.VISIBLE);
    Picasso.get().load(imageUrl).fit().into(holder.image1);
   holder.play1.setOnClickListener(new OnClickListener() {
       @Override
       public void onClick(View v) {

           Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/vip_consultant"));
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);
       }
   });

}


    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public SpacecraftModel getItem(int position) {
        return videosList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
ImageView image, image1;
MaterialButton play, play1;
RelativeLayout relativeLayout, relativeLayout2;

        public ViewHolder(View view) {
            super(view);
      image = view.findViewById(R.id.iv_image);
play = view.findViewById(R.id.mb_play);
play1 = view.findViewById(R.id.mb_play1);
relativeLayout = view.findViewById(R.id.rl_1);
relativeLayout2 = view.findViewById(R.id.rl_2);
image1 = view.findViewById(R.id.iv_image1);

    }






}}