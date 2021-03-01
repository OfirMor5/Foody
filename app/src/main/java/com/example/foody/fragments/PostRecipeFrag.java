package com.example.foody.fragments;

import com.example.foody.R;
import com.example.foody.model.Post;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostRecipeFrag extends Fragment {

    Post post;
    TextView postTitle;
    TextView username;
    TextView postContent;
    ImageView postImg;
    CircleImageView profilePic;

    public PostRecipeFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_postrecipe, container, false);
        postTitle = view.findViewById(R.id.post_details_fragment_title_text_view);
        username = view.findViewById(R.id.post_details_fragment_username_text_view);
        postContent = view.findViewById(R.id.post_details_fragment_post_content_text_view);
        postImg = view.findViewById(R.id.post_details_fragment_post_image_view);
        profilePic = view.findViewById(R.id.post_details_fragment_profile_image_view);
        post = PostRecipeFragArgs.fromBundle(getArguments()).getPost();

        if (post != null) {
            postTitle.setText(post.postTitle);
            username.setText(post.username);
            postContent.setText(post.postContent);
            if (post.postImgUrl != null && post.userProfileImageUrl != null){
                Picasso.get().load(post.postImgUrl).noPlaceholder().into(postImg);
                Picasso.get().load(post.userProfileImageUrl).noPlaceholder().into(profilePic);
            }
            else {
                postImg.setImageResource(R.drawable.profile_pic_placeholde);
                profilePic.setImageResource(R.drawable.profile_pic_placeholde);
            }
        }

        ImageButton closeBtn = view.findViewById(R.id.post_details_fragment_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navCtrl = Navigation.findNavController(view);
                navCtrl.popBackStack();
            }
        });
        return view;
    }

}