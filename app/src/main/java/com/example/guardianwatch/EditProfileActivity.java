package com.example.guardianwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


    }
}