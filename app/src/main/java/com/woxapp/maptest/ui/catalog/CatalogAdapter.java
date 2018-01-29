package com.woxapp.maptest.ui.catalog;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.maps.model.DirectionsResult;
import com.woxapp.maptest.databinding.RouteItemBinding;

import java.util.ArrayList;
import java.util.List;


public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    private List<DirectionsResult> directions = new ArrayList<>();

    @Override
    public CatalogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RouteItemBinding binding = RouteItemBinding.inflate(inflater, parent, false);
        return new CatalogViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(CatalogViewHolder holder, int position) {
        DirectionsResult result = directions.get(position);

        holder.binding.startAddress.setText(result.routes[0].legs[0].startAddress);
        holder.binding.endAddress.setText(result.routes[0].legs[result.routes[0].legs.length - 1].endAddress);
    }

    @Override
    public int getItemCount() {
        return directions.size();
    }

    public void updateDirections(List<DirectionsResult> directions) {
        this.directions.clear();
        this.directions.addAll(directions);
        notifyDataSetChanged();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {
        RouteItemBinding binding;

        public CatalogViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
