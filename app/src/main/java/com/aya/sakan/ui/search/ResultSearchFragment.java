package com.aya.sakan.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.ui.home.HomeFragmentPresenterImp;
import com.aya.sakan.ui.home.adapters.Post;
import com.aya.sakan.ui.home.adapters.PostAdapter;
import com.aya.sakan.util.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class ResultSearchFragment extends Fragment implements ISearchPresenterContract.View {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private SearchPresenterImp searchPresenterImp;
    private String townString, cityString, homeTypeString,
            contractTypeString;
    private Long lowPriceString, highPriceString;

    public ResultSearchFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        Bundle bundle = getArguments();
        townString = bundle.getString("town");
        cityString = bundle.getString("city");
        homeTypeString = bundle.getString("homeType");
        contractTypeString = bundle.getString("contractType");
        lowPriceString = bundle.getLong("lowPrice");
        highPriceString = bundle.getLong("highPrice");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPosts();
        setUpRecyclerView(view);

    }

    private void getPosts() {
        searchPresenterImp = new SearchPresenterImp(this, getActivity());
        LoadingDialog.showProgress(getActivity());
        searchPresenterImp.getPosts(townString, cityString, homeTypeString, contractTypeString
                , lowPriceString, highPriceString);
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
                    searchPresenterImp.loadMorePosts(townString, cityString, homeTypeString, contractTypeString
                            , lowPriceString, highPriceString);
                }
            }
        });
    }

    @Override
    public void getMorePost(Post post) {
        postList.add(post);
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPost(List<Post> postList) {
        LoadingDialog.hideProgress();
        if (postList != null) {
            this.postList = postList;
            postAdapter = new PostAdapter(postList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(postAdapter);
            recyclerView.setHasFixedSize(true);

            postAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "There's no result match these filters", Toast.LENGTH_SHORT).show();
        }
    }

}