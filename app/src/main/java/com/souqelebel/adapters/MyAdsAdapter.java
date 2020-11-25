package com.souqelebel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_home.fragments.Fragment_Favorite;
import com.souqelebel.activities_fragments.activity_product_details.ProductDetailsActivity;
import com.souqelebel.activity_my_ads.MyAdsActivity;
import com.souqelebel.databinding.MyAdsRowBinding;
import com.souqelebel.databinding.ProductFavoriteRowBinding;
import com.souqelebel.models.FavoriteModel;
import com.souqelebel.models.ProductModel;

import java.util.List;

public class MyAdsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private LayoutInflater inflater;
    private MyAdsActivity activity;
    public MyAdsAdapter(List<ProductModel> list, MyAdsActivity activity) {
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyAdsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.my_ads_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.binding.setModel(list.get(position));

            myHolder.binding.imageDelete.setOnClickListener(view -> {

                    activity.deleteAds(list.get(myHolder.getAdapterPosition()),myHolder.getAdapterPosition());

            });

            myHolder.itemView.setOnClickListener(view -> {

                    activity.setProductItemData(list.get(myHolder.getAdapterPosition()));

            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public MyAdsRowBinding binding;

        public MyHolder(@NonNull MyAdsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }



}
