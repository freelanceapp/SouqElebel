package com.souqelebel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.souqelebel.R;
import com.souqelebel.databinding.ColorsRowBinding;
import com.souqelebel.databinding.MemoryRowBinding;
import com.souqelebel.models.SingleProductDataModel;

import java.util.List;

public class ProductColorsAdapter extends RecyclerView.Adapter<ProductColorsAdapter.MyHolder> {

    private List<SingleProductDataModel.Sizes.Colors> colorsList;
    private Context context;
    public int i=0;

    public ProductColorsAdapter(List<SingleProductDataModel.Sizes.Colors> colorsList, Context context) {
        this.colorsList = colorsList;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ColorsRowBinding imageRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.colors_row, parent, false);
        return new MyHolder(imageRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        holder.binding.setModel(colorsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               i=holder.getLayoutPosition();
               notifyDataSetChanged();
            }
        });
     /*   if(i==position){
            if (context instanceof ProductDetailsActivity) {
                ProductDetailsActivity productDetailsActivity = (ProductDetailsActivity) context;
                 productDetailsActivity.setselectcolors(colorsList.get(i));
            }
            holder.binding.cardView.setBackground(context.getResources().getDrawable(R.drawable.small_rounded_btn_memory_selected));
        }
        else {
            holder.binding.cardView.setBackground(null);

        }*/
    }

    @Override
    public int getItemCount() {
        return colorsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ColorsRowBinding binding;

        public MyHolder(ColorsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
