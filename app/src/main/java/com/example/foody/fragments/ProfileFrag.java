package com.example.foody.fragments;

import com.example.foody.R;
import com.example.foody.Utils;
import com.example.foody.model.User;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;



import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFrag extends Fragment {

    TextView userUsername;
    TextView userEmail;
    TextView userInfo;
    CircleImageView userProfileImage;
    ImageButton editProfileBtn;
    ImageView backgroundImageView;

    //-----------------------------------------------------------------------------------------------------

    public ProfileFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        backgroundImageView = view.findViewById(R.id.profile_fragment_background_image_view);
        userUsername = view.findViewById(R.id.profile_fragment_username_text_view);
        userEmail = view.findViewById(R.id.profile_fragment_email_text_view);
        userInfo = view.findViewById(R.id.profile_fragment_info_text_view);
        userProfileImage = view.findViewById(R.id.profile_fragment_profile_image_view);
        editProfileBtn = view.findViewById(R.id.profile_fragment_edit_profile_btn);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEditProfilePage();
            }
        });

        Utils.animateBackground(backgroundImageView);
        setUserProfile();
        return view;
    }

    //-----------------------------------------------------------------------------------------------------

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void toEditProfilePage() {
        NavController navCtrl = Navigation.findNavController(getActivity(), R.id.home_nav_host);
        NavDirections directions = ProfileFragDirections.actionProfileFragmentToEditProfileFragment();
        navCtrl.navigate(directions);
    }

    public void setUserProfile(){
        userUsername.setText(User.getInstance().userUsername);
        userEmail.setText(User.getInstance().userEmail);
        userInfo.setText(User.getInstance().userInfo);

        if (User.getInstance().profileImageUrl != null){
            Picasso.get().load(User.getInstance().profileImageUrl).noPlaceholder().into(userProfileImage);
        }
    }

}