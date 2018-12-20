package com.example.sazzad.farmersapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sazzad.farmersapp.CommentsActivity;
import com.example.sazzad.farmersapp.Model.BlogPost;
import com.example.sazzad.farmersapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    public Context context;
    public List<BlogPost>blog_list;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    public BlogRecyclerAdapter(List<BlogPost>blog_list){

        this.blog_list= blog_list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);

        context=parent.getContext();

        firebaseFirestore = FirebaseFirestore.getInstance();


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        String name_data=blog_list.get(position).getName();
        String phone_data=blog_list.get(position).getPhone();
        String address_data=blog_list.get(position).getAddress();
        String quantity_data=blog_list.get(position).getQuantity();
        String price_data=blog_list.get(position).getPrice();

        holder.setDescText("ফসলের নাম: "+name_data+"\nপরিমান: "+quantity_data+"\nদাম: "+price_data+"\nফোন নাম্বার: "+phone_data+"\nঠিকানা: "+address_data);

        String image_url= blog_list.get(position).getImage_url();
        holder.setPostImage(image_url);

        holder.setIsRecyclable(false);
        final String blogPostId = blog_list.get(position).getBlogId();


        String user_id = blog_list.get(position).getUser_id();

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image_url");

                    holder.setUserData(userName, userImage);


                } else {



                }

            }
        });


        try {
            long millisecond = blog_list.get(position).getTimestamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
            holder.setTime(dateString);
        } catch (Exception e) {

            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        holder.blogCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent commentIntent = new Intent(context, CommentsActivity.class);
                commentIntent.putExtra("blog_post_id", blogPostId);
                context.startActivity(commentIntent);

            }
        });

        firebaseFirestore.collection("Posts/" + blogPostId + "/Comments").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if(!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();

                    holder.updateCommentsCount(count);

                } else {

                    holder.updateCommentsCount(0);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView descView,dateView;
        private ImageView postImageView;
        private TextView blogUserName;
        private CircleImageView blogUserImage;
        private ImageView blogCommentBtn;
        private TextView blogCommentCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            blogCommentBtn = mView.findViewById(R.id.blog_comment_icon);
        }

        public void setDescText(String nameText){
            descView=mView.findViewById(R.id.blog_desc);
            descView.setText(nameText);

        }
        public void setPostImage(String downloadUri){
            postImageView= mView.findViewById(R.id.blog_image);

            Glide.with(context).load(downloadUri).into(postImageView);
        }
        public void setTime(String dateText){
            dateView=mView.findViewById(R.id.blog_date);
            dateView.setText(dateText);
        }

        public void setUserData(String name, String image){

            blogUserImage = mView.findViewById(R.id.blog_user_image);
            blogUserName = mView.findViewById(R.id.blog_user_name);

            blogUserName.setText(name);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.profile_placeholder);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(image).into(blogUserImage);

        }

        public void updateCommentsCount(int count) {
            blogCommentCount=mView.findViewById(R.id.blog_comment_count);
            Log.d(TAG, "updateCommentsCount: " +count);

            blogCommentCount.setText(count+" মন্তব্য");



        }
    }




}
