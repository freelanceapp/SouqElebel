package com.souqelebel.activities_fragments.bank_activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;


import com.souqelebel.R;
import com.souqelebel.adapters.BankAdapter;
import com.souqelebel.databinding.ActivityBankBinding;
import com.souqelebel.interfaces.Listeners;
import com.souqelebel.language.Language;
import com.souqelebel.models.BankDataModel;
import com.souqelebel.models.UserModel;
import com.souqelebel.preferences.Preferences;
import com.souqelebel.remote.Api;
import com.souqelebel.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanksActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityBankBinding binding;
    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private BankAdapter bankAdapter;
    private List<BankDataModel.BankModel> bankModelList;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank);
        initView();
    }

    private void initView() {
        bankModelList = new ArrayList<>();
        bankAdapter = new BankAdapter(bankModelList, this);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
        binding.recbank.setLayoutManager(new GridLayoutManager(this, 1));
        binding.recbank.setItemViewCacheSize(25);
        binding.recbank.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recbank.setDrawingCacheEnabled(true);
        binding.recbank.setAdapter(bankAdapter);
        getBankAccount();


    }

    private void getBankAccount() {
        Api.getService(Tags.base_url)
                .getBanks()
                .enqueue(new Callback<BankDataModel>() {
                    @Override
                    public void onResponse(Call<BankDataModel> call, Response<BankDataModel> response) {
                        //  progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            bankModelList.addAll(response.body().getData());
                            bankAdapter.notifyDataSetChanged();
                        } else {
                            try {
                                Log.e("Error", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(BanksActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BankDataModel> call, Throwable t) {
                        try {

                            //  progBar.setVisibility(View.GONE);
                            Toast.makeText(BanksActivity.this, getString(R.string.something), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                        }
                    }
                });
    }

    @Override
    public void back() {
        finish();
    }


}
