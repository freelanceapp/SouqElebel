package com.souqelebel.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_home.fragments.Fragment_Favorite;
import com.souqelebel.databinding.LoadmoreRowBinding;
import com.souqelebel.databinding.OfferRowBinding;
import com.souqelebel.databinding.ProductRowBinding;
import com.souqelebel.models.NotificationDataModel;
import com.souqelebel.models.ProductModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int DATA_ROW =1;
    private final int LOAD_ROW =2;

    private final String lang;
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;

    public SearchAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == DATA_ROW){
            ProductRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_row, parent, false);
            return new MyHolder(binding);
        }else {
            LoadmoreRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.loadmore_row, parent, false);
            return new LoadMoreHolder(binding);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyHolder){
            MyHolder myHolder = (MyHolder) holder;
            myHolder.binding.setModel(list.get(position));
            myHolder.itemView.setOnClickListener(view -> {

            });


        }else {
            LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
            loadMoreHolder.binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            loadMoreHolder.binding.progBar.setIndeterminate(true);
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ProductRowBinding binding;

        public MyHolder(@NonNull ProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        public LoadmoreRowBinding binding;

        public LoadMoreHolder(@NonNull LoadmoreRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;



        }
    }


    @Override
    public int getItemViewType(int position) {
        ProductModel model = list.get(position);
        if (model ==null){
            return LOAD_ROW;
        }else {
            return DATA_ROW;
        }
    }

}
