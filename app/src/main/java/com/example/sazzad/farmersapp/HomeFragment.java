package com.example.sazzad.farmersapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sazzad.farmersapp.Model.BlogPost;
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

public class HomeFragment extends Fragment {

    private RecyclerView blog_list_view;
    private List<BlogPost> blog_list;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private BlogRecyclerAdapter blogRecyclerAdapter;

    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.home_fragment,container,false);
        Log.d("Home Fragment", "I am here");
        blog_list=new ArrayList<>();
        blog_list_view=view.findViewById(R.id.blog_list_view);

        blogRecyclerAdapter=new BlogRecyclerAdapter(blog_list);
        blog_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        blog_list_view.setAdapter(blogRecyclerAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Query orderByQuery=firebaseFirestore.collection("Posts").orderBy("timestamp",Query.Direction.DESCENDING);
        orderByQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc: documentSnapshots.getDocumentChanges()){
                    if(doc.getType()== DocumentChange.Type.ADDED){


                        BlogPost blogPost= doc.getDocument().toObject(BlogPost.class);
                        Log.d("Post", blogPost.getName());
                        blog_list.add(blogPost);
                        blogRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return view;
    }
}