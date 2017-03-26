package com.gmonetix.mycontacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView nameTv, phoneTv, emailTv;
    private Button Delete;

    String name, phone, email, imageUri, id;
    private final static String INTENT_NAME = "name";
    private final static String INTENT_EMAIL = "email";
    private final static String INTENT_PHONE_NUMBER = "number";
    private final static String INTENT_IMAGE = "image";
    private final static String INTENT_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        profileImage = (ImageView) findViewById(R.id.image_view_detials_activity);
        nameTv = (TextView) findViewById(R.id.name_details);
        phoneTv = (TextView) findViewById(R.id.phone_details);
        emailTv = (TextView) findViewById(R.id.email_details);
        Delete = (Button) findViewById(R.id.delete_btn_details_activity);

        if (getIntent().hasExtra(INTENT_NAME)){
            name = getIntent().getExtras().getString(INTENT_NAME);
            phone = getIntent().getExtras().getString(INTENT_PHONE_NUMBER);
            if (getIntent().hasExtra(INTENT_EMAIL))
            email = getIntent().getExtras().getString(INTENT_EMAIL);
            else email = "No email";
            if (getIntent().hasExtra(INTENT_IMAGE))
            imageUri = getIntent().getExtras().getString(INTENT_IMAGE);
            else profileImage.setImageDrawable(getResources().getDrawable(R.drawable.sample));
            id = getIntent().getExtras().getString(INTENT_ID);
        }

        nameTv.setText(name);
        phoneTv.setText(phone);
        emailTv.setText(email);
        if (imageUri != null)
        profileImage.setImageURI(Uri.parse(imageUri));

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this,R.style.Theme_AppCompat_Light);
                builder.setTitle("WARNING");
                builder.setMessage("Are you sure you want to delete ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteContact(id);
                        Toast.makeText(getApplicationContext(),"All Contacts deleted",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        startActivity(new Intent(DetailsActivity.this,MainActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    private void deleteContact(String id) {
        getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,ContactsContract.Contacts._ID +"=?",new String[] {id});
    }

}
