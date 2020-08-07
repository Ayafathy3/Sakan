package com.aya.sakan.ui.addPost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aya.sakan.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private List<String> imageList;
    private List<File> image_file;
    private Context context;

    public ImageAdapter(List<String> imageList, List<File> image_file, Context context) {
        this.imageList = imageList;
        this.image_file = image_file;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String img = imageList.get(i);

        Glide.with(context).load(img).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, close;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
            close = itemView.findViewById(R.id.image_close);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageList.remove(getAdapterPosition());
                    image_file.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}

