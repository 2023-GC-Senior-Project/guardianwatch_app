package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditProfileActivity extends AppCompatActivity {

    ImageView backArrow;
    ImageView profile_image;
    EditText nameEditText;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGirl;
    RadioButton radioButtonBoy;
    EditText yearEditText;
    EditText monthEditText;
    EditText dayEditText;
    EditText placeEditText;
    Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backArrow=findViewById(R.id.backArrow);
        profile_image=findViewById(R.id.profile_image);
        nameEditText=findViewById(R.id.nameEditText);
        radioGroupGender=findViewById(R.id.radioGroupGender);
        radioButtonGirl=findViewById(R.id.radioButtonGirl);
        radioButtonBoy=findViewById(R.id.radioButtonBoy);
        yearEditText=findViewById(R.id.yearEditText);
        monthEditText=findViewById(R.id.monthEditText);
        dayEditText=findViewById(R.id.dayEditText);
        placeEditText=findViewById(R.id.placeEditText);
        editBtn=findViewById(R.id.editBtn);

        Intent intentData = getIntent();
        int position = intentData.getIntExtra("position",-1);
        ChildData editData = (ChildData) intentData.getSerializableExtra("editData");

        profile_image.setImageURI(Uri.parse(editData.getImageUri()));
        nameEditText.setText(editData.getName());
        int gender = editData.getGender();
        if(gender == 0){
            radioButtonBoy.setChecked(true);
            radioButtonGirl.setChecked(false);
        }
        else{
            radioButtonBoy.setChecked(false);
            radioButtonGirl.setChecked(true);
        }
        yearEditText.setText(editData.getYear());
        monthEditText.setText(editData.getMonth());
        dayEditText.setText(editData.getDay());
        placeEditText.setText(editData.getPlace());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUri = String.valueOf(profile_image.getDrawable());
                if(imageUri == null)
                    imageUri = "android.resource://" + getPackageName() + "/" + R.drawable.default_profile;
                String name = nameEditText.getText().toString();
                int gender = radioGroupGender.getCheckedRadioButtonId();
                String year = yearEditText.getText().toString();
                String month = monthEditText.getText().toString();
                String day = dayEditText.getText().toString();
                String place = placeEditText.getText().toString();

                ChildData editData = new ChildData(
                        name, year, month, day, place, imageUri, gender
                );

                Intent intent = new Intent();
                intent.putExtra("position",position);
                intent.putExtra("editData", editData);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}