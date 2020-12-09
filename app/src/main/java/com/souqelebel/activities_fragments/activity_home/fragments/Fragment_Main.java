package com.souqelebel.activities_fragments.activity_home.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_home.HomeActivity;
import com.souqelebel.activities_fragments.activity_product_details.ProductDetailsActivity;
import com.souqelebel.adapters.CategoryAdapter;

import com.souqelebel.adapters.ProductAdapter;
import com.souqelebel.adapters.SlidingImage_Adapter;
import com.souqelebel.databinding.FragmentMainBinding;
import com.souqelebel.models.MainCategoryDataModel;
import com.souqelebel.models.MainCategoryModel;
import com.souqelebel.models.ProductDetailsModel;
import com.souqelebel.models.ProductModel;
import com.souqelebel.models.Slider_Model;
import com.souqelebel.models.UserModel;
import com.souqelebel.preferences.Preferences;
import com.souqelebel.remote.Api;
import com.souqelebel.share.Common;
import com.souqelebel.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Main extends Fragment {

    private HomeActivity activity;
    private FragmentMainBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;
    private ProductAdapter productAdapter;
    private LinearLayoutManager manager;
    private List<ProductModel> productModelList;


    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView() {

        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        productModelList = new ArrayList<>();
        manager = new LinearLayoutManager(activity);


        productAdapter = new ProductAdapter(productModelList,activity,this);
        binding.recView.setLayoutManager(manager);
        binding.recView.setAdapter(productAdapter);


    }


    public void updateData(List<ProductModel> productModelList){
        binding.progBar.setVisibility(View.GONE);
        this.productModelList.clear();
        productAdapter.notifyDataSetChanged();
        this.productModelList.addAll(productModelList);
        productAdapter.notifyDataSetChanged();
        if (this.productModelList.size()>0){
            binding.llNoData.setVisibility(View.GONE);
        }else {
            binding.llNoData.setVisibility(View.VISIBLE);

        }
    }


    public void setProductItemData(ProductModel productModel) {
      /*  String like="";
        if (productModel.getUser_like()!=null){

            like="t"
        }*/
        Intent intent = new Intent(activity,ProductDetailsActivity.class);
        intent.putExtra("product_id",productModel.getId());

        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }



}
