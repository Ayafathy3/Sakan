package com.aya.sakan.ui.home.rentPosts;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aya.sakan.ui.home.IHomePresenterContract;
import com.aya.sakan.ui.home.adapters.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RentFragmentPresenterImp implements IHomePresenterContract.Presenter {

    private IHomePresenterContract.View mView;
    private FirebaseAuth mAuth;
    private static final String TAG = "RentFragPresenterImp";
    private Context context;
    FirebaseFirestore firebaseFirestore;
    private DocumentSnapshot lastVisible;
    private List<Post> postList;
    private Boolean isFirstPageFirstLoad = true;
    private String userName, userImage;


    public RentFragmentPresenterImp(IHomePresenterContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        postList = new ArrayList<>();
    }

    @Override
    public void getPosts() {
        Query firstQuery = firebaseFirestore.collection("Posts").whereEqualTo("home_type", "Rent").orderBy("timestamp", Query.Direction.DESCENDING).limit(3);
        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e == null) {
                    if (!documentSnapshots.isEmpty()) {

                        if (isFirstPageFirstLoad) {
                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                            postList.clear();
                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                Post post = doc.getDocument().toObject(Post.class);
                                loadUserData(post.getUserId());
                                post.setUerImg(userImage);
                                post.setUserName(userName);

                                if (isFirstPageFirstLoad) {

                                    postList.add(post);

                                } else {
                                    postList.add(0, post);
                                }

                            }
                        }

                        isFirstPageFirstLoad = false;
                        mView.showPost(postList);
                    }
                } else {
                    Log.i(TAG, "get posts failed: " + e.getMessage());
                }
            }

        });

    }

    @Override
    public void loadMorePosts() {

        if (mAuth.getCurrentUser() != null) {

            Query nextQuery = firebaseFirestore.collection("Posts").whereEqualTo("home_type", "Rent").orderBy("timestamp", Query.Direction.DESCENDING).startAfter(lastVisible).limit(3);

            nextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (e == null) {
                        if (!documentSnapshots.isEmpty()) {

                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    Post post = doc.getDocument().toObject(Post.class);
                                    loadUserData(post.getUserId());
                                    post.setUerImg(userImage);
                                    post.setUserName(userName);

                                    mView.getMorePost(post);
                                }
                            }
                        }
                    } else {
                        Log.i(TAG, "load more posts failed: " + e.getMessage());
                    }
                }
            });

        }

    }

    public void loadUserData(final String userId) {

        firebaseFirestore.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    userName = task.getResult().getString("name");
                    userImage = task.getResult().getString("image");

                } else {
                    //Firebase Exception
                    Log.i(TAG, "get user data not success: " + task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "get user data failed: " + e.getMessage());
            }
        });

    }
}
