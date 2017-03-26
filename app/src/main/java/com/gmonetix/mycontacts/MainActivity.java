package com.gmonetix.mycontacts;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> contacts;
    private ListView listView;
    private ContactAdapter adapter;
    private ProgressBar progressBar;

    private Uri QUERY_URI = ContactsContract.Contacts.CONTENT_URI;
    private String CONTACT_ID = ContactsContract.Contacts._ID;
    private String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private String PHOTO_URI = ContactsContract.Contacts.PHOTO_URI;
    private String PHOTO_THUMBNAIL_URI = ContactsContract.Contacts.PHOTO_THUMBNAIL_URI;
    private Uri EMAIL_CONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    private String EMAIL_CONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
    private String EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA;
    private String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private Uri PHONE_CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private String STARRED_CONTACT = ContactsContract.Contacts.STARRED;
    private ContentResolver contentResolver;

    private final static String INTENT_NAME = "name";
    private final static String INTENT_EMAIL = "email";
    private final static String INTENT_PHONE_NUMBER = "number";
    private final static String INTENT_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        contacts = new ArrayList<Contact>();
        contentResolver = getContentResolver();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra(INTENT_NAME,contacts.get(position).getName());
                intent.putExtra(INTENT_PHONE_NUMBER,contacts.get(position).getPhone());
                if (contacts.get(position).getEmail()!= null)
                intent.putExtra(INTENT_EMAIL,contacts.get(position).getEmail());
                if (contacts.get(position).getPhotoUri()!= null)
                intent.putExtra(INTENT_IMAGE,contacts.get(position).getPhotoUri());
                startActivity(intent);
            }
        });

        new Background().execute();

        // The items we want to show


    }

    private void getEmail(String contactId, Contact contact) {
        Cursor emailCursor = contentResolver.query(EMAIL_CONTENT_URI, null, EMAIL_CONTACT_ID + " = ?", new String[]{contactId}, null);
        while (emailCursor.moveToNext()) {
            String email = emailCursor.getString(emailCursor.getColumnIndex(EMAIL_DATA));
            if (!TextUtils.isEmpty(email)) {
                contact.setEmail(email);
            }
        }
        emailCursor.close();
    }

    private void getPhone(Cursor cursor, String contactId, Contact contact) {
        int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
        if (hasPhoneNumber > 0) {
            Cursor phoneCursor = contentResolver.query(PHONE_CONTENT_URI, null, PHONE_CONTACT_ID + " = ?", new String[]{contactId}, null);
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(PHONE_NUMBER));
                contact.setPhone(phoneNumber);
            }
            phoneCursor.close();
        }
    }

    public class Background extends AsyncTask<Void,Void,List<Contact>>{

        @Override
        protected void onPostExecute(List<Contact> list) {
            super.onPostExecute(list);
            adapter = new ContactAdapter(MainActivity.this,list);
            listView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected List<Contact> doInBackground(Void... params) {

            String[] projection = new String[]{CONTACT_ID, DISPLAY_NAME,HAS_PHONE_NUMBER,PHOTO_URI,PHOTO_THUMBNAIL_URI};
            Cursor cursor = contentResolver.query(QUERY_URI, projection, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    contact.setId(id);
                    contact.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    contact.setPhotoUri(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)));
                    contact.setPhotoThumbnailUri(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)));
                    getEmail(String.valueOf(id),contact);
                    getPhone(cursor,String.valueOf(id),contact);
                    contacts.add(contact);
                } while (cursor.moveToNext());

            }
            cursor.close();
            return contacts;
        }
    }

}
