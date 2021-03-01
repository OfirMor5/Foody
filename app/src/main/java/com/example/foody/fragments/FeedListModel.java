package com.example.foody.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foody.model.Model;
import com.example.foody.model.Post;

import java.util.List;

public class FeedListModel extends ViewModel {

    LiveData<List<Post>> liveData;

    public LiveData<List<Post>> getData(){
        if (liveData == null)
            liveData = Model.instance.getAllPosts();
        return liveData;
    }

    public void refresh(Model.CompListener listener){
        Model.instance.refreshPostsList(listener);
    }
}
