package com.aya.sakan.ui.profile;

import com.aya.sakan.ui.home.adapters.Post;

import java.util.List;

public interface IProfilePresenterContract {

    interface View {
        void showMorePost(Post post);

        void showPost(List<Post> posts, String userImg, String userName);

        void showUserData(String userImg, String userName);
    }

    interface Presenter {

        void loadMorePosts(String userID);

        void getPosts(String userId);

        void loadUserData(String userId);

        void updateUserName(String userName, String userId);

        void updateUserImg(String userImg, String userId);
    }
}
