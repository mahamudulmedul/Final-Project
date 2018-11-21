package com.example.sazzad.farmersapp;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class BlogPostId {
    @Exclude
    public String BlogPostId;

    public <T extends BlogPostId> T withId(@NonNull final String id) {
        this.BlogPostId = id;
        return (T) this;
    }
}
