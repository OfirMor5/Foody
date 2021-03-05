package com.example.foody.fragments;
import  com.example.foody.R;
import  com.example.foody.model.Model;
import com.example.foody.model.Post;
import com.example.foody.model.StoreModel;

import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import java.io.FileDescriptor;
import java.io.IOException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageButton;


import androidx.annotation.Nullable;

public class EditPostFrag extends Fragment {

    View view;
    Post post;
    EditText titleInput;
    EditText contentInput;
    Button saveChangesBtn;
    ImageView postImageView;
    ProgressBar progressBar;
    ImageButton closeB;

    Uri postImageUri;
    Bitmap postImgBitmap;
    static int REQUEST_CODE = 1;

    //-----------------------------------------------------------------------------------------------------

    public EditPostFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_editpost, container, false);

        titleInput = view.findViewById(R.id.edit_post_fragment_title_edit_text);
        contentInput = view.findViewById(R.id.edit_post_fragment_content_edit_text);
        postImageView = view.findViewById(R.id.edit_post_fragment_image_view);
        progressBar = view.findViewById(R.id.edit_post_fragment_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        post = PostRecipeFragArgs.fromBundle(getArguments()).getPost();
        if (post != null){
            setEditPostHints();
        }

        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromGallery();
            }
        });

        closeB = view.findViewById(R.id.edit_post_fragment_close_btn);
        closeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navCtrl = Navigation.findNavController(view);
                navCtrl.navigateUp();
            }
        });

        saveChangesBtn = view.findViewById(R.id.edit_post_fragment_save_changes_btn);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePost();
            }
        });

        return view;
    }

    //-----------------------------------------------------------------------------------------------------

    private void setEditPostHints(){
        if (post.postImgUrl != null) {
            Picasso.get().load(post.postImgUrl).noPlaceholder().into(postImageView);
        }
        titleInput.setText(post.postTitle);
        contentInput.setText(post.postContent);
    }

    //-----------------------------------------------------------------------------------------------------

    void updatePost() {

        progressBar.setVisibility(View.VISIBLE);

        if (postImageUri != null){
            StoreModel.uploadImage(postImgBitmap, new StoreModel.Listener() {
                @Override
                public void onSuccess(String url) {

                    Model.instance.addPost(generatedEditedPost(url), new Model.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            NavController navCtrl = Navigation.findNavController(view);
                            navCtrl.navigateUp();
                            navCtrl.navigateUp();
                        }
                    });
                }

                @Override
                public void onFail() {
                    progressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(view, "Failed to edit post", Snackbar.LENGTH_LONG).show();
                }
            });
        }
        else {
            Model.instance.addPost(generatedEditedPost(null), new Model.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    NavController navCtrl = Navigation.findNavController(view);
                    navCtrl.navigateUp();
                    navCtrl.navigateUp();
                }
            });
        }

    }

    //-----------------------------------------------------------------------------------------------------

    private Post generatedEditedPost(String imageUrl) {

        Post editedPost = post;
        if (titleInput.getText().toString() != null && !titleInput.getText().toString().equals(""))
            editedPost.postTitle = titleInput.getText().toString();
        else editedPost.postTitle = post.postTitle;
        if (contentInput.getText().toString() != null && !contentInput.getText().toString().equals(""))
            editedPost.postContent = contentInput.getText().toString();
        else editedPost.postContent = post.postContent;
        if (imageUrl != null)
            editedPost.postImgUrl = imageUrl;

        return editedPost;
    }

    //-----------------------------------------------------------------------------------------------------

    private void chooseImageFromGallery(){

        try{
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            startActivityForResult(openGalleryIntent, REQUEST_CODE);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Edit post Page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //-----------------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null && data != null){
            postImageUri = data.getData();
            postImageView.setImageURI(postImageUri);
            postImgBitmap = uriToBitmap(postImageUri);

        }
        else {
            Toast.makeText(getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    //-----------------------------------------------------------------------------------------------------

    private Bitmap uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}