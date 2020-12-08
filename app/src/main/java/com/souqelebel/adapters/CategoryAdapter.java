package com.souqelebel.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_home.HomeActivity;
import com.souqelebel.activities_fragments.activity_home.fragments.Fragment_Main;
import com.souqelebel.databinding.MainCategoryRowBinding;
import com.souqelebel.models.MainCategoryModel;
import com.souqelebel.tags.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MainCategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private int selectedPos = 0;
    private int oldPos = 0;
    private HomeActivity activity;

    public CategoryAdapter(List<MainCategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (HomeActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MainCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.main_category_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        MainCategoryModel mainCategoryModel = list.get(position);
        if (mainCategoryModel.isSelected()){
            myHolder.binding.image.setBorderColor(ContextCompat.getColor(context,R.color.colorPrimary));
            myHolder.binding.tvName.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
        }else {
            myHolder.binding.image.setBorderColor(ContextCompat.getColor(context,R.color.white));
            myHolder.binding.tvName.setTextColor(ContextCompat.getColor(context,R.color.gray4));

        }
        myHolder.itemView.setOnClickListener(view -> {
            MainCategoryModel model = list.get(myHolder.getAdapterPosition());
            activity.setItemData(model);

            MainCategoryModel model1 = list.get(oldPos);
            model1.setSelected(false);
            list.set(oldPos,model1);
            notifyItemChanged(oldPos);



            selectedPos = myHolder.getAdapterPosition();
            MainCategoryModel model2 = list.get(selectedPos);
            model2.setSelected(true);
            list.set(selectedPos,model2);
            notifyItemChanged(selectedPos);
            oldPos = selectedPos;

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public MainCategoryRowBinding binding;

        public MyHolder(@NonNull MainCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
