package com.aya.sakan.ui.postDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.ui.home.adapters.Post;
import com.aya.sakan.ui.login.LoginActivity;
import com.aya.sakan.ui.profile.ProfileActivity;
import com.aya.sakan.ui.search.SearchActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import ss.com.bannerslider.Slider;

public class PostDetailsActivity extends AppCompatActivity {
    private CircleImageView userCircleImageView;
    private TextView dateTextView, userNameTextView, priceTextView, titleTextView, addressTextView,
            areaTextView, roomsNumTextView, bathroomsNumTextView, postDesTextView;
    private ImageButton phoneCall, whatsApp;
    private ImageButton profile, search, logout;
    private Post post;
    private Slider slider;
    private MainSliderAdapter mainSliderAdapter;
    private static final String TAG = "PostDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        initiViews();
        getData();
        setListeners();
    }

    private void setListeners() {
        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+20" + post.getPhone(), null));
                startActivity(intent);
            }
        });

        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSupportChat();
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostDetailsActivity.this, SearchActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostDetailsActivity.this, ProfileActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutAction();
            }
        });
    }

    private void logoutAction() {
        new AlertDialog.Builder(
                PostDetailsActivity.this)
                .setTitle(R.string.confirm_logout)
                .setMessage(R.string.confirm_logout_msg)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // signed in with google
                                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(PostDetailsActivity.this);

                                // signed in with fb
                                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                                if (isLoggedIn) {
                                    facebookSignOut();
                                }

                                if (account != null) {
                                    googleSignOut();
                                }

                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(PostDetailsActivity.this, LoginActivity.class));
                                finishAffinity();
                            }
                        })
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
    }

    private void googleSignOut() {
        Log.i(TAG, "logout google");
        GoogleSignInClient mGoogleSignInClient;
        GoogleSignInOptions googleSignInOptions;

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void facebookSignOut() {
        Log.i(TAG, "logout fb");
        LoginManager.getInstance().logOut();
    }

    private void startSupportChat() {

        try {
            String headerReceiver = "Sakan:\n";// Replace with your message.
            String bodyMessageFormal = ".......";// Replace with your message.
            String whatsappContain = headerReceiver + bodyMessageFormal;
            String trimToNumner = "+2" + post.getPhone(); //10 digit number
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://wa.me/" + trimToNumner + "/?text=" + whatsappContain));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        post = (Post) bundle.getSerializable("post");

        mainSliderAdapter = new MainSliderAdapter();
        Slider.init(new GlideLoadingImage(PostDetailsActivity.this));
        mainSliderAdapter.setSliderList(post.getImagesURL());
        slider.setAdapter(mainSliderAdapter);
        slider.setInterval(5000);

        // img
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.profile_placeholder);
        Glide.with(this).applyDefaultRequestOptions(requestOptions).load(post.getUerImg()).into(userCircleImageView);

        userNameTextView.setText(post.getUserName());

        long millisecond = post.getDate().getTime();
        String dateString = DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
        dateTextView.setText(dateString);


        titleTextView.setText(post.getTitle());
        addressTextView.setText(post.getLocation());
        postDesTextView.setText(post.getDesc());
        bathroomsNumTextView.setText(post.getBathroomsNum() + " حمام ");
        areaTextView.setText(post.getArea() + " m² ");
        roomsNumTextView.setText(post.getRoomsNum() + " غرف ");
        priceTextView.setText(post.getPrice() + " EGP ");

    }

    private void initiViews() {
        userCircleImageView = findViewById(R.id.post_user_image);
        userNameTextView = findViewById(R.id.post_user_name);
        dateTextView = findViewById(R.id.post_date);
        priceTextView = findViewById(R.id.price);
        titleTextView = findViewById(R.id.post_title);
        addressTextView = findViewById(R.id.post_address);
        areaTextView = findViewById(R.id.area_text);
        roomsNumTextView = findViewById(R.id.rooms_num_text);
        bathroomsNumTextView = findViewById(R.id.bathroom_num_text);
        postDesTextView = findViewById(R.id.post_desc);
        phoneCall = findViewById(R.id.phone_call);
        whatsApp = findViewById(R.id.whats_app);
        profile = findViewById(R.id.profile);
        search = findViewById(R.id.search);
        logout = findViewById(R.id.logout);
        slider = findViewById(R.id.recycler_slider);
    }
}