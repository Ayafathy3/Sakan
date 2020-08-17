package com.aya.sakan.ui.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aya.sakan.R;
import com.aya.sakan.ui.profile.ProfileActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> arrayList;
    private Context context;
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PostAdapter(List<Post> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        final Post post = arrayList.get(position);

        holder.title.setText(post.getTitle());
        holder.bathroomNum.setText(post.getBathroomsNum() + " حمام ");
        holder.area.setText(post.getArea() + " m² ");
        holder.roomsNum.setText(post.getRoomsNum() + " غرف ");
        holder.price.setText(post.getPrice() + " EGP ");

        //date
        long millisecond = post.getDate().getTime();
        String dateString = DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
        holder.date.setText(dateString);

        //post image
        if (post.getImagesURL() != null)
            holder.setHomeImage(post.getImagesURL().get(0));


        //User Data will be retrieved here...
        if (post.getUserName() != null)
            holder.userName.setText(post.getUserName());

        RequestOptions placeholderOption = new RequestOptions();
        placeholderOption.placeholder(R.drawable.profile_placeholder);
        Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(post.getUerImg()).into(holder.userImage);

        // open user profile
        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userId == null) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("userId", post.getUserId());
                    context.startActivity(intent);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView homeImage;
        private TextView userName, date, price, title, roomsNum, bathroomNum, area;
        private CircleImageView userImage;

        public ViewHolder(View itemView) {
            super(itemView);
            intiViews(itemView);
        }

        private void intiViews(View itemView) {
            homeImage = itemView.findViewById(R.id.post_image);
            userImage = itemView.findViewById(R.id.post_user_image);
            userName = itemView.findViewById(R.id.post_user_name);
            date = itemView.findViewById(R.id.post_date);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.post_title);
            roomsNum = itemView.findViewById(R.id.rooms_num_text);
            bathroomNum = itemView.findViewById(R.id.bathroom_num_text);
            area = itemView.findViewById(R.id.area_text);
        }

        public void setHomeImage(String downloadUri) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);
            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri).into(homeImage);
        }
    }
}
