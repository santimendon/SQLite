package com.android.smendon.sqlite.adapters;

import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.smendon.sqlite.R;
import com.android.smendon.sqlite.models.MovieRecord;

import java.util.List;

/**
 * Created by Sanket Mendon on 27-06-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<MovieRecord> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txtGenre, txtLanguage, txtRuntime;
        public RatingBar rBar;

        public ViewHolder(View itemHolder) {
            super(itemHolder);
            txtTitle = (TextView) itemHolder.findViewById(R.id.txt_title);
            txtGenre = (TextView) itemHolder.findViewById(R.id.txt_genre);
            txtLanguage = (TextView) itemHolder.findViewById(R.id.txt_language);
            txtRuntime = (TextView) itemHolder.findViewById(R.id.txt_runtime);
            rBar = (RatingBar) itemHolder.findViewById(R.id.rbar_rating);
        }
    }

    public MovieAdapter(List<MovieRecord> dataset) {
        this.mDataset = dataset;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(mDataset.get(position).getTitle());
        holder.txtGenre.setText(mDataset.get(position).getGenre());
        holder.txtLanguage.setText(mDataset.get(position).getLanguage());
        holder.txtRuntime.setText(String.valueOf(mDataset.get(position).getRuntime()) + " mins");
        holder.rBar.setRating(Float.parseFloat(mDataset.get(position).getRating()));
    }
}
