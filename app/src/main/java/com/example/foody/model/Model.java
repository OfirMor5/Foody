package com.example.foody.model;
import com.example.foody.FoodyApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;
import android.util.Log;


import android.annotation.SuppressLint;

public class Model {

    public static final Model instance = new Model();

    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface CompListener{
        void onComplete();
    }
    private Model(){
    }

    //-----------------------------------------------------------------------------------------------------

    @SuppressLint("StaticFieldLeak")
    public void addPost(final Post post, Listener<Boolean> listener) {
        ModelFirebase.addPost(post,listener);
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDB.db.postDao().insertAllPosts(post);
                return "";
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void deletePost(final Post post, Listener<Boolean> listener){
        ModelFirebase.deletePost(post,listener);
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDB.db.postDao().deletePost(post);
                return "";
            }
        }.execute();
    }


    public void refreshPostsList(final CompListener listener){
        long lastUpdated = FoodyApp.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("PostsLastUpdateDate",0);
        ModelFirebase.getAllPostsSince(lastUpdated,new Listener<List<Post>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Post> data) {
                new AsyncTask<String,String,String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        long lastUpdated = 0;
                        for(Post p: data){
                            AppLocalDB.db.postDao().insertAllPosts(p);
                            if (p.lastUpdated > lastUpdated)
                                lastUpdated = p.lastUpdated;
                        }
                        SharedPreferences.Editor edit = FoodyApp.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
                        edit.putLong("PostsLastUpdateDate",lastUpdated);
                        edit.commit();
                        return "";
                    }
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        cleanLocalDb();
                        if (listener!=null)
                            listener.onComplete();
                    }
                }.execute("");
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void cleanLocalDb(){
        ModelFirebase.getDeletedPostsId(new Listener<List<String>>() {
            @Override
            public void onComplete(final List<String> data) {
                new AsyncTask<String,String,String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        for (String id: data){
                            Log.d("TAG", "deleted id: " + id);
                            AppLocalDB.db.postDao().deleteByPostId(id);
                        }
                        return "";
                    }
                }.execute("");
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------

    public LiveData<List<Post>> getAllPosts(){
        LiveData<List<Post>> liveData = AppLocalDB.db.postDao().getAllPosts();
        refreshPostsList(null);
        return liveData;
    }


    public void updateUserProfile(String username, String info, String profileImgUrl, Listener<Boolean> listener) {
        ModelFirebase.updateUserProfile(username, info, profileImgUrl, listener);
    }

    public void setUserAppData(String email){
        ModelFirebase.setUserAppData(email);
    }

}
