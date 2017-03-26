package com.gmonetix.mycontacts;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends BaseAdapter{

    Context context;
    private List<Contact> list;

    public ContactAdapter(Context context, List<Contact> list){
        this.context = context;
        this.list  =list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.sample_row, null);

            holder.Name = (TextView) convertView.findViewById(R.id.name);
            holder.Phone = (TextView) convertView.findViewById(R.id.phone);
            holder.profilePic = (ImageView) convertView.findViewById(R.id.image_view_sample);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.Name.setText(list.get(position).getName());
        holder.Phone.setText(list.get(position).getPhone());
        if (list.get(position).getPhotoThumbnailUri() != null)
        holder.profilePic.setImageURI(Uri.parse(list.get(position).getPhotoThumbnailUri()));
        else holder.profilePic.setImageDrawable(context.getResources().getDrawable(R.drawable.sample));

        return convertView;
    }
    private class ViewHolder{
        private TextView Name, Phone;
        private ImageView profilePic;
    }
}
