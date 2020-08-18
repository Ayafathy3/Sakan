package com.aya.sakan.ui.profile;

import android.content.Context;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProfilePresenterImp implements IProfilePresenterContract.Presenter {

    private IProfilePresenterContract.View mView;
    private FirebaseAuth mAuth;
    private static final String TAG = "ProfilePresenterImp";
    private Context context;
    FirebaseFirestore firebaseFirestore;
    private DocumentSnapshot lastVisible;
    private List<Post> postList;
    private Boolean isFirstPageFirstLoad = true;
    private String userName, userImage, phone;
    private int uploads = 0;


    public ProfilePresenterImp(IProfilePresenterContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        postList = new ArrayList<>();
    }

    @Override
    public void getPosts(final String userId) {
        Query firstQuery = firebaseFirestore.collection("Posts")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING).limit(3);
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

                                String area = doc.getDocument().getString("area");
                                String desc = doc.getDocument().getString("desc");
                                String roomsNum = doc.getDocument().getString("roomsNum");
                                String bathroomNum = doc.getDocument().getString("bathroomNum");
                                String location = doc.getDocument().getString("location");
                                Long price = doc.getDocument().getLong("price");
                                String home_type = doc.getDocument().getString("home_type");
                                String town = doc.getDocument().getString("town");
                                String city = doc.getDocument().getString("city");
                                String contractType = doc.getDocument().getString("contractType");
                                Date timestamp = doc.getDocument().getDate("timestamp");
                                ArrayList<String> images_url = (ArrayList<String>) doc.getDocument().get("images_url");

                                Post post = new Post(timestamp, images_url, area, desc, roomsNum, bathroomNum, location,
                                        price, userId, home_type, contractType, town, city);
                                loadUserData(post, "first", documentSnapshots.getDocumentChanges().size());

                            }
                        }
                    } else {
                        mView.showPost(null, "0", "0");
                    }
                } else {
                    Log.i(TAG, "get posts failed: " + e.getMessage());
                }
            }

        });

    }

    @Override
    public void loadMorePosts(final String userId) {
        Query nextQuery = firebaseFirestore.collection("Posts")
                .whereEqualTo("userId", userId).orderBy("timestamp", Query.Direction.DESCENDING).startAfter(lastVisible).limit(3);

        nextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e == null) {
                    if (!documentSnapshots.isEmpty()) {

                        lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String area = doc.getDocument().getString("area");
                                String desc = doc.getDocument().getString("desc");
                                String roomsNum = doc.getDocument().getString("roomsNum");
                                String bathroomNum = doc.getDocument().getString("bathroomNum");
                                String location = doc.getDocument().getString("location");
                                Long price = doc.getDocument().getLong("price");
                                String home_type = doc.getDocument().getString("home_type");
                                String town = doc.getDocument().getString("town");
                                String city = doc.getDocument().getString("city");
                                String contractType = doc.getDocument().getString("contractType");
                                Date timestamp = doc.getDocument().getDate("timestamp");
                                ArrayList<String> images_url = (ArrayList<String>) doc.getDocument().get("images_url");

                                Post post = new Post(timestamp, images_url, area, desc, roomsNum, bathroomNum, location,
                                        price, userId, home_type, contractType, town, city);
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
                            mView.showPost(postList, userImage, userName);
                        }
                    } else {
                        mView.showMorePost(post);
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

    @Override
    public void loadUserData(String userId) {
        firebaseFirestore.collection("Users")
                .document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    userName = task.getResult().getString("name");
                    userImage = task.getResult().getString("image");

                    mView.showUserData(userImage, userName);
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

    @Override
    public void updateUserName(final String userName, String userId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", userName);

        firebaseFirestore
                .collection("Users")
                .document(userId)
                .update(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "updateUserName successful");
                        Toast.makeText(context, "your data updated successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "updateUserName failed");
                Toast.makeText(context, "Failed to update your data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateUserImg(String userImg, String userId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("image", userImg);

        firebaseFirestore
                .collection("Users")
                .document(userId)
                .update(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "updateUserImg successful");
                        Toast.makeText(context, "your data updated successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "updateUserImg failed");
                Toast.makeText(context, "Failed to update your data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
