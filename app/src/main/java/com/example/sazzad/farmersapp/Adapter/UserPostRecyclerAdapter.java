package com.example.sazzad.farmersapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sazzad.farmersapp.CommentsActivity;
import com.example.sazzad.farmersapp.Model.BlogPost;
import com.example.sazzad.farmersapp.PostActivity;
import com.example.sazzad.farmersapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

public class UserPostRecyclerAdapter extends RecyclerView.Adapter<UserPostRecyclerAdapter.ViewHolder> {

    public static Context context;
    public List<BlogPost>post_list;
    private FirebaseFirestore firebaseFirestore;

    public UserPostRecyclerAdapter(List<BlogPost> post_list) {
        this.post_list= post_list;
    }

    @NonNull
    @Override
    public UserPostRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_post_list, viewGroup, false);
        context=viewGroup.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserPostRecyclerAdapter.ViewHolder viewHolder, int i) {

        String user_id = post_list.get(i).getUser_id();
        String name_data=post_list.get(i).getName();
        String phone_data=post_list.get(i).getPhone();
        String address_data=post_list.get(i).getAddress();
        String quantity_data=post_list.get(i).getQuantity();
        String price_data=post_list.get(i).getPrice();


        viewHolder.setDescText("ফসলের নাম: "+name_data+"\nপরিমান: "+quantity_data+"\nদাম: "+price_data+"\nফোন নাম্বার: "+phone_data+"\nঠিকানা: "+address_data);

        String image_url= post_list.get(i).getImage_url();
        viewHolder.setPostImage(image_url);

        viewHolder.setIsRecyclable(false);
        final String blogPostId = post_list.get(i).getBlogId();




        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image_url");

                    viewHolder.setUserData(userName, userImage);


                } else {



                }

            }
        });


        try {
            long millisecond = post_list.get(i).getTimestamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
            viewHolder.setTime(dateString);
        } catch (Exception e) {

            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        viewHolder.blogCommentBtn.setOnClickListener(new View.OnClickListener() {
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

                    viewHolder.updateCommentsCount(count);

                } else {

                    viewHolder.updateCommentsCount(0);

                }

            }
        });

        ViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage("আপনি মুছে ফেলতে চান ?");
                builder.setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                firebaseFirestore.collection("Posts").document(blogPostId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                            Intent Intent = new Intent(context, PostActivity.class);
                                            context.startActivity(Intent);
                                        } else {

                                        }
                                    }
                                });
                            }
                        });
                builder.setNegativeButton("না", null);

                AlertDialog alert = builder.create();
                alert.show();





            }
        });
        ViewHolder.btnSoldOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url="https://firebasestorage.googleapis.com/v0/b/farmersapp-1888c.appspot.com/o/sold_out%2Fsold-out-product-sellout-inventory-gone-stamp-3d-animation_s-1n4tlu__F0014.png?alt=media&token=42b947ed-c9fa-4708-90df-e380399cb878";
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage(" ?");
                builder.setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        firebaseFirestore.collection("Posts").document(blogPostId).update("image_url",url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "sold out", Toast.LENGTH_SHORT).show();
                                    Intent Intent = new Intent(context, PostActivity.class);
                                    context.startActivity(Intent);
                                } else {

                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("না", null);

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return post_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static View btnDelete,btnSoldOut;
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
                btnDelete=mView.findViewById(R.id.btn_delete);
                btnSoldOut=mView.findViewById(R.id.btn_soldout);
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
