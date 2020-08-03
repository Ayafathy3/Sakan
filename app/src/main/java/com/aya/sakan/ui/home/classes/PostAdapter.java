package com.aya.sakan.ui.home.classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    ArrayList<Post> arrayList;
    public Context context;
    private Post post;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private PopupWindow popWindow;

    public static final String TAG = "PostAdapter";

    public PostAdapter() {
    }

    public PostAdapter(ArrayList<Post> arrayList) {

        this.arrayList = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        Post post = arrayList.get(position);

        final String currentUserId = firebaseAuth.getCurrentUser().getUid();
        post.setUserId(currentUserId);

        holder.title.setText(post.getTitle());
        holder.bathroomNum.setText(post.getBathroomsNum());
        holder.area.setText(post.getArea());
        holder.roomsNum.setText(post.getRoomsNum());
        holder.price.setText(post.getPrice());

        //date
        long millisecond = post.getDate().getTime();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
        holder.date.setText(dateString);

        //post image
        holder.setHomeImage(post.getImagesURL().get(0));


        //User Data will be retrieved here...
        firebaseFirestore.collection("Users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    String userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image");

                    // user image and name
                    holder.setUserData(userName, userImage);

                } else {
                    //Firebase Exception
                    Log.i(TAG, "get user data failed");
                }
            }
        });

        // open user profile
        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userInformation = new Intent(context, ProfileActivity.class);

                SharedPreferences shared = context.getSharedPreferences("ID", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("USER_ID", currentUserId);
                editor.apply();
                context.startActivity(userInformation);
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private ImageView homeImage;
        private TextView userName, date, price, title, roomsNum, bathroomNum, area;
        private CircleImageView userImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
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

        public void setUserData(String name, String imageURL) {
            userName.setText(name);
            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.profile_placeholder);
            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(imageURL).into(userImage);
        }
    }
}
