package com.souqelebel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_home.fragments.Fragment_Favorite;
import com.souqelebel.activities_fragments.activity_home.fragments.Fragment_Main;
import com.souqelebel.databinding.ProductFavoriteRowBinding;
import com.souqelebel.databinding.ProductRowBinding;
import com.souqelebel.models.FavoriteModel;
import com.souqelebel.models.ProductModel;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FavoriteModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public FavoriteAdapter(List<FavoriteModel> list, Context context, Fragment fragment) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductFavoriteRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_favorite_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.binding.setModel(list.get(position).getProduct());

            myHolder.itemView.setOnClickListener(view -> {
                if (fragment instanceof Fragment_Favorite) {

                    Fragment_Favorite fragment_favorite = (Fragment_Favorite) fragment;
                    fragment_favorite.disLike(list.get(myHolder.getAdapterPosition()),myHolder.getAdapterPosition());
                }
            });



        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ProductFavoriteRowBinding binding;

        public MyHolder(@NonNull ProductFavoriteRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }



}