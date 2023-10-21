package com.example.guardianwatch;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<SliderItem> sliderItems;

    public SliderAdapter(List<SliderItem> sliderItems) {
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_component, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderItem sliderItem = sliderItems.get(position);

//// 이미지 로딩. Glide 라이브러리를 사용한 예시입니다. 이를 위해 Glide를 프로젝트에 추가해야 합니다.
//        Glide.with(holder.profileImage.getContext())
//                .load(sliderItem.getImageUrl())
//                .into(holder.profileImage);

        if("김지안".equals(sliderItem.getText()))
            holder.profileImage.setImageResource(R.drawable.lee_image);
        else if("김서준".equals(sliderItem.getText()))
            holder.profileImage.setImageResource(R.drawable.kim_image);
        else if("최지우".equals(sliderItem.getText()))
            holder.profileImage.setImageResource(R.drawable.choi_image);
        else if("홍길순".equals(sliderItem.getText()))
            holder.profileImage.setImageResource(R.drawable.hongs_image);
        else if("홍길동".equals(sliderItem.getText()))
            holder.profileImage.setImageResource(R.drawable.hongd_image);
        else {
            // imageUri가 null이거나 비어 있는 경우, 기본 이미지를 설정
            holder.profileImage.setImageResource(R.drawable.default_profile);
        }
        // 텍스트 설정
        holder.childName.setText(sliderItem.getText());

        // 대표 아이 체크 및 왕관 아이콘 처리
        if (sliderItem.getRepresent() == 1) {
            holder.crownImageView.setVisibility(View.VISIBLE);
        } else {
            holder.crownImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView profileImage;
        TextView childName;
        ImageView crownImageView;  // 왕관 아이콘 변수 추가


        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            childName = itemView.findViewById(R.id.childName);
            crownImageView = itemView.findViewById(R.id.crownIcon);  // 왕관 아이콘 초기화

        }
    }
}
