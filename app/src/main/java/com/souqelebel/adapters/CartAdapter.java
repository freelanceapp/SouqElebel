package com.souqelebel.adapters;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.souqelebel.activities_fragments.activity_cart.CartActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.souqelebel.R;
import com.souqelebel.models.ItemCartModel;
import com.souqelebel.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Cart_Holder> {
    private List<ItemCartModel> itemCartModelList;
    private Context context;

    public CartAdapter(List<ItemCartModel> itemCartModelList, Context context) {
        this.itemCartModelList = itemCartModelList;
        this.context = context;
    }

    @Override
    public Cart_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_row, viewGroup, false);
        return new Cart_Holder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final Cart_Holder holder, final int i) {
        ItemCartModel model = itemCartModelList.get(i);
        holder.BindData(model);

        holder.imgIncrease.setOnClickListener(v -> {
                    CartActivity cartActivity = (CartActivity) context;
                    ItemCartModel model2 = itemCartModelList.get(holder.getAdapterPosition());
                    int count = model2.getAmount() + 1;
                    holder.tvAmount.setText(String.valueOf(count));
                    model2.setAmount(count);
                    itemCartModelList.set(holder.getAdapterPosition(), model2);
                    cartActivity.increase_decrease(model2, holder.getAdapterPosition());
                    notifyItemChanged(holder.getAdapterPosition());
                }

        );
        holder.imgDecrease.setOnClickListener(v -> {
                    CartActivity cartActivity = (CartActivity) context;

                    ItemCartModel model2 = itemCartModelList.get(holder.getAdapterPosition());
                    int count = model2.getAmount();
                    if (count > 1) {
                        count = count - 1;
                        model2.setAmount(count);
                        holder.tvAmount.setText(String.valueOf(count));
                        itemCartModelList.set(holder.getAdapterPosition(), model2);
                        cartActivity.increase_decrease(model2, holder.getAdapterPosition());
                        notifyItemChanged(holder.getAdapterPosition());
                    }

                }

        );


    }

    @Override
    public int getItemCount() {
        return itemCartModelList.size();
    }

    public class Cart_Holder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvCost, tvAmount;
        private RoundedImageView image;
        private ImageView imgIncrease, imgDecrease;
        public ConstraintLayout consBackground, consForeground;
        public LinearLayout llLeft, llRight;


        public Cart_Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCost = itemView.findViewById(R.id.tvCost);
            imgIncrease = itemView.findViewById(R.id.imgIncrease);
            imgDecrease = itemView.findViewById(R.id.imgDecrease);
            tvAmount = itemView.findViewById(R.id.tvAmount);

            consBackground = itemView.findViewById(R.id.consBackground);
            consForeground = itemView.findViewById(R.id.consForeground);
            llLeft = itemView.findViewById(R.id.llLeft);
            llRight = itemView.findViewById(R.id.llRight);


        }

        public void BindData(ItemCartModel itemCartModel) {

            tvTitle.setText(itemCartModel.getTitle());
            tvCost.setText(String.format(Locale.ENGLISH, "%s %s", String.valueOf(itemCartModel.getPrice()), context.getString(R.string.sar)));
            tvAmount.setText(String.valueOf(itemCartModel.getAmount()));
            Picasso.get().load(Uri.parse(Tags.IMAGE_URL + itemCartModel.getSub_image())).fit().into(image);
        }

    }

}