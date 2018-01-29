package com.woxapp.maptest.ui.catalog;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.google.maps.model.DirectionsResult;
import com.woxapp.maptest.R;
import com.woxapp.maptest.databinding.ActivityCatalogBinding;

import java.util.List;


public class CatalogActivity extends AppCompatActivity implements CatalogContract.View {

    ActivityCatalogBinding binding;
    private CatalogContract.Presenter presenter;
    private CatalogAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, CatalogActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_catalog);

        presenter = new CatalogPresenter(this);

        initList();

        presenter.loadDirections();

    }

    private void initList() {
        adapter = new CatalogAdapter();
        binding.routeList.setLayoutManager(new LinearLayoutManager(this));
        binding.routeList.setAdapter(adapter);
        binding.routeList.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onLoadDirectionsSuccess(List<DirectionsResult> directions) {
        adapter.updateDirections(directions);
    }
}
