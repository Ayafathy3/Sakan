package com.aya.sakan.ui.addPost;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AddPostPresenterImp implements IAddPostPresenterContact.Presenter {

    private IAddPostPresenterContact.View mView;
    private static final String TAG = "AddPostPresenterImp";
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    int finalUploads = 0;

    public AddPostPresenterImp(IAddPostPresenterContact.View mView, Context context) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void uploadPostAndImages(final List<Uri> imageList, final String desc, final String location,
                                    final String area, final Long price, final String roomsNum, final String bathroomNum, final String homeType,
                                    final String contractType, final String town, final String city) {

        // upload images

        // to saveURL
        final List<String> imagesURL = new ArrayList<>();
        final StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        for (int uploads = 0; uploads < imageList.size(); uploads++) {

            Uri Image = imageList.get(uploads);
            final StorageReference imageName = ImageFolder.child("image/" + Image.getLastPathSegment());

            imageName.putFile(imageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            imagesURL.add(String.valueOf(uri));
                            Log.i(TAG, String.valueOf(uri));
                            finalUploads++;

                            if (finalUploads == imageList.size()) {
                                uploadPost(imagesURL, desc, location, area, price, roomsNum, bathroomNum, homeType, contractType,
                                        town, city);
                            }
                        }
                    });

                }
            });
        }
    }


    private void uploadPost(List<String> imageList, String desc, String location, String area, Long price,
                            String roomsNum, String bathroomNum, String homeType,
                            String contractType, String town, String city) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("userId", userId);
        postMap.put("home_type", homeType);
        postMap.put("images_url", imageList);
        postMap.put("desc", desc);
        postMap.put("location", location);
        postMap.put("area", area);
        postMap.put("price", price);
        postMap.put("roomsNum", roomsNum);
        postMap.put("bathroomNum", bathroomNum);
        postMap.put("town", town);
        postMap.put("contractType", contractType);
        postMap.put("city", city);
        postMap.put("timestamp", FieldValue.serverTimestamp());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if (task.isSuccessful()) {
                    mView.goToHome("Upload Successful");
                } else {
                    Log.i(TAG, task.getException().getMessage());
                    mView.goToHome("Failed to upload post: " + task.getException().getMessage());
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Error handling
                Log.i(TAG, e.getMessage());
                mView.goToHome("Failed to upload post: " + e.getMessage());
            }
        });
    }
}
