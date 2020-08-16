package com.aya.sakan.ui.search;

import com.aya.sakan.ui.home.adapters.Post;

import java.util.List;

public interface ISearchPresenterContract {
    interface View {
        void getMorePost(Post post);

        void showPost(List<Post> posts);
    }

    interface Presenter {

        void loadMorePosts(String town, String city, String homeType,
                           String contractType, Long lowPrice, Long highPrice);

        void getPosts(String town, String city, String homeType,
                      String contractType, Long lowPrice, Long highPrice);
    }
}
