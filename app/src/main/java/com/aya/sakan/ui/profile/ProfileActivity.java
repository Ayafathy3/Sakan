package com.aya.sakan.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.HomeFragmentPresenterImp;
import com.aya.sakan.ui.home.adapters.Post;
import com.aya.sakan.ui.home.adapters.PostAdapter;
import com.aya.sakan.util.LoadingDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements IProfilePresenterContract.View {
    CircleImageView userImgCircleImageView;
    TextView userNameTextView;
    RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ProfilePresenterImp profilePresenterImp;
    public List<Post> postList;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userImgCircleImageView = findViewById(R.id.profile_image);
        userNameTextView = findViewById(R.id.profile_name);

        getData();
        setUpRecyclerView();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.getStringExtra("userId") != null) {
            userId = intent.getStringExtra("userId");
        } else {
            userId = FirebaseAuth.getInstance().getUid();
        }

        profilePresenterImp = new ProfilePresenterImp(this, ProfileActivity.this);
        LoadingDialog.showProgress(ProfileActivity.this);
        profilePresenterImp.getPosts(userId);

    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycle);

        postList = new ArrayList<>();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Boolean reachedBottom = !recyclerView.canScrollVertically(1);
                if (reachedBottom) {
                    profilePresenterImp.loadMorePosts(userId);
                }
            }
        });
    }

    @Override
    public void showMorePost(Post post) {
        postList.add(post);
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPost(List<Post> postList, String userImg, String userName) {
        if (postList != null) {
            setUserData(userImg, userName);

            this.postList = postList;
            postAdapter = new PostAdapter(postList);
            postAdapter.setUserId(userId);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(postAdapter);
            recyclerView.setHasFixedSize(true);

            postAdapter.notifyDataSetChanged();
        } else {
            profilePresenterImp.loadUserData(userId);
        }
        LoadingDialog.hideProgress();
    }

    @Override
    public void showUserData(String userImg, String userName) {
        setUserData(userImg, userName);
    }

    private void setUserData(String userImg, String userName) {
        // set User Img
        RequestOptions placeholderOption = new RequestOptions();
        placeholderOption.placeholder(R.drawable.profile_placeholder);
        Glide.with(this).applyDefaultRequestOptions(placeholderOption).load(userImg).into(userImgCircleImageView);

        // set User Name
        userNameTextView.setText(userName);

    }


}