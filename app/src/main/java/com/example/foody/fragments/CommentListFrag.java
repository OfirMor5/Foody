package com.example.foody.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.foody.R;
import com.example.foody.model.Comment;
import com.example.foody.model.Model;
import com.example.foody.model.Post;
import com.example.foody.model.User;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListFrag extends Fragment {

    Post post;
    View view;
    RecyclerView list;
    List<Comment> data = new LinkedList<Comment>();
    CommentsListAdapter adapter;
    CommentListModel viewModel;
    LiveData<List<Comment>> liveData;
    FloatingActionButton addCommentBtn;

    public CommentListFrag() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(CommentListModel.class);
    }

    //-----------------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_comment, container, false);

        post = CommentListFragArgs.fromBundle(getArguments()).getPost();
        if (post != null){
            addCommentBtn = view.findViewById(R.id.comments_list_add_comment_btn);
            addCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popUpCommentAlertDialog();
                }
            });

            list = view.findViewById(R.id.comments_list_list);
            list.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            list.setLayoutManager(layoutManager);

            adapter = new CommentsListAdapter();
            list.setAdapter(adapter);

            liveData = viewModel.getDataPerPost(post.postId);
            liveData.observe(getViewLifecycleOwner(), new Observer<List<Comment>>() {
                @Override
                public void onChanged(List<Comment> comments) {
                    data = reverseData(comments);
                    adapter.notifyDataSetChanged();
                }
            });

            final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.comments_list_swipe_refresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    viewModel.refresh(post.postId, new Model.CompListener() {
                        @Override
                        public void onComplete() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            });
        }

        return view;
    }

    //-----------------------------------------------------------------------------------------------------

    private void popUpCommentAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText editText = new EditText(getContext());
        alert.setTitle("New Comment");
        alert.setView(editText);
        alert.setPositiveButton("Comment", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                saveComment(editText.getText().toString());
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    void saveComment(String commentContent){
        final Comment newComment = generateNewComment(commentContent);

        Model.instance.addComment(newComment, new Model.Listener<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                viewModel.refresh(post.postId, new Model.CompListener() {
                    @Override
                    public void onComplete() {
                    }
                });
            }
        });
    }

    private Comment generateNewComment(String commentContent){
        Comment newComment = new Comment();
        newComment.commentId = UUID.randomUUID().toString();
        newComment.postId = post.postId;
        newComment.userId = User.getInstance().userId;
        newComment.userProfileImageUrl = User.getInstance().profileImageUrl;
        newComment.username = User.getInstance().userUsername;
        newComment.commentContent = commentContent;
        return newComment;
    }

    private List<Comment> reverseData(List<Comment> comments) {
        List<Comment> reversedData = new LinkedList<>();
        for (Comment comment: comments) {
            reversedData.add(0, comment);
        }
        return reversedData;
    }

    //-----------------------------------------------------------------------------------------------------

    static class CommentRowViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView commentContent;
        CircleImageView userProfileImageView;
        ImageButton deleteCommentBtn;
        ImageButton editCommentBtn;
        Comment comment;

        public CommentRowViewHolder(@NonNull final View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.row_comments_username_text_view);
            commentContent = itemView.findViewById(R.id.row_comments_content_text_view);
            userProfileImageView = itemView.findViewById(R.id.row_comments_profile_image_view);

            deleteCommentBtn = itemView.findViewById(R.id.row_comments_delete_comment_btn);
            deleteCommentBtn.setVisibility(View.INVISIBLE);
            deleteCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteComment(comment);
                }
            });
            editCommentBtn = itemView.findViewById(R.id.row_comments_edit_comment_btn);
            editCommentBtn.setVisibility(View.INVISIBLE);
            editCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popUpEditAlertDialog();
                }
            });

        }

        //-----------------------------------------------------------------------------------------------------

        public void bind(Comment commentToBind){
            comment = commentToBind;
            username.setText(commentToBind.username);
            commentContent.setText(commentToBind.commentContent);

            if (commentToBind.userProfileImageUrl != null){
                Picasso.get().load(commentToBind.userProfileImageUrl).noPlaceholder().into(userProfileImageView);
            }
            else {
                userProfileImageView.setImageResource(R.drawable.addimage);
            }

            if (comment.userId.equals(User.getInstance().userId)){
                editCommentBtn.setVisibility(View.VISIBLE);
                deleteCommentBtn.setVisibility(View.VISIBLE);
            }
        }

        private void deleteComment(Comment commentToDelete){

            Model.instance.deleteComment(commentToDelete, new Model.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    Toast.makeText(itemView.getContext(), "Comment Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //----------------------------------------

        private void popUpEditAlertDialog(){
            AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
            final EditText editText = new EditText(itemView.getContext());
            editText.setHint(comment.commentContent);
            alert.setTitle("Edit Comment");
            alert.setView(editText);
            alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    editComment(editText.getText().toString());
                    dialog.dismiss();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });

            alert.show();
        }

        //-----------------------------

        private void editComment(String newCommentContent){
            Comment EditedComment = comment;
            EditedComment.commentContent = newCommentContent;
            Model.instance.editComment(EditedComment, new Model.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    Toast.makeText(itemView.getContext(), "Comment Edited", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    class CommentsListAdapter extends RecyclerView.Adapter<CommentListFrag.CommentRowViewHolder>{

        @NonNull
        @Override
        public CommentListFrag.CommentRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.comments_list, parent, false);
            CommentListFrag.CommentRowViewHolder commentRowViewHolder = new CommentListFrag.CommentRowViewHolder(view);
            return commentRowViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CommentListFrag.CommentRowViewHolder holder, int position) {
            Comment comment = data.get(position);
            holder.bind(comment);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


}