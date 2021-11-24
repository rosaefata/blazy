package com.example.blazy.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blazy.R;
import com.example.blazy.databinding.HomeFragmentBinding;
import com.example.blazy.databinding.ProfileFragmentBinding;
import com.example.blazy.model.apiresponse.userlogin.Data;
import com.example.blazy.util.SessionManagerUtil;

import java.util.Set;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
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
//        profileFragmentBinding.profilePict.setImageResource(R.drawable.circle);
        profileFragmentBinding.email.setText(userData.getEmail());
        profileFragmentBinding.fullName.setText(userData.getFullName());
        return profileFragmentBinding.getRoot();
    }
}