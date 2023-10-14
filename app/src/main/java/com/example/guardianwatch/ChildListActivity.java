package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                                                     startActivity(intent);
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
        ArrayList<ChildData> childDataList = new ArrayList<>();
        childDataList.add(new ChildData("김서준", "2018. 7. 28.", "가천 어린이집",R.drawable.kim_image));
        childDataList.add(new ChildData("이지안", "2019. 2. 18.", "가천 어린이집",R.drawable.lee_image));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomAdapter customAdapter = new CustomAdapter(childDataList);
        recyclerView.setAdapter(customAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));
    }


}