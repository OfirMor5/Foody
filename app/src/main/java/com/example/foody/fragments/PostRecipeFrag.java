package com.example.foody.fragments;
import com.example.foody.R;
import com.example.foody.model.Post;

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


public class PostRecipeFrag extends Fragment {


    Post post;
    TextView postTitle;
    TextView username;
    TextView postContent;

    public PostRecipeFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_postrecipe, container, false);
        postTitle = view.findViewById(R.id.post_details_fragment_title_text_view);
        username = view.findViewById(R.id.post_details_fragment_username_text_view);
        postContent = view.findViewById(R.id.post_details_fragment_post_content_text_view);

      //  post = PostDetailsFragmentArgs.fromBundle(getArguments()).getPost();
        if (post != null) {
            postTitle.setText(post.postTitle);
            username.setText(post.username);
            postContent.setText(post.postContent);
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
