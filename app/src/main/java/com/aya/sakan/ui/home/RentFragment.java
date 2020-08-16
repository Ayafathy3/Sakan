package com.aya.sakan.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.adapters.Post;
import com.aya.sakan.ui.home.adapters.PostAdapter;
import com.aya.sakan.util.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class RentFragment extends Fragment implements IHomePresenterContract.View {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private HomeFragmentPresenterImp rentFragmentPresenterImp;
    public List<Post> postList;

    public RentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        rentFragmentPresenterImp = new HomeFragmentPresenterImp(this, getActivity());
        LoadingDialog.showProgress(getActivity());
        rentFragmentPresenterImp.getPosts("ايجار");

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
                    rentFragmentPresenterImp.loadMorePosts("ايجار");
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
        if (postList != null) {
            this.postList = postList;
            postAdapter = new PostAdapter(postList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(postAdapter);
            recyclerView.setHasFixedSize(true);

            postAdapter.notifyDataSetChanged();
        }
        LoadingDialog.hideProgress();
    }
}
