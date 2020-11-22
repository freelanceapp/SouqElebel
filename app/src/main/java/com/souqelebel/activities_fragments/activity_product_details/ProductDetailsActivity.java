  package com.souqelebel.activities_fragments.activity_product_details;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_images.ImagesActivity;
import com.souqelebel.activities_fragments.activity_search.SearchActivity;
import com.souqelebel.adapters.ProductDetailsAdapter;
import com.souqelebel.adapters.ProductDetialsSlidingImage_Adapter;
import com.souqelebel.adapters.SliderAdapter;
import com.souqelebel.databinding.ActivityProductDetailsBinding;
import com.souqelebel.interfaces.Listeners;
import com.souqelebel.language.Language;
import com.souqelebel.models.ProductDataModel;
import com.souqelebel.models.ProductDetailsModel;
import com.souqelebel.models.ProductImageModel;
import com.souqelebel.models.ProductModel;
import com.souqelebel.models.UserModel;
import com.souqelebel.preferences.Preferences;
import com.souqelebel.remote.Api;
import com.souqelebel.share.Common;
import com.souqelebel.singleton.CartSingleton;
import com.souqelebel.tags.Tags;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityProductDetailsBinding binding;
    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private int product_id;
    private ProductModel productModel;
    private ProductDetailsAdapter adapter;
    private List<ProductDetailsModel> productDetailsModelList;
    private SliderAdapter sliderAdapter;
    private List<ProductImageModel>productImageModelList;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        getDataFromIntent();

        initView();

    }


    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            product_id =  intent.getIntExtra("product_id",0);

        }

    }


    private void initView() {
        productImageModelList = new ArrayList<>();
        productDetailsModelList = new ArrayList<>();
        Paper.init(this);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setBackListener(this);
        binding.setLang(lang);
        binding.tab.setupWithViewPager(binding.pager);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductDetailsAdapter(productDetailsModelList,this);
        binding.recView.setAdapter(adapter);

        sliderAdapter = new SliderAdapter(productImageModelList,this);
        binding.pager.setAdapter(sliderAdapter);



        binding.iconCopy.setOnClickListener(view -> {

            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", productModel.getUser().getPhone_code()+productModel.getUser().getPhone());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show();
        });

        binding.flCall.setOnClickListener(view -> {
            String phone = productModel.getUser().getPhone_code()+productModel.getUser().getPhone();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
            startActivity(intent);
        });

        binding.iconWhatsApp.setOnClickListener(view -> {
            String phone = productModel.getUser().getPhone_code()+productModel.getUser().getPhone();
            String url = "https://api.whatsapp.com/send?phone="+phone;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
        getProductById();
    }

    private void getProductById()
    {

        try {
            String user_id ="";
            if (userModel!=null)
            {
                user_id = String.valueOf(userModel.getUser().getId());
            }

            Api.getService(Tags.base_url)
                    .getProductById(user_id,product_id)
                    .enqueue(new Callback<ProductModel>() {
                        @Override
                        public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                            binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null ) {
                               productModel = response.body();
                               binding.setModel(productModel);

                               if (productModel.getProducts_images()!=null&&productModel.getProducts_images().size()>0){
                                   binding.flNoSlider.setVisibility(View.GONE);
                                   binding.flSlider.setVisibility(View.VISIBLE);
                                   productDetailsModelList.addAll(productModel.getProduct_details());
                                   adapter.notifyDataSetChanged();
                               }else {
                                   binding.flNoSlider.setVisibility(View.VISIBLE);
                                   binding.flSlider.setVisibility(View.GONE);
                               }



                               if (productModel.getVedio()!=null){
                                   productImageModelList.add(new ProductImageModel(0,productModel.getVedio(),"video"));
                               }

                               productImageModelList.addAll(productModel.getProducts_images());


                               binding.scrollView.setVisibility(View.VISIBLE);
                            } else {
                                binding.progBar.setVisibility(View.GONE);

                                if (response.code() == 500) {
                                    Toast.makeText(ProductDetailsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductModel> call, Throwable t) {
                            try {
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(ProductDetailsActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ProductDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }


    @Override
    public void back() {
        finish();
    }


    @Override
    public void onBackPressed() {
        back();
    }





}
