package com.example.foody.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.foody.model.Comment;
import com.example.foody.model.Model;


import java.util.List;

public class CommentListModel extends ViewModel {

    LiveData<List<Comment>> liveData;

    public LiveData<List<Comment>> getData(){
        if (liveData == null)
            liveData = Model.instance.getAllComments();
        return liveData;
    }

    public LiveData<List<Comment>> getDataPerPost(String postId){
        if (liveData == null)
            liveData = Model.instance.getAllCommentsPerPost(postId);
        return liveData;
    }

    public void refresh(String postId, Model.CompListener listener){
        Model.instance.refreshCommentsList(postId, listener);
    }
}