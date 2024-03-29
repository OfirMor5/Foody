package com.example.foody.model;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PostDao {

    @Query("select * from Post")
    LiveData<List<Post>> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPosts(Post... posts);
    @Delete
    void deletePost(Post post);

    @Query("select exists(select * from Post where postId = :postId)")
    boolean isPostExists(String postId);

    @Query("delete from Post where postId = :postId")
    void deleteByPostId(String postId);
}
