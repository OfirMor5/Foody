package com.example.foody.activities;
import com.example.foody.R;
import com.example.foody.fragments.FeedListFrag;
import com.example.foody.model.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity  {

    NavController navC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navC = Navigation.findNavController(this, R.id.home_nav_host);
        BottomNavigationView bottomNav = findViewById(R.id.home_bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navC);
    }

//    @Override
//    public void onItemSelected(Post post) {
//        navCtrl = Navigation.findNavController(this, R.id.home_nav_host);
//        FeedListFragmentDirections.ActionFeedListFragmentToPostDetailsFragment directions = FeedListFragmentDirections.actionFeedListFragmentToPostDetailsFragment(post);
//        navCtrl.navigate(directions);
//    }
}
