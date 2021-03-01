package com.example.foody.activities;
import com.example.foody.R;
import com.example.foody.fragments.FeedListFrag;
import com.example.foody.fragments.FeedListFragDirections;
import com.example.foody.model.Post;

import android.os.Bundle;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomeActivity extends AppCompatActivity implements FeedListFrag.Delegate  {

    NavController navC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navC = Navigation.findNavController(this, R.id.home_nav_host);
        BottomNavigationView bottomNav = findViewById(R.id.home_bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navC);
    }

    @Override
    public void onItemSelected(Post post) {
        navC = Navigation.findNavController(this, R.id.home_nav_host);
        FeedListFragDirections.ActionFeedListFragmentToPostDetailsFragment directions = FeedListFragDirections.actionFeedListFragmentToPostDetailsFragment(post);
        navC.navigate(directions);
    }
}
