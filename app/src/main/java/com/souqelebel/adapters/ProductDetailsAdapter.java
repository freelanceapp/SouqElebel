package com.souqelebel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_order_details.OrderDetailsActivity;
import com.souqelebel.databinding.ProductDetailsRowBinding;
import com.souqelebel.models.OrderModel;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderModel.OrdersDetails> list;
    private Context context;
    private LayoutInflater inflater;

    public ProductDetailsAdapter(List<OrderModel.OrdersDetails> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ProductDetailsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_details_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        OrderModel.OrdersDetails model = list.get(position);

        myHolder.binding.setModel(model);
        myHolder.binding.simplarate.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                if (context instanceof OrderDetailsActivity) {
                    OrderDetailsActivity productDetailsActivity = (OrderDetailsActivity) context;
                    productDetailsActivity.makerate(list.get(myHolder.getLayoutPosition()).getProduct_id(), rating);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ProductDetailsRowBinding binding;

        public MyHolder(@NonNull ProductDetailsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
