package com.crimekiller.safetrip.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crimekiller.safetrip.R;

import java.util.ArrayList;

/**
 * Created by shuangliang on 4/3/16.
 */
public class SimplePostAdapter extends ArrayAdapter <Post>{

    public SimplePostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Post post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_simple_post, parent, false);
        }
        // Lookup view for data population
        TextView tvDate = (TextView) convertView.findViewById(R.id.Simple_Post_Date);
        TextView tvDestination = (TextView) convertView.findViewById(R.id.Simple_Post_Destination);
        // Populate the data into the template view using the data object
        tvDate.setText(post.getDate());
        tvDestination.setText(post.getDestination());
        // Return the completed view to render on screen
        return convertView;
    }

}
