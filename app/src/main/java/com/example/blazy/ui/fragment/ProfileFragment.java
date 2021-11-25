package com.example.blazy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.blazy.R;
import com.example.blazy.ui.activity.LoginActivity;
import com.example.blazy.databinding.ProfileFragmentBinding;
import com.example.blazy.model.apiresponse.userlogin.Data;
import com.example.blazy.util.SessionManagerUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private ProfileFragmentBinding profileFragmentBinding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileFragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false);

        Data userData =  SessionManagerUtil.getInstance().getUserData(getContext());
        profileFragmentBinding.profilePict.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle));
        profileFragmentBinding.profilePict.setImageResource(R.drawable.ic_baseline_person_24);
        profileFragmentBinding.email.setText(userData.getEmail());
        profileFragmentBinding.fullName.setText(userData.getFullName());
        profileFragmentBinding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagerUtil.getInstance().endUserSession(getContext());
                startLoginActivity();
            }
        });
        return profileFragmentBinding.getRoot();
    }

    private void startLoginActivity(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}