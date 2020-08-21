package com.aya.sakan.ui.addPost;

import android.net.Uri;

import java.util.List;

public interface IAddPostPresenterContact {

    interface View {
        void goToHome(String message);
    }

    interface Presenter {
        void uploadPostAndImages(List<Uri> imageList, String desc, String location, String area, Long price,
                                 String roomsNum, String bathroomNum, String homeType,
                                 String contractType, String town, String city);

        void updatePost(List<String> imageList, String desc, String location, String area, Long price,
                        String roomsNum, String bathroomNum, String homeType,
                        String contractType, String town, String city, String postId);
    }
}
