package com.example.guardianwatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

    private ArrayList<ChildData> localDataSet;
    private int selectedPosition = -1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, birthDateTextView, placeTextView;
        private ImageView profileImageView;
        private ImageView closeImageView;
        private TextView editTextView;
        private  ImageView crownImageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            birthDateTextView = itemView.findViewById(R.id.birthDate);
            placeTextView = itemView.findViewById(R.id.place);
            profileImageView = itemView.findViewById(R.id.profile_image);
            closeImageView = itemView.findViewById(R.id.close);
            editTextView = itemView.findViewById(R.id.edit);
            crownImageView = itemView.findViewById(R.id.crownIcon);
            //아이 컴포넌트 누를때
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedPosition); // 이전 선택된 항목의 하이라이트를 제거
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition); // 현재 선택된 항목에 하이라이트 추가
                }
            });

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
                    ChildData clickedChild = localDataSet.get(getAdapterPosition());
                    Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
                    intent.putExtra("childName", clickedChild.getName());
                    view.getContext().startActivity(intent);
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
        holder.birthDateTextView.setText(data.getYear()+"."+data.getMonth()+"."+data.getDay());
        holder.placeTextView.setText(data.getPlace());

        if (data.getImageUri() != null && !data.getImageUri().isEmpty()) {
            Uri imageUri = Uri.parse(data.getImageUri());
            holder.profileImageView.setImageURI(imageUri);
        } else {
            // imageUri가 null이거나 비어 있는 경우, 기본 이미지를 설정
             holder.profileImageView.setImageResource(R.drawable.default_profile);

        }

        if (position == selectedPosition) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }
        if (data.getRepresent() == 1) {
            holder.crownImageView.setVisibility(View.VISIBLE);
        } else {
            holder.crownImageView.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
