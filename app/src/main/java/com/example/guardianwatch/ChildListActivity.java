package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChildListActivity extends AppCompatActivity {

    private static final int CHILD_REGISTER_REQUEST_CODE = 101;

    TextView childRegisterText;
    ImageView backArrow;

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;

            // 첫 번째 아이템 위에 간격을 추가하려면
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = verticalSpaceHeight;
            }
        }
    }
    static ArrayList<ChildData> childDataList = new ArrayList<>();
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);

        //아이등록 누를시에 아이등록 페이지로 이동
        childRegisterText=findViewById(R.id.childRegister);
        childRegisterText.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Intent intent = new Intent(getApplicationContext(), ChildRegisterActivity.class);
                                                     startActivityForResult(intent, CHILD_REGISTER_REQUEST_CODE);
                                                 }
                                             });
        //뒤로가기 버튼 누를 시에 아이 리스트 페이지로 이동
        backArrow=findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String uriString1 = "android.resource://" + getPackageName() + "/" + R.drawable.lee_image;
        childDataList.add(new ChildData("이지안", "2019", "2", "18", "가천 어린이집", uriString1, 1));

        String uriString2 = "android.resource://" + getPackageName() + "/" + R.drawable.kim_image;

        childDataList.add(new ChildData("김서준", "2018","7","28", "가천 어린이집",uriString2,0));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        customAdapter = new CustomAdapter(childDataList);
        recyclerView.setAdapter(customAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHILD_REGISTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ChildData newChild = (ChildData) data.getSerializableExtra("newChild");
            childDataList.add(newChild);
            customAdapter.notifyDataSetChanged();
        }
        if(resultCode == Activity.RESULT_OK){
            ChildData editData = (ChildData) data.getSerializableExtra("editData");
            int position = data.getIntExtra("position",-1);
            if(position!=-1){
                // 편집된 내용을 ChildListActivity의 childDataList에 반영합니다.
                childDataList.set(position, editData);

                // RecyclerView를 새로고침합니다.
                customAdapter.notifyItemChanged(position);
            }
        }
    }


}