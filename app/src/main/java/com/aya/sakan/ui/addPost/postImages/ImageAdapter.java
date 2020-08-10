package com.aya.sakan.ui.addPost.postImages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aya.sakan.R;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private List<Uri> imageList;
    private Context context;
    private CircleImageView img;
    private RecyclerView recyclerView;

    public ImageAdapter(List<Uri> imageList, Context context, CircleImageView img, RecyclerView recyclerView) {
        this.imageList = imageList;
        this.context = context;
        this.img = img;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Uri img = imageList.get(i);

        Glide.with(context).load(img).into(myViewHolder.imageView);

        // implement setOnClickListener event on item view.
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(context, DisplayImgActivity.class);
                intent.putExtra("image", imageList.get(i).toString()); // put image data in Intent
                context.startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, close;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            close = itemView.findViewById(R.id.image_close);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imageList.size() == 1) {
                        img.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    imageList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}

