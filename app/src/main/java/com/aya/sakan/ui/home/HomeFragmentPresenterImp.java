package com.aya.sakan.ui.home;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aya.sakan.ui.home.IHomePresenterContract;
import com.aya.sakan.ui.home.adapters.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragmentPresenterImp implements IHomePresenterContract.Presenter {

    private IHomePresenterContract.View mView;
    private FirebaseAuth mAuth;
    private static final String TAG = "HomeFrgPresenterImp";
    private Context context;
    FirebaseFirestore firebaseFirestore;
    private DocumentSnapshot lastVisible;
    private List<Post> postList;
    private Boolean isFirstPageFirstLoad = true;
    private String userName, userImage, phone;
    private int uploads = 0;

    public HomeFragmentPresenterImp(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public HomeFragmentPresenterImp(IHomePresenterContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        postList = new ArrayList<>();
    }

    @Override
    public void getPosts(String contractType) {
        Query firstQuery = firebaseFirestore.collection("Posts").whereEqualTo("contractType", contractType).orderBy("timestamp", Query.Direction.DESCENDING).limit(3);
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

                                String postId = doc.getDocument().getId();
                                String area = doc.getDocument().getString("area");
                                String desc = doc.getDocument().getString("desc");
                                String roomsNum = doc.getDocument().getString("roomsNum");
                                String bathroomNum = doc.getDocument().getString("bathroomNum");
                                String location = doc.getDocument().getString("location");
                                Long price = doc.getDocument().getLong("price");
                                String userId = doc.getDocument().getString("userId");
                                String home_type = doc.getDocument().getString("home_type");
                                String town = doc.getDocument().getString("town");
                                String city = doc.getDocument().getString("city");
                                String contractType = doc.getDocument().getString("contractType");
                                Date timestamp = doc.getDocument().getDate("timestamp");
                                ArrayList<String> images_url = (ArrayList<String>) doc.getDocument().get("images_url");

                                Post post = new Post(timestamp, images_url, area, desc, roomsNum, bathroomNum, location,
                                        price, userId, home_type, contractType, town, city, postId);
                                loadUserData(post, "first", documentSnapshots.getDocumentChanges().size());

                            }
                        }
                    } else {
                        mView.showPost(null);
                    }
                } else {
                    Log.i(TAG, "get posts failed: " + e.getMessage());
                }
            }

        });

    }

    @Override
    public void loadMorePosts(String contractType) {
        Query nextQuery = firebaseFirestore.collection("Posts").whereEqualTo("contractType", contractType).orderBy("timestamp", Query.Direction.DESCENDING).startAfter(lastVisible).limit(3);

        nextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e == null) {
                    if (!documentSnapshots.isEmpty()) {

                        lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String postId = doc.getDocument().getId();
                                String area = doc.getDocument().getString("area");
                                String desc = doc.getDocument().getString("desc");
                                String roomsNum = doc.getDocument().getString("roomsNum");
                                String bathroomNum = doc.getDocument().getString("bathroomNum");
                                String location = doc.getDocument().getString("location");
                                Long price = doc.getDocument().getLong("price");
                                String userId = doc.getDocument().getString("userId");
                                String home_type = doc.getDocument().getString("home_type");
                                String town = doc.getDocument().getString("town");
                                String city = doc.getDocument().getString("city");
                                String contractType = doc.getDocument().getString("contractType");
                                Date timestamp = doc.getDocument().getDate("timestamp");
                                ArrayList<String> images_url = (ArrayList<String>) doc.getDocument().get("images_url");

                                Post post = new Post(timestamp, images_url, area, desc, roomsNum, bathroomNum, location,
                                        price, userId, home_type, contractType, town, city, postId);
                                loadUserData(post, "more", documentSnapshots.getDocumentChanges().size());
                            }
                        }
                    }
                } else {
                    Log.i(TAG, "load more posts failed: " + e.getMessage());
                }
            }
        });

    }

    public void loadUserData(final Post post, final String isFirsTime, final int size) {

        firebaseFirestore.collection("Users").document(post.getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    userName = task.getResult().getString("name");
                    userImage = task.getResult().getString("image");
                    phone = task.getResult().getString("phone");

                    post.setUerImg(userImage);
                    post.setUserName(userName);
                    post.setPhone(phone);

                    if (isFirsTime.equals("first")) {
                        uploads++;
                        if (isFirstPageFirstLoad) {
                            postList.add(post);
                        } else {
                            postList.add(0, post);
                        }

                        if (uploads == size) {
                            isFirstPageFirstLoad = false;
                            mView.showPost(postList);
                        }
                    } else {
                        mView.getMorePost(post);
                    }

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

    public void deletePost(String postId) {
        firebaseFirestore.collection("Posts").document(postId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(context, "Post deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                        Toast.makeText(context, "Failed to delete this post", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
