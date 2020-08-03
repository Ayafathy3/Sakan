package com.aya.sakan.ui.signUp;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

public interface ISignUpPresenterContract {

    interface View {
        void goToHome(FirebaseUser user);
    }

    interface Presenter {
        void signUp(String mail, String pass, String rentedName, String phone, String accountType);

        void loginWithGoogle(String idToken);

        void loginWithFacebook(AccessToken token);
    }
}
