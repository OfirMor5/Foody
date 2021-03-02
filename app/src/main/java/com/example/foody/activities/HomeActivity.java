package com.example.foody.activities;


<<<<<<< HEAD
public class HomeActivity {
=======


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
>>>>>>> parent of c69de9a (Cleaned the code, added delete function, clean localdb function and more)
}
