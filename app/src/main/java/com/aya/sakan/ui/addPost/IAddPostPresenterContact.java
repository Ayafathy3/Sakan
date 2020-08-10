package com.aya.sakan.ui.addPost;

import android.net.Uri;

import java.util.List;

public interface IAddPostPresenterContact {

    interface View {
        void goToHome(String message);
    }

    interface Presenter {
        void uploadPostAndImages(List<Uri> imageList, String desc, String location, String area, String price, String roomsNum, String bathroomNum,String homeType);

    }
}
