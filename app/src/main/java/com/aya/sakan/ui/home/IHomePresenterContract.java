package com.aya.sakan.ui.home;

import android.net.Uri;

import com.aya.sakan.ui.home.adapters.Post;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface IHomePresenterContract {

    interface View {
        void getMorePost(Post post);

        void showPost(List<Post> posts);
    }

    interface Presenter {

        void loadMorePosts();

        void getPosts();
    }
}
