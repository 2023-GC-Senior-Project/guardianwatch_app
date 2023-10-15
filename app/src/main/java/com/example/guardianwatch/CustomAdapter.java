package com.example.guardianwatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static ArrayList<ChildData> localDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, birthDateTextView, placeTextView;
        private ImageView profileImageView;
        private ImageView closeImageView;
        private TextView editTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            birthDateTextView = itemView.findViewById(R.id.birthDate);
            placeTextView = itemView.findViewById(R.id.place);
            profileImageView = itemView.findViewById(R.id.profile_image);
            closeImageView = itemView.findViewById(R.id.close);
            editTextView = itemView.findViewById(R.id.edit);
            // X 누를 시에 확인 메시지 후 아이 목록에서 삭제
            closeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("삭제 확인");
                        builder.setMessage("정말로 이 항목을 삭제하시겠습니까?");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                localDataSet.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                            }
                        });
                        builder.setNegativeButton("아니오", null);
                        builder.show();
                    }
                }
            });
            //편집 누를 시에 아이 편집 페이지로 이동
            editTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EditProfileActivity.class);
                    context.startActivity(intent);
                }
            });

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
