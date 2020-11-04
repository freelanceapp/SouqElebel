package com.souqelebel.activities_fragments.activity_home.fragments.fragment_order;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.souqelebel.R;
import com.souqelebel.activities_fragments.activity_home.HomeActivity;
import com.souqelebel.activities_fragments.activity_order_details.OrderDetailsActivity;
import com.souqelebel.adapters.OrderAdapter;
import com.souqelebel.databinding.FragmentCurrentPreviousOrderBinding;
import com.souqelebel.models.OrderDataModel;
import com.souqelebel.models.OrderModel;
import com.souqelebel.models.UserModel;
import com.souqelebel.preferences.Preferences;
import com.souqelebel.remote.Api;
import com.souqelebel.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Current_Order extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private HomeActivity activity;
    private FragmentCurrentPreviousOrderBinding binding;
    private LinearLayoutManager manager;
    private OrderAdapter adapter;
    private List<OrderModel> orderModelList;
    private Preferences preferences;
    private UserModel userModel;
    private int current_page = 1;
    private boolean isLoading = false;

    public static Fragment_Current_Order newInstance() {
        return new Fragment_Current_Order();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_previous_order, container, false);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        orderModelList = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        manager = new LinearLayoutManager(activity);
        binding.recView.setLayoutManager(manager);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new OrderAdapter(activity, orderModelList, this);
        binding.recView.setAdapter(adapter);
        binding.swipeToRefresh.setOnRefreshListener(this);
        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int total_item = binding.recView.getAdapter().getItemCount();
                    int last_visible_item = ((LinearLayoutManager) binding.recView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                    if (total_item >= 20 && (total_item - last_visible_item) == 5 && !isLoading) {

                        isLoading = true;
                        int page = current_page + 1;
                        orderModelList.add(null);
                        adapter.notifyItemInserted(orderModelList.size() - 1);

                        loadMore(page);
                    }
                }
            }
        });
        getOrders();


    }

    private void getOrders() {
        try {
            current_page = 1;
            Api.getService(Tags.base_url)
                    .getOrders(userModel.getUser().getToken(), "current", "on", current_page, 20)
                    .enqueue(new Callback<OrderDataModel>() {
                        @Override
                        public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                            binding.progBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                                orderModelList.clear();
                                orderModelList.addAll(response.body().getData());
                                Log.e("mmmmmmm",orderModelList.size()+"");

                                if (orderModelList.size() > 0) {

                                    adapter.notifyDataSetChanged();

                                    binding.tvNoOrder.setVisibility(View.GONE);
                                } else {
                                    binding.tvNoOrder.setVisibility(View.VISIBLE);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderDataModel> call, Throwable t) {
                            try {
                                binding.progBar.setVisibility(View.GONE);

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }

    private void loadMore(int page) {
        try {

            Api.getService(Tags.base_url)
                    .getOrders(userModel.getUser().getToken(), "current", "on", page, 20)
                    .enqueue(new Callback<OrderDataModel>() {
                        @Override
                        public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                            isLoading = false;
                            orderModelList.remove(orderModelList.size() - 1);
                            adapter.notifyItemRemoved(orderModelList.size() - 1);


                            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {

                                int oldPos = orderModelList.size() - 1;

                                orderModelList.addAll(response.body().getData());

                                if (response.body().getData().size() > 0) {
                                    current_page = response.body().getCurrent_page();
                                    adapter.notifyItemRangeChanged(oldPos, orderModelList.size() - 1);

                                }
                            } else {
                                if (response.code() == 500) {
                                    Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                    try {

                                        Log.e("error", response.code() + "_" + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderDataModel> call, Throwable t) {
                            try {

                                if (orderModelList.get(orderModelList.size() - 1) == null) {
                                    isLoading = false;
                                    orderModelList.remove(orderModelList.size() - 1);
                                    adapter.notifyItemRemoved(orderModelList.size() - 1);

                                }

                                if (t.getMessage() != null) {
                                    Log.e("error", t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }

    public void setItemData(OrderModel model) {
        Intent intent = new Intent(activity, OrderDetailsActivity.class);
        intent.putExtra("data", model);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.swipeToRefresh.setRefreshing(false);
                getOrders();
            }
        }, 2000);
    }
}
