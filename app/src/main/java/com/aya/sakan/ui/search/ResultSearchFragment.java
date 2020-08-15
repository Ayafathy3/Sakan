package com.aya.sakan.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.adapters.Post;
import com.aya.sakan.ui.home.adapters.PostAdapter;
import com.aya.sakan.ui.home.rentPosts.RentFragmentPresenterImp;

import java.util.ArrayList;
import java.util.List;

public class ResultSearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private String townString, cityString, homeTypeString,
            contractTypeString, lowPriceString, highPriceString, roomsNumString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        Bundle bundle = getArguments();
        townString = bundle.getString("town");
        cityString = bundle.getString("city");
        homeTypeString = bundle.getString("homeType");
        contractTypeString = bundle.getString("contractType");
        lowPriceString = bundle.getString("lowPrice");
        highPriceString = bundle.getString("highPrice");
        roomsNumString = bundle.getString("roomsNum");

        // rentFragmentPresenterImp = new RentFragmentPresenterImp(this, getActivity());
        // rentFragmentPresenterImp.getPosts();

        setUpRecyclerView(view);
        return view;
    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycle);

        postList = new ArrayList<>();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Boolean reachedBottom = !recyclerView.canScrollVertically(1);
                if (reachedBottom) {
//                    rentFragmentPresenterImp.loadMorePosts();
                }
            }
        });
    }


    public void getMorePost(Post post) {
        postList.add(post);
        postAdapter.notifyDataSetChanged();
    }

    public void showPost(List<Post> postList) {
        this.postList = postList;
        postAdapter = new PostAdapter(postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(postAdapter);
        recyclerView.setHasFixedSize(true);

        postAdapter.notifyDataSetChanged();
    }
}