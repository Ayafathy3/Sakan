package com.aya.sakan.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.HomeFragmentPresenterImp;
import com.aya.sakan.ui.home.adapters.Post;
import com.aya.sakan.ui.home.adapters.PostAdapter;
import com.aya.sakan.util.LoadingDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements IProfilePresenterContract.View {
    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 1;
    private final String TAG = "ProfileActivity";
    ImageView userImgCircleImageView;
    EditText userNameEditText;
    ImageButton editName;
    RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ProfilePresenterImp profilePresenterImp;
    public List<Post> postList;
    private String userId;
    TextView save;
    String userImgUrl;
    boolean isChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initiViews();
        getData();
        setListeners();
        setUpRecyclerView();
    }

    private void initiViews() {
        userImgCircleImageView = findViewById(R.id.profile_image);
        editName = findViewById(R.id.name_edit);
        save = findViewById(R.id.save);

        userNameEditText = findViewById(R.id.profile_name);
        userNameEditText.setTag(userNameEditText.getKeyListener());
        userNameEditText.setKeyListener(null);

    }

    private void setListeners() {
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEditText.setKeyListener((KeyListener) userNameEditText.getTag());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(userNameEditText, InputMethodManager.SHOW_IMPLICIT);
                userNameEditText.requestFocus();

            }
        });

        userImgCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userId.equals(FirebaseAuth.getInstance().getUid())) {
                    openGallery();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePresenterImp.updateUserName(userNameEditText.getText().toString(), userId);
                if (isChanged) {
                    profilePresenterImp.updateUserImg(userImgUrl, userId);
                }
            }
        });
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(ProfileActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_MEDIA);

            } else {

                BringImagePicker();
            }

        } else {

            BringImagePicker();
        }
    }

    private void BringImagePicker() {

        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).
                setAspectRatio(1, 1).
                start(ProfileActivity.this);

    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.getStringExtra("userId") != null) {
            userId = intent.getStringExtra("userId");
            save.setVisibility(View.GONE);
            editName.setVisibility(View.GONE);
        } else {
            userId = FirebaseAuth.getInstance().getUid();
            save.setVisibility(View.VISIBLE);
            editName.setVisibility(View.VISIBLE);
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
        if (userId.equals(FirebaseAuth.getInstance().getUid())) {
            placeholderOption.placeholder(R.drawable.img);
        } else {
            placeholderOption.placeholder(R.drawable.profile_placeholder);
        }
        Glide.with(this).applyDefaultRequestOptions(placeholderOption).load(userImg).into(userImgCircleImageView);

        // set User Name
        userNameEditText.setText(userName);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri uri = result.getUri();
                userImgUrl = uri.toString();
                // set User Img
                RequestOptions placeholderOption = new RequestOptions();
                placeholderOption.placeholder(R.drawable.profile_placeholder);
                Glide.with(this).applyDefaultRequestOptions(placeholderOption).load(uri).into(userImgCircleImageView);
                isChanged = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Log.i(TAG, "bring image failed: " + error);

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_MEDIA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    BringImagePicker();
                }
                break;

            default:
                break;
        }
    }
}