package com.example.foody.fragments;

import com.example.foody.R;
import com.example.foody.model.Post;
import com.example.foody.model.StoreModel;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import  com.example.foody.model.Model;
import com.example.foody.model.User;
import com.example.foody.model.ModelFirebase;
import android.widget.Button;


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
import android.widget.Toast;


import de.hdodenhof.circleimageview.CircleImageView;

public class PostRecipeFrag extends Fragment {

    Post post;
    View view;
    TextView postTitle;
    TextView username;
    TextView postContent;
    ImageView postImg;
    CircleImageView profilePic;
    ImageButton closeB;
    ImageButton editPostB;
    ImageButton deletePostB;
    Button contact;
    Button comments;

    //-----------------------------------------------------------------------------------------------------

    public PostRecipeFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_postrecipe, container, false);
        postTitle = view.findViewById(R.id.post_details_fragment_title_text_view);
        username = view.findViewById(R.id.post_details_fragment_username_text_view);
        postContent = view.findViewById(R.id.post_details_fragment_post_content_text_view);
        postImg = view.findViewById(R.id.post_details_fragment_post_image_view);
        profilePic = view.findViewById(R.id.post_details_fragment_profile_image_view);
        comments = view.findViewById(R.id.post_details_fragment_comments_btn);
        post = PostRecipeFragArgs.fromBundle(getArguments()).getPost();

        if (post != null) {
            postTitle.setText(post.postTitle);
            username.setText(post.username);
            postContent.setText(post.postContent);
            if (post.postImgUrl != null && post.userProfileImageUrl != null) {
                Picasso.get().load(post.postImgUrl).noPlaceholder().into(postImg);
                Picasso.get().load(post.userProfileImageUrl).noPlaceholder().into(profilePic);
            } else {
                postImg.setImageResource(R.drawable.profile_pic_placeholde);
                profilePic.setImageResource(R.drawable.profile_pic_placeholde);
            }

//            contact.setText("Contact " + post.username);
//            contact.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DialogFrag dialog = DialogFrag.newInstance(post.username + "'s Contact Info", post.contact);
//                    dialog.show(getParentFragmentManager(),"TAG");
//                }
//            });

            comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toCommentsPage(post);
                }
            });

            closeB = view.findViewById(R.id.post_details_fragment_close_btn);
            closeB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navCtrl = Navigation.findNavController(view);
                    navCtrl.popBackStack();

                }
            });


            editPostB = view.findViewById(R.id.post_details_fragment_edit_btn);
            editPostB.setVisibility(View.INVISIBLE);
            deletePostB = view.findViewById(R.id.post_details_fragment_delete_btn);
            deletePostB.setVisibility(View.INVISIBLE);

            if (post.userId.equals(User.getInstance().userId)) {

                editPostB.setVisibility(View.VISIBLE);
                editPostB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toEditPostPage(post);
                    }
                });

                deletePostB.setVisibility(View.VISIBLE);
                deletePostB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletePost(post);
                    }
                });
            }
        }

        return view;
    }

    //-----------------------------------------------------------------------------------------------------

    private void toEditPostPage(Post post) {
        NavController navCtrl = Navigation.findNavController(getActivity(), R.id.home_nav_host);
        PostRecipeFragDirections.ActionPostDetailsFragmentToEditPostFragment directions = PostRecipeFragDirections.actionPostDetailsFragmentToEditPostFragment(post);
        navCtrl.navigate(directions);
    }

    private void toCommentsPage(Post post){
        NavController navCtrl = Navigation.findNavController(getActivity(), R.id.home_nav_host);
        PostRecipeFragDirections.ActionPostDetailsFragmentToCommentListFragment directions = PostRecipeFragDirections.actionPostDetailsFragmentToCommentListFragment(post);
        navCtrl.navigate(directions);
    }

    //-----------------------------------------------------------------------------------------------------


    void deletePost(Post postToDelete){

        Model.instance.deletePost(postToDelete, new Model.Listener<Boolean>() {
            @Override
            public void onComplete(Boolean data) {

                StoreModel.deleteImage(post.postImgUrl, new StoreModel.Listener() {
                    @Override
                    public void onSuccess(String url) {
                        NavController navCtrl = Navigation.findNavController(view);
                        navCtrl.navigateUp();
                    }
                    @Override
                    public void onFail() {
                        Snackbar.make(view, "Failed to create post and save it in databases", Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

}