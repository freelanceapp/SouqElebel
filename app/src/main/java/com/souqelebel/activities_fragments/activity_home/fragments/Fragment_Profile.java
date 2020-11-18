package com.souqelebel.activities_fragments.activity_home.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_about_app.AboutAppActivity;
import com.souqelebel.activities_fragments.activity_edit_profile.EditProfileActivity;
import com.souqelebel.activities_fragments.activity_home.HomeActivity;
import com.souqelebel.databinding.FragmentProfileBinding;
import com.souqelebel.interfaces.Listeners;
import com.souqelebel.models.SettingModel;
import com.souqelebel.models.UserModel;
import com.souqelebel.preferences.Preferences;
import com.souqelebel.remote.Api;
import com.souqelebel.share.Common;
import com.souqelebel.tags.Tags;


import java.io.IOException;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Profile extends Fragment {

    private HomeActivity activity;
    private FragmentProfileBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;

    public static Fragment_Profile newInstance() {

        return new Fragment_Profile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setModel(userModel);

        binding.flEditProfile.setOnClickListener(view -> {
            Intent intent = new Intent(activity,EditProfileActivity.class);
            startActivityForResult(intent,100);
        });

    }












    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK){
            userModel = preferences.getUserData(activity);
            binding.setModel(userModel);

        }
    }


}
