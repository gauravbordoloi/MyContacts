package com.gmonetix.mycontacts;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView nameTv, phoneTv, emailTv;

    String name, phone, email, imageUri;
    private final static String INTENT_NAME = "name";
    private final static String INTENT_EMAIL = "email";
    private final static String INTENT_PHONE_NUMBER = "number";
    private final static String INTENT_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        profileImage = (ImageView) findViewById(R.id.image_view_detials_activity);
        nameTv = (TextView) findViewById(R.id.name_details);
        phoneTv = (TextView) findViewById(R.id.phone_details);
        emailTv = (TextView) findViewById(R.id.email_details);

        if (getIntent().hasExtra(INTENT_NAME)){
            name = getIntent().getExtras().getString(INTENT_NAME);
            phone = getIntent().getExtras().getString(INTENT_PHONE_NUMBER);
            if (getIntent().hasExtra(INTENT_EMAIL))
            email = getIntent().getExtras().getString(INTENT_EMAIL);
            else email = "No email";
            if (getIntent().hasExtra(INTENT_IMAGE))
            imageUri = getIntent().getExtras().getString(INTENT_IMAGE);
            else imageUri = null;
        }

        nameTv.setText(name);
        phoneTv.setText(phone);
        emailTv.setText(email);
        if (imageUri != null)
        profileImage.setImageURI(Uri.parse(imageUri));

    }

}
