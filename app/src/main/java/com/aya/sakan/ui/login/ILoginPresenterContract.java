package com.aya.sakan.ui.login;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

public interface ILoginPresenterContract {

    interface View {
        void goToHome(FirebaseUser user);

        void resultPassRecover(String result);
    }

    interface Presenter {
        void loginAction(String mail, String pass);

        void recoverPass(String email);

        void loginWithGoogle(String idToken);

        void loginWithFacebook(AccessToken token);
    }
}
