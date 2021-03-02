package com.example.foody.model;

import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import androidx.annotation.NonNull;

public class ModelFirebase {

    final static String POST_COLLECTION = "posts";

    public static void getAllPostsSince(long since, final Model.Listener<List<Post>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(since,0);
        db.collection(POST_COLLECTION).whereGreaterThanOrEqualTo("lastUpdated", ts).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> postsData = null;
                if (task.isSuccessful()){
                    postsData = new LinkedList<Post>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Map<String,Object> json = doc.getData();
                        Post posts = factory(json);
                        postsData.add(posts);
                    }
                }
                listener.onComplete(postsData);
                Log.d("TAG","refresh " + postsData.size());
            }
        });
    }

    public static void getAllPosts(final Model.Listener<List<Post>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(POST_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> postsData = null;
                if (task.isSuccessful()){
                    postsData = new LinkedList<Post>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Post posts = doc.toObject(Post.class);
                        postsData.add(posts);
                    }
                }
                listener.onComplete(postsData);
            }
        });
    }

    public static void addPost(Post post, final Model.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(POST_COLLECTION).document(post.getPostId()).set(toJson(post)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    private static Post factory(Map<String, Object> json){
        Post newPost = new Post();
        newPost.postId = (String) json.get("postId");
        newPost.postTitle = (String) json.get("postTitle");
        newPost.postContent = (String) json.get("postContent");
        newPost.postImgUrl = (String) json.get("postImgUrl");
        newPost.userId = (String) json.get("userId");
        newPost.userProfileImageUrl = (String) json.get("userProfilePicUrl");
        newPost.username = (String) json.get("username");
        Timestamp ts = (Timestamp)json.get("lastUpdated");
        if (ts != null)
            newPost.lastUpdated = ts.getSeconds();
        return newPost;
    }

    private static Map<String, Object> toJson(Post post){
        HashMap<String, Object> json = new HashMap<>();
        json.put("postId", post.postId);
        json.put("postTitle", post.postTitle);
        json.put("postContent", post.postContent);
        json.put("postImgUrl", post.postImgUrl);
        json.put("userId", post.userId);
        json.put("userProfilePicUrl", post.userProfileImageUrl);
        json.put("username", post.username);
        json.put("lastUpdated", FieldValue.serverTimestamp());
        return json;
    }
}
