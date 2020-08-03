package com.aya.sakan.ui.home.classes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aya.sakan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("ValidFragment")
public class RentFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Post> post;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private PostAdapter postAdapter;

    private DocumentSnapshot lastVisible;

    public RentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        post = new ArrayList<>();

        List<String> images = new ArrayList<>();
        images.add("https://media.gettyimages.com/photos/canada-british-columbia-kelowna-house-exterior-picture-id76038132?s=2048x2048");
        images.add("https://media.gettyimages.com/photos/idyllic-home-with-covered-porch-picture-id479767332?s=2048x2048");
        images.add("https://p0.pxfuel.com/preview/635/294/448/sky-dark-night-moon.jpg");

        post.add(new Post(Calendar.getInstance().getTime(), "3333 EGP", "1", images, "اعلان جديد عن سكننننن", "3 غرف",
                "2 حمام", "120m"));

        post.add(new Post(Calendar.getInstance().getTime(), "3333 EGP", "1", images, "اعلان جديد عن سكننننن", "3 غرف",
                "2 حمام", "120m"));

        post.add(new Post(Calendar.getInstance().getTime(), "3333 EGP", "1", images, "اعلان جديد عن سكننننن", "3 غرف",
                "2 حمام", "120m"));

        recyclerView = view.findViewById(R.id.recycle);

        //TODO get posts from firebase

        firebaseAuth = FirebaseAuth.getInstance();
        postAdapter = new PostAdapter(post);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(postAdapter);
        recyclerView.setHasFixedSize(true);

/*        if (firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if (reachedBottom) {
                        loadMorePost();
                    }
                }
            });


            Query firstQuery = firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING).limit(3);
            firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        if (isFirstPageFirstLoad) {

                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                            post.clear();

                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String blogPostId = doc.getDocument().getId();
                                Post blogPost = doc.getDocument().toObject(Post.class).withId(blogPostId);

                                if (isFirstPageFirstLoad) {

                                    post.add(blogPost);

                                } else {

                                    post.add(0, blogPost);

                                }


                                postAdapter.notifyDataSetChanged();

                            }
                        }

                        isFirstPageFirstLoad = false;

                    }

                }

            });
}
*/

        // Inflate the layout for this fragment
        return view;
    }

    public void loadMorePost() {

        if (firebaseAuth.getCurrentUser() != null) {

            Query nextQuery = firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING).startAfter(lastVisible).limit(3);

            nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String blogPostId = doc.getDocument().getId();
                                //     Post blogPost = doc.getDocument().toObject(Post.class).withId(blogPostId);
                                //     post.add(blogPost);

                                postAdapter.notifyDataSetChanged();
                            }

                        }
                    }

                }
            });

        }

    }


}
