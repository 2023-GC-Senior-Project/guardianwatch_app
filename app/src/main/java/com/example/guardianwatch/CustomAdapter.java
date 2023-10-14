package com.example.guardianwatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<ChildData> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, birthDateTextView, placeTextView;
        private ImageView profileImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            birthDateTextView = itemView.findViewById(R.id.birthDate);
            placeTextView = itemView.findViewById(R.id.place);
            profileImageView = itemView.findViewById(R.id.profile_image);  // 추가

        }
    }

    public CustomAdapter(ArrayList<ChildData> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_child_item, parent, false);
        return new CustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        ChildData data = localDataSet.get(position);
        holder.nameTextView.setText(data.getName());
        holder.birthDateTextView.setText(data.getBirthDate());
        holder.placeTextView.setText(data.getPlace());
        holder.profileImageView.setImageResource(data.getImageResId());  // 사진 설정

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
