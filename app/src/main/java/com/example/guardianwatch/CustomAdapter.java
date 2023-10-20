package com.example.guardianwatch;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<ChildData> localDataSet;
    private int selectedPosition = -1;
    public int getSelectedPosition() {
        return selectedPosition;
    }
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
//                                localDataSet.remove(getAdapterPosition());
//                                notifyItemRemoved(getAdapterPosition());
                                // 해당 아이의 정보를 서버에서 삭제하는 코드 추가
                                ChildData childToDelete = localDataSet.get(getAdapterPosition());
                                deleteChild(childToDelete, view.getContext(), getAdapterPosition());
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

    private void deleteChild(ChildData child, Context context, int position) {
        // Retrofit을 사용하여 API 호출
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inclab3.gachon.ac.kr:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        Call<ResponseBody> call = service.deleteChild(UserData.getInstance().getUserId(), child.getName());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 아이 정보 삭제 성공
                    localDataSet.remove(position);
                    notifyItemRemoved(position);

                    // 로그 추가: 아이 정보 삭제 성공
                    Log.d("delete", "Child deleted successfully with name: " + child.getName());
                } else {
                    Toast.makeText(context, "아이를 삭제하는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    // 로그 추가: 아이 정보 삭제 실패 (HTTP 응답 에러)
                    String errorMsg = "";
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch (IOException e) {
                            errorMsg = "Failed to extract error message from response body.";
                            Log.e("delete", errorMsg, e);
                        }
                    }

                    Log.e("delete", "Failed to delete child. Server responded with status: " + response.code() + ". Message: " + errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                // 로그 추가: 아이 정보 삭제 실패 (네트워크 오류)
                Log.e("delete", "Network error while deleting child: " + t.getMessage());
            }
        });
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
